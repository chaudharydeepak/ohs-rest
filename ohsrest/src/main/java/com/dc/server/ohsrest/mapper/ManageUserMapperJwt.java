package com.dc.server.ohsrest.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.dc.server.ohsrest.domain.ApplicationUserJwt;
import com.dc.server.ohsrest.domain.Authority;
import com.dc.server.ohsrest.domain.AuthorityName;

public class ManageUserMapperJwt implements RowMapper<ApplicationUserJwt>
{
	public ApplicationUserJwt mapRow(ResultSet arg0, int arg1) throws SQLException
	{

		System.out.println("inside ManageUserMapper.mapRow");
		ApplicationUserJwt _user = new ApplicationUserJwt();

		_user.setId((long) arg0.getInt("ID"));
		_user.setUsername(arg0.getString("USERNAME"));
		_user.setPassword(arg0.getString("PASSWORD"));
		_user.setFirstname(arg0.getString("FIRST_NAME"));
		_user.setLastname(arg0.getString("LAST_NAME"));
		_user.setEmail(arg0.getString("USERNAME"));
		_user.setEnabled(arg0.getBoolean("ACTIVE"));

		List<Authority> authorities = new ArrayList<Authority>();
		Authority _autorityAdmin = new Authority();
		_autorityAdmin.setId((long) 1);
		_autorityAdmin.setName(AuthorityName.ROLE_ADMIN);
		authorities.add(_autorityAdmin);

		Authority _autorityUser = new Authority();
		_autorityUser.setId((long) 1);
		_autorityUser.setName(AuthorityName.ROLE_USER);
		authorities.add(_autorityUser);

		_user.setAuthorities(authorities);

		return _user;
	}
}