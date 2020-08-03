package br.com.cademp.errors;

public class ObjetoNotFoundException extends RuntimeException {

	public ObjetoNotFoundException() {
		super();
	}

	public ObjetoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjetoNotFoundException(String message) {
		super(message);
	}
	
	

}
