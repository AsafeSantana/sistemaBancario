package com.sistema.contasbancarias.repository;

import com.sistema.contasbancarias.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
}
