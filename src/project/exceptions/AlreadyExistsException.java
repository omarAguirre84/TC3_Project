package project.exceptions;

public class AlreadyExistsException extends Exception{
	
	private static final long serialVersionUID = 1572689470954079090L;
	
	public AlreadyExistsException(String element){
		super(element + " ya existe y no se puede repetir");
	}
}
