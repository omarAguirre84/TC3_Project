package project.run;

import project.server.Server;
import project.server.factories.ProtocolFactory;

public class StartServer {
	
	public static void main(String[] args) {
		try {
			ProtocolFactory.selectInterface("INTERFACES");
//			Server a = Server.getInstance();
//			a.listen();
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
