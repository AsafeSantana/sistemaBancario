package com.sistema.contasbancarias.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction  {

    private Long id;

    private LocalDateTime data;

    private BigDecimal quantidade;


}
