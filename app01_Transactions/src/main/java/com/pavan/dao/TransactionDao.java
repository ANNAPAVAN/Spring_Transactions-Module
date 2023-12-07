package com.pavan.dao;

public interface TransactionDao {
	public String transferFunds(String fromAccount,String toAccount, int transferAmount);
}
