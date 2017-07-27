package project.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Observable;

public class Client extends Observable{

	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private ClientManager sessionManager;
	private Thread thread;
	private Logger logger;
	private String userName;
	
	
	public Client(Socket clientSocket, ClientManager sessionManager, Logger logger) {
		thread = new Thread();
		thread.run();
		this.logger = logger;
		this.clientSocket = clientSocket;
		this.sessionManager = sessionManager;
		
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), Charset.forName("UTF8")));
		} catch (Exception e) {
		}

		userLogin();
	}

	public void userLogin() {
		send("PROYECTO CHAT SERVER");
		String userName=null;
		do {
			send("Ingrese Usuario: ");
			userName = this.receive();
		} while (userName.length() < 3 || userName.equals(null));

		send("***BIENVENIDO: " + this.getUserName() + " ****");
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
				sessionManager.destroySession(this);
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
				sessionManager.destroySession(this);
			}
			if (sessionManager.isActiveSession(this)) {
//				String client_socket = clientSocket.getRemoteSocketAddress().toString().replace("/", "");
				this.clientSocket.getRemoteSocketAddress().toString().replace("/", "");
				logger.update(this, msg);
				sendAll(this.getUserName() + ": " + msg);
			}
		} catch (IOException | NullPointerException e) {
			sessionManager.destroySession(this);
		}
		return msg;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}
	public String getUserName() {
		return this.getUserName();
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setSessionManager(ClientManager sm) {
		this.sessionManager = sm;
	}

	private void sendAll(String msg) {
		try {
			for (Client s : sessionManager.getSessionsList()) {
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
