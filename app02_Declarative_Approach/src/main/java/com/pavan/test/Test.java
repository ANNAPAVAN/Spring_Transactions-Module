package com.pavan.test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.pavan.dao.*;
public class Test {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/com/pavan/resources/applicationContext.xml");
		TransactionDao transactionDao = (TransactionDao)context.getBean("transactionDao");
		String status = transactionDao.transferFunds("abc123","xyz123",5000);
		System.out.println(status);

	}

}



/*
mysql> create table account(
	    -> accno varchar(20),
	    -> balance int,
	    -> primary key(accno));
	Query OK, 0 rows affected (0.03 sec)
	
	
*/