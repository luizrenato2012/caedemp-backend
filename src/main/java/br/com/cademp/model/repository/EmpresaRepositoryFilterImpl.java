package br.com.cademp.model.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.cademp.model.bean.Empresa;
import br.com.cademp.model.bean.TipoEmpresa;
import br.com.cademp.resource.dto.EmpresaDTO;

@Repository
public class EmpresaRepositoryFilterImpl implements EmpresaRepositoryFilter{

	@Override
	public List<EmpresaDTO> resume(EmpresaFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
