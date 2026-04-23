package br.com.fecaf.view;

import br.com.fecaf.controller.ClienteController;
import br.com.fecaf.model.Cliente;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class TelaContaCliente extends JFrame {

    private Cliente cliente;

    private JLabel lblSaldo;
    private JButton btnOlho;

    private boolean saldoVisivel = false;

    public TelaContaCliente(Cliente cliente){

        this.cliente = cliente;

        setTitle("Conta do Cliente");
        setSize(420,520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        Color laranjaItau = new Color(255,98,0);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(Color.WHITE);

        add(painelPrincipal);

        //--------------------------------
        // TARJETA SALDO
        //--------------------------------

        JPanel cartaoSaldo = new JPanel();
        cartaoSaldo.setBackground(laranjaItau);
        cartaoSaldo.setMaximumSize(new Dimension(420,120));
        cartaoSaldo.setLayout(new FlowLayout(FlowLayout.CENTER,10,40));

        JLabel lblTextoSaldo = new JLabel("Saldo:");
        lblTextoSaldo.setForeground(Color.WHITE);
        lblTextoSaldo.setFont(new Font("Arial",Font.BOLD,18));

        lblSaldo = new JLabel();
        lblSaldo.setForeground(Color.WHITE);
        lblSaldo.setFont(new Font("Arial",Font.BOLD,24));

        btnOlho = new JButton("X👁");
        btnOlho.setFocusPainted(false);
        btnOlho.setBorderPainted(false);
        btnOlho.setContentAreaFilled(false);
        btnOlho.setFont(new Font("Arial",Font.PLAIN,20));

        atualizarSaldo();

        btnOlho.addActionListener(e -> {
            saldoVisivel = !saldoVisivel;
            atualizarSaldo();
        });

        cartaoSaldo.add(lblTextoSaldo);
        cartaoSaldo.add(lblSaldo);
        cartaoSaldo.add(btnOlho);

        painelPrincipal.add(cartaoSaldo);
        painelPrincipal.add(Box.createVerticalStrut(25));

        //--------------------------------
        // BOTÕES
        //--------------------------------

        JPanel painelBotoes = new JPanel(new GridLayout(3,2,20,20));
        painelBotoes.setBackground(Color.WHITE);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(0,30,0,30));

        JButton btnDepositar = criarBotao("Depositar");
        JButton btnSacar = criarBotao("Sacar");
        JButton btnTransferir = criarBotao("Transferir"); // NOVO
        JButton btnExtrato = criarBotao("Extrato");
        JButton btnSair = criarBotao("Sair");

        //--------------------------------
        // DEPÓSITO
        //--------------------------------

        btnDepositar.addActionListener(e -> {

            String valorStr = JOptionPane.showInputDialog("Valor do depósito:");

            if(valorStr != null && !valorStr.isEmpty()){

                try{
                    double valor = Double.parseDouble(valorStr.replace(",", "."));

                    ClienteController.depositar(cliente, valor);

                    atualizarSaldo();

                    JOptionPane.showMessageDialog(this,"Depósito realizado!");

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this,"Valor inválido!");
                }
            }
        });

        //--------------------------------
        // SAQUE
        //--------------------------------

        btnSacar.addActionListener(e -> {

            String valorStr = JOptionPane.showInputDialog("Valor do saque:");

            if(valorStr != null && !valorStr.isEmpty()){

                try{
                    double valor = Double.parseDouble(valorStr.replace(",", "."));

                    boolean sucesso = ClienteController.sacar(cliente, valor);

                    if(sucesso){
                        atualizarSaldo();
                        JOptionPane.showMessageDialog(this,"Saque realizado!");
                    }else{
                        JOptionPane.showMessageDialog(this,"Saldo insuficiente!");
                    }

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this,"Valor inválido!");
                }
            }
        });

        //--------------------------------
        // TRANSFERÊNCIA (NOVO)
        //--------------------------------

        btnTransferir.addActionListener(e -> {
            new TelaTransferencia(cliente);
        });

        //--------------------------------
        // EXTRATO
        //--------------------------------

        btnExtrato.addActionListener(e -> {
            new TelaExtrato(cliente);
        });

        //--------------------------------
        // SAIR
        //--------------------------------

        btnSair.addActionListener(e -> {
            dispose();
            new TelaMenuInicial();
        });

        //--------------------------------
        // ADICIONAR BOTÕES
        //--------------------------------

        painelBotoes.add(btnDepositar);
        painelBotoes.add(btnSacar);
        painelBotoes.add(btnTransferir);
        painelBotoes.add(btnExtrato);
        painelBotoes.add(btnSair);
        painelBotoes.add(new JLabel()); // espaço vazio p/ alinhar

        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(Box.createVerticalStrut(30));

        //--------------------------------
        // INFO CONTA
        //--------------------------------

        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new BoxLayout(painelInfo,BoxLayout.Y_AXIS));
        painelInfo.setBackground(Color.WHITE);
        painelInfo.setBorder(BorderFactory.createEmptyBorder(0,30,0,30));

        JLabel lblCliente = new JLabel("Cliente: " + cliente.getNome());
        JLabel lblAgencia = new JLabel("Agência: " + cliente.getAgencia());
        JLabel lblConta = new JLabel("Conta: " + cliente.getNumeroConta());

        painelInfo.add(lblCliente);
        painelInfo.add(lblAgencia);
        painelInfo.add(lblConta);

        painelPrincipal.add(painelInfo);

        setVisible(true);
    }

    //--------------------------------
    private JButton criarBotao(String texto){

        JButton botao = new JButton(texto);

        botao.setBackground(Color.WHITE);
        botao.setFocusPainted(false);

        Border sombra = new MatteBorder(1,1,3,3,new Color(220,220,220));
        Border margem = new EmptyBorder(20,10,20,10);

        botao.setBorder(new CompoundBorder(sombra,margem));

        return botao;
    }

    //--------------------------------
    private void atualizarSaldo(){

        if(saldoVisivel){
            lblSaldo.setText(String.format("R$ %.2f", cliente.getSaldo()));
            btnOlho.setText("👁");
        }else{
            lblSaldo.setText("••••••");
            btnOlho.setText("X👁");
        }
    }
}