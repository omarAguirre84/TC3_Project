package proyect.serverLogic;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

import project.exceptions.NotClientException;

public class Session implements Runnable {

	private Socket request;
	private String user;
	private DataOutputStream out;
	private BufferedReader in;
	private Server server;

	public Session(Socket r, String welcomeMsg) {
		server = Server.getInstance();
		request = r;
		try {
			out = new DataOutputStream(request.getOutputStream());
			in = new BufferedReader(new InputStreamReader(request.getInputStream()));
		} catch (Exception e) {
			
		}

		send(welcomeMsg);
		send("Ingrese Usuario \n");
		user = receive();
	}

	public void send(String msg) {
		try {
			out.writeUTF(msg);
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
			int h = msg.hashCode();
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
