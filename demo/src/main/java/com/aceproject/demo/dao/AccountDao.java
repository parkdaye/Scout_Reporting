package com.aceproject.demo.dao;

import com.aceproject.demo.model.Account;

public interface AccountDao {
	
	Account get(int teamId);
	
	int insert(Account account);
	
	void update(Account account);
}
