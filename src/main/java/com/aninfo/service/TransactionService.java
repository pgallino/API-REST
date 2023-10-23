package com.aninfo.service;

import com.aninfo.exceptions.InvalidTransactionException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction, AccountService accountService) {
        Optional<Account> accountOptional = accountService.findById(transaction.getCbu());

        if (!accountOptional.isPresent()) {
            throw new InvalidTransactionException("cbu not found to carry out the transaction");
        }
        if ("withdraw".equals(transaction.getType())){
            accountService.withdraw(transaction.getCbu(),transaction.getAmount());
        } else if ("deposit".equals(transaction.getType())) {
            accountService.deposit(transaction.getCbu(),transaction.getAmount());
        }
        return transactionRepository.save(transaction);
    }

    public Collection<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    public Collection<Transaction> findByCbu(Long cbu) {
        Collection<Transaction> cbuTransactions = new ArrayList<>();

        for (Transaction transaction: transactionRepository.findAll()) {
            if (transaction.getCbu().equals(cbu)) {
                cbuTransactions.add(transaction);
            }
        }
        return cbuTransactions;
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }
}
