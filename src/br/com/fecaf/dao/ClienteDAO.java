package br.com.fecaf.dao;

import br.com.fecaf.database.ConexaoPostgres;
import br.com.fecaf.model.Cliente;
import br.com.fecaf.utils.Formatador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClienteDAO {

    //--------------------------------
    // SALVAR CLIENTE
    //--------------------------------
    public static void salvar(Cliente cliente){

        String sql = """
        INSERT INTO clientes
        (nome, cpf, telefone, senha, agencia, numero_conta, saldo)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try(
                Connection conn = ConexaoPostgres.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ){

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getSenha());
            stmt.setString(5, cliente.getAgencia());
            stmt.setString(6, cliente.getNumeroConta());
            stmt.setDouble(7, cliente.getSaldo());

            stmt.executeUpdate();

            System.out.println("Cliente salvo!");

        }catch(Exception e){
            System.out.println("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    //--------------------------------
// LOGIN COM HASH
//--------------------------------
    public static Cliente fazerLogin(String cpf, String senha){

        String sql = "SELECT * FROM clientes WHERE cpf = ?";

        try(
                Connection conn = ConexaoPostgres.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ){

            stmt.setString(1, cpf);

            try(ResultSet rs = stmt.executeQuery()){

                if(rs.next()){

                    String senhaHash = rs.getString("senha");

                    //COMPARAÇÃO
                    if(!br.com.fecaf.security.SenhaUtils.verificarSenha(senha, senhaHash)){
                        return null;
                    }

                    Cliente cliente = new Cliente();

                    cliente.setNome(rs.getString("nome"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setTelefone(rs.getString("telefone"));
                    cliente.setSenha(senhaHash);
                    cliente.setAgencia(rs.getString("agencia"));
                    cliente.setNumeroConta(rs.getString("numero_conta"));
                    cliente.setSaldo(rs.getDouble("saldo"));

                    return cliente;
                }

            }

        }catch(Exception e){
            System.out.println("Erro no login: " + e.getMessage());
        }

        return null;
    }

    //--------------------------------
    // BUSCAR POR CPF
    //--------------------------------
    public static Cliente buscarPorCpf(String cpf){

        String sql = "SELECT * FROM clientes WHERE cpf = ?";

        try(
                Connection conn = ConexaoPostgres.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ){

            stmt.setString(1, cpf);

            try(ResultSet rs = stmt.executeQuery()){

                if(rs.next()){

                    Cliente cliente = new Cliente();

                    cliente.setNome(rs.getString("nome"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setTelefone(rs.getString("telefone"));
                    cliente.setSenha(rs.getString("senha"));
                    cliente.setAgencia(rs.getString("agencia"));
                    cliente.setNumeroConta(rs.getString("numero_conta"));
                    cliente.setSaldo(rs.getDouble("saldo"));

                    return cliente;
                }
            }

        }catch(Exception e){
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }

        return null;
    }

    //--------------------------------
    // BUSCAR POR CONTA (NOVO)
    //--------------------------------
    public static Cliente buscarPorConta(String agencia, String conta){

        String sql = """
        SELECT * FROM clientes
        WHERE agencia = ? AND numero_conta = ?
        """;

        try(
                Connection conn = ConexaoPostgres.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ){

            stmt.setString(1, agencia);
            stmt.setString(2, conta);

            try(ResultSet rs = stmt.executeQuery()){

                if(rs.next()){

                    Cliente cliente = new Cliente();

                    cliente.setNome(rs.getString("nome"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setTelefone(rs.getString("telefone"));
                    cliente.setSenha(rs.getString("senha"));
                    cliente.setAgencia(rs.getString("agencia"));
                    cliente.setNumeroConta(rs.getString("numero_conta"));
                    cliente.setSaldo(rs.getDouble("saldo"));

                    return cliente;
                }
            }

        }catch(Exception e){
            System.out.println("Erro ao buscar conta: " + e.getMessage());
        }

        return null;
    }

    //--------------------------------
    // ATUALIZAR SALDO
    //--------------------------------
    public static void atualizarSaldo(String cpf, double novoSaldo){

        String sql = "UPDATE clientes SET saldo = ? WHERE cpf = ?";

        try(
                Connection conn = ConexaoPostgres.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ){

            stmt.setDouble(1, novoSaldo);
            stmt.setString(2, cpf);

            stmt.executeUpdate();

        }catch(Exception e){
            System.out.println("Erro ao atualizar saldo: " + e.getMessage());
        }
    }
}