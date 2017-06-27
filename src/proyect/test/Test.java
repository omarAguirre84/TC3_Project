package proyect.test;

import proyect.serverLogic.Server;

public class Test {
	
	public static void main(String[] args) {
		try {
			Server a = Server.getInstance();
			a.listen();
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
