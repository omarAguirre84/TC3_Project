package project.server;

import java.net.ServerSocket;
import java.net.Socket;

import project.clients.Client;
import project.clients.ClientManager;
import project.clients.swicthFactory.Switch;
import project.clients.swicthFactory.SwitchFactory;
import project.clients.swicthImpl.SwitchTcpSocketImpl;
import project.file.FileHelper;
import project.file.FileHelperImpl;
import project.logger.Logger;
import project.protocolFactory.Http;
import project.protocolFactory.Protocol;
import project.protocolFactory.ProtocolFactory;
import project.protocolFactory.ProtocolsEnum;
import project.protocolFactory.TcpSocket;

public final class Server {
	private static Server instance;
//	private 
	private ClientManager clientManager;
	private FileHelper fileHelper;
	
	private Protocol protocol;
	
	private Server() {
		fileHelper = new FileHelperImpl();
		clientManager = new ClientManager(new Logger(fileHelper));
		this.protocol = ProtocolFactory.getProtocol();
	}

	public void listen() {
		if (this.protocol instanceof TcpSocket) {
			ServerSocket serverSocket = this.protocol.getServerSocket();
			
			while (true) {
				try {
					Socket clientSocket = serverSocket.accept();
					System.out.println("Se conecto " + clientSocket.getRemoteSocketAddress());
					
					new Thread(new Runnable() {
						@Override
						public void run() {
							clientManager.newTcpSocketClient(clientSocket);
//							Client client = new Client(clientManager);
//							Switch sw = SwitchFactory.makeSwitch(ProtocolsEnum.TCP_SOCKET);
//							((SwitchTcpSocketImpl) sw).setClientSocket(clientSocket);
//							
//							client.setSw(sw);
//							clientManager.addObserver(client);
//							client.getSw().process();
							
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