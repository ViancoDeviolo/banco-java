package br.com.fecaf.database;

import java.sql.Connection;
import java.sql.DriverManager;
import br.com.fecaf.utils.Formatador;

public class ConexaoPostgres {

    private static final String URL = "jdbc:postgresql://localhost:/banco_java";
    private static final String USER = "postgres";
    private static final String PASSWORD = "";

    public static Connection conectar(){

        try{

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectado ao PostgreSQL!");

            return conn;

        }catch(Exception e){

            System.out.println("Erro na conexão: " + e.getMessage());
            return null;
        }
    }
}
