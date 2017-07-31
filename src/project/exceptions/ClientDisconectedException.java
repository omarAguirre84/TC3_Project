package project.exceptions;

public class ClientDisconectedException extends Exception{
	private static final long serialVersionUID = 1L;

	public ClientDisconectedException() {
		super("No es cliente conectado");
	}
	
}
