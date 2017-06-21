package com.almond.account.dao;

import com.almond.account.domain.Account;

public interface AccountMapper {
	public Account signin(Account account) throws Exception;
}
