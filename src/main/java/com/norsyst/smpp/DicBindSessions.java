package com.norsyst.smpp;

import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.jsmpp.extra.SessionState;

public class DicBindSessions {
	public TreeMap<String, BindSession> bindSessionTreeMap = new TreeMap<String, BindSession>();
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final Lock wl = readWriteLock.writeLock();
	private final Lock rl = readWriteLock.readLock();

	public void add(String key, BindSession newSP) {
		try {
			wl.lock();
			bindSessionTreeMap.putIfAbsent(key, newSP);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			wl.unlock();
		}
	}

	public void remove(String key) {
		try {
			wl.lock();
			if (bindSessionTreeMap.remove(key) != null) {
				System.out.println("SMPPServerSession Removed " + key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			wl.unlock();
		}
	}

	public BindSession search(String key) {
		BindSession resp = null;
		try {
			rl.lock();
			resp = bindSessionTreeMap.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rl.unlock();
		}
		return resp;
	}

	public BindSession seachBySystemIdRX(String SystemId) {
		BindSession resp = null;
		try {
			rl.lock();
			
			Optional<BindSession> r = bindSessionTreeMap.values().stream()
					.filter(b -> (
							b.session.getSessionState().equals(SessionState.BOUND_RX)
							|| 
							b.session.getSessionState().equals(SessionState.BOUND_TRX)
							)
							&&
							b.systemid.equals(SystemId.toLowerCase())
							)
					.findAny();
			
			if (r != null) {
				resp = r.get();
			}
			

		} catch (Exception e) {

		} finally {
			rl.unlock();
		}
		return resp;

	}
	
	public BindSession seachBySessionId(String SessionId) {
		BindSession resp = null;
		try {
			rl.lock();
			
			Optional<BindSession> r = bindSessionTreeMap.values().stream()
					.filter(b -> (
							b.sessionid.equals(SessionId)
							))
					.findAny();
			
			if (r != null) {
				resp = r.get();
			}
			

		} catch (Exception e) {

		} finally {
			rl.unlock();
		}
		return resp;

	}

	public BindSession seachByAddressRange(String Address) {
		BindSession resp = null;
		try {
			rl.lock();
			
			Optional<BindSession> r = bindSessionTreeMap.values().stream()
					.filter(b -> (
							b.session.getSessionState().equals(SessionState.BOUND_RX)
							|| 
							b.session.getSessionState().equals(SessionState.BOUND_TRX)
							)
							&&
							Address.matches(b.addressrange))
							.findAny();
			
			if (r != null) {
				resp = r.get();
			}
			
		} catch (Exception e) {

		} finally {
			rl.unlock();
		}
		return resp;

	}

	public void clearAll() {
		try {
			wl.lock();
			bindSessionTreeMap.clear();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			wl.unlock();
		}
	}
}
