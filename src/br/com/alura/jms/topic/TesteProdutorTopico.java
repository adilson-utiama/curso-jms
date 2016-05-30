package br.com.alura.jms.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorTopico {

	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("loja");
				
		MessageProducer producer = session.createProducer(fila);
		
		
		Message message = session.createTextMessage("<pedido><id>2222</id></pedido>");
		producer.send(message);
		System.out.println("Mensagem enviada com sucesso!");
		
		session.close();
		connection.close();
		context.close();
	}

	
}
