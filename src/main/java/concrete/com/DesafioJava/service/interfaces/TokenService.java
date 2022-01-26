package concrete.com.DesafioJava.service.interfaces;

import concrete.com.DesafioJava.model.Usuario;
import io.jsonwebtoken.Claims;

public interface TokenService {

    String generateToken(Usuario usuario);
    Claims decodeToken(String token);

}
