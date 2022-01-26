package concrete.com.DesafioJava.repository;

<<<<<<< HEAD

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import concrete.com.DesafioJava.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

}
=======
import concrete.com.DesafioJava.model.Usuario;

import java.util.Optional;

public abstract class UsuarioRepository implements  IUsuarioRepository{

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return Optional.empty();
    }
}
>>>>>>> main
