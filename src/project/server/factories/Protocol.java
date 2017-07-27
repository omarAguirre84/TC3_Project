package project.server.factories;

import java.net.ServerSocket;

import javax.xml.ws.spi.http.HttpHandler;

public abstract class Protocol {
	private ServerSocket serverSocket;
	private HttpHandler httpServletRequest;
	
}
