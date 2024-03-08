package com.sistema.contasbancarias.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroConta;

    private BigDecimal saldo;

    private String nomeTitular;

    private String agencia;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();


}
