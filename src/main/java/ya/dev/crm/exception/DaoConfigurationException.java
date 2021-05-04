package ya.dev.crm.exception;

public class DaoConfigurationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DaoConfigurationException(String msg) {
		super(msg);
	}
	
	public DaoConfigurationException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public DaoConfigurationException(Throwable cause) {
		super(cause);
	}

}
