package com.aceproject.demo.dao;

import java.util.List;

import com.aceproject.demo.model.Person;

public interface PersonDao {

	Person get(int personId);
	
	int insert(Person person);
	
	void update(Person person);
	
	List<Person> getAll();
}
