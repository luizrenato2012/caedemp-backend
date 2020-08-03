package br.com.cademp.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.cademp.resource.dto.EmpresaDTO;

@Repository
public interface EmpresaRepositoryFilter {
	
	public List<EmpresaDTO> resume (EmpresaFilter filter);

}
