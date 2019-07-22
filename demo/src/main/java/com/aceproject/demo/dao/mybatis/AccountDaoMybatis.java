package com.aceproject.demo.dao.mybatis;

import org.springframework.stereotype.Repository;

import com.aceproject.demo.dao.AccountDao;
import com.aceproject.demo.model.Account;
import com.aceproject.demo.support.CommonDaoSupport;

@Repository
public class AccountDaoMybatis extends CommonDaoSupport implements AccountDao {

	@Override
	public Account get(int accountId) {
		return getSqlSession().selectOne("com.aceproject.demo.dao.account.select", accountId);
	}

	@Override
	public int insert(Account account) {
		return getSqlSession().insert("com.aceproject.demo.dao.account.insert", account);
	}

	@Override
	public void update(Account account) {
		getSqlSession().update("com.aceproject.demo.dao.account.update", account);
	}


}
