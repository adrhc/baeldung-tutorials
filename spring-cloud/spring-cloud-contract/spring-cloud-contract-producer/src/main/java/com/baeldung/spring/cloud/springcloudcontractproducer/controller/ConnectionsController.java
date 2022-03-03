package com.baeldung.spring.cloud.springcloudcontractproducer.controller;

import com.baeldung.spring.cloud.springcloudcontractproducer.model.GetConnections;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connections")
public class ConnectionsController {
	@GetMapping
	public GetConnections get(GetConnections input) {
		return input;
	}
}
