package service.impl;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import config.AeroSpikeConfig;
import model.AccountHolder;
import service.AccountHolderService;

import java.util.ArrayList;
import java.util.List;

public class AccountHolderServiceImpl implements AccountHolderService {

    AerospikeClient client = AeroSpikeConfig.getAeroSpikeClient();

    @Override
    public List<AccountHolder> getAllAccountHolders() {
        Statement stmt = new Statement();
        stmt.setNamespace("test");
        stmt.setSetName("account_holder");
        RecordSet records =  client.query(null, stmt);

        List<AccountHolder> list = new ArrayList<>();

        while(records.next()) {
            Record current = records.getRecord();

            AccountHolder accountHolder = new AccountHolder();
            accountHolder.setId(current.getString("id"));
            accountHolder.setFirstName(current.getString("firstName"));
            accountHolder.setLastName(current.getString("lastName"));
            accountHolder.setBalance(current.getDouble("balance"));

            list.add(accountHolder);
        }
        return list;
    }

    @Override
    public AccountHolder getAccountHolder(String id) {
        Key key = new Key("test", "account_holder", id);
        Record record = client.get(null, key);

        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(record.getString("id"));
        accountHolder.setFirstName(record.getString("firstName"));
        accountHolder.setLastName(record.getString("lastName"));
        accountHolder.setBalance(record.getDouble("balance"));

        return accountHolder;

    }

    @Override
    public AccountHolder createNewAccount(AccountHolder accountHolder) {
        client = AeroSpikeConfig.getAeroSpikeClient();

        Key key = new Key("test", "account_holder", accountHolder.getId());
        Bin id = new Bin("id", accountHolder.getId());
        Bin firstName = new Bin("firstName", accountHolder.getFirstName());
        Bin lastName = new Bin("lastName", accountHolder.getLastName());
        Bin balance = new Bin("balance", accountHolder.getBalance());

        client.put(null, key, id, firstName, lastName, balance);
        return accountHolder;
    }

    @Override
    public boolean updateBalance(String id, double amount) {
        return false;
    }
}
