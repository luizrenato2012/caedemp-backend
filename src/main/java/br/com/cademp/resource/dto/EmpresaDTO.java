package br.com.cademp.resource.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

import br.com.cademp.model.bean.Empresa;
import br.com.cademp.model.bean.TipoEmpresa;

public class EmpresaDTO {
	private Long id;

	@NotNull
	private String cnpj;

	@NotNull
	private String nome;

	@NotNull
	private String tipo;

	@NotNull
	private String razaoSocial;

	@NotNull
	private String contato;

	@NotNull
	private String email;

	@Valid
	private EnderecoDTO endereco;
	
	private Long idMatriz;

	public EmpresaDTO build (Empresa empresa) {
		if (empresa==null) {
			return null;
		}
		
		id = empresa.getId();
		cnpj = empresa.getCnpj();
		nome = empresa.getNome();
		tipo = empresa.getTipo().toString().toUpperCase();
		razaoSocial = empresa.getRazaoSocial();
		contato = empresa.getContato();
		email = empresa.getEmail();
		endereco = new EnderecoDTO(empresa.getEndereco());
		idMatriz = empresa.getMatriz()!=null ? empresa.getMatriz().getId() : null;
		return this;
	}
	
	public Empresa parse() {
		Empresa empresa = new Empresa();
		empresa.setCnpj(this.cnpj);
		empresa.setContato(this.contato);
		empresa.setEmail(this.email);
		empresa.setEndereco(this.endereco!=null ? this.endereco.parse(): null);
		empresa.setId(id);
		empresa.setNome(nome);
		empresa.setRazaoSocial(razaoSocial);
		empresa.setTipo( TipoEmpresa.get(tipo));
		return empresa;
	}

	public EmpresaDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getNome() {
		return nome;
	}

	public String getTipo() {
		return tipo;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public String getContato() {
		return contato;
	}

	public String getEmail() {
		return email;
	}

	public EnderecoDTO getEndereco() {
		return endereco;
	}

	public Long getIdMatriz() {
		return idMatriz;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}

	public void setIdMatriz(Long idMatriz) {
		this.idMatriz = idMatriz;
	}
	
	
}
