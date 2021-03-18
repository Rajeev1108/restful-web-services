package com.myProject.rest.webservices.user;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
public class UserJPAController {
	
	@Autowired
	private UserServiceDao usd;
	
	private MessageSource msgSource;
	
	@Autowired
	private UserRepository userRepo;
	
	//get all Users
	@GetMapping("/jpa/Users")
	public List<User> findAll() {
		return userRepo.findAll();
	}
	
	//Get a specific user
	@GetMapping("/jpa/User/{id}")
	public User findSingleUser(@PathVariable int id){
		Optional<User> usr = userRepo.findById(id);
		if(!usr.isPresent()) {
			throw new userNotFoundException("Id"+id);
		}
		return usr.get();
		
	}
	
	//Create a user
	//@RequestBody to be used in case when we are trying to create new records via post call
	@PostMapping("/jpa/user/add")
	public EntityModel<User> createUser(@Valid @RequestBody User usr) {
		User saveUser = userRepo.save(usr);
		
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
	@DeleteMapping("/jpa/User/{id}")
	public void deleteUser(@PathVariable int id){
		userRepo.deleteById(id);
		
	}

	@GetMapping(path = "/jpa/global-user")
	public String globalUserMessage(@RequestHeader(name = "Accept-Lang", required = false) Locale locale){
		return msgSource.getMessage("website.welcome.message",null, locale);
		
	}
	
}
