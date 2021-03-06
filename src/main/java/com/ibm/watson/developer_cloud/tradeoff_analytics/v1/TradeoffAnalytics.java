/**
 * Copyright 2015 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.watson.developer_cloud.tradeoff_analytics.v1;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import com.ibm.watson.developer_cloud.service.Request;
import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.Dilemma;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.Problem;
import com.ibm.watson.developer_cloud.util.MediaType;
import com.ibm.watson.developer_cloud.util.ResponseUtil;

public class TradeoffAnalytics extends WatsonService {

	private static String URL = "https://gateway.watsonplatform.net/tradeoff-analytics/api";

	/**
	 * Instantiates a new tradeoff analytics.
	 */
	public TradeoffAnalytics() {
		setEndPoint(URL);
	}

	/**
	 * Dilemmas.
	 * 
	 * @param problem
	 *            the problem
	 * @return the dilemma
	 */
	public Dilemma dilemmas(Problem problem) {
		if (problem == null)
			throw new IllegalArgumentException("problem was not specified");

		String contentJson = getGson().toJson(problem);

		HttpRequestBase request = Request.Post("/v1/dilemmas")
				.withContent(contentJson, MediaType.APPLICATION_JSON).build();

		try {
			HttpResponse response = execute(request);
			String dilemmaJson = ResponseUtil.getString(response);
			Dilemma dilemma = getGson().fromJson(dilemmaJson, Dilemma.class);
			return dilemma;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
