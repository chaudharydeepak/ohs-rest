package com.dc.server.ohsrest.dao;

import java.util.List;

import javax.sql.DataSource;

import com.dc.server.ohsrest.domain.Observation;

public interface ObsCRUDDao
{
	/**
	 * Interface to Set Data Source.
	 */
	public void setDataSource(DataSource dataSource);

	public List<Observation> loadAllObservations();
}
