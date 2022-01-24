package concrete.com.DesafioJava.repository;

import concrete.com.DesafioJava.model.Usuario;

import java.util.Optional;

public abstract class UsuarioRepository implements  IUsuarioRepository{

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return Optional.empty();
    }
}
