package com.dc.server.ohsrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dc.server.ohsrest.dao.ObsCRUDDao;
import com.dc.server.ohsrest.domain.Observation;

public class ObsCRUDServiceImpl implements ObsCRUDService
{
	@Autowired
	ObsCRUDDao obsCRUDdao;
	
	@Override
	public List<Observation> loadAllObservations()
	{
		return obsCRUDdao.loadAllObservations();
	}

}
