package com.sistema.contasbancarias.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String numeroConta;

    private BigDecimal saldo;

    private String nomeTitular;

    private String agencia;

    public void debitar(BigDecimal valor){
        this.saldo = this.saldo.subtract(valor);
    }

    public void creditar(BigDecimal valor){
        this.saldo = this.saldo.add(valor);
    }

    @OneToMany(mappedBy = "conta",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transacao> transacoes = new ArrayList<>();


}
