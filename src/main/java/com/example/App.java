package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class App {
	
	@RequestMapping(method=RequestMethod.GET)
	public String home() {
		return "Hello World!";
	}
	@RequestMapping(value="/{name}", method=RequestMethod.GET)
	public String name(@PathVariable String name) {
		return "Hello World! " + name;
	}
	@RequestMapping(value="/{name}/{age}", method=RequestMethod.GET)
	public String nameAndAge(@PathVariable String name, @PathVariable String age) {
		return String.format("Hello World! %s - %s", name, age) ;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
