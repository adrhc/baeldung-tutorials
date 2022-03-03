package com.baeldung.spring.cloud.springcloudcontractconsumer.controller;

import com.baeldung.spring.cloud.springcloudcontractconsumer.model.GetConnections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

@RestController
public class BasicMathController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/calculate")
	public String checkOddAndEven(@RequestParam("number") Integer number) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/json");

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"http://localhost:8090/validate/prime-number?number=" + number,
				HttpMethod.GET,
				new HttpEntity<>(httpHeaders),
				String.class);

		return responseEntity.getBody();
	}

	@GetMapping("/connections")
	public GetConnections connections(GetConnections input) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/json");

		URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/connections")
				.queryParam("offset", input.getOffset())
				.queryParam("limit", input.getLimit())
				.queryParam("sort", Optional.ofNullable(input.getSort())
						.stream().flatMap(Arrays::stream).toArray())
				.build().toUri();

		ResponseEntity<GetConnections> responseEntity = restTemplate.exchange(
				uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), GetConnections.class);

		return responseEntity.getBody();
	}
}
