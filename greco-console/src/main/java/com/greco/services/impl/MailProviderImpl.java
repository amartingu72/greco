package com.greco.services.impl;

import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.greco.entities.Community;
import com.greco.entities.User;
import com.greco.repositories.UserDAO;
import com.greco.services.MailProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.MemberItem;
import com.greco.services.helpers.ReservationItem;
import com.greco.services.helpers.UserItem;
import com.greco.utils.EMail;

@Service("mailProvider")
public class MailProviderImpl implements MailProvider {
	
	//private final String JNDI_NAME="java:jboss/mail/Default";  //Para JBOSS
	
	//private final String JNDI_NAME="mail/gmailAccount";  //Para Glassfish.
	
	
    public JavaMailSender emailSender;
	
	private final String SENDER="admin@alnura.es";

	@Resource(name="UsersRepository")
	private UserDAO usersRepository;
	
	
	private final String BUNDLE_NAME = "com.greco.emails"; //$NON-NLS-1$

	private final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);
	
	
	
	
	
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
	@Autowired
	public MailProviderImpl(JavaMailSender sender) {
        this.emailSender = sender;
    }
	/**
	 * Envía un correo
	 * @param email  Dirección de correo.
	 * @param subject Asunto
	 * @param content Contenido
	 * @throws MessagingException
	 */
	private void send(String emailTo, String subject, String body){
		 MimeMessagePreparator message = newMessage -> {
	            newMessage.setRecipient(
	                    Message.RecipientType.TO,
	                    new InternetAddress(emailTo)
	            );
	            newMessage.setFrom(SENDER);
	            newMessage.setSubject(subject);
	            newMessage.setText(body);
	        };

	        this.emailSender.send(message);
		
		/*
		
		
		
		
		MimeMessage message = new MimeMessage(mailSession);
		
		Address toAddress = new InternetAddress(email);
		Address fromAddress = new InternetAddress(SENDER);
		message.setRecipient(RecipientType.TO, toAddress);
		message.setSubject(subject);
		message.setFrom(fromAddress);
		
		
		
		
		message.setText(content);
		message.saveChanges();
		Transport tr = mailSession.getTransport();
		String serverPassword = mailSession.getProperty("mail.password");
		tr.connect(null, serverPassword);
		tr.sendMessage(message, message.getAllRecipients());
		tr.close();*/
	
	}

	@Override
	public void sendNewPassword(UserItem userItem, String newPwd)  throws MessagingException{
				
		//Contenido
		
		String content= getString("content.greeting") + userItem.getNickname() + ":";
		content+=getString("pwdforgotten.text1") + newPwd + "\n";
		content+=getString("pwdforgotten.text2") + "\n";
		content+="- " + getString("pwdforgotten.text3") + "\n";
		User user=usersRepository.loadSelectedUser(userItem.getId());
		Iterator<Community> it=user.getCommunities().iterator();
		Community community;
		while ( it.hasNext()) {
			community=it.next();
			content+="- " + community.getName() +" ("+ getString("signature.locale_reference") + community.getId() +")\n";	
		}
		
		content+=getString("signature.admin") + "\n\n";
		
		send(userItem.getEmail(), getString("pwdforgotten.subject"), content);
		
		

	}
	@Override
	public void sendUnsubscribed(UserItem userItem, CommunityItem communityItem)
			throws MessagingException {
		
		String subject=communityItem.getName()+". "+getString("unsubscribed.subject");
		
		
		//Contenido
		String content= getString("content.greeting") + userItem.getNickname() + ":";
		content+=getString("unsubscribed.text2");
		content+=getString("signature.locale_admin");
		content+=getString("signature.locale_reference") + communityItem.getId(); //URL de la comunidad.
		
		send(userItem.getEmail(), subject,content);
		
	}


	@Override
	public void sendSubscriptionRejected(MemberItem memberItem,
			CommunityItem communityItem, String rejectionMsg)
			throws MessagingException {
		
		String subject=communityItem.getName()+". "+getString("rejected.subject");
		
		
		//Contenido
		String content= getString("content.greeting") + memberItem.getNickname() + ":";
		content+=getString("rejected.text2");
		//Si el adminsitrador indicó alguna causa, lo incluimos.
		if ( (rejectionMsg!=null) && !rejectionMsg.trim().equals("") ){
			content+=getString("rejected.text3");
			content+="\n\n" + rejectionMsg + "\n\n";
		}
		
		if ( (memberItem.getApplication()!=null) && !memberItem.getApplication().trim().equals("") ){
			content+="\n" + getString("rejected.text4");
			content+="\n\n" + memberItem.getApplication() + "\n";
		}
		
		content+=getString("rejected.text5");
		
			
		content+=getString("signature.locale_admin");
		content+=getString("signature.locale_reference") + communityItem.getId(); //URL de la comunidad.
		
		send(memberItem.getEmail(),subject, content);
		
		
		
		
	}


	@Override
	public void sendSubscriptionApproved(UserItem userItem,
			CommunityItem communityItem)
			throws MessagingException {
		
		String subject=communityItem.getName()+". "+getString("approved.subject");
		String content= getString("content.greeting") + userItem.getNickname() + ":";
		
		content+=getString("approved.text1");
		content+=getString("signature.locale_reference") + communityItem.getId();
		content+=getString("signature.locale_admin");
		content+=getString("signature.locale_reference") + communityItem.getId(); //URL de la comunidad.
		
		send(userItem.getEmail(),subject, content);
	}




	@Override
	public void sendSubscriptionPending(UserItem userItem,
			CommunityItem communityItem) throws MessagingException {
		
		String subject=communityItem.getName() + ". " + getString("pending.subject");
		String content=getString("content.greeting") + userItem.getNickname() + ":";
		content+=getString("pending.text1");
		
		content+=getString("signature.locale_admin");
		content+=getString("signature.locale_reference") + communityItem.getId(); //URL de la comunidad.
		
		send(userItem.getEmail(),subject, content);
		
		
	}




	@Override
	public void sendYouAreAdmin(UserItem userItem, CommunityItem communityItem)
			throws MessagingException {
		String subject=communityItem.getName() + ". " + getString("setadmin.subject");
		String content=getString("content.greeting") + userItem.getNickname() + ":";
		content+=getString("setadmin.text1");
		content+=getString("signature.console");
		
		content+=getString("signature.locale_admin");
		content+=getString("signature.locale_reference") + communityItem.getId(); //URL de la comunidad.
		
		send(userItem.getEmail(),subject, content);
		
	}




	@Override
	public void sendYouAreNotAdmin(UserItem userItem,
			CommunityItem communityItem) throws MessagingException {
		String subject=communityItem.getName() + ". " + getString("unsetadmin.subject");
		String content=getString("content.greeting") + userItem.getNickname() + ":";
		content+=getString("unsetadmin.text1");
		
		content+=getString("signature.admin");
		content+=getString("signature.reference");
		
		send(userItem.getEmail(),subject, content);
		
	}




	@Override
	public void sendReservConfirmation(UserItem userItem,
			CommunityItem communityItem, List<ReservationItem> reservations)
			throws MessagingException {
		String subject=communityItem.getName() + ". " + getString("reservation.subject");
		String content=getString("content.greeting") + userItem.getNickname() + ":";
		content+=getString("reservation.text1") + "\n";
		
		//Incluimos las reservas confirmadas.
		Iterator<ReservationItem> it=reservations.iterator();
		ReservationItem reservationItem=null;
		while (it.hasNext()){
			reservationItem=it.next();
			content+="\n- " + reservationItem.getDate() + " - " +reservationItem.getName() + " (" + reservationItem.getTypeDesc() + ") ";
			content+=reservationItem.getFromTime() + " - " + reservationItem.getToTime(); 
		}
		
		content+=getString("signature.locale_admin");
		content+=getString("signature.locale_reference") + communityItem.getId(); //URL de la comunidad.
		
		send(userItem.getEmail(),subject, content);
	}




	@Override
	public void sendCancelReservation(UserItem userItem,
			CommunityItem communityItem, ReservationItem reservationItem)
			throws MessagingException {
		String subject=communityItem.getName() + ". " + getString("cancelation.subject");
		String content=getString("content.greeting") + userItem.getNickname() + ":";
		content+=getString("cancelation.text1") + "\n";
		
		
		
		content+="\n- " + reservationItem.getDate() + " - " +reservationItem.getName() + " (" + reservationItem.getType() + ") ";
		content+=reservationItem.getFromTime() + " - " + reservationItem.getToTime(); 
		
		
		content+=getString("signature.locale_admin");
		content+=getString("signature.locale_reference") + communityItem.getId(); //URL de la comunidad.
		
		send(userItem.getEmail(),subject, content);
		
	}




	@Override
	public void sendMessageToAdmin(List<MemberItem> admins, EMail email, CommunityItem communityItem,
			String message) throws MessagingException {
		//Subject
		String subject=communityItem.getName() + ". " + getString("adminmsg.subject");
		Iterator<MemberItem> it=admins.iterator();
		MemberItem admin;
		while ( it.hasNext()){
			admin=it.next();
			String content=getString("content.greeting") + admin.getNickname() + ":";
			content+=getString("adminmsg.text1");
	
			content+=message;
			content+=getString("adminmsg.text2") + email.toString() +"\n\n";
			
			content+=getString("signature.admin");
			content+=getString("signature.locale_reference") + communityItem.getId(); //URL de la comunidad.
			
			send(admin.getEmail(),subject, content);		
		}
	}
	
	@Override
	public void sendActivationMsg(UserItem userItem) throws MessagingException {
		String subject=getString("activation.subject");
		String content=getString("content.greeting") + userItem.getNickname() + ":";
		content+=getString("activation.text1");
		content+=getString("activation.text2") + userItem.getActCode();
		content+=getString("signature.admin");
		send(userItem.getEmail(),subject, content);
	}
	
	@Override
	public void sendActivation2Msg(UserItem userItem) throws MessagingException {
		String subject=getString("activation2.subject");
		String content=getString("content.greeting") + userItem.getNickname() + ":";
		content+=getString("activation2.text1");
		content+=getString("activation2.text2") + userItem.getActCode();
		content+=getString("signature.admin");
		send(userItem.getEmail(),subject, content);
	}
	
	@Override
	public void sendActivationMsg(UserItem userItem, CommunityItem communityItem) throws MessagingException {
		String subject=communityItem.getName() + " - " + getString("activation.locale_subject");
		String content=getString("content.greeting") + userItem.getNickname() + ":";
		content+=getString("activation.text1");
		content+=getString("activation.text2") + userItem.getActCode();
		content+=getString("signature.admin");
		content+=getString("signature.locale_reference") + communityItem.getId(); //URL de la comunidad.
		send(userItem.getEmail(),subject, content);
	}
}
