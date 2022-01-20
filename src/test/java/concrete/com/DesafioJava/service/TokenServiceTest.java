package concrete.com.DesafioJava.service;

import concrete.com.DesafioJava.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static concrete.com.DesafioJava.service.usuarioFactory.UsuarioAuthFactory.UsuarioSimples;

public class TokenServiceTest {

    @InjectMocks
    TokenService tokenService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testGenerateToken_Retorno_Token() {
        //arrange
        Usuario usuario = UsuarioSimples();

        //act
        String retornoToken = tokenService.generateToken(usuario);

        //assert
        Assertions.assertNotEquals("", retornoToken);
    }

    @Test
    void testGenerateToken_Retorno_Claims() {
        //arrange
        var token = Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject("Teste")
                .setExpiration(new Date(System.currentTimeMillis() + 1800000))
                .signWith(SignatureAlgorithm.HS256, "DesafioConcrete")
                .compact();
        //act
        var claims = tokenService.decodeToken(token);

        //assert
        Assertions.assertNotEquals("", claims);
        Assertions.assertNotNull(claims);
    }
}
