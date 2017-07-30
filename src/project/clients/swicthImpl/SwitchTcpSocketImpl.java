package project.clients.swicthImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;

import project.clients.Client;
import project.clients.swicthFactory.Switch;
import project.exceptions.ClientDisconectedException;
import project.protocolFactory.Protocol;

public class SwitchTcpSocketImpl implements Switch{

	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private Client client;
	private Thread thread;
	private String clientIp;
	
	public SwitchTcpSocketImpl() {
//		thread = new Thread();
//		thread.run();
	}
	
	@Override
	public void initInAndOut() {
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), Charset.forName("UTF8")));
		} catch (Exception e) {
		}
	}
	
	@Override
	public void send(String msg) {
		try {
			out.println(msg + "\n" + "\r");
		} catch (Exception e) {
		}
	}

	@Override
	public String receive() {
		String msg = null;
		try {
			msg = in.readLine();
			client.interceptorMsgNull(msg);
//			try {
//				if (!client.getNickName().equals(null)) {
					if (client.isActiveClient() && client.interceptorMsgNull(msg)) {
						client.update(client, msg);
					}
//				}
//			} catch (NullPointerException e) {
//				client.setNickName(msg);
//			}
			
		} catch (IOException | NullPointerException e) {
			
		}
		return msg;
	}

	
	public Client getClient() {
		return client;
	}
	public Socket getClientSocket() {
		return clientSocket;
	}
	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.setClientIp(clientSocket.getRemoteSocketAddress().toString().replace("/", ""));
	}
	public void process() {
		client.userWelcome();
		boolean run = true;
		while (run) {
			if (!this.clientSocket.isClosed()) {
//				try {
					receive();
//				} catch (ClientDisconectedException e) {
//					
//				}
			} else {
				run = false;
			}
		}
	}

	@Override
	public Thread getThread() {
		return this.thread;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
