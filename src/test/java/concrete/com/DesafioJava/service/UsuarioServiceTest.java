package concrete.com.DesafioJava.service;

import concrete.com.DesafioJava.dto.UsuarioCadastroDTO;
import concrete.com.DesafioJava.model.Usuario;
import concrete.com.DesafioJava.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static concrete.com.DesafioJava.service.usuarioFactory.UsuarioFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    UsuarioService usuarioService;

    @Spy
    @InjectMocks
    TokenService tokenService;

    @Mock
    UsuarioRepository usuarioRepository;
    private  String token;

    @BeforeEach
    public void init() {

        MockitoAnnotations.openMocks(this);
        token ="eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NDE5OTAyOTUsInN1YiI6IlRlc3RlIEpXVCBBUEkiLCJleHAiOjE2NDE5OTIwOTV9.e3drFCO4E2IWvrUrqmujL5fXlwR1ArMVcef3qAjs84c";
    }

    @Test
    void testCadastroUsuario_RetornOk()
    {
        //arrange
        Usuario usuario = usuarioCadastroDtoSimples();
        when(tokenService.generateToken(usuario)).thenReturn(token);

        //act
        usuarioService.Cadastro(usuario);

        //assert
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testCadastroUsuario_RetornaException()
    {
        //arrange
        Usuario usuario = usuarioCadastroDtoSimples();
        String expectedMessage = "Erro ao cadastrar Usuario.";
        RuntimeException exceptionMock = new RuntimeException("Erro ao conectar ao servidor");
        when(tokenService.generateToken(usuario)).thenThrow(exceptionMock);

        //act
        Exception exception = assertThrows(RuntimeException.class, () -> usuarioService.Cadastro(usuario));

        //assert
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testCadastroUsuario_QuandoEmailVazio_RetornaMensagem()
    {
        //arrange
        Usuario usuario = usuarioCadastroDtoEmailVazio();

        //act
        usuarioService.Cadastro(usuario);

        //assert
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testCadastroUsuario_QuandoEmailInvalido_RetornaMensagem()
    {
        //arrange
        Usuario usuario = usuarioCadastroDtoEmailInvalido();

        //act
        usuarioService.Cadastro(usuario);

        //assert
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testCadastroUsuario_QuandoNomeVazio_RetornaMensagem()
    {
        //arrange
        Usuario usuario = usuarioCadastroDtoNomeVazio();

        //act
        usuarioService.Cadastro(usuario);

        //assert
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testCadastroUsuario_QuandoSenhaVazio_RetornaMensagem()
    {
        //arrange
        Usuario usuario = usuarioCadastroDtoSenhaVazio();

        //act
        usuarioService.Cadastro(usuario);

        //assert
        verify(usuarioRepository, times(1)).save(usuario);
    }
}