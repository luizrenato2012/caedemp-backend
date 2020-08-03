package br.com.cademp.resource.dto;

import br.com.cademp.model.bean.Endereco;

public class EnderecoDTO {
	
	private Long id;
	private String cep;
	private String estado;
	private String bairro ;
	private String cidade;
	private String logradouro;
	private String complemento ;
	
	public EnderecoDTO() {
		super();
	}

	public EnderecoDTO(Endereco endereco) {
		if (endereco==null) {
			return;
		}
		id = endereco.getId();
		cep = endereco.getCep();
		estado = endereco.getEstado();
		bairro  = endereco.getBairro();
		cidade= endereco.getCidade();
		logradouro = endereco.getLogradouro();
		complemento  = endereco.getComplemento();
	}
	
	public Endereco parse() {
		Endereco endereco = new Endereco ();
		endereco.setBairro(this.bairro);
		endereco.setCep(cep);
		endereco.setCidade(cidade);
		endereco.setComplemento(complemento);
		endereco.setEstado(estado);
		endereco.setLogradouro(logradouro);
		endereco.setId(id);
		return endereco;
	}

	public Long getId() {
		return id;
	}

	public String getCep() {
		return cep;
	}

	public String getEstado() {
		return estado;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	
	
}	
