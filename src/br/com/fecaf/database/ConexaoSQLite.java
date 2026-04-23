package br.com.fecaf.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import br.com.fecaf.utils.Formatador;

public class ConexaoSQLite {

    private static final String URL = "jdbc:sqlite:banco.db";

    public static Connection conectar() {

        try {

            Connection conn = DriverManager.getConnection(URL);
            System.out.println("Conectado ao banco!");

            return conn;

        } catch (SQLException e) {

            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;

        }

    }

}
