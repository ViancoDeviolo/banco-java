package br.com.fecaf.database;

import java.sql.Connection;
import java.sql.DriverManager;
import br.com.fecaf.utils.Formatador;

public class ConexaoPostgres {

    private static final String URL = "DB_URL";
    private static final String USER = "DB_USER";
    private static final String PASSWORD = "DB_PASSWORD";

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
