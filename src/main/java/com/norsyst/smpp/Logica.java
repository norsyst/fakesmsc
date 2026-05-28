package com.norsyst.smpp;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import webvas.utiles.SeqLocalByHostname;
import webvas.utiles.StringsFormat;

public class Logica {
	Logger LOG = LogManager.getLogger(this.getClass());
	public SeqLocalByHostname localSequence;
	
	public Logica() {
		try {
            // Get the local host name
			String serverName = InetAddress.getLocalHost().getHostName();
			
			localSequence = SeqLocalByHostname.getSeqLocalByHostname(serverName);
			
			LOG.info("%s MinSecuenciaLocal:%s MaxSecuenciaLocal:%s".formatted(serverName, StringsFormat.getNumericPy(localSequence.getMinSeq()), StringsFormat.getNumericPy(localSequence.getMaxSeq())));

		} catch (UnknownHostException e) {
            LOG.error(e);
        }
	}
	
	public Integer verificaPassword(String systemId, String password) {
		Integer resp = 0;
		return resp;
	}	
}
