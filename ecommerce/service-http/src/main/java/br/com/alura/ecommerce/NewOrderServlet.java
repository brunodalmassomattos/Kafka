package br.com.alura.ecommerce;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewOrderServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>()) {
			try (KafkaDispatcher<Email> emailDispatcher = new KafkaDispatcher<>()) {
				try {
//					String email = getSaltString() + "@email.com";
					String email = req.getParameter("email");
					
//					BigDecimal amount = new BigDecimal((Math.random() * 5000) + 1);
					BigDecimal ticketPrice = new BigDecimal(200);
					BigDecimal amount = new BigDecimal(req.getParameter("qtd")).multiply(ticketPrice);
					
					String orderID = UUID.randomUUID().toString();
					Order order = new Order(orderID, amount, email);
					
					orderDispatcher.send("LOJA_NOVO_PEDIDO", email, order);
					emailDispatcher.send("LOJA_EMAIL_PEDIDO", email, new Email());
					
					resp.setStatus(HttpServletResponse.SC_OK);
					resp.getWriter().println("Nova ordem enviada!");
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected static String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 10) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}

		return salt.toString();
	}
}
