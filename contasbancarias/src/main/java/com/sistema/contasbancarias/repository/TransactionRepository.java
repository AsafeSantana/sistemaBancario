package com.sistema.contasbancarias.repository;

import com.sistema.contasbancarias.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
