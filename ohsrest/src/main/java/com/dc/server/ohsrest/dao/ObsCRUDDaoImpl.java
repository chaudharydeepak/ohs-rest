package com.dc.server.ohsrest.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.dc.server.ohsrest.domain.Actions;
import com.dc.server.ohsrest.domain.Observation;
import com.dc.server.ohsrest.mapper.ActionMapper;
import com.dc.server.ohsrest.mapper.ObservationMapper;

public class ObsCRUDDaoImpl implements ObsCRUDDao
{

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource)
	{
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<Observation> loadAllObservations()
	{
		String fetchAllObservations = "select o.*, u.first_name, u.last_name from app.ObservationMaster o, app.EHS_SECURITY_USERPROFILE u where o.initiatedby = u.username and o.active = true";
		List<Observation> obsList = null;

		obsList = namedParameterJdbcTemplate.query(fetchAllObservations, new ObservationMapper());

		Map<String, Object> namedParameters = new HashMap<String, Object>();

		obsList.forEach(item ->
		{

			if (null != item)
			{
				/* fetch actions */
				String fetchActions = "Select * from app.ObservationActions where obs_id=:obsid";
				namedParameters.put("obsid", Integer.valueOf(item.getObsID()));
				List<Actions> actionList = namedParameterJdbcTemplate.query(fetchActions, namedParameters,
						new ActionMapper());

				if (null != actionList && !actionList.isEmpty())
					item.setActionsList(actionList);
			}
		});
		return obsList;
	}

}
