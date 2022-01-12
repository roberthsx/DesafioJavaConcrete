package concrete.com.DesafioJava.service.usuarioFactory;

import concrete.com.DesafioJava.dto.UsuarioCadastroDTO;
import concrete.com.DesafioJava.model.Usuario;

public class UsuarioFactory {

    public static Usuario usuarioCadastroDtoSimples(){
        return new Usuario("Teste1","teste1@teste.com.br","teste1");
    }

    public static Usuario usuarioCadastroDtoEmailVazio(){
        return new Usuario("Teste1","","teste1");
    }

    public static Usuario usuarioCadastroDtoEmailInvalido(){
        return new Usuario("Teste1","inv@lido@com.br","teste1");
    }

    public static Usuario usuarioCadastroDtoNomeVazio(){
        return new Usuario("","teste1@teste.com.br","teste1");
    }

    public static Usuario usuarioCadastroDtoSenhaVazio(){
        return new Usuario("Teste1","teste1@teste.com.br","");
    }
}