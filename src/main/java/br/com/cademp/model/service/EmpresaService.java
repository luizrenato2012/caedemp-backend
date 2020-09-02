package br.com.cademp.model.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.cademp.exception.ErroNegocioException;
import br.com.cademp.exception.ObjetoNotFoundException;
import br.com.cademp.model.bean.Empresa;
import br.com.cademp.model.bean.Endereco;
import br.com.cademp.model.bean.TipoEmpresa;
import br.com.cademp.model.repository.EmpresaFilter;
import br.com.cademp.model.repository.EmpresaRepository;
import br.com.cademp.resource.dto.EmpresaDTO;
import br.com.cademp.resource.dto.EnderecoDTO;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository repository;
	
	public EmpresaDTO save(EmpresaDTO empresaDTO) {
		Empresa empresa = empresaDTO.parse();
		trataFilial(empresaDTO, empresa);
		Empresa empresaNova = repository.save(empresa);
		return new EmpresaDTO().build(empresaNova);
	}

	private void trataFilial(EmpresaDTO empresaDTO, Empresa empresa) {
		if (empresaDTO.getTipo().equals(TipoEmpresa.FILIAL.toString())) {
			validaMatriz(empresaDTO);
			setMatriz(empresa, empresaDTO);
		}
	}
	
	private void validaMatriz(EmpresaDTO empresaDTO) {
		if (empresaDTO.getTipo().equalsIgnoreCase("FILIAL") && 
				(empresaDTO.getIdMatriz()==null || empresaDTO.getIdMatriz().equals(0l))) {
			throw new ErroNegocioException("Filial id ["+ 
				empresaDTO.getId() +"] sem identificacao de matriz");
		}
	}
	
	private void setMatriz(Empresa empresa, EmpresaDTO empresaDTO) {
		Optional<Empresa> optional = repository.findById(empresaDTO.getIdMatriz());
		Empresa matriz = optional.orElseThrow(() -> 
			new ObjetoNotFoundException("Matriz id [" + empresaDTO.getIdMatriz() + "] nao encontrada"));
		if (matriz.getTipo().equals(TipoEmpresa.FILIAL)) {
			throw new ErroNegocioException("Empresa id [" + empresaDTO.getIdMatriz() + "] nao é matriz");
		}
		empresa.setMatriz(matriz);
	}
	
	public EmpresaDTO update(EmpresaDTO empresaDto) {
		if (empresaDto.getId()==null || empresaDto.getId()==0) {
			throw new ObjetoNotFoundException("Empresa não encontrada par atualização");
		}
		Empresa empresaAtual = atualiza(empresaDto);
		
		Empresa empresaNova = repository.save(empresaAtual);
		return new EmpresaDTO().build(empresaNova);
	}

	private Empresa atualiza(EmpresaDTO empresaDTO) {
		Optional<Empresa> optional = repository.findById(empresaDTO.getId());
		Empresa empresaAtual = optional.orElseThrow(()->
			new ObjetoNotFoundException("Empresa id [" + empresaDTO.getId() + "] nao encontrada para atualização"));
		empresaAtual = copia(empresaDTO, empresaAtual);
		
		if (empresaDTO.getTipo().equalsIgnoreCase(TipoEmpresa.FILIAL.toString())) {
			trataFilial(empresaDTO, empresaAtual);
		} else {
			empresaAtual.setMatriz(null);
		}
		
		return empresaAtual;
	}
	
	private Empresa copia(EmpresaDTO empresaDTO, Empresa empresa) {
		empresa = preenche(empresaDTO, empresa);
		Endereco endereco = copiaEndereco(empresaDTO.getEndereco(), empresa.getEndereco());
		empresa.setEndereco(endereco);
		return empresa;
	}
	
	private Empresa preenche(EmpresaDTO empresaDTO, Empresa empresa) {
		empresa.setCnpj(empresaDTO.getCnpj());
		empresa.setContato(empresaDTO.getContato());
		empresa.setEmail(empresaDTO.getEmail());
		empresa.setNome(empresaDTO.getNome());
		empresa.setRazaoSocial(empresaDTO.getRazaoSocial());
		empresa.setTipo(TipoEmpresa.get(empresaDTO.getTipo()));
		return empresa;
	}
	
	private Endereco copiaEndereco(EnderecoDTO enderecoDTO, Endereco endereco) {
		endereco.setBairro(enderecoDTO.getBairro());
		endereco.setCep(enderecoDTO.getCep());
		endereco.setCidade(enderecoDTO.getCidade());
		endereco.setComplemento(enderecoDTO.getComplemento());
		endereco.setEstado(enderecoDTO.getEstado());
		endereco.setLogradouro(enderecoDTO.getLogradouro());
		return endereco;
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public List<EmpresaDTO> listAll() {
		List<Empresa> lista= repository.findAll();
		List<EmpresaDTO> listaDto = lista.stream()
				.map(empresa -> new EmpresaDTO().build(empresa))
				.collect(Collectors.toList());
		return listaDto;
	}
	
	public EmpresaDTO load(Long id) {
		try {
			return new EmpresaDTO().build(repository.getOne(id));
		} catch (EntityNotFoundException e ) {
			throw new ObjetoNotFoundException("Empresa id [" + id + "] não encontrada");
		}
	}
	
	public List<EmpresaDTO> listBytipo (String tipo) {
		List<Empresa> lista= repository.findByTipoOrderByNome(TipoEmpresa.get(tipo.toUpperCase()));
		List<EmpresaDTO> listaDto = lista.stream()
				.map(empresa -> new EmpresaDTO().build(empresa))
				.collect(Collectors.toList());
		return listaDto;
	}
	
	public List<EmpresaDTO> resume(EmpresaFilter filter) {
		
		Example example = criaExample(filter);
		List<Empresa> lista = repository.findAll(example);
		List<EmpresaDTO> listaDto = lista.stream()
				.map(empresa -> new EmpresaDTO().build(empresa))
				.collect(Collectors.toList());
		return listaDto;
	}
	
	private Example criaExample(EmpresaFilter filter) {
		ExampleMatcher matcher =null;
		
		Empresa empresaExample = new Empresa();
		empresaExample.setCnpj(filter.getCnpj());
		empresaExample.setNome(filter.getNome());
		
		if ( !StringUtils.isEmpty(filter.getTipo())) {
			empresaExample.setTipo(TipoEmpresa.get(filter.getTipo()));
		}
		boolean possuiNome = !StringUtils.isEmpty(filter.getNome());
		if (possuiNome) {
			matcher = ExampleMatcher.matchingAll()
					.withIgnoreCase("nome")
					.withStringMatcher(StringMatcher.CONTAINING);
		}
		return possuiNome ? Example.of(empresaExample, matcher) :
				Example.of(empresaExample);
	}
	
}
