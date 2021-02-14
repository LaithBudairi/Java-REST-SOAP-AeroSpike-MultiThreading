package utils;

import com.aerospike.client.Record;
import model.AccountHolder;

public class AccountHolderUtil {

    private AccountHolderUtil() {

    }

    public static AccountHolder extractAccountHolder(Record record) {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(record.getString("id"));
        accountHolder.setFirstName(record.getString("firstName"));
        accountHolder.setLastName(record.getString("lastName"));
        accountHolder.setBalance(record.getDouble("balance"));
        return accountHolder;
    }
}