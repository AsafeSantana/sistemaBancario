package com.sistema.contasbancarias.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Enum tipo;

    private LocalDateTime data;

    private BigDecimal quantidade;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;


}
