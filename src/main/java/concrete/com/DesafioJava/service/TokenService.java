package concrete.com.DesafioJava.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import concrete.com.DesafioJava.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    private String key = "DesafioConcrete";
    //30 minutos
    private static final long expirationTime = 1800000;

    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(usuario.getNome())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Claims decodeToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
}