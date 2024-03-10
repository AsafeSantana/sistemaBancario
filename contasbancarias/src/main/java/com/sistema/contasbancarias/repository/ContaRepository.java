package com.sistema.contasbancarias.repository;

import com.sistema.contasbancarias.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContaRepository extends JpaRepository <Conta, UUID>{

}
