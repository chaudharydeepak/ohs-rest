package com.dc.server.ohrest.jwtsecurityImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dc.server.ohsrest.dao.UserDao;
import com.dc.server.ohsrest.domain.ApplicationUserJwt;
import com.dc.server.ohsrest.jwtsecurity.JwtUserFactory;

public class JwtUserDetailsServiceImpl implements UserDetailsService
{

	@Autowired
	private UserDao userDao;

	/**
	 * This will be called from
	 * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider#retrieveUser(java.lang.String, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)}
	 * when
	 * {@link AuthenticationService#authenticate(java.lang.String, java.lang.String)}
	 * calls
	 * {@link AuthenticationManager#authenticate(org.springframework.security.core.Authentication)}.
	 * Easy.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		System.out.println(" *** JwtUserDetailsServiceImpl.loadUserByUsername");
		ApplicationUserJwt user = userDao.loadUserByUsernameJWT(username);

		if (user == null)
		{
			System.out.println(user);
			throw new UsernameNotFoundException("User " + username + " not found");
		}
		return JwtUserFactory.create(user);
	}
}