package com.sistema.contasbancarias.repository;

import com.sistema.contasbancarias.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


public interface ContaRepository extends JpaRepository <Conta, UUID>{


}
