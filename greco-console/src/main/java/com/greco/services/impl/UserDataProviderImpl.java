package com.greco.services.impl;


import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
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
	
	/*@Resource(name = "mail/gmailAccount")
	private Session mailSession;*/
		
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
		if (pwdUpdated) user.setPassword(userItem.getPassword());
		
		usersRepository.saveUser(user);
	}
	
	@Override
	@Transactional
	public String changePassword(int userId){
		//Generamos una nueva contraseña aleatoria.
		String pwd=RandomStringUtils.randomAlphanumeric(8);
		User user=usersRepository.loadSelectedUser(userId);
		user.setPassword(pwd);
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
		
		User user=usersRepository.loadSelectedUserByMail(email);
		
		
		//Comprobamos que el usuario contiene la comunidad.
		List<Community> comms=user.getCommunities(); 
		if ( comms == null ) throw new NoMemberException();
		Iterator<Community> it=comms.iterator();
		boolean found=false;
		Community comm=null;
		while ( it.hasNext() && !found) {
			comm=it.next();
			found=( comm.getId()== communityId);
		}
		if ( !found )
			throw new NoMemberException();	
		
		//Si ha encontrado una comunidad, comprobamos que está activa.
		if ( found && (comm.getAvailable()==0) ) 
			throw new UnavailableCommunity();
		
		//Comprobamos que la suscripción está activa.
		int status=userCommunitiesDAO.getSubscriptionStatus(user.getId(), communityId);
		if ( status== UserCommunitiesDAO.PENDING_STATUS)
			throw new PendingException();
		
		
		//Una vez hechas las validaciones, cargaos el bean.
		UserSBean userbean=null;
		if ( user != null ) {
			userbean=new UserSBean();
			userbean.setId(user.getId());
			userbean.setNickname(user.getNickname());
			userbean.setEmail(user.getEmail());
			userbean.setMydata(user.getMydata());
			userbean.setCommunityId(communityId);
			
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
		user.setPassword(userItem.getPassword());
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
		usersCommunity.setStatus(StatusItem.PENDING);
		usersCommunity.setUser(user);
		usersCommunity.setApplication(application);
		this.userCommunitiesDAO.add(usersCommunity);
		
	}

	
	
}
