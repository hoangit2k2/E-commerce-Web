package rrs.utils;

public class CustomException extends Exception {

	private static final long serialVersionUID = 4832427514148515425L;

	private String message;
	
	public CustomException() {
		super();
	}
	
	public CustomException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message == null ? super.getMessage() : 
			message.isEmpty() ? super.getMessage() : message;
	}
	
}
