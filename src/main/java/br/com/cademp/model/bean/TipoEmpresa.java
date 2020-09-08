package br.com.cademp.model.bean;

public enum TipoEmpresa {

	FILIAL,MATRIZ;
	
	public static TipoEmpresa get(String descricao) {
		switch (descricao.toUpperCase()) {
		case "FILIAL" :
			return TipoEmpresa.FILIAL;
		case "MATRIZ" :
			return TipoEmpresa.MATRIZ;
		default:
			throw new RuntimeException("Tipo de empresa [" + descricao + "] nao encontrado");
		}
	}
}
