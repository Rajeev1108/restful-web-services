package com.myProject.rest.webservices.filtering;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilteringController {
	
	@GetMapping("/filtering")
	public TestBean retrieve() {
		
		return new TestBean("value1","Value2","Value3");
	}

}
