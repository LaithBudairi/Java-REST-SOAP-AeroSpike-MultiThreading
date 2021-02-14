package service;

import model.AccountHolder;

import java.util.List;

public interface AccountHolderService {

    List<AccountHolder> getAllAccountHolders();
    AccountHolder getAccountHolder(String id);
    AccountHolder createNewAccount(AccountHolder accountHolder);
    boolean updateBalance(String id, double amount);
    boolean deleteAccountById(String id);
}
