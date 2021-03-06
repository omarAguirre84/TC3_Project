package proyect.serverLogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import project.exceptions.NotSessionException;
import proyect.user.User;

public class Session extends Thread {

	private Socket request;
	private String user;
	private PrintWriter out;
	private BufferedReader in;
	private Server server;
	private String welcomeMsg = "PROYECTO CHAT";
	private String adminPass = "123";

	public Session(Socket clientSocket) {
		server = Server.getInstance();
		request = clientSocket;
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (Exception e) {
		}
		userWelcome();
	}

	public void adminUserWelcome() {
		send("INTENTO DE LOGUE ADMIN");
		send("Ingrese Password: ");
		String pass = receive();
		if (pass.equals(adminPass)) {
			send("***USUARIO ADMIN****");
			send("quit, para salir del chat");
		} else {
			send("incorrecto");
		}
	}

	public void userWelcome() {
		send(welcomeMsg);
		send("Ingrese Usuario: ");

		user = this.receive();
		send("***BIENVENIDO: " + user + " ****");
		send("quit, para salir del chat");
	}

	private ArrayList<String> menu() {

		return null;
	}

	public void send(String msg) {
		try {
			out.println(msg);
		} catch (Exception e) {
			server.destroySession(this);
		}
	}

	public String receive() {
		String msg = null;
		try {
			msg = in.readLine();

			if (msg.contains("quit")) {
				server.destroySession(this);
			}
			if (msg.contains("admin")) {
				this.adminUserWelcome();
			}

			if (server.isActiveSession(this)) {
				System.out.println(user + ": " + msg);
				sendAll(user + ": " + msg);
			}
		} catch (IOException | NullPointerException e) {
			try {
				server.destroySession(this);
			} catch (Exception e2) {
			}
		}
		return msg;
	}

	public Socket getRequest() {
		return request;
	}

	public String getUser() {
		return this.user;
	}

	private void sendAll(String msg) {
		try {
			for (Session s : server.getSessions()) {
				if (this.request.hashCode() != s.getRequest().hashCode() && !msg.contains("admin")) {
					s.send(msg);
				}
			}
		} catch (NullPointerException e) {
		}
	}

	public void process() {
		boolean run = true;
		while (run) {
			if (!this.request.isClosed()) {
				receive();
			} else {
				run = false;
			}
		}
	}
}
