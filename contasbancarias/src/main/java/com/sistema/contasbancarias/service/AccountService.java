package com.sistema.contasbancarias.service;

import com.sistema.contasbancarias.model.Account;
import com.sistema.contasbancarias.model.Transaction;
import com.sistema.contasbancarias.repository.AccountRepository;
import com.sistema.contasbancarias.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private TransactionRepository transactionRepository;

    private List<Account> accounts = new ArrayList<>();

    public BigDecimal getSaldo(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        return account.getSaldo();
    }
    public List<Transaction> getTransactions(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Transação não encontrada"));
        return account.getTransactions();
    }

    //abrir uma conta
    public Account createAccount(String numeroConta, BigDecimal saldoInicial, String nomeTitular, String agencia){
        Account account = new Account();
        account.setNumeroConta(numeroConta);
        account.setSaldo(saldoInicial);
        account.setNomeTitular(nomeTitular);
        account.setAgencia(agencia);
        return accountRepository.save(account);
    }

    //obter contas
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
