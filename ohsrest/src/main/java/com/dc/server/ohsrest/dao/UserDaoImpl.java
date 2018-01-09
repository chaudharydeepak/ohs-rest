package com.dc.server.ohsrest.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.dc.server.ohsrest.domain.ApplicationUser;
import com.dc.server.ohsrest.domain.ApplicationUserJwt;

public class UserDaoImpl implements UserDao
{

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource)
	{
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public ApplicationUser loadUserByUsername(String username)
	{
		System.out.println("UserDaoImpl.loadUserByUsername");
		String fetchUserSQL = "Select * from app.EHS_SECURITY_USERPROFILE u, app.EHS_SECURITY_USERAUTHORITY a where u.username = a.username and active=true and u.username = 'chaudharydeepak08@gmail.com'";
		List<ApplicationUser> usersList = namedParameterJdbcTemplate.query(fetchUserSQL,
				new com.dc.server.ohsrest.mapper.ManageUserMapper());

		System.out.println("UserDaoImpl.loadUserByUsername" + usersList.size());
		return usersList.get(0);
	}

	@Override
	public ApplicationUserJwt loadUserByUsernameJWT(String arg0)
	{
		System.out.println("UserDaoImpl.loadUserByUsername");
		String fetchUserSQL = "Select * from app.EHS_SECURITY_USERPROFILE u, app.EHS_SECURITY_USERAUTHORITY a where u.username = a.username and active=true and u.username = 'chaudharydeepak08@gmail.com'";
		List<ApplicationUserJwt> usersList = namedParameterJdbcTemplate.query(fetchUserSQL,
				new com.dc.server.ohsrest.mapper.ManageUserMapperJwt());

		System.out.println("UserDaoImpl.loadUserByUsernameJWT" + usersList.size());
		return usersList.get(0);
	}

}
