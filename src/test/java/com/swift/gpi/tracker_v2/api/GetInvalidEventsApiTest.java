package com.swift.gpi.tracker_v2.api;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.swift.gpi.tracker_v2.ApiException;
import com.swift.gpi.tracker_v2.ApiResponse;
import com.swift.gpi.tracker_v2.helper.UtilHelper;
import com.swift.gpi.tracker_v2.model.CamtA0500103;
import com.swift.gpi.tracker_v2.model.CamtA0500203;
import com.swift.gpi.tracker_v2.model.GetInvalidEventsRequest;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

/**
 * API tests for GetInvalidEventsApi
 */

@RunWith(JUnitParamsRunner.class)
public class GetInvalidEventsApiTest {

	private final static GetInvalidEventsApi api = new GetInvalidEventsApi();

	static String laUApplicationID;
	static String laUVersion;
	static String laUCallTime;
	static String laURequestNonce;
	static String laUResponseNonce;
	static String laUSigned;
	static String laUSignature;
	static String xApi;
	static String xRecord;
	static boolean signnature_required;
	static URI uri = null;

	@BeforeClass
	public static void setup() throws NoSuchAlgorithmException, IOException {
		laUApplicationID = UtilHelper.getInstance().mymap.get("laUApplicationID");
		laUVersion = UtilHelper.getInstance().mymap.get("laUVersion");
		laUCallTime = UtilHelper.getInstance().mymap.get("laUCallTime");
		laURequestNonce = UtilHelper.getInstance().mymap.get("laURequestNonce");
		laUResponseNonce = UtilHelper.getInstance().mymap.get("laUResponseNonce");
		laUSigned = UtilHelper.getInstance().mymap.get("laUSigned");
		laUSignature = UtilHelper.getInstance().mymap.get("laUSignature");
		xApi = UtilHelper.getInstance().mymap.get("xApi");
		xRecord = UtilHelper.getInstance().mymap.get("GetInvalidEventsApiTest.xRecord");
		signnature_required = Boolean
				.parseBoolean(UtilHelper.getInstance().mymap.get("GetInvalidEventsApiTest.signatureRequired"));
		uri = null;
		api.getApiClient().setBasePath("https://sandbox.swiftlab-api-developer.com/swift-apitracker-pilot/v2");

		try {
			uri = new URI(api.getApiClient().getBasePath());
		} catch (URISyntaxException ex) {
			Logger.getLogger("Tracker API").log(Level.SEVERE, null, ex);
		}
	}

	@Test
	public void getInvalidEventsPostTest() throws ApiException, NoSuchAlgorithmException, IOException,
			ProcessingException, URISyntaxException, InterruptedException {
		Thread.sleep(2000);
		CamtA0500103 requestBody = new CamtA0500103();
		requestBody.setGetInvalidEventsRequest(new GetInvalidEventsRequest());
		List<String> myInstitution = asList("CCLABEB0");
		requestBody.getGetInvalidEventsRequest().setMyInstitution(myInstitution);
		requestBody.getGetInvalidEventsRequest().setFromDateTime("2017-05-25T09:00:00.000Z");
		requestBody.getGetInvalidEventsRequest().setToDateTime("2017-05-25T17:30:00.000Z");

		// Print the JSON structure constructed
		System.out.println(api.getApiClient().getJSON().serialize(requestBody));

		// TODO: Set always to 'true' and provide gpi Connector's certificate
		// for a production environment
		api.getApiClient().setVerifyingSsl(true);
		// api.getApiClient().setSslCaCert(sslCaCert);
		// api.getApiClient().setKeyManagers(managers);

		// Call API

		ApiResponse<CamtA0500203> response = api.getInvalidEventsPostWithHttpInfo(laUApplicationID, laUVersion,
				laUCallTime, laURequestNonce, laUSigned, laUSignature, xApi, requestBody, xRecord);
		// Print response
		CamtA0500203 responseBody = response.getData();
		System.out.println(api.getApiClient().getJSON().serialize(responseBody));
		System.out.println(response.getStatusCode());

	}

	@Test
	public void getInvalidEventsPost404ErrorTest() throws ApiException, NoSuchAlgorithmException, IOException,
			ProcessingException, URISyntaxException, InterruptedException {
		Thread.sleep(2000);
		CamtA0500103 requestBody = new CamtA0500103();
		CamtA0500203 responseBody = null;
		try {
			ApiResponse<CamtA0500203> response = api.getInvalidEventsPostWithHttpInfo(laUApplicationID, laUVersion,
					laUCallTime, laURequestNonce, laUSigned, laUSignature, xApi, requestBody, "1");
			// Print response
			responseBody = response.getData();
			System.out.println(responseBody.getGetInvalidEventsResponse());
			System.out.println(api.getApiClient().getJSON().serialize(responseBody));
		} catch (ApiException e) {
			// TODO: handle exception
			// reading from Swagger.json
			System.out.println("Response Code =" + e.getCode() + " Response Message=" + e.getMessage());

		}

	}

	@Test
	public void getInvalidEventsPost401ErrorTestWithInvalidXApi() throws ApiException, NoSuchAlgorithmException,
			IOException, ProcessingException, URISyntaxException, InterruptedException {
		Thread.sleep(2000);
		CamtA0500103 requestBody = new CamtA0500103();
		CamtA0500203 responseBody = null;
		try {
			ApiResponse<CamtA0500203> response = api.getInvalidEventsPostWithHttpInfo(laUApplicationID, laUVersion,
					laUCallTime, laURequestNonce, laUSigned, laUSignature, "wrong", requestBody, xRecord);
			// Print response
			responseBody = response.getData();
			System.out.println(responseBody.getGetInvalidEventsResponse());
			System.out.println(api.getApiClient().getJSON().serialize(responseBody));
		} catch (ApiException e) {
			// TODO: handle exception
			// reading from Swagger.json
			System.out.println(e.getMessage());
			assertEquals(e.getMessage(), "Missing or invalid API key.");
		}

	}

}
