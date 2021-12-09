package concrete.com.DesafioJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import concrete.com.DesafioJava.model.Usuario;
import concrete.com.DesafioJava.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	  private UsuarioRepository userRepository;
	    private TokenService tokenService;

	    @Autowired
	    public UsuarioService(UsuarioRepository userRepository, TokenService tokenService){
	        this.userRepository = userRepository;
	        this.tokenService = tokenService;
	    }

	    public Usuario registrate(Usuario user){
	        user.setToken(tokenService.generateToken(user));
	        return userRepository.save(user);
	    }

}
