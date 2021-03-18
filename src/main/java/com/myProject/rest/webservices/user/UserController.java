package com.myProject.rest.webservices.user;

import java.net.URI;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
 
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
	
	@Autowired
	private UserServiceDao usd;
	
	private MessageSource msgSource;
	
	//get all Users
	@GetMapping("/Users")
	public List<User> findAll() {
		return usd.findAll();
	}
	
	//Get a specific user
	@GetMapping("/User/{id}")
	public User findSingleUser(@PathVariable int id){
		User usr = usd.findUser(id);
		if(usr == null) {
			throw new userNotFoundException("Id"+id);
		}
		return usd.findUser(id);
		
	}
	
	//Create a user
	//@RequestBody to be used in case when we are trying to create new records via post call
	@PostMapping("/user/add")
	public EntityModel<User> createUser(@Valid @RequestBody User usr) {
		User saveUser = usd.addUser(usr);
		
		//to return the id and location of path of new record created
		URI location  = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(saveUser.getId()).toUri();
		
		EntityModel<User> resource = EntityModel.of(saveUser);
		
		WebMvcLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).findAll());
				
		resource.add(linkTo.withRel("Here is the link to find all the users"));
		
		return resource;
	}
	
	//Get a specific user
	@DeleteMapping("/User/{id}")
	public void deleteUser(@PathVariable int id){
		User usr = usd.deleteUser(id);

		if(usr == null) {
			throw new userNotFoundException("Id->"+id);
		}
		
	}

	@GetMapping(path = "/global-user")
	public String globalUserMessage(@RequestHeader(name = "Accept-Lang", required = false) Locale locale){
		return msgSource.getMessage("website.welcome.message",null, locale);
		
	}
	
}
