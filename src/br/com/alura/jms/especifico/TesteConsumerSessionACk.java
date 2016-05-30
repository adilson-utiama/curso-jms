package br.com.alura.jms.especifico;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
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

public class TesteConsumerSessionACk {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
		

		Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila);
		
		consumer.setMessageListener(new MessageListener(){

		    @Override
		    public void onMessage(Message message){
		    	ObjectMessage ObjectMsg = (ObjectMessage) message;
		    	
		        try {
		        	Pedido pedido = (Pedido) ObjectMsg.getObject();
					System.out.println(pedido.getCodigo());
					session.commit();
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
