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

import br.com.cademp.errors.ObjetoNotFoundException;
import br.com.cademp.model.bean.Empresa;
import br.com.cademp.model.bean.TipoEmpresa;
import br.com.cademp.model.repository.EmpresaFilter;
import br.com.cademp.model.repository.EmpresaRepository;
import br.com.cademp.resource.dto.EmpresaDTO;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository repository;
	
	public EmpresaDTO save(EmpresaDTO empresa) {
		Empresa empresaNova = repository.save(empresa.parse());
		return new EmpresaDTO().build(empresaNova);
	}
	
	public EmpresaDTO update(EmpresaDTO empresaDto) {
		if (empresaDto.getId()==null || empresaDto.getId()==0) {
			throw new ObjetoNotFoundException("Empresa não encontrada par atualização");
		}
		Empresa empresaAtual = atualiza(empresaDto);
		
		Empresa empresaNova = repository.save(empresaAtual);
		return new EmpresaDTO().build(empresaNova);
	}

	private Empresa atualiza(EmpresaDTO empresaDto) {
		Optional<Empresa> optional = repository.findById(empresaDto.getId());
		Empresa empresaAtual = optional.orElseThrow(()->new ObjetoNotFoundException("Empresa nao encontrada"));
		empresaAtual.setCnpj(empresaDto.getCnpj());
		empresaAtual.setContato(empresaDto.getContato());
		empresaAtual.setEmail(empresaDto.getEmail());
		
		if (empresaDto.getIdMatriz()!=null) {
			empresaAtual.setMatriz(repository.getOne(empresaDto.getIdMatriz()));
		} else {
			empresaAtual.setMatriz(null);
		}
		empresaAtual.setNome(empresaDto.getNome());
		empresaAtual.setRazaoSocial(empresaDto.getRazaoSocial());
		empresaAtual.setTipo(TipoEmpresa.get(empresaDto.getTipo()));
		empresaAtual.setEndereco(empresaDto.getEndereco().parse());
		return empresaAtual;
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
