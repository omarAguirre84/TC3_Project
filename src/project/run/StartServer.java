package project.run;
import project.server.Server;

public class StartServer {
	
	public static void main(String[] args) {
		try {
			Server.getInstance().listen();
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
