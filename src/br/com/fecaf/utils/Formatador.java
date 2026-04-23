package br.com.fecaf.utils;

public class Formatador {

    public static String limparCpf(String cpf){
        return cpf.replaceAll("[^0-9]", "");
    }
}