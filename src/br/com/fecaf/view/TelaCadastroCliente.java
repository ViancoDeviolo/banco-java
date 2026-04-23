package br.com.fecaf.view;

import br.com.fecaf.controller.ClienteController;
import br.com.fecaf.controller.ValidadorCPF;
import br.com.fecaf.model.Cliente;
import br.com.fecaf.security.SenhaUtils;
import br.com.fecaf.utils.Formatador;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;

public class TelaCadastroCliente extends JFrame {

    private JTextField txtNome;
    private JTextField txtEmail;
    private JFormattedTextField txtCpf;
    private JFormattedTextField txtTelefone;
    private JPasswordField txtSenha;

    private BotaoArredondado btnCadastrar;
    private BotaoArredondado btnVoltar;

    public TelaCadastroCliente(){

        setTitle("Cadastro de Cliente");
        setSize(450,380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        Color laranjaItau = new Color(255,98,0);
        Color azulItau = new Color(0,51,160);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(Color.WHITE);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel lblTitulo = new JLabel("Cadastro de Cliente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Myriad Pro", Font.BOLD, 22));
        lblTitulo.setForeground(laranjaItau);

        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelCampos = new JPanel(new GridLayout(5,2,10,10));
        painelCampos.setBackground(Color.WHITE);

        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField();

        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField();

        JLabel lblCpf = new JLabel("CPF:");

        //--------------------------------
        // CPF COM MÁSCARA
        //--------------------------------
        try {
            MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
            mascaraCpf.setPlaceholderCharacter('_');
            txtCpf = new JFormattedTextField(mascaraCpf);
        } catch (Exception e) {
            txtCpf = new JFormattedTextField();
        }

        JLabel lblTelefone = new JLabel("Celular:");

        try {
            MaskFormatter mascaraTelefone = new MaskFormatter("(##) #####-####");
            mascaraTelefone.setPlaceholderCharacter('_');
            txtTelefone = new JFormattedTextField(mascaraTelefone);
        } catch (Exception e) {
            txtTelefone = new JFormattedTextField();
        }

        JLabel lblSenha = new JLabel("Senha:");
        txtSenha = new JPasswordField();

        //--------------------------------
        // LIMITAR SENHA A 6 DÍGITOS
        //--------------------------------
        txtSenha.setDocument(new javax.swing.text.PlainDocument(){
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a)
                    throws javax.swing.text.BadLocationException {

                if (getLength() + str.length() <= 6) {
                    super.insertString(offs, str, a);
                }
            }
        });

        //--------------------------------
        // CAMPOS
        //--------------------------------

        painelCampos.add(lblNome);
        painelCampos.add(txtNome);

        painelCampos.add(lblEmail);
        painelCampos.add(txtEmail);

        painelCampos.add(lblCpf);
        painelCampos.add(txtCpf);

        painelCampos.add(lblTelefone);
        painelCampos.add(txtTelefone);

        painelCampos.add(lblSenha);
        painelCampos.add(txtSenha);

        painelPrincipal.add(painelCampos, BorderLayout.CENTER);

        //--------------------------------
        // BOTÕES
        //--------------------------------

        JPanel painelBotoes = new JPanel(new GridLayout(1,2,10,10));
        painelBotoes.setBackground(Color.WHITE);

        btnCadastrar = new BotaoArredondado("Cadastrar");
        btnVoltar = new BotaoArredondado("Voltar");

        btnCadastrar.setBackground(azulItau);
        btnVoltar.setBackground(laranjaItau);

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnVoltar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);

        //--------------------------------
        // EMAIL MINÚSCULO AUTOMÁTICO
        //--------------------------------

        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                txtEmail.setText(txtEmail.getText().toLowerCase());
            }
        });

        //--------------------------------
        // AÇÕES
        //--------------------------------

        btnCadastrar.addActionListener(e -> cadastrarCliente());

        btnVoltar.addActionListener(e -> {
            new TelaMenuInicial();
            dispose();
        });

        setVisible(true);
    }

    private void cadastrarCliente(){

        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();

        //  CPF LIMPO (PADRÃO PROFISSIONAL)
        String cpf = Formatador.limparCpf(txtCpf.getText());

        String telefone = txtTelefone.getText();
        String senha = new String(txtSenha.getPassword()).trim();

        if(senha.length() != 6){
            JOptionPane.showMessageDialog(null,"A senha deve ter exatamente 6 dígitos!");
            return;
        }

        if(nome.isEmpty() || email.isEmpty() || cpf.length() != 11 || telefone.contains("_") || senha.isEmpty()){
            JOptionPane.showMessageDialog(null,"Preencha todos os campos!");
            return;
        }

        if(!ValidadorCPF.validarCPF(cpf)){
            JOptionPane.showMessageDialog(null,"CPF inválido!");
            return;
        }

        if(ClienteController.cpfExiste(cpf)){
            JOptionPane.showMessageDialog(null,"CPF já cadastrado!");
            return;
        }

        //--------------------------------
        // HASH DA SENHA
        //--------------------------------
        String senhaHash = SenhaUtils.gerarHash(senha);

        Cliente cliente = new Cliente();

        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setCpf(cpf); // salva limpo
        cliente.setTelefone(telefone);
        cliente.setSenha(senhaHash);

        ClienteController.adicionarCliente(cliente);

        JOptionPane.showMessageDialog(
                null,
                "Cliente cadastrado com sucesso!\n\n" +
                        "Agência: " + cliente.getAgencia() +
                        "\nConta: " + cliente.getNumeroConta()
        );

        limparCampos();
    }

    private void limparCampos(){

        txtNome.setText("");
        txtEmail.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtSenha.setText("");

        txtNome.requestFocus();
    }
}