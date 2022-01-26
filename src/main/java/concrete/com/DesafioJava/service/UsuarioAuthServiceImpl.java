package concrete.com.DesafioJava.service;

import concrete.com.DesafioJava.exception.ExpiredTokenException;
import concrete.com.DesafioJava.exception.InvalidLoginException;
import concrete.com.DesafioJava.model.DadosLogin;
import concrete.com.DesafioJava.model.Usuario;
import concrete.com.DesafioJava.repository.UsuarioRepository;
import concrete.com.DesafioJava.service.interfaces.TokenService;
import concrete.com.DesafioJava.service.interfaces.UsuarioAuthService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsuarioAuthServiceImpl implements UsuarioAuthService {

    private final UsuarioRepository _usuarioRepository;
    private final TokenService _tokenService;

<<<<<<< HEAD:src/main/java/concrete/com/DesafioJava/service/UsuarioAuthServiceImpl.java
    public UsuarioAuthServiceImpl(UsuarioRepository UsuarioRepository, TokenService tokenService) {
        this._usuarioRepository = UsuarioRepository;
=======
    @Autowired
    public UsuarioAuthService(IUsuarioRepository usuarioRepository, ITokenService tokenService) {
        this._usuarioRepository = usuarioRepository;
>>>>>>> main:src/main/java/concrete/com/DesafioJava/service/UsuarioAuthService.java
        this._tokenService = tokenService;
    }

    public Object autenticacao(DadosLogin dados, String token) {
        try {
            Optional<Usuario> usuario = _usuarioRepository.findByEmail(dados.getEmail());
            //.orElseThrow(ExistingEmailException::new);

            if (dados.getSenha().equals(usuario.get()) && !token.isEmpty() && validacao(token)) {
                return usuario;
            } else {
                throw new InvalidLoginException();
            }
        } catch (Exception exception) {
            throw new RuntimeException("Erro ao realizar autenticação", exception);
        }
    }

    private boolean validacao(String token) {
        try {
            String tokenTratado = token.replace("Bearer ", "");
            Claims claims = _tokenService.decodeToken(tokenTratado);

            System.out.println(claims.getIssuer());
            System.out.println(claims.getIssuedAt());

            //Verifica se o token está expirado
            if (claims.getExpiration().before(new Date(System.currentTimeMillis()))) throw new ExpiredTokenException();
            System.out.println(claims.getExpiration());
            return true;
        } catch (ExpiredTokenException expiredException) {
            expiredException.printStackTrace();
            throw new RuntimeException("Erro ao validar Usuario, Token Expirado", expiredException);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Erro ao validar Usuario", exception);
        }
    }
}