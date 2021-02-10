package soap;

import service.TransactionService;

import javax.jws.WebService;

@WebService(endpointInterface = "service.TransactionService")
public class TransactionServiceImp implements TransactionService {
    @Override
    public String createTransaction() {
        return "yoo";
    }
}
