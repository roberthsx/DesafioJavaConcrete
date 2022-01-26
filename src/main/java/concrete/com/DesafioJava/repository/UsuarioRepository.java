package concrete.com.DesafioJava.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import concrete.com.DesafioJava.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

}