package com.dc.server.ohsrest.jwtsecurity;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dc.server.ohsrest.domain.ApplicationUserJwt;
import com.dc.server.ohsrest.domain.Authority;

public final class JwtUserFactory
{

	private JwtUserFactory()
	{
	}

	public static JwtUser create(ApplicationUserJwt user)
	{
		return new JwtUser(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(),
				user.getPassword(), mapToGrantedAuthorities(user.getAuthorities()), user.getEnabled());
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities)
	{
		return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
				.collect(Collectors.toList());
	}
}