package com.sistema.contasbancarias.service;

import com.sistema.contasbancarias.enums.TipoTransacao;
import com.sistema.contasbancarias.model.Conta;
import com.sistema.contasbancarias.model.Transacao;
import com.sistema.contasbancarias.repository.ContaRepository;
import com.sistema.contasbancarias.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    private List<Conta> contas = new ArrayList<>();

    public BigDecimal getSaldo(UUID contaId) {
        Conta conta = contaRepository.findById(contaId).orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        return conta.getSaldo();
    }
    public List<Transacao> getTransacoes(UUID contaId) {
        Conta conta = contaRepository.findById(contaId).orElseThrow(() -> new RuntimeException("Transação não encontrada"));
        return conta.getTransacoes();
    }

    //abrir uma conta
    public Conta criarConta(String numeroConta, BigDecimal saldoInicial, String nomeTitular, String agencia){
        Conta conta = new Conta();
        conta.setNumeroConta(numeroConta);
        conta.setSaldo(saldoInicial);
        conta.setNomeTitular(nomeTitular);
        conta.setAgencia(agencia);
        return contaRepository.save(conta);
    }

    //obter contas - ADM
    public List<Conta> getAllContas() {
        return contaRepository.findAll();
    }

    //encerramento de conta- ADM
    public void encerrarConta(UUID contaId) {
        Conta conta = contaRepository.findById(contaId).orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        if (conta.getSaldo().compareTo(BigDecimal.ZERO) != 0){
            throw new IllegalArgumentException("A conta não pode ser encerrada,pois ainda tem saldo");
        }
        contaRepository.delete(conta);
    }

    //DEPOSITAR
    public void depositar(UUID contaId, BigDecimal valor){
        Conta conta = contaRepository.findById(contaId).orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero reais");
        }

        BigDecimal novoSaldo = conta.getSaldo().add(valor);
        conta.setSaldo(novoSaldo);
        contaRepository.save(conta);

        Transacao transacao = new Transacao();
        transacao.setData(LocalDateTime.now());
        transacao.setQuantidade(valor);
        transacao.setTipo(TipoTransacao.DEPOSITO);
        transacao.setConta(conta);
        transacaoRepository.save(transacao);
    }

    //SACAR
    public void saque(UUID contaId, BigDecimal valor){
        Conta conta = contaRepository.findById(contaId).orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (valor.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("O valor do saque deve ser superior a zero reais");
        }

        BigDecimal saldoAtual= conta.getSaldo();

        if(saldoAtual.compareTo(valor) < 0){
            throw new IllegalArgumentException("Saldo Insuficiente para realizar o saque");
        }

        BigDecimal novoSaldo  = saldoAtual.subtract(valor);
        conta.setSaldo(novoSaldo);
        contaRepository.save(conta);


        Transacao transacao = new Transacao();
        transacao.setData(LocalDateTime.now());
        transacao.setQuantidade(valor.negate());
        transacao.setTipo(TipoTransacao.SAQUE);
        transacao.setConta(conta);
        transacaoRepository.save(transacao);
    }

    //TRANSFERENCIA
    @Transactional
    public  void transferencia(UUID contaOrigemId, UUID contaDestinoId, BigDecimal valor){
       Conta contaOrigem = contaRepository.findById(contaOrigemId).orElseThrow(() -> new IllegalArgumentException("Conta origem não encontrada"));
       Conta contaDestino = contaRepository.findById(contaDestinoId).orElseThrow(() -> new IllegalArgumentException("Conta destino não encontrada"));

       if (valor.compareTo(BigDecimal.ZERO) <= 0 ){
           throw new IllegalArgumentException("O valor da transferência deve ser maior que zero");
       }
        if (contaOrigem.getSaldo().compareTo(valor) < 0){
            throw new RuntimeException("Saldo Insuficiente para efetuar a transferência!");
        }

        contaOrigem.debitar(valor);
        contaDestino.creditar(valor);

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);


        registrarTransacao(contaOrigem, TipoTransacao.TRANSFERENCIA, valor);
        registrarTransacao(contaDestino, TipoTransacao.TRANSFERENCIA,valor);


    }
    //registrar transação transferencia
    private void registrarTransacao(Conta conta, TipoTransacao tipoTransacao, BigDecimal valor) {
        Transacao transacao = new Transacao();
        transacao.setConta(conta);
        transacao.setData(LocalDateTime.now());
        transacao.setTipo(tipoTransacao);
        transacao.setQuantidade(valor);
        transacaoRepository.save(transacao);
    }

}
