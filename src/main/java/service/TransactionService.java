package service;

import model.Transaction;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface TransactionService {

    @WebMethod
    Transaction createTransaction(Transaction transaction);
}
