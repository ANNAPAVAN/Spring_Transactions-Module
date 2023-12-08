package com.pavan.dao;

import java.util.List;
import com.pavan.mapper.*;
import com.pavan.beans.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class TransactionDaoImpl implements TransactionDao {
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Transactional 
	public String deposit(String accNo, int depAmt)
	{
		String status="";
		int depositRowCount = credit(accNo,depAmt);
		if(depositRowCount==1)
		{
			status="Transaction Success";
		}
		else {
			status = "Transaction Failure";
			throw new RuntimeException("Insufficient Details in Transaction"); //if exception then it has to perform rollback()
		}
		return status;
	}
	
	public String withdraw(String accNo, int wdAmt) {
	    // Use parameterized query to prevent SQL injection
	    List<Object> accountList = jdbcTemplate.query(
	        "SELECT * FROM account WHERE accno = ?",
	        new Object[]{accNo},
	        new AccountRowMapper()
	    );

	    if (accountList.isEmpty()) {
	        return "Account does not exist.";
	    }

	    Account account = (Account) accountList.get(0); // Get the first account (if any)
	    int balance = account.getBalance();
	    
	    String status="";
	    if(balance<wdAmt) {
	    	status="Transaction Failure";
	    	throw new RuntimeException("Insufficient Funds in Account");
	    }else {
	    	int debitRowCount = debit(accNo,wdAmt);
	    	if(debitRowCount==1)
	    	{
	    		status="Transaction Success";
	    	}else {
	    		status="Transaction Failure";
	    	}
	    }
	    

	    return status; 
	}

	

	@Transactional      //we have to keep this above the business method
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
