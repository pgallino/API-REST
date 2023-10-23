package com.aninfo.repository;

import com.aninfo.model.Account;
import com.aninfo.model.Transaction;

import java.util.Collection;
import java.util.List;

import com.aninfo.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Transaction findAccountById(Long id);

    @Override
    List<Transaction> findAll();

}
