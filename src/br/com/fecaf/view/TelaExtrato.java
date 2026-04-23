package br.com.fecaf.view;

import br.com.fecaf.dao.TransacaoDAO;
import br.com.fecaf.model.Cliente;
import br.com.fecaf.model.Transacao;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class TelaExtrato extends JFrame {

    public TelaExtrato(Cliente cliente){

        setTitle("Extrato da Conta");
        setSize(420,500);
        setLocationRelativeTo(null);
        setResizable(false);

        Color laranjaItau = new Color(255,98,0);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout());
        painelPrincipal.setBackground(Color.WHITE);

        //--------------------------------
        // TÍTULO
        //--------------------------------

        JLabel titulo = new JLabel("Extrato Bancário", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(laranjaItau);
        titulo.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));

        painelPrincipal.add(titulo, BorderLayout.NORTH);

        //--------------------------------
        // LISTA DE TRANSAÇÕES
        //--------------------------------

        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
        listaPanel.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(listaPanel);
        scroll.setBorder(null);

        painelPrincipal.add(scroll, BorderLayout.CENTER);

        //--------------------------------
        // FORMATADORES
        //--------------------------------

        NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        //--------------------------------
        // BUSCAR TRANSAÇÕES
        //--------------------------------

        List<Transacao> lista = TransacaoDAO.buscarPorCpf(cliente.getCpf());

        if(lista.isEmpty()){

            JLabel vazio = new JLabel("Nenhuma movimentação encontrada");
            vazio.setFont(new Font("Arial", Font.PLAIN, 14));
            vazio.setHorizontalAlignment(SwingConstants.CENTER);
            vazio.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));

            listaPanel.add(vazio);

        }else{

            for(Transacao t : lista){

                JPanel item = new JPanel(new BorderLayout());
                item.setBackground(Color.WHITE);
                item.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

                //--------------------------------
                // TIPO (DEPÓSITO / SAQUE)
                //--------------------------------

                JLabel lblTipo = new JLabel(t.getTipo());
                lblTipo.setFont(new Font("Arial", Font.BOLD, 14));

                if(t.getTipo().equalsIgnoreCase("DEPOSITO")){
                    lblTipo.setForeground(new Color(0,150,0)); // verde
                }else{
                    lblTipo.setForeground(Color.RED); // vermelho
                }

                //--------------------------------
                // VALOR
                //--------------------------------

                JLabel lblValor = new JLabel(moeda.format(t.getValor()));
                lblValor.setFont(new Font("Arial", Font.BOLD, 14));

                if(t.getTipo().equalsIgnoreCase("DEPOSITO")){
                    lblValor.setForeground(new Color(0,150,0));
                }else{
                    lblValor.setForeground(Color.RED);
                }

                //--------------------------------
                // DATA
                //--------------------------------

                String dataFormatada = t.getData();

                try{
                    LocalDateTime data = LocalDateTime.parse(t.getData());
                    dataFormatada = data.format(formatoData);
                }catch(Exception ignored){}

                JLabel lblData = new JLabel(dataFormatada);
                lblData.setFont(new Font("Arial", Font.PLAIN, 12));
                lblData.setForeground(Color.GRAY);

                //--------------------------------
                // ORGANIZAÇÃO
                //--------------------------------

                JPanel esquerda = new JPanel();
                esquerda.setLayout(new BoxLayout(esquerda, BoxLayout.Y_AXIS));
                esquerda.setBackground(Color.WHITE);

                esquerda.add(lblTipo);
                esquerda.add(lblData);

                item.add(esquerda, BorderLayout.WEST);
                item.add(lblValor, BorderLayout.EAST);

                //--------------------------------
                // LINHA SEPARADORA
                //--------------------------------

                JPanel linha = new JPanel();
                linha.setBackground(new Color(230,230,230));
                linha.setMaximumSize(new Dimension(Integer.MAX_VALUE,1));

                listaPanel.add(item);
                listaPanel.add(linha);
            }
        }

        //--------------------------------
        // BOTÃO FECHAR
        //--------------------------------

        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFocusPainted(false);

        btnFechar.addActionListener(e -> dispose());

        JPanel rodape = new JPanel();
        rodape.setBackground(Color.WHITE);
        rodape.add(btnFechar);

        painelPrincipal.add(rodape, BorderLayout.SOUTH);

        add(painelPrincipal);

        setVisible(true);
    }
}