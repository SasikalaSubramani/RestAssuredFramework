package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.StringUtil;

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
	
	
	@Test(priority = 2)
	public void getUserTest() {	
		
		User user = new User("Sasi", StringUtil.getRandomEmailId(), "female", "active");

		Integer userId = restClient.post(GOREST_ENDPOINT, "json", user, true, true).then().log().all().assertThat()
				.statusCode(APIHttpStatus.CREATED_201.getCode()).extract().path("id");

		System.out.println("User id: " + userId);
		
		restClient.get(GOREST_ENDPOINT+"/"+userId,true, true)
										.then().log().all()
											.assertThat()
												.statusCode(APIHttpStatus.OK_200.getCode())
													.and().body("id", equalTo(userId));
										
		
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
