package br.com.fecaf.controller;

import br.com.fecaf.dao.ClienteDAO;
import br.com.fecaf.dao.TransacaoDAO;
import br.com.fecaf.model.Cliente;
import br.com.fecaf.model.Transacao;
import br.com.fecaf.utils.Formatador;
import java.time.LocalDateTime;
import java.util.Random;

public class ClienteController {

    public static boolean cpfExiste(String cpf){
        return ClienteDAO.buscarPorCpf(cpf) != null;
    }

    public static void adicionarCliente(Cliente cliente){

        cliente.setAgencia("0001");
        cliente.setNumeroConta(gerarNumeroConta());
        cliente.setSaldo(0);

        ClienteDAO.salvar(cliente);
    }

    public static Cliente fazerLogin(String cpf, String senha){
        return ClienteDAO.fazerLogin(cpf, senha);
    }

    //--------------------------------
    // DEPOSITAR
    //--------------------------------
    public static void depositar(Cliente cliente, double valor){

        double novoSaldo = cliente.getSaldo() + valor;
        cliente.setSaldo(novoSaldo);

        ClienteDAO.atualizarSaldo(cliente.getCpf(), novoSaldo);

        registrar(cliente.getCpf(), "DEPOSITO", valor);
    }

    //--------------------------------
    // SACAR
    //--------------------------------
    public static boolean sacar(Cliente cliente, double valor){

        if(cliente.getSaldo() < valor) return false;

        double novoSaldo = cliente.getSaldo() - valor;
        cliente.setSaldo(novoSaldo);

        ClienteDAO.atualizarSaldo(cliente.getCpf(), novoSaldo);

        registrar(cliente.getCpf(), "SAQUE", valor);

        return true;
    }

    //--------------------------------
    // TRANSFERIR (NOVO)
    //--------------------------------
    public static boolean transferir(Cliente origem, String agencia, String conta, double valor){

        if(valor <= 0 || origem.getSaldo() < valor) return false;

        Cliente destino = ClienteDAO.buscarPorConta(agencia, conta);

        if(destino == null) return false;

        // atualizar saldos
        origem.setSaldo(origem.getSaldo() - valor);
        destino.setSaldo(destino.getSaldo() + valor);

        ClienteDAO.atualizarSaldo(origem.getCpf(), origem.getSaldo());
        ClienteDAO.atualizarSaldo(destino.getCpf(), destino.getSaldo());

        registrar(origem.getCpf(), "TRANSFERENCIA_ENVIADA", valor);
        registrar(destino.getCpf(), "TRANSFERENCIA_RECEBIDA", valor);

        return true;
    }

    //--------------------------------
    // REGISTRAR TRANSAÇÃO
    //--------------------------------
    private static void registrar(String cpf, String tipo, double valor){

        Transacao t = new Transacao();

        t.setCpfCliente(cpf);
        t.setTipo(tipo);
        t.setValor(valor);
        t.setData(LocalDateTime.now().toString());

        TransacaoDAO.registrar(t);
    }

    //--------------------------------
    private static String gerarNumeroConta(){

        return (new Random().nextInt(90000) + 10000) + "-0";
    }
}