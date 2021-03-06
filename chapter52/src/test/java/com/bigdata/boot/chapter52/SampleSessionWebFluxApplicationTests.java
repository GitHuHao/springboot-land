package com.bigdata.boot.chapter52;

import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = "server.servlet.session.timeout:2", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleSessionWebFluxApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Test
	public void userDefinedMappingsSecureByDefault() throws Exception {
		WebClient webClient = this.webClientBuilder.baseUrl("http://localhost:" + this.port + "/").build();
		ClientResponse response = webClient.get().header("Authorization", getBasicAuth()).exchange().block();
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
		ResponseCookie sessionCookie = response.cookies().getFirst("SESSION");
		String sessionId = response.bodyToMono(String.class).block();
		response = webClient.get().cookie("SESSION", sessionCookie.getValue()).exchange().block();
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.bodyToMono(String.class).block()).isEqualTo(sessionId);
		Thread.sleep(2000);
		response = webClient.get().cookie("SESSION", sessionCookie.getValue()).exchange().block();
		assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	private String getBasicAuth() {
		return "Basic " + Base64.getEncoder().encodeToString("user:password".getBytes());
	}

}