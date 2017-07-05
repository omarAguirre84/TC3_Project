package project.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Observable;
import project.user.User;
import project.user.UserFactory;
import project.user.UserType;

public class Session extends Observable{

	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private SessionManager sessionManager;
	private User user;
	private OptionManager menu;
	private Thread thread;
	private Logger logger;
	
	
	public Session(Socket clientSocket, SessionManager sessionManager, Logger logger) {
		thread = new Thread();
		thread.run();
		user = null;
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

		if (inputAdmin(userName)) {
			adminLogin();
		} else {
			user = UserFactory.userCreate(userName, UserType.CLIENT, null);
		}
		send("***BIENVENIDO: " + user.getUserName() + " ****");
		send("***TIPO DE USUARIO: " + user.getUserType() + " ****");
		send("quit, para salir del chat");
	}
	
	public void adminLogin() {
		String pass = null;
		send("INTENTO DE LOGUEO ADMIN");
		send("INGRESE PASSWORD: ");

		pass = receive();
		user = UserFactory.userCreate("ADMIN", UserType.ADMIN, pass);
		
		send("***BIENVENIDO: " + user.getUserName() + " ****");
		send("***TIPO DE USUARIO: " + user.getUserType() + " ****");
		send("quit, para salir del chat");
		
		send("*************************");
		//		for (String op : user.getMenu().getOptions()) {
//			send(cont + ": " + op);
//			cont++;
//		}int cont = 0;

//		send(cont + ": " + "quit para salir");
		send("*************************");
		//Validar
		send("ELIJA OPCION: ");
		String option = receive();
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
	public boolean inputAdmin(String u) {
		return (u.equals("admin")) ? true : false;
	}

	private ArrayList<String> menu() {
		return null;
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
			if (msg.contains("admin")) {
				this.adminLogin();
			}

			if (sessionManager.isActiveSession(this)) {
				String client_socket = clientSocket.getRemoteSocketAddress().toString().replace("/", "");

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

	public User getUser() {
		return this.user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getUserName() {
		return this.user.getUserName();
	}

	public void setSessionManager(SessionManager sm) {
		this.sessionManager = sm;
	}

	private void sendAll(String msg) {
		try {
			for (Session s : sessionManager.getSessionsList()) {
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
