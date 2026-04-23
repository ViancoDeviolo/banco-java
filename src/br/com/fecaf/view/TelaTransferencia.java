package br.com.fecaf.view;

import br.com.fecaf.controller.ClienteController;
import br.com.fecaf.dao.ClienteDAO;
import br.com.fecaf.model.Cliente;

import javax.swing.*;
import java.awt.*;

public class TelaTransferencia extends JFrame {

    private Cliente cliente;
    private Cliente destino;

    private JTextField txtAgencia;
    private JTextField txtConta;
    private JTextField txtValor;

    private JLabel lblDestino;

    public TelaTransferencia(Cliente cliente){

        this.cliente = cliente;

        setTitle("Transferência");
        setSize(350,350);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel(new GridLayout(6,2,10,10));
        painel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        txtAgencia = new JTextField();
        txtConta = new JTextField();
        txtValor = new JTextField();

        lblDestino = new JLabel(" ");

        JButton btnVerificar = new JButton("Verificar");
        JButton btnConfirmar = new JButton("Confirmar");

        //--------------------------------
        // CAMPOS
        //--------------------------------

        painel.add(new JLabel("Agência:"));
        painel.add(txtAgencia);

        painel.add(new JLabel("Conta:"));
        painel.add(txtConta);

        painel.add(new JLabel("Valor:"));
        painel.add(txtValor);

        painel.add(new JLabel("Destinatário:"));
        painel.add(lblDestino);

        painel.add(btnVerificar);
        painel.add(btnConfirmar);

        add(painel);

        //--------------------------------
        // VERIFICAR DESTINO
        //--------------------------------

        btnVerificar.addActionListener(e -> {

            destino = ClienteDAO.buscarPorConta(
                    txtAgencia.getText(),
                    txtConta.getText()
            );

            if(destino != null){
                lblDestino.setText(destino.getNome());
            }else{
                lblDestino.setText("Conta não encontrada");
            }
        });

        //--------------------------------
        // CONFIRMAR TRANSFERÊNCIA
        //--------------------------------

        btnConfirmar.addActionListener(e -> {

            if(destino == null){
                JOptionPane.showMessageDialog(this,"Verifique a conta primeiro!");
                return;
            }

            try{

                double valor = Double.parseDouble(
                        txtValor.getText().replace(",", ".")
                );

                int opcao = JOptionPane.showConfirmDialog(
                        this,
                        "Confirmar transferência?\n\n" +
                                "Para: " + destino.getNome() +
                                "\nValor: R$ " + valor,
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION
                );

                if(opcao == JOptionPane.YES_OPTION){

                    boolean ok = ClienteController.transferir(
                            cliente,
                            txtAgencia.getText(),
                            txtConta.getText(),
                            valor
                    );

                    if(ok){
                        JOptionPane.showMessageDialog(this,"Transferência realizada!");
                        dispose();
                    }else{
                        JOptionPane.showMessageDialog(this,"Erro na transferência!");
                    }
                }

            }catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Valor inválido!");
            }
        });

        setVisible(true);
    }
}