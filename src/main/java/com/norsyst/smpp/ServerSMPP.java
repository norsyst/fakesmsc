package com.norsyst.smpp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsmpp.session.SMPPServerSession;
import org.jsmpp.session.SMPPServerSessionListener;

import webvas.utiles.ConfigProp;

public class ServerSMPP {

	private Boolean keepReceiving = true;
	public SMPPServerSessionListener serverListener;
	public DicBindSessions bindSessions = new DicBindSessions();
	private final ExecutorService waitBindExecService = Executors.newFixedThreadPool(100);
	private final ExecutorService waitSessionClosedService = Executors.newSingleThreadExecutor();

	public void start() {

		try {

			serverListener = new SMPPServerSessionListener(Integer.parseInt((String) ConfigProp.prop.getProperty("app.smppserverport")));
			serverListener.setPduProcessorDegree(100);
			serverListener.setQueueCapacity(20000);
			serverListener.setInitiationTimer(3000);

			while (keepReceiving) {
				SMPPServerSession session = serverListener.accept();
				session.setEnquireLinkTimer(90000);
				session.setTransactionTimer(Integer.parseInt((String) ConfigProp.prop.getProperty("app.smpptransactimeout")));
				session.setQueueCapacity(2000);
				session.setMessageReceiverListener(new TaskServerReceiverProcessor());
				waitBindExecService.execute(new TaskServerWaitBind(session, 2000));
				waitSessionClosedService.execute(new TaskServerSessionClosed(session.getSessionId()));
			}

		} catch (Exception e) {
			if (!e.getMessage().contains("Socket closed")) {
				e.printStackTrace();
			}
		}

		try {
			waitBindExecService.shutdown();
			waitSessionClosedService.shutdown();
			bindSessions.clearAll();
		} catch (Exception e) {

		}
	}

	public void stop() {
		keepReceiving = false;
		try {
			try {
				waitBindExecService.shutdown();
				waitSessionClosedService.shutdown();
			} catch (Exception e) {

			}
			serverListener.close();
			bindSessions.clearAll();
			serverListener = null;

		} catch (Exception e) {

		}
	}

}
