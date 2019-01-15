package com.renu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class HelloController {
private static final Logger LOGGER=LoggerFactory.getLogger(HelloController.class);
@Autowired
RestTemplate restTemplate;
@HystrixCommand(fallbackMethod="fallback",
groupKey="Hello",
commandKey="hello",
threadPoolKey="helloThread")
@RequestMapping(value="/hello/client")
public String hello(){
	LOGGER.info("From class HelloController,method : hello()");
	String url="http://first-server/hello/server";
	String response=restTemplate.getForObject(url, String.class)+" via client";
	return response;
}
public String fallback(Throwable hystrixCommand) {
	return "Fall back occured !!!";
}

}
