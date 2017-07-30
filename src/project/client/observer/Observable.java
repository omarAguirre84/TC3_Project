package project.client.observer;

import project.clients.Client;

public abstract class Observable {
	
	public void addObserver(Client observer) {};
	public void deleteObserver(Client observer) {};
	
}
