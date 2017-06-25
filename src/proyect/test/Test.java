package proyect.test;

import proyect.serverLogic.Client;
import proyect.serverLogic.Server;

public class Test {
	
	public static void main(String[] args) {
		try {
			Client c = new Client("Pepe", "cc");
			Server a = Server.getInstance();
			a.listen();
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
