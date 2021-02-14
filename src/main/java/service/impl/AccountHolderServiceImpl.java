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
import utils.AccountHolderUtil;

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

            AccountHolder accountHolder = AccountHolderUtil.extractAccountHolder(current);

            list.add(accountHolder);
        }
        return list;
    }

    @Override
    public AccountHolder getAccountHolder(String id) {
        Key key = new Key("test", "account_holder", id);
        Record record = client.get(null, key);

        if(record == null) {
            return null;
        }

        AccountHolder accountHolder = AccountHolderUtil.extractAccountHolder(record);

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
        Key key = new Key("test", "account_holder", id);
        Record record = client.get(null, key, "balance");
        Bin balance = new Bin("balance", amount + record.getDouble("balance"));
        client.put(null, key, balance);
        return true;
    }

    @Override
    public boolean deleteAccountById(String id) {
        Key key = new Key("test", "account_holder", id);
        client.delete(null, key);
        return true;
    }
}