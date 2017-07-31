package project.clients;

import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.util.ArrayList;

import project.client.observer.Observable;
import project.clients.swicthFactory.Switch;
import project.clients.swicthFactory.SwitchFactory;
import project.clients.swicthImpl.SwitchTcpSocketImpl;
import project.exceptions.AlreadyExistsException;
import project.logger.Logger;
import project.protocolFactory.ProtocolsEnum;

public final class ClientManager extends Observable{
	private ArrayList<Client> observersList;
	private Logger logger;
	
	public ClientManager(Logger logger){
		observersList = new ArrayList<Client>();
		this.logger = logger;
	}
	
	public void addObserver(Client observer) {
		this.observersList.add(observer);
	}
	
	public void newTcpSocketClient(Socket clientSocket) {
		Client client = new Client();
		
		client.setClientManager(this);
		Switch sw = SwitchFactory.makeSwitch(ProtocolsEnum.TCP_SOCKET);
		((SwitchTcpSocketImpl) sw).setClient(client);
		
		((SwitchTcpSocketImpl) sw).setClientSocket(clientSocket);
		((SwitchTcpSocketImpl) sw).initInAndOut();
		((SwitchTcpSocketImpl) sw).setThread(new Thread());
		((SwitchTcpSocketImpl) sw).getThread().run();
		client.setSw(sw);
		addObserver(client);
		client.getSw().process();
	}

	public void notifyObservers(Client observer, String msg) {
		for (Client c : this.getObserversList()) {
			if (observer.getNickName() != c.getNickName()) {
				c.getSw().send(observer.getNickName() +": "+ msg);
			}
		}
		logger.logObserverActivity(observer, msg);
	}
	
	public boolean isActiveClient(Client client) {
		boolean res = false;
		for (Client cli : this.observersList) {
				if (cli.hashCode() == client.hashCode()) {
					res = true;
				}
			}
		return res;
	}
	
	public void deleteObserver(Client observer) {
		String name = observer.getNickName();
		try {
			if (this.observersList.contains(observer)) {
				observer.getSw().getThread().interrupt();
				observer.killSwitch();
				this.observersList.remove(observer);
				
				System.out.println(name + " DESCONECTADO");
				notifyObservers(observer, (name + " DESCONECTADO"));
			}
		} catch (Exception e) {
		}
	}
	
	public boolean nickNameAlreadyExists(String msg) throws AlreadyExistsException {
		for (Client client : observersList) {
			if(client.getNickName().equals(msg)) {
				throw new AlreadyExistsException("El usuario '"+msg+"' ya existe");
			}
		}
		return false;
	}
	
	public Client getSession(Client session) {
		Client res = null;
		for (Client s: observersList) {
			if (s.equals(session)) {
				res = s;
			}
		}
		return res;
	}
	
	public ArrayList<Client> getObserversList() {
		return observersList;
	}
	public void setSessionsList(ArrayList<Client> sessionsList) {
		this.observersList = sessionsList;
	}

	public Logger getLogger() {
		return logger;
	}
}