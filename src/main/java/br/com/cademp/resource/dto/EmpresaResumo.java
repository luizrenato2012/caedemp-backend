package br.com.cademp.resource.dto;

import br.com.cademp.model.bean.TipoEmpresa;

public class EmpresaResumo {

	private Long id;	
	private String tipo;	
	private String nome;
	private String  cnpj;
	
	public EmpresaResumo() {
		super();
	}
	
	public EmpresaResumo(Long id, TipoEmpresa tipo, String nome, String cnpj) {
		super();
		this.id = id;
		this.tipo = tipo.toString();
		this.nome = nome;
		this.cnpj = cnpj;
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
	
	
}
