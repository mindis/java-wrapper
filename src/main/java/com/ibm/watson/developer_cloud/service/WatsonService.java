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
package com.ibm.watson.developer_cloud.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.watson.developer_cloud.util.MediaType;
import com.ibm.watson.developer_cloud.util.ResponseUtil;

/**
 * Watson service abstract common functionality of various Watson Services. It
 * handle authentication and default url
 * 
 * @author German Attanasio Ruiz <germanatt@us.ibm.com>
 * @see <a
 *      href="http://www.ibm.com/smarterplanet/us/en/ibmwatson/developercloud/">
 *      IBM Watson Developer Cloud</a>
 */
public abstract class WatsonService {

	private static final Logger log = Logger.getLogger(WatsonService.class
			.getName());

	/**
	 * Field ACCEPT. (value is ""Accept"")
	 */
	private static final String ACCEPT = "Accept";
	/**
	 * Field AUTHORIZATION. (value is ""Authorization"")
	 */
	private static final String AUTHORIZATION = "Authorization";

	/**
	 * Field CONNECTION_TIMEOUT. (value is 20000)
	 */
	private static final int CONNECTION_TIMEOUT = 20000;
	/**
	 * Field MAX_TOTAL_CONNECTIONS. (value is 10)
	 */
	private static final int MAX_TOTAL_CONNECTIONS = 10;
	/**
	 * Field httpClient.
	 */
	private HttpClient httpClient;

	/**
	 * Field gson.
	 */
	private Gson gson = new Gson();
	/**
	 * Field apiKey.
	 */
	private String apiKey;
	/**
	 * Field endPoint.
	 */
	private String endPoint;

	/**
	 * Instantiates a new Watson service.
	 * 
	 */
	public WatsonService() {
	}

	/**
	 * Builds the request URI appending the service end point to the path.<br>
	 * <b>From:</b> /v1/foo/bar <br>
	 * <b>to:</b>https://host:port/api/v1/foo/bar
	 * 
	 * @param request
	 *            the http request
	 * 
	 * @return the URI including the service end point
	 */
	private URI buildRequestURI(HttpRequestBase request) {
		String requestURL = getEndPoint() + request.getURI();

		try {
			requestURL = getEndPoint() + request.getURI();
			return new URI(requestURL);
		} catch (URISyntaxException e) {
			log.log(Level.SEVERE, requestURL
					+ " could not be parsed as a URI reference");
			throw new RuntimeException(e);
		}
	}

	/**
	 * Execute the Http request.
	 * 
	 * @param request
	 *            the http request
	 * 
	 * @return the http response
	 */
	protected HttpResponse execute(HttpRequestBase request) {
		if (getApiKey() == null)
			throw new IllegalArgumentException(
					"apiKey or username and password were not specified");
		else {
			request.addHeader(AUTHORIZATION,
					apiKey.startsWith("Basic ") ? apiKey : "Basic " + apiKey);
		}

		if (getEndPoint() == null)
			throw new IllegalArgumentException(
					"service endpoint was not specified");

		if (!request.containsHeader(ACCEPT)) {
			request.addHeader(ACCEPT, getDefaultContentType());
		}

		// from /v1/foo/bar to https://host:port/api/v1/foo/bar
		if (!request.getURI().isAbsolute()) {
			request.setURI(buildRequestURI(request));
		}
		HttpResponse response;
		log.log(Level.FINEST, "Request to: " + request.getURI());
		try {
			response = getHttpClient().execute(request);
		} catch (ClientProtocolException e) {
			log.log(Level.SEVERE, "ClientProtocolException", e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			log.log(Level.SEVERE, "IOException", e);
			throw new RuntimeException(e);
		}
		final int status = response.getStatusLine().getStatusCode();
		log.log(Level.FINEST, "Response HTTP Status: " + status);

		if (status >= 200 && status < 300)
			return response;

		// There was a Client Error 4xx or a Server Error 5xx
		// Get the error message and create the exception
		String error = getErrorMessage(response);
		log.log(Level.SEVERE, "Error message from service:" + error);

		switch (status) {
		case HttpStatus.SC_BAD_REQUEST: // HTTP 400
			throw new BadRequestException(error != null ? error : "Bad Request");
		case HttpStatus.SC_UNAUTHORIZED: // HTTP 401
			throw new UnauthorizedException(
					"Unauthorized: Access is denied due to invalid credentials");
		case HttpStatus.SC_FORBIDDEN: // HTTP 403
			throw new ForbiddenException(error != null ? error
					: "Forbidden: Service refuse the request");
		case HttpStatus.SC_NOT_FOUND: // HTTP 404
			throw new NotFoundException(error != null ? error : "Not found");
		case HttpStatus.SC_NOT_ACCEPTABLE: // HTTP 406
			throw new ForbiddenException(error != null ? error
					: "Forbidden: Service refuse the request");
		case HttpStatus.SC_REQUEST_TOO_LONG: // HTTP 413
			throw new RequestTooLargeException(
					error != null ? error
							: "Request too large: The request entity is larger than the server is able to process");
		case HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE: // HTTP 415
			throw new UnsupportedException(
					error != null ? error
							: "Unsupported MIME type: The request entity has a media type which the server or resource does not support");
		case 429: // HTTP 429
			throw new TooManyRequestsException(error != null ? error
					: "Too many requests");
		case HttpStatus.SC_INTERNAL_SERVER_ERROR: // HTTP 500
			throw new InternalServerErrorException(error != null ? error
					: "Internal Server Error");
		case HttpStatus.SC_SERVICE_UNAVAILABLE: // HTTP 503
			throw new ServiceUnavailableException(error != null ? error
					: "Service Unavailable");
		default: // other errors
			throw new ServiceResponseException(status, error);
		}
	}

	/**
	 * Gets the error message from a JSON response
	 * 
	 * <pre>
	 * {
	 *   code: 400
	 *   error: 'bad request'
	 * }
	 * </pre>
	 * 
	 * .
	 * 
	 * @param resp
	 *            the HTTP response
	 * @return the error message from the json object
	 */
	private String getErrorMessage(HttpResponse resp) {
		String error = null;
		try {
			error = ResponseUtil.getString(resp);
			JsonParser parser = new JsonParser();
			JsonObject respJson = parser.parse(error).getAsJsonObject();
			error = respJson.get("error").getAsString();
			if (error != null && error.isEmpty()) {
				error = null;
			}
		} catch (Exception e) {}

		return error;
	}

	/**
	 * Gets the API key.
	 * 
	 * 
	 * @return the API key
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * Gets the default content type.
	 * 
	 * 
	 * @return the default content type
	 */
	protected String getDefaultContentType() {
		return MediaType.APPLICATION_JSON;
	}

	/**
	 * Gets the default request.
	 * 
	 * 
	 * @return the default request
	 */
	protected HttpParams getDefaultRequestParams() {
		final HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, CONNECTION_TIMEOUT);
		HttpClientParams.setRedirecting(params, false);
		HttpProtocolParams.setUserAgent(params, getUserAgent());
		ConnManagerParams.setMaxTotalConnections(params, MAX_TOTAL_CONNECTIONS);

		return params;
	}

	/**
	 * Gets the API end point.
	 * 
	 * 
	 * @return the API end point
	 */
	public String getEndPoint() {
		return endPoint;
	}

	/**
	 * Gets the gson.
	 * 
	 * 
	 * @return the gson
	 */
	protected Gson getGson() {
		return gson;
	};

	/**
	 * Gets the http client.
	 * 
	 * 
	 * @return the http client
	 */
	public HttpClient getHttpClient() {
		if (httpClient == null) {
			httpClient = new DefaultHttpClient(getDefaultRequestParams());
		}
		return httpClient;
	}

	/**
	 * Gets the user agent.
	 * 
	 * 
	 * @return the user agent
	 */
	private final String getUserAgent() {
		return "watson-developer-cloud-java-wrapper-0.1.3";
	}

	/**
	 * Sets the API key.
	 * 
	 * @param apiKey
	 *            the new API key
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * Sets the end point.
	 * 
	 * @param endPoint
	 *            the new end point
	 */
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * Sets the username and password.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 */
	public void setUsernameAndPassword(String username, String password) {
		String auth = username + ":" + password;
		apiKey = new String(Base64.encodeBase64(auth.getBytes()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WatsonService [");
		if (endPoint != null) {
			builder.append("endPoint=");
			builder.append(endPoint);
		}
		builder.append("]");
		return builder.toString();
	}
}
