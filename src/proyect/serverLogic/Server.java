package proyect.serverLogic;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public final class Server {
	private ServerSocket serverSocket;
	private static Server instance;
	private ArrayList<Session> sessions;
	private int defaultPort;
	private String welcomeMsj;
	private String ip;

	private Server() {
		try {
			initializeVars();
			serverSocket = new ServerSocket(defaultPort);
			serverSocket.setSoTimeout(0);
			System.out.println("Servidor escuchando en " + ip + ":" + serverSocket.getLocalPort());
		} catch (Exception e) {
			System.out.println("Puerto " + defaultPort + " ocupado, intentando con otro");
		} finally {
			defaultPort = defaultPort + 100;
		}
	}

	public void listen() {
		while (true) {
			try {
				Socket request = serverSocket.accept();
				System.out.println("Se conecto " + request.getRemoteSocketAddress());
				Session s = new Session(request, welcomeMsj);

				Thread t = new Thread(s);
				sessions.add(s);
				t.start();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private boolean isActiveSession(String user) {
		boolean res = false;
		// for (Client c : clientList) {
		// if (user.equals(c.getUser())) {
		// if (pass.equals(c.getPassword())) {
		// res = true;
		// }
		// }
		// }
		return res;
	}

	public void disconnectClient(String user) {
		try {
			this.sessions.remove(getSession(user));
		} catch (Exception e) {

		}
	}

	private Session getSession(String user) {
		Session res = null;
		boolean done = false;

		do {
			for (Session s : sessions) {
				if (user.equals(s.getUser())) {
					res = s;
				}
			}
		} while (!done);

		return res;
	}

	public static Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}
		return instance;
	}

	public void initializeVars() {
		welcomeMsj = "HOLA DESDE EL SERVIDOR \n";
		defaultPort = 8080;
		sessions = new ArrayList<Session>();
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
		}

	}

	public ArrayList<Session> getSessions() {
		return this.sessions;
	}

	// public void run() {
	// while (true) {
	// try {
	// System.out.println("Waiting for client on port " +
	// serverSocket.getLocalPort() + "...");
	// Socket server = serverSocket.accept();
	//
	// System.out.println("Just connected to " +
	// server.getRemoteSocketAddress());
	// DataInputStream in = new DataInputStream(server.getInputStream());
	//
	// System.out.println(in.readUTF());
	// DataOutputStream out = new DataOutputStream(server.getOutputStream());
	// out.writeUTF("Thank you for connecting to " +
	// server.getLocalSocketAddress() + "\nGoodbye!");
	// server.close();
	//
	// } catch (SocketTimeoutException s) {
	// System.out.println("Socket timed out!");
	// break;
	// } catch (IOException e) {
	// e.printStackTrace();
	// break;
	// }
	// }
	// }
	//
	// public static void main(String[] args) {
	// int port = Integer.parseInt(args[0]);
	// try {
	// Thread t = new Server(port);
	// t.start();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
}