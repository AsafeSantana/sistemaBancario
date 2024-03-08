package com.sistema.contasbancarias.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;

    private BigDecimal quantidade;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
