package br.com.ffroliva.portfolio.controller;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import lombok.extern.slf4j.Slf4j;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import br.com.ffroliva.portfolio.payload.LoginRequest;

@Slf4j
public class BaseTestController {
	
	
	public String getAuthorization(String usernameOrEmail, String password) {
		MockMvcResponse response = given()
				.contentType(JSON)
				.body(LoginRequest.of(usernameOrEmail, password))
				.post("/api/auth/signin");
		String accessToken = response.getBody().jsonPath().get("accessToken");
		String authorization = "Bearer " + accessToken;
		log.info(authorization);
		return authorization;
	}

}