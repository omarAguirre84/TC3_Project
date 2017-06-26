package project.exceptions;

public class NotClientException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotClientException() {
		super("No es cliente registrado");
	}
	
}
