package proyect.serverLogic;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public final class Server {
	private ServerSocket serverSocket;
	private static Server instance;
	
	private SessionManager sessionManager;
	
	private int serverDefaultPort;
	private String serverIp;
	private MessageLog log;

	private Server() {
		sessionManager = new SessionManager();
		boolean done= false;
		do {
			try {
				serverDefaultPort = 8080;
				serverSocket = new ServerSocket(serverDefaultPort);
				serverSocket.setSoTimeout(0);
				serverIp = InetAddress.getLocalHost().getHostAddress();
				System.out.println("Servidor escuchando en " + serverIp + ":" + serverSocket.getLocalPort());
				
				done = true;
			} catch (Exception e) {
				System.out.println("Puerto " + serverDefaultPort + " ocupado, intentando con otro");
			} finally {
				serverDefaultPort = serverDefaultPort + 100;
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
						s.setSessionManager(sessionManager);
			        	s.process();
			        	sessionManager.addSession(s);
			        	
			        }
			    }).start();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}
		return instance;
	}

//	public ArrayList<Session> getSessions() {
//		return this.clientSessionsList;
//	}
	
	
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