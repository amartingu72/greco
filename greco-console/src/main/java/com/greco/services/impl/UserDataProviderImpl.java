package com.greco.services.impl;


import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greco.beans.UserSBean;
import com.greco.entities.Community;
import com.greco.entities.Profile;
import com.greco.entities.User;
import com.greco.entities.UsersCommunity;
import com.greco.repositories.CommunityDAO;
import com.greco.repositories.ProfileDAO;
import com.greco.repositories.UserCommunitiesDAO;
import com.greco.repositories.UserDAO;
import com.greco.services.MailProvider;
import com.greco.services.UserDataProvider;
import com.greco.services.except.user.NoMemberException;
import com.greco.services.except.user.PendingException;
import com.greco.services.except.user.UnavailableCommunity;
import com.greco.services.except.user.UnknownCommunityException;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.StatusItem;
import com.greco.services.helpers.UserItem;


/**
 * 
 * @author Alberto Martín
 *
 */

@Service("userDataProvider") 
public class UserDataProviderImpl implements UserDataProvider{
	
	@Resource(name="UsersRepository")
	private UserDAO usersRepository;
	
	@Resource(name="CommunityRepository")
	private CommunityDAO communityDAO;
	
	@Resource(name="UserCommunitiesRepository")
	private UserCommunitiesDAO userCommunitiesDAO;
	
	@Resource(name="ProfilesRepository")
	private ProfileDAO profileDAO;
	
	@Resource(name="mailProvider")
	private MailProvider mailProvider;
	
	
	
	@SuppressWarnings("unused")
	private final String SUPERUSER_PROFILE="SUPERUSER";
	private final String USER_PROFILE="USER";
	
	@Override
	@Transactional
	public void save(UserItem userItem, boolean pwdUpdated) {
		User user=usersRepository.loadSelectedUser(userItem.getId());
		
		//Si ha cambiado la cuenta de correo, se requerirá código de activación en el próximo login. Enviamos correo con el nuevo código de activación.
		if ( !user.getEmail().equals(userItem.getEmail()) ){
			String actCode=RandomStringUtils.randomAlphanumeric(6);
			user.setActcode(actCode);
			userItem.setActCode(actCode);
			//Enviamos correo.
			try {
				mailProvider.sendActivation2Msg(userItem); 
			} catch (MessagingException e) {
				// No hay nada que hacer si no es posible enviar el correo.
				
				e.printStackTrace();
			}
		}
		
		user.setEmail(userItem.getEmail());
		user.setMydata(userItem.getMydata());
		user.setNickname(userItem.getNickname());
		
		if (pwdUpdated) {
			//Generamos hash
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(userItem.getPassword());
			user.setPassword(hashedPassword);
		}
		user.setAdds((byte) (userItem.isAdds() ? 1 : 0 ));
		
		usersRepository.saveUser(user);
	}
	
	@Override
	@Transactional
	public String changePassword(int userId){
		//Generamos una nueva contraseña aleatoria.
		String pwd=RandomStringUtils.randomAlphanumeric(8);
		User user=usersRepository.loadSelectedUser(userId);
		//Encriptamos
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(pwd);
		user.setPassword(hashedPassword);
		
		
		usersRepository.saveUser(user);
		
		return pwd;
	}

	@Override
	public UserSBean loadUserCredentials(String email, int communityId) 
			throws NoMemberException, UnknownCommunityException, UnavailableCommunity, PendingException {
		//Comprobamos que el identificador de comunidad coincide con un ID de comunidad válido.
		Community com=communityDAO.loadSelectedCommunity(communityId);
		if ( com == null) 
			throw new UnknownCommunityException();
		
		//Comprobamos que la comunidad esté activa.
		if ( com.getAvailable()==0 ) 
			throw new UnavailableCommunity();
		
		User user=usersRepository.loadSelectedUserByMail(email);
			
		//Comprobamos que la suscripción está activa.
		UsersCommunity uc=userCommunitiesDAO.getSubscription(user.getId(), communityId);
		if (uc==null)
			throw new NoMemberException();
		if ( uc.getStatus()== UserCommunitiesDAO.PENDING_STATUS)
			throw new PendingException();
		
		
		//Una vez hechas las validaciones, cargamos el bean.
		UserSBean userbean=null;
		if ( user != null ) {
			userbean=new UserSBean();
			userbean.setId(user.getId());
			userbean.setNickname(user.getNickname());
			userbean.setEmail(user.getEmail());
			userbean.setMydata(user.getMydata());
			userbean.setCommunityId(communityId);
			userbean.setProfile(uc.getProfile().getProfile());
			userbean.setPassword(user.getPassword());
			userbean.setAdds(user.getAdds()!=0);
			userbean.setActCode(user.getActcode());
			
		}
		
		return userbean;
	}

	
	
	@Override
	/**
	 * Completa los datos de usuario a partir del correo.
	 * @param email
	 * @return El bean o null si el usuario no exisite.
	 */
	public UserSBean loadAdminCredentials(String email) {
		
		//Comprobamos usuario y contraseña
		User user=usersRepository.loadSelectedUserByMail(email);
		UserSBean userbean=null;
		
		if ( user != null) {
			userbean=new UserSBean();
			userbean.setId(user.getId());
			userbean.setNickname(user.getNickname());
			userbean.setEmail(user.getEmail());
			userbean.setMydata(user.getMydata());		
			userbean.setCommunityId(UserSBean.UNDEFINED_COMMUNITY);
			userbean.setPassword(user.getPassword());
			userbean.setAdds(user.getAdds()!=0);
			userbean.setActCode(user.getActcode());
		}
		return userbean;
	}

	
	/* (non-Javadoc)
	 * @see com.greco.services.UserDataProvider#getMyComunities(com.greco.services.helpers.UserBean)
	 */
	public CommunityItem[] getMyComunities(UserSBean userBean) {

		CommunityItem[] myCommunities;


		User user=usersRepository.loadSelectedUser(userBean.getId());
		//Obtengo la lista de comunidades.
		List<Community> coms=user.getCommunities();
		

		//Cargo la tabla con mis comunidades
		Iterator<Community> it = coms.iterator();
		Community com;
		myCommunities = new CommunityItem[coms.size()];
		int i=0;
		UsersCommunity ucItem;
		String profiles="-";
		String joiningDate="-";
		while (it.hasNext()) {
			com=it.next();

			//Busco la fecha en que me uní a la comunidad y mis perfiles
			Iterator<UsersCommunity> itUC=com.getUsersCommunities().iterator();
			boolean firstEvent=false; 
			//La primera ocurrencia de mi usuario, será la fecha de adhesión.
			//Aunque en users_communities solo debería haber una entrada por comunidad.
			while (itUC.hasNext()) {
				ucItem=itUC.next();
				if ( userBean.getId() == ucItem.getUser().getId() ) {
					if ( !firstEvent ) {
						firstEvent=true;
						joiningDate=new SimpleDateFormat("MM/dd/yyyy HH:mm").format(ucItem.getRegisterDate());
						profiles=ucItem.getProfile().getProfile();
					} else 	profiles=", " + ucItem.getProfile().getProfile();
				}

			}

			//Incluimos fila en lista de comunidades
			//Recordar: available en BBDD es un TINYINT y hay que convertir a boolean.
			myCommunities[i]=new CommunityItem(com.getId(),(com.getAvailable()!=0), com.getName(),
					com.getZipcode(),joiningDate,profiles,com.getNotes(), com.getCountry().getName());
			myCommunities[i].setMembercheck((com.getMembercheck()!=0));
			i++;
		}
		
		return myCommunities;
	}
	
	
	public boolean isDuplicated(String nickname){
		return usersRepository.loadSelectedUser(nickname)!=null;
	}
	
	@Override
	@Transactional
	public int addUser(UserItem userItem, String actCode){
		//Creamos el usuario.
		User user=new User();
		user.setEmail(userItem.getEmail());
		user.setMydata(userItem.getMydata());
		user.setNickname(userItem.getNickname());
		user.setAdds((byte) (userItem.isAdds() ? 1 : 0 ));
		
		//Generamos código de activación si actCode==null;
		String myActCode;
		if (actCode==null){
			myActCode=RandomStringUtils.randomAlphanumeric(6);
			
		}
		else myActCode=actCode;
		user.setActcode(myActCode);
		
		//Encriptamos
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(userItem.getPassword());
		user.setPassword(hashedPassword);
		
		user.setProfile(USER_PROFILE);
				
		User newUser=this.usersRepository.newUser(user);
	
		userItem.setId(newUser.getId());
		userItem.setActCode(myActCode);
		//Enviamos correo con código de activación.
		
		try {
			mailProvider.sendActivationMsg(userItem);
		} catch (MessagingException e) {
			// No hay nada que hacer si no es posible enviar el correo.
			
			e.printStackTrace();
		}
		
		return newUser.getId();
		
	}

	@Override
	@Transactional
	public int addUser(UserItem userItem){		
		return addUser(userItem, null);
	}
	

	
	@Override
	@Transactional
	public void subscribe(int userId, int communityId, String application){
		
		
		//Suscribimos a la comunidad en estado "pendiente".
		User user=usersRepository.loadSelectedUser(userId);
		Community community=communityDAO.loadSelectedCommunity(communityId);
		Profile profile=profileDAO.loadSelected(ProfileDAO.ROLE_USER_ID);
		
		UsersCommunity usersCommunity=new UsersCommunity();
		usersCommunity.setCommunity(community);
		usersCommunity.setProfile(profile);
		
		//Comprobamos si la comunidad está configurada con aprobación automática de 
		//suscripción o no. En caso de estarlo, se aprueba y se envía correo.
		boolean membercheck=community.getMembercheck()!=0;
		if (membercheck)
			usersCommunity.setStatus(StatusItem.PENDING);
		else
			usersCommunity.setStatus(StatusItem.MEMBER);
		
		usersCommunity.setUser(user);
		usersCommunity.setApplication(application);
		//Evitar que un refresco genere varias suscripciones.
		if ( userCommunitiesDAO.getSubscription(userId, communityId)==null)	
			this.userCommunitiesDAO.add(usersCommunity);	
		
		
	}

	@Override
	public UserItem getUserItem(String email) {
		UserItem userItem=null;
		User user=usersRepository.loadSelectedUserByMail(email);
		if ( user!= null){
			userItem=new UserItem();
			userItem.setEmail(user.getEmail());
			userItem.setId(user.getId());
			userItem.setMydata(user.getMydata());
			userItem.setNickname(user.getNickname());
			userItem.setPassword(user.getPassword());
			userItem.setProfile(USER_PROFILE);
			userItem.setAdds(user.getAdds()!=0);
			userItem.setActCode(user.getActcode());
			
		}
		return userItem;
	}

	@Override
	public UserItem getUserItemByNick(String nickname) {
		UserItem userItem=null;
		User user=usersRepository.loadSelectedUser(nickname);
		if ( user!= null){
			userItem=new UserItem();
			userItem.setEmail(user.getEmail());
			userItem.setId(user.getId());
			userItem.setMydata(user.getMydata());
			userItem.setNickname(user.getNickname());
			userItem.setPassword(user.getPassword());
			userItem.setProfile(USER_PROFILE);
			userItem.setAdds(user.getAdds()!=0);
			userItem.setActCode(user.getActcode());
		}
		return userItem;
	}

	@Override
	@Transactional
	public boolean activate(UserItem userItem, String code) {
		boolean ret=false;
		if ( userItem.getActCode().equals(code) ){
			//Si el código de activación coincide, activar.
			this.usersRepository.activate(userItem.getId());
			ret=true;
		}
		
		return ret;
	}

	@Override
	@Transactional
	public void remove(UserItem userItem) {
		usersRepository.remove(userItem.getId());
		
	}

	@Override
	public void sendActivactionCode(UserItem userItem) {
		try {
			mailProvider.sendActivationMsg(userItem);
		} catch (MessagingException e) {
			// No hay nada que hacer si no es posible enviar el correo.
			
			e.printStackTrace();
		}
	}

	
	
}
