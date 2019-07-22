package com.aceproject.demo.dao.mybatis;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aceproject.demo.dao.PersonDao;
import com.aceproject.demo.model.Person;

public class PersonDaoMybatisTest {

	@Autowired
	PersonDao personDao;
	
	@Test
	public void test() {
		List<Person> persons = personDao.getAll();
		
		System.out.println(persons.get(0).getName());
		
		Person person = Person.builder()
				.name("test")
				.build();
		
		int personId = personDao.insert(person);
		System.out.println(personDao.get(personId).getName());
		
		person.setName("test22");
		personDao.update(person);
		System.out.println(personDao.get(personId).getName());
	}

}
