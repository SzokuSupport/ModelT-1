/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2018 SAP SE
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * Hybris ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with SAP Hybris.
 */
package de.hybris.platform.hac.facade.impl;

import de.hybris.platform.hac.facade.DatabaseFacade;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 *
 */
@Service
public class PSPerformanceTestsFacade
{
	private static final Logger LOG = Logger.getLogger(PSPerformanceTestsFacade.class);
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public PSPerformanceTestsFacade(final DatabaseFacade databaseFacade)
	{
		this.jdbcTemplate = new JdbcTemplate(databaseFacade.getDataSource());
	}

	@Transactional("txManager")
	public synchronized Map<String, Object> executeSqlMaxTest()
	{
		LOG.info("Running sql max test...");
		final Map<String, Object> result = new HashMap<String, Object>();

		createTestingTable();

		final long t1 = System.currentTimeMillis();

		fillIn10000Rows();
		final long t2 = System.currentTimeMillis();
		result.put("durationAdded", Long.valueOf(t2 - t1));

		fillIn10000RowsExecutingMaxFunction();

		final long t3 = System.currentTimeMillis();
		result.put("durationAddedMax", Long.valueOf(t3 - t2));

		createIndexOnMaxtestTable();
		final long t4 = System.currentTimeMillis();

		fillIn10000RowsExecutingMaxFunction();
		final long t5 = System.currentTimeMillis();
		result.put("durationAddedMaxIndex", Long.valueOf(t5 - t4));

		dropTestingTable();

		LOG.info("Finishing sql max test...");
		return result;
	}

	private void fillIn10000RowsExecutingMaxFunction()
	{
		LOG.info("Fill in 10000 rows into testing table");
		for (int i = 0; i < 10000; i++)
		{
			int nr = jdbcTemplate.queryForObject("select max(nr) from maxtest", Integer.class).intValue();
			nr++;
			jdbcTemplate.update("INSERT INTO maxtest ( id, nr ) VALUES ( 'row" + nr + "', " + nr + " ) ");
		}
		LOG.info("Finished filling in 10000 rows into testing table");
	}

	private void fillIn10000Rows()
	{
		LOG.info("Fill in 10000 rows into testing table");
		for (int i = 0; i < 10000; i++)
		{
			jdbcTemplate.update("INSERT INTO maxtest ( id, nr ) VALUES ( 'row" + i + "', " + i + " ) ");
		}
		LOG.info("Finished filling in 10000 rows into testing table");
	}

	private void createTestingTable()
	{
		LOG.info("Create testing table 'maxtests'");
		dropTestingTable();
		jdbcTemplate.update("CREATE TABLE maxtest ( id varchar(10) primary key , nr decimal(12,0) )");
		LOG.info("Created testing table 'maxtests'");
	}

	private void dropTestingTable()
	{
		try
		{
			LOG.info("Drop testing table 'maxtests'");
			jdbcTemplate.update("DROP TABLE maxtest");
			LOG.info("Dropped testing table 'maxtests'");
		}
		catch (final Exception e)
		{
			// fine
		}
	}

	private void createIndexOnMaxtestTable()
	{
		LOG.info("Create index nrIdx on maxtest");
		jdbcTemplate.update("create index nrIdx on maxtest ( nr )");
		LOG.info("Created index nrIdx on maxtest");
	}
}
