package concrete.com.DesafioJava.service;


import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

import concrete.com.DesafioJava.repository.UsuarioRepository;
import concrete.com.DesafioJava.dto.DadosLogin;
import concrete.com.DesafioJava.exception.ExistingEmailException;
import concrete.com.DesafioJava.exception.ExpiredTokenException;
import concrete.com.DesafioJava.exception.InvalidLoginException;
import concrete.com.DesafioJava.exception.InvalidTokenException;
import concrete.com.DesafioJava.model.Usuario;

@Service
public class UsuarioAuthService {

	 private final UsuarioRepository userRepository;
	    private final TokenService tokenService;

	    @Autowired
	    public UsuarioAuthService(UsuarioRepository userRepository, TokenService tokenService){
	        this.userRepository = userRepository;
	        this.tokenService = tokenService;
	    }


	    public Usuario autenticacao(DadosLogin dados, String token){
	    	Usuario user = userRepository.findByEmail(dados.getEmail()).orElseThrow(ExistingEmailException::new);
	        if(dados.getSenha().equals(user.getSenha()) && !token.isEmpty() && validacao(token)) {
	            return user;
	        }
	        else {
	            throw new InvalidLoginException();
	        }
	    }

	    private boolean validacao(String token) {
	        try {
	            String tokenTratado = token.replace("Bearer ", "");
	            Claims claims = tokenService.decodeToken(tokenTratado);

	            System.out.println(claims.getIssuer());
	            System.out.println(claims.getIssuedAt());
	            //Verifica se o token está expirado
	            if (claims.getExpiration().before(new Date(System.currentTimeMillis()))) throw new ExpiredTokenException();
	            System.out.println(claims.getExpiration());
	            return true;
	        } catch (ExpiredTokenException et){
	            et.printStackTrace();
	            throw et;
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new InvalidTokenException();
	        }

	    }
	
}
