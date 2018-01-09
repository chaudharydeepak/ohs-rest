package com.dc.server.ohsrest.domain;

import java.util.List;

public class Authority
{
	private Long id;
	private AuthorityName name;
	private List<ApplicationUserJwt> users;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public AuthorityName getName()
	{
		return name;
	}

	public void setName(AuthorityName name)
	{
		this.name = name;
	}

	public List<ApplicationUserJwt> getUsers()
	{
		return users;
	}

	public void setUsers(List<ApplicationUserJwt> users)
	{
		this.users = users;
	}
}
