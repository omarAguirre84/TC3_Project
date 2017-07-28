package project.run;

import project.server.Server;
import project.server.factories.ProtocolFactory;
import project.server.factories.Protocols;

public class StartServer {
	
	public static void main(String[] args) {
		try {
			Protocols.HTTP.getPort();
			ProtocolFactory.init();
			
//			Server a = Server.getInstance();
//			a.listen();
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
