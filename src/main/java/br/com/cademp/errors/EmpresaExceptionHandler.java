package br.com.cademp.errors;

import java.time.LocalDateTime;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
		String descricao = messageSource.getMessage("recurso.nao-encontrado", null, 
				LocaleContextHolder.getLocale());
		String origem = getUri(request);
		return this.handleExceptionInternal(exception, new MensagemErro(descricao,exception.getMessage(), origem), 
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String descricao = messageSource.getMessage("parametro.nao-encontrado", null, 
				LocaleContextHolder.getLocale());
		String origem = getUri(request);
		return this.handleExceptionInternal(ex, new MensagemErro(descricao, ex.getMessage(), origem), 
				headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String descricao = messageSource.getMessage("mensagem.invalida", null, 
				LocaleContextHolder.getLocale());
		String origem = getUri(request);
		return this.handleExceptionInternal(ex, new MensagemErro(descricao, ex.getMessage(), origem), 
				headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String descricao = messageSource.getMessage("tipo-mensagem.invalido", null, 
				LocaleContextHolder.getLocale());
		String origem = getUri(request);
		return this.handleExceptionInternal(ex, new MensagemErro(descricao, ex.getMessage(), origem), 
				headers, HttpStatus.BAD_REQUEST, request);
	}
	
	private String getUri(WebRequest request) {
		 return ((ServletWebRequest)request).getRequest().getRequestURI();
	}
}

class MensagemErro {
	
	private LocalDateTime dataHora;
	private String erro;
	private String descricao;
	private String origem;
	
	public MensagemErro(String descricao, String erro, String origem) {
		super();
		this.erro = erro;
		this.descricao = descricao;
		this.dataHora = LocalDateTime.now();
		this.origem = origem;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}
	
}
