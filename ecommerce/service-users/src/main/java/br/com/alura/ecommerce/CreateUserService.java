package br.com.alura.ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class CreateUserService {

	private final Connection connection;

	CreateUserService() throws SQLException {
		String url = "jdbc:sqlite:target/users_database.db";
		connection = DriverManager.getConnection(url);
		connection.createStatement().execute("create table Users (uuid varchar(200) primary key, email varchar(200))");
	}

	public static void main(String[] args) throws SQLException {
		CreateUserService fraudService = new CreateUserService();

		try (KafkaService<Order> kaflaService = new KafkaService<>(CreateUserService.class.getSimpleName(),
				"LOJA_NOVO_PEDIDO", fraudService::parse, Order.class, Map.of())) {
			kaflaService.run();
		}
	}

	private void parse(ConsumerRecord<String, Order> registro) throws SQLException {
		System.out.println("CHECANDO NOVO USUARIO");
		System.out.println(registro.value());

		Order ordem = registro.value();
		if (isNewUser(ordem.getEmail())) {
			insertNewUser(ordem.getEmail());
		}
	}

	private void insertNewUser(String email) throws SQLException {
		PreparedStatement insert = connection.prepareStatement("insert into Users (uuid, email) values (?, ?)");
		insert.setString(1, "uuid");
		insert.setString(2, email);
		insert.execute();

		System.out.println("Usuario criado!");
	}

	private boolean isNewUser(String email) throws SQLException {
		PreparedStatement select = connection.prepareStatement("select uuid from Users where email = ? limit 1");
		select.setString(1, email);

		ResultSet executeQuery = select.executeQuery();
		return !executeQuery.next();
	}
}
