package project.server;

import java.net.ServerSocket;
import java.net.Socket;

import project.server.file.FileHelper;
import project.server.file.FileHelperImpl;
import project.server.logger.Logger;
import project.server.protocolFactory.Protocol;
import project.server.protocolFactory.ProtocolFactory;
import project.server.protocolFactory.ProtocolsEnum;
import project.server.protocols.Http;
import project.server.protocols.TcpSocket;

public final class Server {
	private static Server instance;
	private ServerSocket serverSocket;
	private ClientManager clientManager;
	private FileHelper fileHelper;
	
	private Protocol protocol;
	
	private Server() {
		clientManager = new ClientManager();
		fileHelper = new FileHelperImpl();
		this.protocol = ProtocolFactory.init();
		this.serverSocket = this.protocol.getServerSocket();
	}

	public void listen() {
		if (this.protocol instanceof TcpSocket) {
			while (true) {
				try {
					Socket clientSocket = serverSocket.accept();
					System.out.println("Se conecto " + clientSocket.getRemoteSocketAddress());
					
					new Thread(new Runnable() {
						@Override
						public void run() {
							Client s = new Client(clientSocket, clientManager, new Logger(fileHelper));
							clientManager.addSession(s);
							s.process();
						}
					}).start();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}else if(this.protocol instanceof Http){
			
		}
	}

	public static Server getInstance() {
		return (instance == null) ?	instance = new Server() : instance;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	// public ArrayList<Session> getSessions() {
	// return this.clientSessionsList;
	// }

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