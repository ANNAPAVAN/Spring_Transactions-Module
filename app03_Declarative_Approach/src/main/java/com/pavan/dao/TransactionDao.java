package com.pavan.dao;

public interface TransactionDao {
	public String transferFunds(String fromAccount,String toAccount, int transferAmount);
	public String deposit(String accNo,int depAmt);
	public String withdraw(String accNo,int wdAmt);
}
