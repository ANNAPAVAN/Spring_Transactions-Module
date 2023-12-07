package com.pavan.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionDaoImpl implements TransactionDao {
	private JdbcTemplate jdbcTemplate;
	private DataSourceTransactionManager transactionManager;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void setTransactionManager(DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public String transferFunds(String fromAccount, String toAccount, int transferAmount) {
		String status="";
		TransactionDefinition txDef = new DefaultTransactionDefinition();
		TransactionStatus txStatus = transactionManager.getTransaction(txDef);
		try {
			int debitRowCount = debit(fromAccount,transferAmount);
			int creditRowCount = credit(toAccount,transferAmount);
			if(debitRowCount ==1 && creditRowCount==1)
			{
				transactionManager.commit(txStatus);
				status = "Transaction Success";
			}else {
				transactionManager.rollback(txStatus);
				status = "Transaction Failure";
			}
			
		}catch(Exception e) {
			transactionManager.rollback(txStatus);
			status = "Transaction Failure";
			e.printStackTrace();
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
