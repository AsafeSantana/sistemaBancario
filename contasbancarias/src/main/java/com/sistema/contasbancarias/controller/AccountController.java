package com.sistema.contasbancarias.controller;

import com.sistema.contasbancarias.model.Account;
import com.sistema.contasbancarias.model.Transaction;
import com.sistema.contasbancarias.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}/saldo")
    public BigDecimal getSaldo(@PathVariable Long accountId){
        return accountService.getSaldo(accountId);
    }

    @GetMapping("/{accountId}/transacoes")
    public List<Transaction>getTransactions(@PathVariable Long accountId){
        return accountService.getTransactions(accountId);
    }


    //abrir contaBancaria
    @PostMapping("/")
    public Account createAccount(@RequestParam String numeroConta,@RequestParam BigDecimal saldoInicial, @RequestParam String nomeTitular, @RequestParam String agencia){
        return accountService.createAccount(numeroConta, saldoInicial, nomeTitular, agencia);
    }

    //exibir contas abertas e dados  da mesma
    @GetMapping("/")
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }
}
