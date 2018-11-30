
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
import com.swift.gpi.tracker_v2.model.ActiveOrHistoricCurrencyAndAmount;
import com.swift.gpi.tracker_v2.model.CamtA0100103;
import com.swift.gpi.tracker_v2.model.CamtA0100202;
import com.swift.gpi.tracker_v2.model.CurrencyExchange8;
import com.swift.gpi.tracker_v2.model.PaymentReason1Code;
import com.swift.gpi.tracker_v2.model.PaymentScenario1Code;
import com.swift.gpi.tracker_v2.model.PaymentStatus3;
import com.swift.gpi.tracker_v2.model.PaymentStatusType2Choice;
import com.swift.gpi.tracker_v2.model.StatusDetails2;
import com.swift.gpi.tracker_v2.model.TransactionIndividualStatus4Code;
import com.swift.gpi.tracker_v2.model.UpdatePaymentStatusRequest;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

/**
 * API tests for StatusConfirmationsApi
 */
@RunWith(JUnitParamsRunner.class)
public class StatusConfirmationsApiTest {

	private final static StatusConfirmationsApi api = new StatusConfirmationsApi();
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
		xRecord = UtilHelper.getInstance().mymap.get("StatusConfirmationsApiTest.xRecord");
		signnature_required = Boolean
				.parseBoolean(UtilHelper.getInstance().mymap.get("StatusConfirmationsApiTest.signatureRequired"));
		uri = null;
		api.getApiClient().setBasePath("https://sandbox.swiftlab-api-developer.com/swift-apitracker-pilot/v2");

		try {
			uri = new URI(api.getApiClient().getBasePath());
		} catch (URISyntaxException ex) {
			Logger.getLogger("Tracker API").log(Level.SEVERE, null, ex);
		}
	}

	@Test
	public void statusConfirmationsPostTest() throws ApiException, NoSuchAlgorithmException, IOException,
			ProcessingException, URISyntaxException, InterruptedException {
		Thread.sleep(2000);
		CamtA0100103 requestBody = new CamtA0100103();

		requestBody.setUpdatePaymentStatusRequest(new UpdatePaymentStatusRequest());
		requestBody.getUpdatePaymentStatusRequest().setFrom("CCLABEB0");
		requestBody.getUpdatePaymentStatusRequest().setBusinessService("121");
		requestBody.getUpdatePaymentStatusRequest().setInstructionIdentification("CCLABEB0ACSPG000");
		requestBody.getUpdatePaymentStatusRequest().setPaymentStatus(new PaymentStatusType2Choice());
		requestBody.getUpdatePaymentStatusRequest().getPaymentStatus().setDetailedStatus(new StatusDetails2());
		requestBody.getUpdatePaymentStatusRequest().getPaymentStatus().getDetailedStatus().setOriginator("CCLABEB0");
		requestBody.getUpdatePaymentStatusRequest().getPaymentStatus().getDetailedStatus()
				.setConfirmedAmount(new ActiveOrHistoricCurrencyAndAmount());
		requestBody.getUpdatePaymentStatusRequest().getPaymentStatus().getDetailedStatus().getConfirmedAmount()
				.setAmount("1000.");
		requestBody.getUpdatePaymentStatusRequest().getPaymentStatus().getDetailedStatus().getConfirmedAmount()
				.setCurrency("USD");
		requestBody.getUpdatePaymentStatusRequest().getPaymentStatus().getDetailedStatus()
				.setTransactionStatus(new PaymentStatus3());
		requestBody.getUpdatePaymentStatusRequest().getPaymentStatus().getDetailedStatus().getTransactionStatus()
				.setStatus(TransactionIndividualStatus4Code.ACSC);
		requestBody.getUpdatePaymentStatusRequest().getPaymentStatus().getDetailedStatus().getTransactionStatus()
				.setReason(PaymentReason1Code.G000);

		// Print the JSON structure constructed
		System.out.println(api.getApiClient().getJSON().serialize(requestBody));

		// TODO: Set always to 'true' and provide gpi Connector's certificate
		// for a production environment
		api.getApiClient().setVerifyingSsl(true);
		// api.getApiClient().setSslCaCert(sslCaCert);
		// api.getApiClient().setKeyManagers(managers);

		// Call API
		CamtA0100202 responseBody = null;
		try {
			ApiResponse<CamtA0100202> response = api.statusConfirmationsPostWithHttpInfo(laUApplicationID, laUVersion,
					laUCallTime, laURequestNonce, laUSigned, laUSignature, signnature_required, xApi, requestBody,
					xRecord);
			// Print response
			responseBody = response.getData();

			System.out.println(api.getApiClient().getJSON().serialize(responseBody));
		} catch (ApiException e) {
			// TODO: handle exception

			System.out.println(e.getCode());
			System.out.println(e.getMessage());
			System.out.println(e.getResponseBody());
		}

	}

	@Test
	public void statusConfirmationsPost404ErrorTest() throws ApiException, NoSuchAlgorithmException, IOException,
			ProcessingException, URISyntaxException, InterruptedException {
		Thread.sleep(2000);
		CamtA0100103 requestBody = new CamtA0100103();
		CamtA0100202 responseBody = null;
		try {
			ApiResponse<CamtA0100202> response = api.statusConfirmationsPostWithHttpInfo(laUApplicationID, laUVersion,
					laUCallTime, laURequestNonce, laUSigned, laUSignature, signnature_required, xApi, requestBody, "1");
			// Print response
			responseBody = response.getData();
			System.out.println(responseBody.getUpdatePaymentStatusResponse());
			System.out.println(api.getApiClient().getJSON().serialize(responseBody));
		} catch (ApiException e) {
			// TODO: handle exception
			// reading from Swagger.json

		}

	}

	@Test
	public void statusConfirmationsPost401ErrorTestWithInvalidXApi() throws ApiException, NoSuchAlgorithmException,
			IOException, ProcessingException, URISyntaxException, InterruptedException {
		Thread.sleep(2000);
		CamtA0100103 requestBody = new CamtA0100103();
		CamtA0100202 responseBody = null;
		try {
			ApiResponse<CamtA0100202> response = api.statusConfirmationsPostWithHttpInfo(laUApplicationID, laUVersion,
					laUCallTime, laURequestNonce, laUSigned, laUSignature, signnature_required, "wrong", requestBody,
					xRecord);
			// Print response
			responseBody = response.getData();
			System.out.println(responseBody.getUpdatePaymentStatusResponse());
			System.out.println(api.getApiClient().getJSON().serialize(responseBody));
		} catch (ApiException e) {
			// TODO: handle exception
			// reading from Swagger.json
			System.out.println(e.getMessage());
			assertEquals(e.getMessage(), "Missing or invalid API key.");
		}

	}

}
