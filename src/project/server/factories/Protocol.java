package project.server.factories;

import java.net.ServerSocket;

public abstract class Protocol {
	private String port;
	private ServerSocket serverSocket;
	private FuncionHttpExample http;
	
	
	
	
	
	
	
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	public FuncionHttpExample getHttp() {
		return http;
	}
	public void setHttp(FuncionHttpExample http) {
		this.http = http;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
}
