package project.protocolFactory;

import java.net.ServerSocket;

public abstract class Protocol {
	private String port;
	private ServerSocket serverSocket;
	private FuncionHttpMock http;
	
	
	
	
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	public FuncionHttpMock getHttp() {
		return http;
	}
	public void setHttp(FuncionHttpMock http) {
		this.http = http;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
}
