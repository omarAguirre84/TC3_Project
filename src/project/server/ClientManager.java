package project.server;

import java.util.ArrayList;

public final class ClientManager {
	private ArrayList<Client> sessionsList;
	
	
	public ClientManager(){
		sessionsList = new ArrayList<Client>(); 
	}
	
	public void addSession(Client session) {
		this.sessionsList.add(session);
	}
	
	public boolean isActiveSession(Client session) {
		boolean res = false;
		for (Client s : this.sessionsList) {
				if (s.hashCode() == session.hashCode()) {
					res = true;
				}
			}
		return res;
	}
	
	public void destroySession(Client session) {
		String name = session.getUserName();
		try {
			if (this.sessionsList.contains(session)) {
				session.getClientSocket().close();
				session.getThread().interrupt();
				this.sessionsList.remove(session);
				System.out.println(name + " DESCONECTADO");
			}
		} catch (Exception e) {
		}
	}
	
	public Client getSession(Client session) {
		Client res = null;
		for (Client s: sessionsList) {
			if (s.equals(session)) {
				res = s;
			}
		}
		return res;
	}
//	public Logger getMessageLogger(){
//		return logger;
//	}
	public ArrayList<Client> getSessionsList() {
		return sessionsList;
	}
	public void setSessionsList(ArrayList<Client> sessionsList) {
		this.sessionsList = sessionsList;
	}
	
}