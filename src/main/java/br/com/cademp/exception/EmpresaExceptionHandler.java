package br.com.cademp.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EmpresaExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler({ObjetoNotFoundException.class, EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handlerNotFoundResourceException(RuntimeException exception, 
			WebRequest request  ) {
		String tipo = messageSource.getMessage("recurso.nao-encontrado", null, 
				LocaleContextHolder.getLocale());
		String origem = getUri(request);
		return this.handleExceptionInternal(exception, new MensagemErro(tipo,exception.getMessage(), origem), 
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String tipo = messageSource.getMessage("parametro.nao-encontrado", null, 
				LocaleContextHolder.getLocale());
		String origem = getUri(request);
		return this.handleExceptionInternal(ex, new MensagemErro(tipo, ex.getMessage(), origem), 
				headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String tipo = messageSource.getMessage("mensagem.invalida", null, 
				LocaleContextHolder.getLocale());
		String origem = getUri(request);
		String mensagem = ex.getCause()!=null ? ex.getCause().getMessage() : ex.getMessage();
		return this.handleExceptionInternal(ex, new MensagemErro(tipo, mensagem, origem), 
				headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String tipo = messageSource.getMessage("tipo-mensagem.invalido", null, 
				LocaleContextHolder.getLocale());
		String origem = getUri(request);
		return this.handleExceptionInternal(ex, new MensagemErro(tipo, ex.getMessage(), origem), 
				headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String tipo = messageSource.getMessage("campo-ausente", null, 
				LocaleContextHolder.getLocale());
		String origem = getUri(request);
		List<MensagemErro> listaErros = new ArrayList<>();
		String detalhe = "";
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			detalhe= messageSource.getMessage(fieldError, LocaleContextHolder.getLocale() );
			listaErros.add(new MensagemErro(tipo, detalhe, origem));
		}
		return this.handleExceptionInternal(ex, listaErros, headers, 
				HttpStatus.BAD_REQUEST, request);
	}

	private String getUri(WebRequest request) {
		 return ((ServletWebRequest)request).getRequest().getRequestURI();
	}
}

class MensagemErro {
	
	private LocalDateTime dataHora;
	private String erro;
	private String tipo;
	private String recurso;
	
	public MensagemErro(String tipo, String erro, String recurso) {
		super();
		this.erro = erro;
		this.tipo = tipo;
		this.dataHora = LocalDateTime.now();
		this.recurso = recurso;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String origem) {
		this.recurso = origem;
	}
	
}
