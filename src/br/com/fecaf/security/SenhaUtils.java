package br.com.fecaf.security;
import br.com.fecaf.utils.Formatador;
import org.mindrot.jbcrypt.BCrypt;

public class SenhaUtils {

    // gerar hash
    public static String gerarHash(String senha){
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    // verificar senha
    public static boolean verificarSenha(String senhaDigitada, String senhaHash){
        return BCrypt.checkpw(senhaDigitada, senhaHash);
    }
}