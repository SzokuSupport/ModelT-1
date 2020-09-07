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
package de.hybris.platform.hac.controller;

import de.hybris.platform.hac.facade.impl.PSPerformanceTestsFacade;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.log4j.Logger;


/**
 *
 */
@Controller()
@RequestMapping(value = {"**/psMonitoring/psPerformance/**", "/psMonitoring/psPerformance/**", "**/psPerformance/**"})
public class PSPerformanceController
{
	private static final String VIEW_BASE = "psMonitoring/psPerformance/";
	private static final Logger LOG = Logger.getLogger(PSPerformanceController.class);

	@Autowired
	private PSPerformanceTestsFacade performanceTestsFacade;

	@Value("${hac.extlinks.wiki.performance}")
	private String wikiPerformance;

	@RequestMapping(value = "/")
	public String psPerformance(final Model model) throws IOException
	{
		LOG.debug("PSPErformanceController executed and returning " + VIEW_BASE + "psPerformance");
		model.addAttribute("wikiPerformance", wikiPerformance);
		return VIEW_BASE + "psPerformance";
	}


	@RequestMapping(value = "sqlmax", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Map<String, Object> sqlMax()
	{
		return performanceTestsFacade.executeSqlMaxTest();
	}

}
