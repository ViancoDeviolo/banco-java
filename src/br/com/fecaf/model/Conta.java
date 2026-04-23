package br.com.fecaf.model;
import br.com.fecaf.utils.Formatador;
public class Conta {
    // Atributos da Conta
    private String numero, agencia;
    private double saldo;
    private Cliente cliente;


    // Construtor para Conta
    public Conta (String numero, String agencia, Cliente cliente) {
        this.numero = numero;
        this.agencia = agencia;
        this.saldo = 0.0;

        this.cliente = cliente;

    }

    // Depositar
    public void depositar(double valor) {
        if (valor > 0) {
            //saldo = saldo + valor;
            saldo += valor;

        } else {
            System.out.println("Valor Inválido !");
            // Depois alterar para o JOption
        }
    }

    // Saque
    public boolean sacar(double valorSaque) {
        if (valorSaque > 0 && valorSaque < saldo) {
            //saldo = saldo - valorSaque;
            saldo -= valorSaque;
            return true;
        }
        return false;
    }


    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}

