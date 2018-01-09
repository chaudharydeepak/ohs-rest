package com.dc.server.ohsrest.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dc.server.ohsrest.domain.ApplicationUser;

public class ManageUserMapper implements RowMapper<ApplicationUser>
{
	public ApplicationUser mapRow(ResultSet arg0, int arg1) throws SQLException
	{
		System.out.println("inside ManageUserMapper.mapRow");
		ApplicationUser _user = new ApplicationUser(arg0.getString("USERNAME"),
				arg0.getString("FIRST_NAME") + " " + arg0.getString("LAST_NAME"), arg0.getString("PASSWORD"),
				"ROLE_SPECIAL", "ADMIN");
		return _user;
	
	}
}