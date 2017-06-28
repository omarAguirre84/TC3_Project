package project.exceptions;

public class NotSessionException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotSessionException() {
		super("No es cliente registrado");
	}
	
}
