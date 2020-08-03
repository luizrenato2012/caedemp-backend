package br.com.cademp.errors;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EmpresaExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler({ObjetoNotFoundException.class})
	public ResponseEntity<Object> handlerBadException(ObjetoNotFoundException exception, 
			WebRequest request ) {
		return this.handleExceptionInternal(exception, new MensagemErro(exception.getMessage()), 
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return this.handleExceptionInternal(ex, new MensagemErro(ex.getMessage()), 
				headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return this.handleExceptionInternal(ex, new MensagemErro(ex.getMessage()), 
				headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return this.handleExceptionInternal(ex, new MensagemErro(ex.getMessage()), 
				headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return this.handleExceptionInternal(ex, new MensagemErro(ex.getMessage()), 
				headers, HttpStatus.BAD_REQUEST, request);
	}
}

class MensagemErro {
	
	private LocalDateTime dataHora;
	private String erro;
	
	public MensagemErro(String mensagem) {
		super();
		this.erro = mensagem;
		this.dataHora = LocalDateTime.now();
	}

	public MensagemErro() {
		super();
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String mensagem) {
		this.erro = mensagem;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
	
}
