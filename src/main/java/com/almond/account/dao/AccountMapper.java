package com.almond.account.dao;

import com.almond.account.domain.Account;

public interface AccountMapper {
	public int selectAccountById(Account account) throws Exception;
	public Account signin(Account account) throws Exception;
	public int signup(Account account) throws Exception;
}
