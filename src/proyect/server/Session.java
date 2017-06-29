package proyect.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Session extends Thread {

	private Socket clientSocket;
	private String user;
	private PrintWriter out;
	private BufferedReader in;
	private String welcomeMsg = "PROYECTO CHAT";
	private String adminPass = "123";
	private SessionManager sessionManager;

	public Session(Socket clientSocket, SessionManager sm) {
		user = "";
		this.clientSocket = clientSocket;
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (Exception e) {
		}
		if(sm != null){
			this.sessionManager = sm;
		}
		userWelcome();
	}
	
	public void adminUserWelcome(){
		send("INTENTO DE LOGUE ADMIN");
		send("Ingrese Password: ");
		String pass= receive();
		if(pass.equals(adminPass)){
			send("***USUARIO ADMIN****");
			send("quit, para salir del chat");
		}else {
			send("incorrecto");
		}
	}
	
	public void userWelcome(){
		send(welcomeMsg);
		send("Ingrese Usuario: ");
		
		user = this.receive();
		send("***BIENVENIDO: "+user+" ****");
		send("quit, para salir del chat");
	}
	
	private ArrayList<String> menu(){
		return null;
	}

	public void send(String msg) {
		try {
			out.println(msg);
		} catch (Exception e) {
			try {
				sessionManager.destroySession(this);
			}  catch (Exception e3) {
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
			if(msg.contains("admin")){
				this.adminUserWelcome();
			}

			if (sessionManager.isActiveSession(this)) {
//				System.out.println(user + ": " + msg);
				String client_socket = clientSocket.getRemoteSocketAddress().toString();
				
				this.sessionManager.getMessageLogger().setAllMembers(user, client_socket, msg);
				
				System.out.println(this.sessionManager.getMessageLogger().formReg());
				sendAll(user + ": " + msg);
			}
		} catch (IOException | NullPointerException e) {
			sessionManager.destroySession(this);
		}
		return msg;
	}

	public Socket getRequest() {
		return clientSocket;
	}
	public String getUser() {
		return this.user;
	}
	public void setSessionManager(SessionManager sm) {
		this.sessionManager = sm;
	}

	private void sendAll(String msg) {
		try {
			for (Session s : sessionManager.getSessionsList()) {
				if(this.clientSocket.hashCode() != s.getRequest().hashCode() && !msg.contains("admin")){
					s.send(msg);
				}
			}
		} catch (NullPointerException e) {}
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
}
