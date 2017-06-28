package proyect.serverLogic;

import java.util.ArrayList;

public class SessionManager {
	private ArrayList<Session> sessionsList;
	
	public SessionManager(){
		this.sessionsList = new ArrayList<Session>(); 
	}

	
	public void addSession(Session session) {
		this.sessionsList.add(session);
		this.startSession(session);
	}
	
	private void startSession(Session s) {
		new Thread(new Runnable() {
//			@Override
			public void run(){
	        	s.process();
	        }
	    }).start();
	}
	public ArrayList<Session> getSessionsList() {
		return sessionsList;
	}
	public void setSessionsList(ArrayList<Session> sessionsList) {
		this.sessionsList = sessionsList;
	}
	
}
