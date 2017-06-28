package proyect.server;

import java.util.ArrayList;

public final class SessionManager {
	private ArrayList<Session> sessionsList;
	private MessageLogger messageLogger;
	
	
	public SessionManager(){
		this.messageLogger = MessageLogger.getInstance();
		this.sessionsList = new ArrayList<Session>(); 
	}

	
	public void addSession(Session session) {
		this.sessionsList.add(session);
	}
	
	public boolean isActiveSession(Session session) {
		boolean res = false;
		for (Session s : this.sessionsList) {
				if (s.hashCode() == session.hashCode()) {
					res = true;
				}
			}
		return res;
	}

	public void destroySession(Session session) {
		String name = session.getUser();
		try {
			if (this.sessionsList.contains(session)) {
				session.getRequest().close();
				session.interrupt();
				this.sessionsList.remove(session);
				System.out.println(name + " DESCONECTADO");
			}
		} catch (Exception e) {
		}
	}
	
	public Session getSession(Session session) {
		Session res = null;
		for (Session s: sessionsList) {
			if (s.equals(session)) {
				res = s;
			}
		}
		return res;
	}
	public MessageLogger getMessageLogger(){
		return this.messageLogger;
	}
	public ArrayList<Session> getSessionsList() {
		return sessionsList;
	}
	public void setSessionsList(ArrayList<Session> sessionsList) {
		this.sessionsList = sessionsList;
	}
	
}