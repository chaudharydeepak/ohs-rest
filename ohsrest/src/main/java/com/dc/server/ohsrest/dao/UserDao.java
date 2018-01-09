package com.dc.server.ohsrest.dao;

import javax.sql.DataSource;

import com.dc.server.ohsrest.domain.ApplicationUser;
import com.dc.server.ohsrest.domain.ApplicationUserJwt;

public interface UserDao
{
	/**
	 * Interface to Set Data Source.
	 */
	public void setDataSource(DataSource dataSource);
	
	public ApplicationUser loadUserByUsername(String arg0);
	
	public ApplicationUserJwt loadUserByUsernameJWT(String arg0);
}
