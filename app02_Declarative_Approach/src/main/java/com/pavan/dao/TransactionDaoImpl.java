package com.pavan.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class TransactionDaoImpl implements TransactionDao {
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String transferFunds(String fromAccount, String toAccount, int transferAmount) {
		String status="";
			int debitRowCount = debit(fromAccount,transferAmount);
//			float f=100/0;    //after debit if we get an exception the no debit happened i.e rollback() method executes (no changes inside database)
			int creditRowCount = credit(toAccount,transferAmount);
			if(debitRowCount ==1 && creditRowCount==1)
			{
				status = "Transaction Success";
			}else {
				status = "Transaction Failure";
			}
			
		return status;
	}
	
	public int debit(String fromAccount, int transferAmount) {
		int rowCount = jdbcTemplate.update("update account set balance = balance - "+transferAmount+" where accno='"+fromAccount+"'");
		System.out.println(transferAmount+" is debited from "+fromAccount);
		return rowCount;
	}
	
	public int credit(String toAccount, int transferAmount) {
		int rowCount = jdbcTemplate.update("update account set balance = balance + "+transferAmount+" where accno='"+toAccount+"'");
		System.out.println(transferAmount+" is credited to "+toAccount);
		return rowCount;
	}

}
