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
}