package com.aceproject.demo.dao.mybatis;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.dao.AccountDao;
import com.aceproject.demo.model.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class AccountDaoMybatisTest {

	@Autowired
	private AccountDao accountDao;

	@Test
	public void test() {
		int testTeamId = 1;

		Account account = Account.builder().teamId(testTeamId).ap(100).build();

		accountDao.insert(account);
		assertTrue(100 == accountDao.get(testTeamId).getAp());

		account.setAp(1003300);
		accountDao.update(account);
		System.out.println(accountDao.get(testTeamId).getAp());
		assertTrue(1003300 == accountDao.get(testTeamId).getAp());

//		assertThat(account.getAp(), is(accountDao.get(testTeamId).getAp()));
//		
//		account.setAp(1003300);
//		accountDao.update(account);
//
//		assertThat(account.getAp(), is(accountDao.get(testTeamId).getAp()));
	}

}
