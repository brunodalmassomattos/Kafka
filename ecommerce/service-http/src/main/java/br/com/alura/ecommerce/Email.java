package br.com.alura.ecommerce;

public class Email {
	private String subject;
	private String body;

	public Email() {
		this.subject = "";
		this.body = "Obrigado, seu pedido esta sendo processado";
	}

	public Email(String subject, String body) {
		this.subject = subject;
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
