package com.norsyst.smpp;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsmpp.PDUStringException;
import org.jsmpp.SMPPConstant;
import org.jsmpp.session.BindRequest;
import org.jsmpp.session.SMPPServerSession;

public class TaskServerWaitBind implements Runnable {
	Logger LOG = LogManager.getLogger(this.getClass());
	private final SMPPServerSession serverSession;
	private final long timeout;

	public TaskServerWaitBind(SMPPServerSession serverSession, long timeout) {
		this.serverSession = serverSession;
		this.timeout = timeout;
	}

	@Override
	public void run() {
		try {
			Integer bindStatus = -1;
			BindRequest bindRequest = serverSession.waitForBind(timeout);

			try {
				bindStatus = FakesmscApplication.logica.verificaPassword(bindRequest.getSystemId(),
						bindRequest.getPassword());

				if (bindStatus == 0) {
					BindSession bs = new BindSession();
					bs.sessionid = serverSession.getSessionId();
					bs.modo = bindRequest.getBindType().name();
					bs.systemid = bindRequest.getSystemId().toLowerCase();
					bs.session = serverSession;
					
					FakesmscApplication.serversmpp.bindSessions.add(bs.sessionid, bs);

					bindRequest.accept("FakeSmsc");
				} else {
					bindRequest.reject(bindStatus);
				}


			} catch (PDUStringException e) {
				bindStatus = SMPPConstant.STAT_ESME_RBINDFAIL;
				bindRequest.reject(bindStatus);
			}

			LOG.info(
					"Bind modo:%s SystemId:%s Password:%s AddressRange:%s IP:%s sessionId:%s Status:%s".formatted(
							bindRequest.getBindType().name(), bindRequest.getSystemId(), bindRequest.getPassword(),
							bindRequest.getAddressRange(), serverSession.getInetAddress().getHostAddress(),
							serverSession.getSessionId(), bindStatus)
					);

			
		} catch (IllegalStateException e) {
			LOG.error(e.getMessage());
		} catch (TimeoutException e) {
			LOG.error(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}

}
