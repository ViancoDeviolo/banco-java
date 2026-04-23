package br.com.fecaf.view;

import javax.swing.*;
import java.awt.*;

public class TelaMenuInicial extends JFrame {

    private BotaoArredondado btnCadastrarCliente;
    private BotaoArredondado btnLogin;
    private BotaoArredondado btnSair;

    public TelaMenuInicial() {

        setTitle("Banco");
        setSize(450,320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Color laranjaItau = new Color(255,98,0);
        Color azulItau = new Color(0,51,160);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(Color.WHITE);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        JLabel lblTitulo = new JLabel("Banco", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Myriad Pro", Font.BOLD, 32));
        lblTitulo.setForeground(laranjaItau);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(3,1,15,15));
        painelBotoes.setBackground(Color.WHITE);

        btnCadastrarCliente = new BotaoArredondado("Cadastrar Cliente");
        btnLogin = new BotaoArredondado("Login");
        btnSair = new BotaoArredondado("Sair");

        btnCadastrarCliente.setBackground(azulItau);
        btnLogin.setBackground(azulItau);
        btnSair.setBackground(laranjaItau);

        painelBotoes.add(btnCadastrarCliente);
        painelBotoes.add(btnLogin);
        painelBotoes.add(btnSair);

        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);

        add(painelPrincipal);

        btnCadastrarCliente.addActionListener(e -> {
            new TelaCadastroCliente();
            dispose();
        });

        btnLogin.addActionListener(e -> {

            new TelaLogin();
            dispose();

        });

        btnSair.addActionListener(e -> {

            int opcao = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja realmente sair?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
            );

            if(opcao == JOptionPane.YES_OPTION){
                System.exit(0);
            }

        });

        setVisible(true);
    }
}

