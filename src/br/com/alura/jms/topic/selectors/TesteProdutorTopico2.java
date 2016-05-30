package br.com.alura.jms.topic.selectors;

import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;

import br.com.alura.jms.modelo.Pedido;
import br.com.alura.jms.modelo.PedidoFactory;

public class TesteProdutorTopico2 {

	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("loja");
				
		MessageProducer producer = session.createProducer(fila);
		
		
		Pedido pedido = new PedidoFactory().geraPedidoComValores();

//		StringWriter writer = new StringWriter();
//		JAXB.marshal(pedido, writer);
//		String xml = writer.toString();

		Message message = session.createObjectMessage(pedido);
		
		//message.setBooleanProperty("ebook", true);
		
		producer.send(message);
		System.out.println("Mensagem enviada com sucesso!");
		
		session.close();
		connection.close();
		context.close();
	}

	
}
