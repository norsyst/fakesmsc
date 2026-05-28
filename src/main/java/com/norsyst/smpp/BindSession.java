package com.norsyst.smpp;

import java.time.LocalDateTime;

import org.jsmpp.session.SMPPServerSession;

public class BindSession {
	public String sessionid;
	public String modo; // BIND_TX, BIND_RX, BIND_TRX, REST
	public String systemid;
	public String addressrange;
	public SMPPServerSession session;
	public LocalDateTime fecha;
}
