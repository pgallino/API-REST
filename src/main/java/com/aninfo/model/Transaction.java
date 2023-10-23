package com.aninfo.model;

import javax.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long cbu;
    private Double amount;

    private String type;

    public Transaction(){
    }

    public Transaction(Long cbu, Double amount) {
        this.cbu = cbu;
        this.amount = amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getCbu() {
        return this.cbu;
    }

    public String getType() {
        return this.type;
    }
}
