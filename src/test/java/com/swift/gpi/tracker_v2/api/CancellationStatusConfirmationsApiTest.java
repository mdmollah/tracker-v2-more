package com.swift.gpi.tracker_v2.api;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.swift.gpi.tracker_v2.ApiException;
import com.swift.gpi.tracker_v2.ApiResponse;
import com.swift.gpi.tracker_v2.helper.UtilHelper;
import com.swift.gpi.tracker_v2.model.CamtA0700102;
import com.swift.gpi.tracker_v2.model.CamtA0700202;
import com.swift.gpi.tracker_v2.model.CancellationResponseDetails1;
import com.swift.gpi.tracker_v2.model.InvestigationExecutionConfirmation5Code;
import com.swift.gpi.tracker_v2.model.InvestigationExecutionStatusReason1;
import com.swift.gpi.tracker_v2.model.PaymentCancellationRejection3Code;
import com.swift.gpi.tracker_v2.model.PendingPaymentCancellationReason1Code;
import com.swift.gpi.tracker_v2.model.TransactionCancellationStatusRequest;

import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class CancellationStatusConfirmationsApiTest {
	private final static CancellationStatusConfirmationsApi api = new CancellationStatusConfirmationsApi();

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
		xRecord = UtilHelper.getInstance().mymap.get("CancelTransactionsApiTest.xRecord");
		signnature_required = Boolean.parseBoolean(
				UtilHelper.getInstance().mymap.get("CancellationStatusConfirmationsApiTest.signatureRequired"));
		uri = null;
		api.getApiClient().setBasePath("https://sandbox.swiftlab-api-developer.com/swift-apitracker-pilot/v2");

		try {
			uri = new URI(api.getApiClient().getBasePath());
		} catch (URISyntaxException ex) {
			Logger.getLogger("Tracker API").log(Level.SEVERE, null, ex);
		}
	}

	@Test
	public void cancellationStatusConfirmationsPostTest() throws ApiException, NoSuchAlgorithmException, IOException,
			ProcessingException, URISyntaxException, InterruptedException {
		Thread.sleep(2000);
		CamtA0700102 requestBody = new CamtA0700102();
		requestBody.setTransactionCancellationStatusRequest(new TransactionCancellationStatusRequest());
		requestBody.getTransactionCancellationStatusRequest().setFrom("CCLABEB0");
		requestBody.getTransactionCancellationStatusRequest().setBusinessService("121");
		requestBody.getTransactionCancellationStatusRequest().setUetr("");
		requestBody.getTransactionCancellationStatusRequest().assignmentIdentification("");
		requestBody.getTransactionCancellationStatusRequest().resolvedCaseIdentification("");
		requestBody.getTransactionCancellationStatusRequest()
				.setUnderlyingCancellationDetails(new CancellationResponseDetails1());
		requestBody.getTransactionCancellationStatusRequest().getUnderlyingCancellationDetails()
				.setInvestigationExecutionStatus(InvestigationExecutionConfirmation5Code.CNCL);
		requestBody.getTransactionCancellationStatusRequest().getUnderlyingCancellationDetails()
				.setInvestigationExecutionStatusReason(new InvestigationExecutionStatusReason1());
		requestBody.getTransactionCancellationStatusRequest().getUnderlyingCancellationDetails()
				.getInvestigationExecutionStatusReason().setRejected(PaymentCancellationRejection3Code.AGNT);
		requestBody.getTransactionCancellationStatusRequest().getUnderlyingCancellationDetails()
				.getInvestigationExecutionStatusReason().setPending(PendingPaymentCancellationReason1Code.PTNA);
		requestBody.getTransactionCancellationStatusRequest().setOriginator("");

		System.out.println(api.getApiClient().getJSON().serialize(requestBody));

		// TODO: Set always to 'true' and provide gpi Connector's certificate
		// for a production environment
		api.getApiClient().setVerifyingSsl(true);
		// api.getApiClient().setSslCaCert(sslCaCert);
		// api.getApiClient().setKeyManagers(managers);

		ApiResponse<CamtA0700202> response = api.cancellationStatusConfirmationsPostWithHttpInfo(laUApplicationID,
				laUVersion, laUCallTime, laURequestNonce, laUSigned, laUSignature, signnature_required, xApi,
				requestBody, xRecord);
		// Print response
		CamtA0700202 responseBody = response.getData();
		System.out.println(api.getApiClient().getJSON().serialize(responseBody));
		System.out.println(response.getStatusCode());
		// Verify LAU
		Map<String, List<String>> headers = response.getHeaders();
		laUApplicationID = headers.get("LAUApplicationID").get(0);
		laUCallTime = headers.get("LAUCallTime").get(0);
		laURequestNonce = headers.get("LAURequestNonce").get(0);
		laUResponseNonce = headers.get("LAUResponseNonce").get(0);
		laUVersion = headers.get("LAUVersion").get(0);
		laUSignature = headers.get("LAUSignature").get(0);

	}

	@Test
	public void cancelTransactionsApiPost404ErrorTest() throws ApiException, NoSuchAlgorithmException, IOException,
			ProcessingException, URISyntaxException, InterruptedException {
		Thread.sleep(2000);
		CamtA0700102 requestBody = new CamtA0700102();
		CamtA0700202 responseBody = null;
		try {
			ApiResponse<CamtA0700202> response = api.cancellationStatusConfirmationsPostWithHttpInfo(laUApplicationID,
					laUVersion, laUCallTime, laURequestNonce, laUSigned, laUSignature, signnature_required, xApi,
					requestBody, xRecord);
			// Print response
			responseBody = response.getData();
			// System.out.println(responseBody.getTransactionCancellationStatusResponse());
			// System.out.println(api.getApiClient().getJSON().serialize(responseBody));
		} catch (ApiException e) {
			// TODO: handle exception

			System.out.println("Response Code =" + e.getCode() + " Response Message=" + e.getMessage());

		}

	}

	@Test
	public void cancelTransactionsApiPost401ErrorTestWithInvalidXApi() throws ApiException, NoSuchAlgorithmException,
			IOException, ProcessingException, URISyntaxException, InterruptedException {
		Thread.sleep(2000);
		CamtA0700102 requestBody = new CamtA0700102();
		CamtA0700202 responseBody = null;
		try {
			ApiResponse<CamtA0700202> response = api.cancellationStatusConfirmationsPostWithHttpInfo(laUApplicationID,
					laUVersion, laUCallTime, laURequestNonce, laUSigned, laUSignature, signnature_required, xApi,
					requestBody, xRecord);
			// Print response
			responseBody = response.getData();
			// System.out.println(responseBody.getTransactionCancellationStatusResponse());
			// System.out.println(api.getApiClient().getJSON().serialize(responseBody));
		} catch (ApiException e) {
			// TODO: handle exception
			// reading from Swagger.json
			System.out.println(e.getMessage());
			assertEquals(e.getMessage(), "Missing or invalid API key.");
		}

	}

}
