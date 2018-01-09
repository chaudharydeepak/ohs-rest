package com.dc.server.ohsrest.service;

import java.util.List;

import com.dc.server.ohsrest.domain.Observation;



public interface ObsCRUDService
{
	public List<Observation> loadAllObservations( );
}
