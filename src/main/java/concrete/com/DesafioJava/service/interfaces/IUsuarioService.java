package concrete.com.DesafioJava.service.interfaces;

import concrete.com.DesafioJava.model.Usuario;

import java.util.ArrayList;

public interface IUsuarioService {
    Object Cadastro(Usuario usuario);
    ArrayList<String> Valida(Usuario usuario);
}
