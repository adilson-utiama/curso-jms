package br.com.alura.jms.topic.selectors;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

import br.com.alura.jms.modelo.Pedido;

public class TesteConsumerTopicoComercial2 {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection();
		connection.setClientID("comercial");
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Topic topico = (Topic) context.lookup("loja");
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura");
		
		consumer.setMessageListener(new MessageListener(){

		    @Override
		    public void onMessage(Message message){
ObjectMessage ObjectMsg = (ObjectMessage) message;
		    	
		        try {
		        	
		        	Pedido pedido = (Pedido) ObjectMsg.getObject();
					System.out.println(pedido.getCodigo());
				} catch (JMSException e) {
					e.printStackTrace();
				}
		    }

		});
		
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}
}
