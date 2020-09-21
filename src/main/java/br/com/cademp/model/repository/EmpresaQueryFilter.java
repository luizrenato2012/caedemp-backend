package br.com.cademp.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cademp.model.bean.Empresa;
import br.com.cademp.resource.dto.EmpresaResumo;

@Repository
public interface EmpresaQueryFilter extends JpaRepository<Empresa, Long> {

	@Query(value="select new br.com.cademp.resource.dto.EmpresaResumo(e.id, e.tipo, e.nome, e.cnpj, e.endereco.cidade, e.endereco.estado) from Empresa e " +
				 " where e.matriz.id = :id")
	public Page<EmpresaResumo> resume (Long id, Pageable pageable);
}
