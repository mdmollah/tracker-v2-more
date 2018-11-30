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
import com.swift.gpi.tracker_v2.model.CamtA0400103;
import com.swift.gpi.tracker_v2.model.CamtA0400203;
import com.swift.gpi.tracker_v2.model.GetChangedPaymentTransactionsRequest;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class GetChangedPaymentTransactionsApiTest {

	private final static GetChangedPaymentTransactionsApi api = new GetChangedPaymentTransactionsApi();

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
		xRecord = UtilHelper.getInstance().mymap.get("GetChangedPaymentTransactionsApiTest.xRecord");
		signnature_required = Boolean.parseBoolean(
				UtilHelper.getInstance().mymap.get("GetChangedPaymentTransactionsApiTest.signatureRequired"));
		uri = null;
		api.getApiClient().setBasePath("https://sandbox.swiftlab-api-developer.com/swift-apitracker-pilot/v2");

		try {
			uri = new URI(api.getApiClient().getBasePath());
		} catch (URISyntaxException ex) {
			Logger.getLogger("Tracker API").log(Level.SEVERE, null, ex);
		}
	}

	@Test
	public void getChangedPaymentTransactionsPostTest() throws ApiException, NoSuchAlgorithmException, IOException,
			ProcessingException, URISyntaxException, InterruptedException {
		Thread.sleep(2000);
		CamtA0400103 requestBody = new CamtA0400103();
		requestBody.setGetChangedPaymentTransactionsRequest(new GetChangedPaymentTransactionsRequest());
		List<String> myInstitution = asList("CCLABEB0");
		requestBody.getGetChangedPaymentTransactionsRequest().setMyInstitution(myInstitution);
		requestBody.getGetChangedPaymentTransactionsRequest().setStartTime("2017-05-25T09:24:41.174Z");

		// Print the JSON structure constructed
		System.out.println(api.getApiClient().getJSON().serialize(requestBody));

		// TODO: Set always to 'true' and provide gpi Connector's certificate
		// for a production environment
		api.getApiClient().setVerifyingSsl(true);
		// api.getApiClient().setSslCaCert(sslCaCert);
		// api.getApiClient().setKeyManagers(managers);

		ApiResponse<CamtA0400203> response = api.getChangedPaymentTransactionsPostWithHttpInfo(laUApplicationID,
				laUVersion, laUCallTime, laURequestNonce, laUSigned, laUSignature, xApi, requestBody, xRecord);
		// Print response
		CamtA0400203 responseBody = response.getData();
		System.out.println(api.getApiClient().getJSON().serialize(responseBody));
		System.out.println(response.getStatusCode());

	}

	@Test
	public void getChangedPaymentTransactionsPost404ErrorTest() throws ApiException, NoSuchAlgorithmException,
			IOException, ProcessingException, URISyntaxException, InterruptedException {
		Thread.sleep(2000);
		CamtA0400103 requestBody = new CamtA0400103();
		CamtA0400203 responseBody = null;
		try {
			ApiResponse<CamtA0400203> response = api.getChangedPaymentTransactionsPostWithHttpInfo(laUApplicationID,
					laUVersion, laUCallTime, laURequestNonce, laUSigned, laUSignature, xApi, requestBody, "1");
			// Print response
			responseBody = response.getData();
			System.out.println(responseBody.getGetChangedPaymentTransactionsResponse());
			System.out.println(api.getApiClient().getJSON().serialize(responseBody));
		} catch (ApiException e) {
			// TODO: handle exception
			// reading from Swagger.json
			System.out.println("Response Code =" + e.getCode() + " Response Message=" + e.getMessage());

		}

	}

	@Test
	public void getChangedPaymentTransactionsPost401ErrorTestWithInvalidXApi() throws ApiException,
			NoSuchAlgorithmException, IOException, ProcessingException, URISyntaxException, InterruptedException {
		Thread.sleep(2000);
		CamtA0400103 requestBody = new CamtA0400103();
		CamtA0400203 responseBody = null;
		try {
			ApiResponse<CamtA0400203> response = api.getChangedPaymentTransactionsPostWithHttpInfo(laUApplicationID,
					laUVersion, laUCallTime, laURequestNonce, laUSigned, laUSignature, "wrong", requestBody, xRecord);
			// Print response
			responseBody = response.getData();
			System.out.println(responseBody.getGetChangedPaymentTransactionsResponse());
			System.out.println(api.getApiClient().getJSON().serialize(responseBody));
		} catch (ApiException e) {
			// TODO: handle exception
			// reading from Swagger.json
			System.out.println(e.getMessage());
			assertEquals(e.getMessage(), "Missing or invalid API key.");
		}

	}

}
