package com.boyang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boyang.service.TestService;

@RestController
@RequestMapping("/")
public class TestController {
	
	@PostMapping("/modify")
	String modify(@RequestParam("uname") String uname,@RequestParam("uage") String uage){
		return testService.modify(uname, uage)?"success":"false";
	}
	@GetMapping("/")
	String sayHello(){
		return testService.getData().toString();
	}
	
	@Autowired
	private TestService testService;
}
