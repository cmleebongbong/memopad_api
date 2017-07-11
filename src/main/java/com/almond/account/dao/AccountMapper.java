package com.almond.account.dao;

import com.almond.account.domain.Account;

public interface AccountMapper {
	public Account selectAccountById(String id) throws Exception;
	public Account selectAccountByKey(String key) throws Exception;
	public Account signin(Account account) throws Exception;
	public int signup(Account account) throws Exception;
	public int updateKey(Account account) throws Exception;
}
