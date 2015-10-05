package com.greco.services.impl;

import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.stereotype.Service;

import com.greco.entities.Community;
import com.greco.entities.User;
import com.greco.repositories.UserDAO;
import com.greco.services.MailProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.UserItem;

@Service("mailProvider")
public class MailProviderImpl implements MailProvider {
	
	private final String JNDI_NAME="java:jboss/mail/Default";  //Para JBOSS
	//private final String JNDI_NAME="mail/gmailAccount";  //Para Glassfish
	
	@Resource(name="UsersRepository")
	private UserDAO usersRepository;
	
	
	private Session mailSession;
	
	private final String BUNDLE_NAME = "com.greco.emails"; //$NON-NLS-1$

	private final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);
	
	@PostConstruct
	public void init(){
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			 mailSession =(Session) ctx.lookup(JNDI_NAME);
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
       
	}
	
	
	@Override
	public void sendNewPassword(UserItem userItem, String newPwd)  throws MessagingException{
		
		MimeMessage message = new MimeMessage(mailSession);
		
		Address toAddress = new InternetAddress(userItem.getEmail());
		message.setRecipient(RecipientType.TO, toAddress);
		message.setSubject(getString("pwdforgotten.subject"));
		
		//Contenido
		String content=getString("pwdforgotten.text1") + newPwd + "\n";
		content+=getString("pwdforgotten.text2") + "\n";
		content+="- " + getString("pwdforgotten.text3") + "\n";
		User user=usersRepository.loadSelectedUser(userItem.getId());
		Iterator<Community> it=user.getCommunities().iterator();
		Community community;
		while ( it.hasNext()) {
			community=it.next();
			content+="- " + community.getName() +" ("+ getString("pwdforgotten.text4") + community.getId() +")\n";	
		}
		
		content+=getString("signature.admin") + "\n\n";
		message.setText(content);
		message.saveChanges();
		Transport tr = mailSession.getTransport();
		String serverPassword = mailSession.getProperty("mail.password");
		tr.connect(null, serverPassword);
		tr.sendMessage(message, message.getAllRecipients());
		tr.close();

	}
	
	/**
	 * Obtiene los textos del archivo de properties asociado.
	 * @param key Clave.
	 * @return Texto correspondiente a la clave.
	 */
	private String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}


	@Override
	public void sendUnsubscribed(UserItem userItem, CommunityItem communityItem)
			throws MessagingException {
		MimeMessage message = new MimeMessage(mailSession);
		
		Address toAddress = new InternetAddress(userItem.getEmail());
		message.setRecipient(RecipientType.TO, toAddress);
		String subject=communityItem.getName()+". "+getString("unsubscribed.subject");
		message.setSubject(subject);
		
		//Contenido
		String content=getString("unsubscribed.text1") + " " + userItem.getNickname() + ":";
		content+=getString("unsubscribed.text2");
		content+=getString("signature.locale_admin");
		content+=getString("signature.locale_reference") + communityItem.getId(); //URL de la comunidad.
		
		message.setText(content);
		message.saveChanges();
		Transport tr = mailSession.getTransport();
		String serverPassword = mailSession.getProperty("mail.password");
		tr.connect(null, serverPassword);
		tr.sendMessage(message, message.getAllRecipients());
		tr.close();
		
	}


	@Override
	public void sendSubscriptionRejected(UserItem userItem,
			CommunityItem communityItem, String rejectionMsg)
			throws MessagingException {
		MimeMessage message = new MimeMessage(mailSession);
		
		Address toAddress = new InternetAddress(userItem.getEmail());
		message.setRecipient(RecipientType.TO, toAddress);
		String subject=communityItem.getName()+". "+getString("rejected.subject");
		message.setSubject(subject);
		
		//Contenido
		String content=getString("rejected.text1") + " " + userItem.getNickname() + ":";
		content+=getString("rejected.text2");
		//Si el adminsitrador indicó alguna causa, lo incluimos.
		if ( rejectionMsg.trim().equals("") || (rejectionMsg==null)){
			content+=getString("rejected.text3");
			content+="\n\n" + rejectionMsg + "\n\n";
		}
		content+=getString("rejected.text4");
		
			
		content+=getString("signature.locale_admin");
		content+=getString("signature.locale_reference") + communityItem.getId(); //URL de la comunidad.
		
		message.setText(content);
		message.saveChanges();
		Transport tr = mailSession.getTransport();
		String serverPassword = mailSession.getProperty("mail.password");
		tr.connect(null, serverPassword);
		tr.sendMessage(message, message.getAllRecipients());
		tr.close();
		
	}

}
