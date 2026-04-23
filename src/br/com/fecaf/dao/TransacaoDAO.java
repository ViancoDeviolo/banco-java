package br.com.fecaf.dao;

import br.com.fecaf.database.ConexaoPostgres;

import br.com.fecaf.database.ConexaoPostgres;
import br.com.fecaf.model.Transacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import br.com.fecaf.utils.Formatador;

public class TransacaoDAO {

    //--------------------------------
    // REGISTRAR TRANSACAO
    //--------------------------------

    public static void registrar(Transacao transacao){

        String sql = """
        INSERT INTO transacoes
        (cpf_cliente, tipo, valor, data)
        VALUES (?, ?, ?, ?)
        """;

        try(
                Connection conn = ConexaoPostgres.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ){

            stmt.setString(1, transacao.getCpfCliente());
            stmt.setString(2, transacao.getTipo());
            stmt.setDouble(3, transacao.getValor());
            stmt.setString(4, transacao.getData());

            stmt.executeUpdate();

            System.out.println(" Transação registrada no PostgreSQL!");

        }catch(Exception e){

            System.out.println(" Erro ao registrar transação:");
            e.printStackTrace();
        }
    }

    //--------------------------------
    // BUSCAR TRANSACOES
    //--------------------------------

    public static List<Transacao> buscarPorCpf(String cpf){

        List<Transacao> lista = new ArrayList<>();

        String sql = """
        SELECT * FROM transacoes
        WHERE cpf_cliente = ?
        ORDER BY id DESC
        """;

        try(
                Connection conn = ConexaoPostgres.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ){

            stmt.setString(1, cpf);

            try(ResultSet rs = stmt.executeQuery()){

                while(rs.next()){

                    Transacao t = new Transacao();

                    t.setCpfCliente(rs.getString("cpf_cliente"));
                    t.setTipo(rs.getString("tipo"));
                    t.setValor(rs.getDouble("valor"));
                    t.setData(rs.getString("data"));

                    lista.add(t);
                }
            }

            System.out.println("Transações encontradas: " + lista.size());

        }catch(Exception e){

            System.out.println("Erro ao buscar transações:");
            e.printStackTrace();
        }

        return lista;
    }
}