package concrete.com.DesafioJava.service;

import concrete.com.DesafioJava.dto.DadosLogin;
import concrete.com.DesafioJava.model.Usuario;
import concrete.com.DesafioJava.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Optional;

import static concrete.com.DesafioJava.service.usuarioFactory.UsuarioAuthFactory.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UsuarioAuthServiceTest {

    @InjectMocks
    UsuarioAuthService usuarioAuthService;

    @Spy
    @InjectMocks
    TokenService tokenService;

    @Mock
    UsuarioRepository usuarioRepository;

    private String token;

    @BeforeEach
    public void init() {

        MockitoAnnotations.openMocks(this);
        token = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NDE5OTAyOTUsInN1YiI6IlRlc3RlIEpXVCBBUEkiLCJleHAiOjE2NDE5OTIwOTV9.e3drFCO4E2IWvrUrqmujL5fXlwR1ArMVcef3qAjs84c";
    }

    @Test
    void testAutenticacaoUsuario_RetornOk() {
        //arrange
        DadosLogin dadosLogin = DadosLoginSimples();
        Usuario usuario = UsuarioSimples();
        //Claims claims = new Claims();
        when(usuarioRepository.findByEmail(dadosLogin.getEmail())).thenReturn(Optional.of(usuario));
        //when(tokenService.decodeToken(token)).thenReturn();

        //act
        var usuarioRetorno = usuarioAuthService.autenticacao(dadosLogin, token);

        //assert
        Assertions.assertEquals(dadosLogin.getSenha(), usuarioRetorno.getSenha());
        Assertions.assertEquals(dadosLogin.getEmail(), usuarioRetorno.getEmail());
        Assertions.assertEquals(token, usuarioRetorno.getToken());
    }

    @Test
    void testAutenticacaoUsuario_RetornaException() {
        //arrange
        DadosLogin dadosLogin = DadosLoginSimples();
        String expectedMessage = "Erro ao realizar autenticação";
        RuntimeException exceptionMock = new RuntimeException("Erro ao conectar ao servidor");
        when(usuarioRepository.findByEmail(dadosLogin.getEmail())).thenThrow(exceptionMock);

        //act
        Exception exception = assertThrows(RuntimeException.class, () -> usuarioAuthService.autenticacao(dadosLogin, token));

        //assert
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testAutenticacaoUsuario_Quando_Senha_Diferente_Retorno_ExistingEmailException() {
        //arrange
        DadosLogin dadosLogin = DadosLoginSimples();
        String expectedMessage = "Erro ao realizar autenticação";
        String expectedMessage2 = "Email já cadastrado.";

        //act
        Exception exception = assertThrows(RuntimeException.class, () -> usuarioAuthService.autenticacao(dadosLogin, token));

        //assert
        Assertions.assertEquals(expectedMessage, exception.getMessage());
        Assertions.assertEquals(expectedMessage2, exception.getCause().getMessage());
    }

    @Test
    void testAutenticacaoUsuario_Quando_Senha_Diferente_Retorno_InvalidLoginException() {
        //arrange
        DadosLogin dadosLogin = DadosLoginSimples();
        Usuario usuario = UsuarioLoginInvalido();
        String expectedMessage = "Erro ao realizar autenticação";
        String expectedMessage2 = "Login Inválido.";

        when(usuarioRepository.findByEmail(dadosLogin.getEmail())).thenReturn(Optional.of(usuario));

        //act
        Exception exception = assertThrows(RuntimeException.class, () -> usuarioAuthService.autenticacao(dadosLogin, token));

        //assert
        Assertions.assertEquals(expectedMessage, exception.getMessage());
        Assertions.assertEquals(expectedMessage2, exception.getCause().getMessage());
    }
}