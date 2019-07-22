package com.aceproject.demo.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aceproject.demo.dao.PersonDao;
import com.aceproject.demo.model.Person;
import com.aceproject.demo.support.CommonDaoSupport;

@Repository
public class PersonDaoMybatis extends CommonDaoSupport implements PersonDao {
	
	@Override
	public List<Person> getAll() {
		return getSqlSession().selectList("com.aceproject.demo.dao.person.selectList");
	}

	@Override
	public Person get(int personId) {
		return getSqlSession().selectOne("com.aceproject.demo.dao.person.select", personId);
	}

	@Override
	public int insert(Person person) {
		return getSqlSession().insert("com.aceproject.demo.dao.person.insert", person);
	}

	@Override
	public void update(Person person) {
		getSqlSession().update("com.aceproject.demo.dao.person.update", person);
	}
}
