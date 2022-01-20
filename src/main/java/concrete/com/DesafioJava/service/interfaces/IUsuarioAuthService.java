package concrete.com.DesafioJava.service.interfaces;

import concrete.com.DesafioJava.model.DadosLogin;

public interface IUsuarioAuthService {
    Object autenticacao(DadosLogin dados, String token);
}
