package br.com.fecaf.database;

import java.sql.Connection;
import java.sql.Statement;
import br.com.fecaf.utils.Formatador;

public class CriarTabelas {

    public static void criar() {

        String sqlClientes = """
        CREATE TABLE IF NOT EXISTS clientes (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nome TEXT,
            cpf TEXT UNIQUE,
            telefone TEXT,
            senha TEXT,
            agencia TEXT,
            numero_conta TEXT,
            saldo REAL
        );
        """;

        String sqlTransacoes = """
        CREATE TABLE IF NOT EXISTS transacoes (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            cpf_cliente TEXT,
            tipo TEXT,
            valor REAL,
            data TEXT
        );
        """;

        try {

            Connection conn = ConexaoPostgres.conectar();
            Statement stmt = conn.createStatement();

            // cria tabela clientes
            stmt.execute(sqlClientes);

            // cria tabela transacoes
            stmt.execute(sqlTransacoes);

            System.out.println("Tabelas criadas ou já existentes.");

        } catch (Exception e) {

            System.out.println("Erro ao criar tabelas: " + e.getMessage());

        }

    }

}