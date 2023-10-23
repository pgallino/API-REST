package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

// @Service indica que es un servicio
// acá va la lógica de negocio, no debe mezclarse con el controller
// el controller delega los request a los servicios

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // simplemente recibe por parámetro la cuenta que se creó y delega al Repository la persistencia
    public Account createAccount(Account account) {
        Double initial_balance = account.getBalance();
        Long cbu = account.getCbu();
        Transaction transaction = new Transaction(cbu, initial_balance);

        return accountRepository.save(account);
    }

    public Collection<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long cbu) {
        return accountRepository.findById(cbu);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void deleteById(Long cbu) {
        accountRepository.deleteById(cbu);
    }

    @Transactional
    public Account withdraw(Long cbu, Double sum) {
        Account account = accountRepository.findAccountByCbu(cbu);

        if (account.getBalance() < sum) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - sum);
        accountRepository.save(account);

        return account;
    }

    @Transactional
    public Account deposit(Long cbu, Double sum) {

        Double discount = 0.1;
        Double topDiscount = 500.0;

        if (sum <= 0) {
            throw new DepositNegativeSumException("Cannot deposit non positive sums");
        }

        if (sum >= 2000) {
            if (sum*discount < 500) {
                sum += sum*discount;
            } else {
                sum += topDiscount;
            }
        }

        Account account = accountRepository.findAccountByCbu(cbu);
        account.setBalance(account.getBalance() + sum);
        accountRepository.save(account);

        return account;
    }

}
