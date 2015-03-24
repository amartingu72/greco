package com.greco.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.greco.entities.User;
import com.greco.repositories.UserDAO;
import com.greco.services.AuthenticationProvider;


/**
 * 
 * @author Alberto Martín
 *
 */
@Service("myUserDetailService")
public class AthenticationProviderImpl implements UserDetailsService, AuthenticationProvider {

	
	
	@Resource(name="UsersRepository")
	private UserDAO usersRepository;
	
	//////////METODOS PARA IMPLEMENTACIÓN SPRING SECURITY
	
	
	
	@Override
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException {

		//En el login viene, además del login un sufijo (con separador #), que indica si se solicita login como usuario 
		//(para una comunidad) o como administrador.
		String[] arrLogin=login.split("#");
		
		
		User user= usersRepository.loadSelectedUserByMail(arrLogin[0]);
		

		if ( user == null )
			throw new UsernameNotFoundException("User:" + login);
			
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		//Buscamos el origen para determinar el rol:
		//-comlogin.xhtml: ROLE_USER.
		//-login.xhtml: ROLE_ADMIN.
		
		
		List<GrantedAuthority> authList =  new ArrayList<GrantedAuthority>();
		
		authList.add(new SimpleGrantedAuthority(arrLogin[1]));
		
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), 
				user.getPassword(), 
				enabled, 
				accountNonExpired, 
				credentialsNonExpired, 
				accountNonLocked,
				authList
				);
	}
	@Deprecated
	public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}

	@Deprecated
	public List<String> getRoles(Integer role) {

		List<String> roles = new ArrayList<String>();

		if (role.intValue() == 1) {
			roles.add("ROLE_USER");
			//roles.add("ROLE_ADMIN");
		} else if (role.intValue() == 2) {
			roles.add("ROLE_USER");
		}
		return roles;
	}
	@Deprecated
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}




}
