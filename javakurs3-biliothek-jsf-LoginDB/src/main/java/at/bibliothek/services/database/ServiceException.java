package at.bibliothek.services.database;

public class ServiceException extends Exception{


	private static final long serialVersionUID = 1L;

	public ServiceException(String message, Throwable t){
		super (message, t);
	}
}
