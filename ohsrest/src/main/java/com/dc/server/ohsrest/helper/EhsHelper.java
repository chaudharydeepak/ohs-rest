package com.dc.server.ohsrest.helper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dc.server.ohsrest.domain.Observation;
import com.dc.server.ohsrest.service.ObsCRUDService;




public class EhsHelper
{
	@Autowired
	ObsCRUDService obsCRUDService;
	
	public List<Observation> loadAllObservations()
	{
		return obsCRUDService.loadAllObservations();
	}
}
