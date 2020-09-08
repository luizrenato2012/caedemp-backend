package br.com.cademp.exception;

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
