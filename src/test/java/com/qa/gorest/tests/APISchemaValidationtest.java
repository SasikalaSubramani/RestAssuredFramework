package com.qa.gorest.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.StringUtil;

public class APISchemaValidationtest extends BaseTest {
	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);
	}

	@Test
	public void createUserAPISchemaTest() {

		// 1. post:
		User user = new User("Tom", StringUtil.getRandomEmailId(), "Male", "Active");

		restClient.post(GOREST_ENDPOINT, "json", user, true, true).then().log().all().assertThat()
				.statusCode(APIHttpStatus.CREATED_201.getCode())
				.body(matchesJsonSchemaInClasspath("createuserschema.json"));

	}

}
