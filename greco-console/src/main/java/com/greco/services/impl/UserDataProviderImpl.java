package com.greco.services.impl;


import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

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
 * @author Alberto Mart�n
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
	
	
	
	@SuppressWarnings("unused")
	private final String SUPERUSER_PROFILE="SUPERUSER";
	private final String USER_PROFILE="USER";
	
	@Override
	@Transactional
	public void save(UserItem userItem, boolean pwdUpdated) {
		User user=usersRepository.loadSelectedUser(userItem.getId());
		
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
		//Generamos una nueva contrase�a aleatoria.
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
		//Comprobamos que el identificador de comunidad coincide con un ID de comunidad v�lido.
		Community com=communityDAO.loadSelectedCommunity(communityId);
		if ( com == null) 
			throw new UnknownCommunityException();
		
		//Comprobamos que la comunidad est� activa.
		if ( com.getAvailable()==0 ) 
			throw new UnavailableCommunity();
		
		User user=usersRepository.loadSelectedUserByMail(email);
			
		//Comprobamos que la suscripci�n est� activa.
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
		
		//Comprobamos usuario y contrase�a
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

			//Busco la fecha en que me un� a la comunidad y mis perfiles
			Iterator<UsersCommunity> itUC=com.getUsersCommunities().iterator();
			boolean firstEvent=false; 
			//La primera ocurrencia de mi usuario, ser� la fecha de adhesi�n.
			//Aunque en users_communities solo deber�a haber una entrada por comunidad.
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
	public int addUser(UserItem userItem){
		//Creamos el usuario.
		User user=new User();
		user.setEmail(userItem.getEmail());
		user.setMydata(userItem.getMydata());
		user.setNickname(userItem.getNickname());
		user.setAdds((byte) (userItem.isAdds() ? 1 : 0 ));
		
		
		//Encriptamos
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(userItem.getPassword());
		user.setPassword(hashedPassword);
		
		user.setProfile(USER_PROFILE);
				
		User newUser=this.usersRepository.newUser(user);
		
		return newUser.getId();
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
		
		//Comprobamos si la comunidad est� configurada con aprobaci�n autom�tica de 
		//suscripci�n o no. En caso de estarlo, se aprueba y se env�a correo.
		boolean membercheck=community.getMembercheck()!=0;
		if (membercheck)
			usersCommunity.setStatus(StatusItem.PENDING);
		else
			usersCommunity.setStatus(StatusItem.MEMBER);
		
		usersCommunity.setUser(user);
		usersCommunity.setApplication(application);
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
			
		}
		return userItem;
	}

	
	
}
