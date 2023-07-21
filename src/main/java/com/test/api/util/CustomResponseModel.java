package com.test.api.util;

public class CustomResponseModel {
	private int status;
	private String mensagem;
	
	public CustomResponseModel() {}
	
	public CustomResponseModel(int status, String mensagem) {
		this.status = status;
		this.mensagem = mensagem;
	}
	
	public int getStatus() {
		return status;
	}
	public CustomResponseModel setStatus(int status) {
		this.status = status;
		return this;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	public CustomResponseModel setMensagem(String mensagem) {
		this.mensagem = mensagem;
		return this;
	}
}
