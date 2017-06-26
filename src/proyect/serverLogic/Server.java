package proyect.serverLogic;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import project.exceptions.NotClientException;

public final class Server {
	private ServerSocket serverSocket;
	private static Server instance;
	private ArrayList<Session> sessions;
	private int defaultPort;
	private String ip;

	private Server() {
		boolean done= false;
		do {
			try {
				defaultPort = 8080;
				sessions = new ArrayList<Session>();
				serverSocket = new ServerSocket(defaultPort);
				serverSocket.setSoTimeout(0);
				ip = InetAddress.getLocalHost().getHostAddress();
				System.out.println("Servidor escuchando en " + ip + ":" + serverSocket.getLocalPort());
				done = true;
			} catch (Exception e) {
				System.out.println("Puerto " + defaultPort + " ocupado, intentando con otro");
			} finally {
				defaultPort = defaultPort + 100;
			}
		} while (!done);
	}
	
	public void listen() {
		while (true) {
			try {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Se conecto " + clientSocket.getRemoteSocketAddress());
				
				new Thread(new Runnable() {
					@Override
					public void run(){
						Session s = new Session(clientSocket);
			        	sessions.add(s);
			        	s.run();
			        }
			    }).start();
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public boolean isActiveSession(Session userSession) {
		boolean res = false;
		try {
			for (Session s : this.sessions) {
				if (s.equals(userSession)) {
					res = true;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return res;
	}

	public void disconnectClient(Session clientSocket) throws NotClientException {
		String name = clientSocket.getUser();
		try {
			if (this.sessions.contains(clientSocket)) {
				clientSocket.getRequest().close();
				clientSocket.interrupt();
				this.sessions.remove(clientSocket);
				System.out.println(name + " DESCONECTADO");
			}
		} catch (Exception e) {
			throw new NotClientException();
		}
	}

	private Session getSession(String user) {
		return null;
	}

	public static Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}
		return instance;
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