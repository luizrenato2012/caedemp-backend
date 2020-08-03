package br.com.cademp.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cademp.model.bean.Empresa;
import br.com.cademp.model.bean.TipoEmpresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>{
	
	public List<Empresa> findByTipoOrderByNome(TipoEmpresa tipo);

}
