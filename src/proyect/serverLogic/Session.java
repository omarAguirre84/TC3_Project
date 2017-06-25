package proyect.serverLogic;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Session extends Thread  {

	private Socket request;
	private String user;
//	private DataOutputStream out;
	private BufferedOutputStream out2;
	private BufferedReader in;
	private Server server;
	String welcomeMsg = "HOLA DESDE EL SERVIDOR";
	
	public Session(Socket r) {
		server = Server.getInstance();
		request = r;
		try {
//			out = new DataOutputStream(request.getOutputStream());
			out2 = new BufferedOutputStream(request.getOutputStream());
			in = new BufferedReader(new InputStreamReader(request.getInputStream()));
		} catch (Exception e) {
		}

		send(welcomeMsg);
		send("Ingrese Usuario: ");
		user = receive();
	}

	public void send(String msg) {
		try {
			byte[] a = msg.getBytes();
			out2.write(a);
//			out.writeUTF(msg+"\n");
		} catch (IOException e) {
			System.out.println("El usuario " +user + " se desconecto");
			server.disconnectClient(user);
		}
	}

	public String receive() {
		String msg = null;
		try {
			msg = in.readLine();
			System.out.println(user + ": " + msg);
			sendAll(user + ": " + msg + "\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return msg;
	}

	public Socket getRequest() {
		return request;
	}

	@Override
	public void run() {
		while (true) {
			receive();
		}
	}

	private void sendAll(String msg) {
		for (Session s : server.getSessions()) {
			s.send(msg);
		}
	}

	public String getUser() {
		return this.user;
	}
}
