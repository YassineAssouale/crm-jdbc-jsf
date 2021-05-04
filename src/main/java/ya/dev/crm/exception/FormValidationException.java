package ya.dev.crm.exception;

public class FormValidationException extends Exception{

	private static final long serialVersionUID = 8855021813885470469L;
	
	public FormValidationException(String msg) {
		super(msg);
	}
	
}
