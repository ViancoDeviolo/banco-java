package br.com.fecaf.view;

import br.com.fecaf.controller.ClienteController;
import br.com.fecaf.model.Cliente;
import br.com.fecaf.utils.Formatador;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;

public class TelaLogin extends JFrame {

    private JFormattedTextField txtCpf;
    private JPasswordField txtSenha;

    public TelaLogin(){

        setTitle("Login");
        setSize(350,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel painel = new JPanel(new GridLayout(3,2,10,10));
        painel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        //--------------------------------
        // CPF COM MÁSCARA
        //--------------------------------
        try {
            MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
            txtCpf = new JFormattedTextField(mascaraCpf);
        } catch (Exception e) {
            txtCpf = new JFormattedTextField();
        }

        txtSenha = new JPasswordField();

        JButton btnEntrar = new JButton("Entrar");
        JButton btnVoltar = new JButton("Voltar");

        painel.add(new JLabel("CPF:"));
        painel.add(txtCpf);

        painel.add(new JLabel("Senha:"));
        painel.add(txtSenha);

        painel.add(btnEntrar);
        painel.add(btnVoltar);

        add(painel);

        //--------------------------------
        // AÇÕES
        //--------------------------------

        btnEntrar.addActionListener(e -> login());

        btnVoltar.addActionListener(e -> {
            new TelaMenuInicial();
            dispose();
        });

        setVisible(true);
    }

    private void login(){

        // 🔥 CPF LIMPO
        String cpf = Formatador.limparCpf(txtCpf.getText());

        String senha = new String(txtSenha.getPassword());

        Cliente cliente = ClienteController.fazerLogin(cpf, senha);

        if(cliente != null){

            JOptionPane.showMessageDialog(null,"Login realizado!");

            new TelaContaCliente(cliente);
            dispose();

        }else{
            JOptionPane.showMessageDialog(null,"CPF ou senha inválidos");
        }
    }
}