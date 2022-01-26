package concrete.com.DesafioJava.service.interfaces;

import concrete.com.DesafioJava.model.DadosLogin;

public interface UsuarioAuthService {

    Object autenticacao(DadosLogin dados, String token);

}
