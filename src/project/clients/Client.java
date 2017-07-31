package project.clients;

import java.io.IOException;

import project.client.observer.Observer;
import project.clients.swicthFactory.Switch;
import project.clients.swicthImpl.SwitchHttpImpl;
import project.clients.swicthImpl.SwitchTcpSocketImpl;
import project.exceptions.AlreadyExistsException;

public class Client implements Observer {

	private ClientManager clientManager;
	private String nickName;
	private Switch sw;

	public Client() {

	}

	public void userWelcome() {
		boolean sameUser = false;
		nickName = "newGuest try";
		String newNick=null;
		
		sw.send("PROYECTO CHAT SERVER");
		sw.send("El nombre no puede ser menor a 3 digitos o nulo");
		do {
			sw.send("Ingrese Usuario: ");
			try {
				newNick = sw.receive();
				clientManager.nickNameAlreadyExists(newNick);
				sameUser = false;
			} catch (AlreadyExistsException e) {
				sw.send(e.getMessage());
				sameUser = true;
			} catch (NullPointerException e) {
				this.clientManager.deleteObserver(this);
			}
		} while (nickName.length() < 3 || nickName.equals(null) || sameUser);
		nickName = newNick;
		sw.send("***BIENVENIDO: " + this.nickName + " ****");
		sw.send("quit, para salir del chat");
	}

	@Override
	public void update(Client observer, String msg) {
		this.clientManager.notifyObservers(this, msg);
	}

	public void msgContainsQuit(String msg) {
		if (msg.contains("quit")) {
			clientManager.deleteObserver(this);
		}
	}

	public boolean msgIsEmpty(String msg) {
		return (msg.equals(null) || msg.equals("")) ? true : false;

	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void killSwitch() {
		if (sw instanceof SwitchTcpSocketImpl) {
			try {
				((SwitchTcpSocketImpl) sw).getClientSocket().close();
			} catch (IOException e) {
			}
		} else if (sw instanceof SwitchHttpImpl) {

		}
	}

	public ClientManager getClientManager() {
		return clientManager;
	}

	public void setClientManager(ClientManager clientManager) {
		this.clientManager = clientManager;
	}

	public boolean isActiveClient() {
		return this.clientManager.isActiveClient(this);
	}

	public Switch getSw() {
		return sw;
	}

	public void setSw(Switch sw) {
		this.sw = sw;
	}
}
