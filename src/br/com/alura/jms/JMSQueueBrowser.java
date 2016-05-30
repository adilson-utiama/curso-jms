package br.com.alura.jms;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class JMSQueueBrowser {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("financeiro");
		QueueBrowser browser = session.createBrowser((Queue) fila);
		
		Enumeration enumeration = browser.getEnumeration();
		while(enumeration.hasMoreElements()) {
			TextMessage msg = (TextMessage) enumeration.nextElement(); 
		    System.out.println("Message: " + msg.getText()); 
		}
		
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}
}
