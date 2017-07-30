package project.client.observer;

import project.clients.Client;

public interface Observer {
	public void update(Client observer, String msg);
}
