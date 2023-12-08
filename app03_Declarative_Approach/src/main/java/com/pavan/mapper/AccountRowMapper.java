package com.pavan.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.pavan.beans.*;
import org.springframework.jdbc.core.RowMapper;

public class AccountRowMapper implements RowMapper<Object> {
	
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setAccNo(rs.getString("accNo")); // Assuming the column name for accNo in the database
        account.setBalance(rs.getInt("balance")); // Assuming the column name for balance in the database
        return account;
    }
}
