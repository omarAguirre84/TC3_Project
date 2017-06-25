package project.exceptions;

public class NotClientException extends Exception{

	public NotClientException() {
		super("No es cliente registrado");
	}
	
}
