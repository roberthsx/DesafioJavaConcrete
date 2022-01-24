package concrete.com.DesafioJava.service;

import concrete.com.DesafioJava.model.DadosLogin;
import concrete.com.DesafioJava.model.Usuario;
import concrete.com.DesafioJava.repository.IUsuarioRepository;
import concrete.com.DesafioJava.service.interfaces.ITokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;

import static concrete.com.DesafioJava.service.usuarioFactory.UsuarioAuthFactory.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioAuthServiceTest {

    @InjectMocks
    UsuarioAuthService usuarioAuthService;

    @Mock
    ITokenService tokenService;

    @Mock
    IUsuarioRepository usuarioRepository;

    private String token;

    @BeforeEach
    public void init() {

        MockitoAnnotations.openMocks(this);
        Usuario usuario = UsuarioSimples();
        when(usuarioRepository.findByEmail(any(String.class))).thenReturn(Optional.of(usuario));
        token = Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject("Teste")
                .setExpiration(new Date(System.currentTimeMillis() + 1800000))
                .signWith(SignatureAlgorithm.HS256, "DesafioConcrete")
                .compact();
    }

    @Test
    void testAutenticacaoUsuario_RetornOk() {
        //arrange
        DadosLogin dadosLogin = DadosLoginSimples();
        Usuario usuario = UsuarioSimples();
        Claims claims = new DefaultClaims();
        claims.setExpiration(new Date(System.currentTimeMillis() + 1800000));
        when(usuarioRepository.findByEmail(dadosLogin.getEmail())).thenReturn(Optional.of(usuario));
        when(tokenService.decodeToken(token)).thenReturn(claims);

        //act
        var usuarioRetorno = (Usuario) usuarioAuthService.autenticacao(dadosLogin, token);

        //assert
        Assertions.assertEquals(dadosLogin.getSenha(), usuarioRetorno.getSenha());
        Assertions.assertEquals(dadosLogin.getEmail(), usuarioRetorno.getEmail());
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

    @Test
    void testAutenticacaoUsuario_Quando_Ao_Validar_Token_Retorna_ExpirationException() {
        //arrange
        DadosLogin dadosLogin = DadosLoginSimples();
        Usuario usuario = UsuarioSimples();
        Claims claims = new DefaultClaims();
        claims.setExpiration(new Date(System.currentTimeMillis() + 1));
        when(usuarioRepository.findByEmail(dadosLogin.getEmail())).thenReturn(Optional.of(usuario));
        when(tokenService.decodeToken(token)).thenReturn(claims);
        String expectedMessage = "Erro ao realizar autenticação";
        String expectedMessage2 = "Erro ao validar Usuario, Token Expirado";

        //act
        Exception exception = assertThrows(RuntimeException.class, () -> usuarioAuthService.autenticacao(dadosLogin, token));

        //assert
        Assertions.assertEquals(expectedMessage, exception.getMessage());
        Assertions.assertEquals(expectedMessage2, exception.getCause().getMessage());
    }

    @Test
    void testAutenticacaoUsuario_Quando_Ao_Validar_Token_Encontra_Erro_No_Servico_Retorna_Exception() {
        //arrange
        DadosLogin dadosLogin = DadosLoginSimples();
        Usuario usuario = UsuarioSimples();
        when(usuarioRepository.findByEmail(any(String.class))).thenReturn(Optional.of(usuario));
        when(tokenService.decodeToken(token)).thenThrow(new RuntimeException("erro de processamento"));
        String expectedMessage = "Erro ao realizar autenticação";
        String expectedMessage2 = "Erro ao validar Usuario";

        //act
        Exception exception = assertThrows(RuntimeException.class, () -> usuarioAuthService.autenticacao(dadosLogin, token));

        //assert
        Assertions.assertEquals(expectedMessage, exception.getMessage());
        Assertions.assertEquals(expectedMessage2, exception.getCause().getMessage());
        verify(usuarioRepository).findByEmail("first");
    }
}