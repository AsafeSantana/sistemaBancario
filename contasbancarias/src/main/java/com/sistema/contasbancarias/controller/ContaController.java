package com.sistema.contasbancarias.controller;

import com.sistema.contasbancarias.model.Conta;
import com.sistema.contasbancarias.model.Transacao;
import com.sistema.contasbancarias.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/{contaId}/depositar")
    public ResponseEntity<String> depositar(@PathVariable UUID contaId, @RequestParam BigDecimal valor){
        contaService.depositar(contaId, valor);
        return ResponseEntity.ok("Depósito realizado com sucesso!");
    }
    @GetMapping("/{contaId}/saldo")
    public BigDecimal getSaldo(@PathVariable UUID contaId){
        return contaService.getSaldo(contaId);
    }

    @GetMapping("/{contaId}/transacoes")
    public List<Transacao>getTransactions(@PathVariable UUID contaId){
        return contaService.getTransacoes(contaId);
    }

    //abrir contaBancaria
    @PostMapping("/")
    public Conta createAccount(@RequestParam String numeroConta, @RequestParam BigDecimal saldoInicial, @RequestParam String nomeTitular, @RequestParam String agencia){
        return contaService.criarConta(numeroConta, saldoInicial, nomeTitular, agencia);
    }

    //exibir contas abertas e dados  da mesma
    @GetMapping("/")
    public List<Conta> getAllAccounts(){
        return contaService.getAllContas();
    }

}
