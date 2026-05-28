package com.norsyst.smpp;

import java.util.Timer;
import java.util.TimerTask;

import org.jsmpp.extra.SessionState;

public class TaskServerSessionClosed implements Runnable {
	public String sessionid;
	private Timer timer = new Timer();
	private Boolean intentando = false;
	private Integer intentos = 0;

	public TaskServerSessionClosed(String sessionid) {
		this.sessionid = sessionid;
	}

	private void checkSession() {
		intentando = true;
		try {
			intentos++;
			BindSession bs = FakesmscApplication.serversmpp.bindSessions.search(this.sessionid);
			if (bs != null) {
				if (bs.session.getSessionState().equals(SessionState.CLOSED)) {

					FakesmscApplication.serversmpp.bindSessions.remove(sessionid);
					
					timer.cancel();
					timer = null;

				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		intentando = false;
	}

	@Override
	public void run() {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (!intentando) {
					checkSession();
				}
			}
		}, (500), (500));
	}
}
