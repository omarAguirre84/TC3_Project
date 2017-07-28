package project.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Observable;

import project.server.logger.Logger;

public class Client extends Observable{

	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private ClientManager clientManager;
	private Thread thread;
	private Logger logger;
	private String nickName;
	
	
	public Client(Socket clientSocket, ClientManager clientManager, Logger logger) {
		thread = new Thread();
		thread.run();
		this.logger = logger;
		this.clientSocket = clientSocket;
		this.clientManager = clientManager;
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), Charset.forName("UTF8")));
		} catch (Exception e) {
		}
		
		userLogin();
	}

	public void userLogin() {
		send("PROYECTO CHAT SERVER");
		nickName=null;
		do {
			send("Ingrese Usuario: ");
			nickName = this.receive();
		} while (nickName.length() < 3 || nickName.equals(null));

		send("***BIENVENIDO: " + this.nickName + " ****");
		send("quit, para salir del chat");
	}
	
	public String ask3TimesToMeetCondition(String what, String cond) {
		String res = null;
		int cont = 0;
		while(cont < 3){
			send(what);
			res = receive();
		}
		return res;
	}
	
	public void send(String msg) {
		try {
			out.println(msg + "\n" + "\r");
		} catch (Exception e) {
			try {
				clientManager.destroySession(this);
			} catch (Exception e3) {
				System.out.println(e3.getMessage());
			}
		}
	}

	public String receive() {
		String msg = null;
		try {
			msg = in.readLine();

			if (msg.contains("quit")) {
				clientManager.destroySession(this);
			}
			if (clientManager.isActiveSession(this)) {
//				String client_socket = clientSocket.getRemoteSocketAddress().toString().replace("/", "");
				this.clientSocket.getRemoteSocketAddress().toString().replace("/", "");
				logger.update(this, msg);
				sendAll(this.getNickName() + ": " + msg);
			}
		} catch (IOException | NullPointerException e) {
			clientManager.destroySession(this);
		}
		return msg;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}
	public String getNickName() {
		return this.nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setSessionManager(ClientManager sm) {
		this.clientManager = sm;
	}

	private void sendAll(String msg) {
		try {
			for (Client s : clientManager.getSessionsList()) {
				if (this.clientSocket.hashCode() != s.getClientSocket().hashCode() && !msg.contains("admin")) {
					s.send(msg);
				}
			}
		} catch (NullPointerException e) {
		}
	}

	public void process() {
		boolean run = true;
		while (run) {
			if (!this.clientSocket.isClosed()) {
				receive();
			} else {
				run = false;
			}
		}
	}

	public Thread getThread() {
		return thread;
	}
	
}
