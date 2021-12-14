package concrete.com.DesafioJava.service;

import org.springframework.stereotype.Service;

import concrete.com.DesafioJava.model.Usuario;
import concrete.com.DesafioJava.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
  	private UsuarioRepository _userRepository;
	private TokenService _tokenService;


	public UsuarioService(UsuarioRepository userRepository, TokenService tokenService){
		this._userRepository = userRepository;
		this._tokenService = tokenService;
	}

	public Usuario cadastro(Usuario user){
		user.setToken(_tokenService.generateToken(user));
		return _userRepository.save(user);
	}
}
