package com.myProject.rest.webservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserServiceDao {

	private static List<User> usrs = new ArrayList<>();
	private int counter = 3;

	static {
		usrs.add(new User(1, "Rajeev", new Date()));
		usrs.add(new User(2, "James", new Date()));
		usrs.add(new User(3, "RaY", new Date()));

	}

	// Get All the users

	public List<User> findAll() {
		return usrs;
	}

	// Find a specific user
	public User findUser(int id) {
		for (User usr : usrs) {
			if (usr.getId() == id) {
				return usr;
			}
		}
		return null;

	}

	// Add a new user
	public User addUser(User usr) {
		if (usr.getId() == null) {
			usr.setId(++counter);
		}
		usrs.add(usr);
		return usr;

	}
	
	// Find a specific user
	public User deleteUser(int id) {
		Iterator<User> itr = usrs.iterator();
		while(itr.hasNext()) {
			User usr = itr.next();
			if(usr.getId()==id) {
				itr.remove();
				return usr;
			}
		}
		
		return null;

	}

}
