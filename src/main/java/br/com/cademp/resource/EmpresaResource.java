package br.com.cademp.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cademp.model.repository.EmpresaFilter;
import br.com.cademp.model.service.EmpresaService;
import br.com.cademp.resource.dto.EmpresaDTO;

@RestController
@RequestMapping("/empresas")
public class EmpresaResource {
	
	@Autowired
	private EmpresaService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<EmpresaDTO> busca(@PathVariable Long id) {
		EmpresaDTO empresa= service.load(id);
		return ResponseEntity.ok(empresa);
	}
	
	@PostMapping
	public ResponseEntity<EmpresaDTO> inclui(@Valid @RequestBody EmpresaDTO empresa) {
		EmpresaDTO empresaDtoNova = service.save(empresa);
		ResponseEntity response = new ResponseEntity(HttpStatus.CREATED);
		return response.of(Optional.of(empresaDtoNova));
	}
	
	@PutMapping
	public ResponseEntity<EmpresaDTO> altera(@Valid @RequestBody EmpresaDTO empresa) {
		EmpresaDTO empresaDtoNova = service.update(empresa);
		ResponseEntity response = new ResponseEntity(HttpStatus.CREATED);
		return response.of(Optional.of(empresaDtoNova));
	}
	
	@GetMapping
	public ResponseEntity<List<EmpresaDTO>> pesquisaResumo(EmpresaFilter filter) {
		List<EmpresaDTO> lista = service.resume(filter);
		return ResponseEntity.ok(lista);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity exclui(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
