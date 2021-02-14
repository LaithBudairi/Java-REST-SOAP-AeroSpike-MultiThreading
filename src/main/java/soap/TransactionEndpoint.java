package soap;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import config.AeroSpikeConfig;
import model.Transaction;
import service.AccountHolderService;
import service.TransactionService;
import service.impl.AccountHolderServiceImpl;

import javax.jws.WebService;

@WebService(endpointInterface = "service.TransactionService")
public class TransactionEndpoint implements TransactionService {

    AccountHolderService accountHolderService = new AccountHolderServiceImpl();
    AerospikeClient client = AeroSpikeConfig.getAeroSpikeClient();

    @Override
    public Transaction createTransaction(Transaction t) {
        accountHolderService.updateBalance(t.getFrom(), t.getAmount() * -1);
        accountHolderService.updateBalance(t.getTo(), t.getAmount());

        Key key = new Key("test", "transaction", t.getId());
        Bin from = new Bin("from", t.getFrom());
        Bin to = new Bin("to", t.getTo());
        Bin amount = new Bin("amount", t.getAmount());
        client.put(null, key, from, to, amount);
        return t;
    }
}
