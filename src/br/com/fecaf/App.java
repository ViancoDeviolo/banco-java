package br.com.fecaf;

import br.com.fecaf.database.ConexaoPostgres;
import br.com.fecaf.database.CriarTabelas;
import br.com.fecaf.view.TelaMenuInicial;

public class App {

    public static void main(String[] args) {

        ConexaoPostgres.conectar();

        CriarTabelas.criar();

        new TelaMenuInicial();
    }
}