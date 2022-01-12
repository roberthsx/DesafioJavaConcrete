package concrete.com.DesafioJava.service.usuarioFactory;

import concrete.com.DesafioJava.dto.DadosLogin;
import concrete.com.DesafioJava.dto.UsuarioCadastroDTO;
import concrete.com.DesafioJava.model.Usuario;

public class UsuarioAuthFactory {

    public static DadosLogin DadosLoginSimples(){
        return new DadosLogin("teste1@test.com","teste1");
    }

    public static Usuario UsuarioSimples(){
        return new Usuario("Teste1","teste1@test.com","teste1");
    }

    public static Usuario UsuarioLoginInvalido(){
        return new Usuario("Teste1","teste1@test.com","teste");
    }
}