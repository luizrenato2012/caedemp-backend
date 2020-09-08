package br.com.cademp.model.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cademp.exception.ObjetoNotFoundException;
import br.com.cademp.resource.vo.BuscaCepVO;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class CepService {

	public BuscaCepVO busca(Integer cep) {
		if (StringUtils.isEmpty(cep) || (cep+"").length()!=8) {
			throw new ObjetoNotFoundException("CEP invalido");
		}
		
		String body="";
		final String urlCep = "https://viacep.com.br/ws/" + cep + "/json";
		final String MENSAGEM_ERRO = "\"erro\": true";
		final int REQUEST_ERROR=400;
		try {

			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder().url(urlCep).build();
			Response response = client.newCall(request).execute();
			Headers headers = response.headers();
			body = response.body()!=null ? response.body().string() : "";
			if (body.contains(MENSAGEM_ERRO)) {
				throw new ObjetoNotFoundException("CEP " + cep + " não encontrado");
			}
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(body);
			return mapper.readValue(body, BuscaCepVO.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
