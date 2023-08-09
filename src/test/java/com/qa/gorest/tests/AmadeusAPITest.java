package com.qa.gorest.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AmadeusAPITest extends BaseTest{
	
	private String accessToken;
	//OAuth-2 Test
	@Parameters({"baseURI","grantType","ClientId","ClientSecret"} )
	@BeforeMethod
	public void flightAPISetup(String baseURI, String grantType, String ClientId, String ClientSecret) {
		restClient = new RestClient(prop, baseURI);
		accessToken = restClient.getAccessToken(AMADEUS_TOKEN_ENDPOINT, grantType, ClientId, ClientSecret);
	}
	
	@Test 
	public void getFlightInfoTest() {
		
		RestClient restClientFlight = new RestClient(prop,baseURI);
		Map<String,Object> queryParams = new HashMap<String,Object>();
		queryParams.put("origin", "PAR");
		queryParams.put("maxPrice", 200);
		
		Map<String,String> headersMap = new HashMap<String,String>();
		headersMap.put("Authorization", "Bearer " + accessToken);
		Response flightDataResponse = restClientFlight.get(AMADEUS_FLIGHTBOOKING_ENDPOINT, queryParams, headersMap, false, false)
		.then().log().all()
		.assertThat()
		.statusCode(APIHttpStatus.OK_200.getCode())
			.and()
				.extract().response();
		
		JsonPath js = flightDataResponse.jsonPath();
		String type = js.get("data[0].type");
		System.out.println(type);
		
		
	}
}
