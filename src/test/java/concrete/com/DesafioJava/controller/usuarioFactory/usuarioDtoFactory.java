package concrete.com.DesafioJava.controller.usuarioFactory;

import concrete.com.DesafioJava.dto.UsuarioCadastroDTO;

public class usuarioDtoFactory {
    public static UsuarioCadastroDTO usuarioCadastroDTOSimples() {
        return new UsuarioCadastroDTO("teste", "teste@teste.com.br", "teste");
    }

    public static UsuarioCadastroDTO usuarioCadastroDTONomeVazio() {
        return new UsuarioCadastroDTO("", "teste1@teste.com.br", "teste1");
    }
}
