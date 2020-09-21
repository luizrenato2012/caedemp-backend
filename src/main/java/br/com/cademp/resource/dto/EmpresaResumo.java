package br.com.cademp.resource.dto;

import br.com.cademp.model.bean.TipoEmpresa;

public class EmpresaResumo {

	private Long id;	
	private String tipo;	
	private String nome;
	private String  cnpj;
	private String cidade;
	private String estado;
	
	public EmpresaResumo() {
		super();
	}
	
	public EmpresaResumo(Long id, TipoEmpresa tipo, String nome, String cnpj, String cidade, String estado) {
		super();
		this.id = id;
		this.tipo = tipo.toString();
		this.nome = nome;
		this.cnpj = cnpj;
		this.cidade = cidade;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
