package com.sistema.contasbancarias.controller;

import com.sistema.contasbancarias.model.Conta;
import com.sistema.contasbancarias.model.Transacao;
import com.sistema.contasbancarias.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private  ContaService contaService;

    //depositar
    @PostMapping("/{contaId}/depositar")
    public ResponseEntity<String> depositar(@PathVariable UUID contaId, @RequestParam BigDecimal valor){
        contaService.depositar(contaId, valor);
        return ResponseEntity.ok("Depósito realizado com sucesso!");
    }

    //exibir saldo da conta
    @GetMapping("/{contaId}/saldo")
    public BigDecimal getSaldo(@PathVariable UUID contaId){
        return contaService.getSaldo(contaId);
    }


    //exibir historico de transações
    @GetMapping("/{contaId}/transacoes")
    public List<Transacao>getTransactions(@PathVariable UUID contaId){
        return contaService.getTransacoes(contaId);
    }

    //abrir contaBancaria
    @PostMapping("/")
    public Conta criarConta(@RequestParam String numeroConta, @RequestParam BigDecimal saldoInicial, @RequestParam String nomeTitular, @RequestParam String agencia){
        return contaService.criarConta(numeroConta, saldoInicial, nomeTitular, agencia);
    }

    //exibir contas abertas e dados  da mesma - ADM
    @GetMapping("/")
    public List<Conta> getAllContas(){
        return contaService.getAllContas();
    }

    //encerramento da conta- ADM
    @DeleteMapping("/{contaId}")
    public ResponseEntity<String> encerrarConta(@PathVariable UUID contaId){
       try {
           contaService.encerrarConta(contaId);
           return ResponseEntity.ok("Conta encerrada com sucesso");
       }catch (IllegalArgumentException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
           }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    //sacar dinheiro
    @PostMapping("/{contaId}/sacar")
    public ResponseEntity<String> sacar(@PathVariable UUID contaId, @RequestParam BigDecimal valor){
        try{
            contaService.saque(contaId,valor);
            return ResponseEntity.ok("Saque realizado com sucesso");
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
