package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

public class GetUserTest extends BaseTest{
	
	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);
	}
	
	@Test(enabled = true, priority = 3)
	public void getAllUsersTest() {
		restClient.get(GOREST_ENDPOINT, true, true)
										.then().log().all()
											.assertThat()
												.statusCode(APIHttpStatus.OK_200.getCode());
		
	}
	@Test(priority = 2,enabled = false)
	public void getUserTest() {	
		
		restClient.get(GOREST_ENDPOINT+"/4318629",true, true)
										.then().log().all()
											.assertThat()
												.statusCode(APIHttpStatus.OK_200.getCode())
													.and().body("id", equalTo(4318629));
										
		
	}
	@Test(priority = 1)
	public void getUserWithQueryParamsTest() {
		Map<String,Object> queryParams = new HashMap<String,Object>();
		queryParams.put("name", "naveen");
		queryParams.put("status", "active");

		restClient.get(GOREST_ENDPOINT, queryParams, null,true, true)
					.then().log().all()
						.assertThat().statusCode(APIHttpStatus.OK_200.getCode());											
										
		
	}
	

}
