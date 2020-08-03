package br.com.cademp.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cademp.model.service.CepService;
import br.com.cademp.resource.vo.BuscaCepVO;

@RestController
@RequestMapping("/enderecos")
public class EnderecoResource {
	
	@Autowired
	private CepService service;

	@GetMapping
	@RequestMapping("/cep/{cep}")
	public ResponseEntity<BuscaCepVO> busca(@PathVariable Integer cep) {
		BuscaCepVO enderecoDTO = service.busca(cep);
		return ResponseEntity.ok(enderecoDTO);
	}
}
