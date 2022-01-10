package concrete.com.DesafioJava.service;

import org.springframework.stereotype.Service;

import concrete.com.DesafioJava.model.Usuario;
import concrete.com.DesafioJava.repository.UsuarioRepository;

import java.util.logging.Logger;

@Service
public class UsuarioService {
	
  	private UsuarioRepository _userRepository;
	private TokenService _tokenService;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


	public UsuarioService(UsuarioRepository userRepository, TokenService tokenService){
		this._userRepository = userRepository;
		this._tokenService = tokenService;
	}

	public Usuario cadastro(Usuario user){
		try {
			user.setToken(_tokenService.generateToken(user));
			return _userRepository.save(user);
		}catch (Exception exception){
			throw new RuntimeException("Erro ao cadastrar Usuario.",exception);
		}
	}
}
