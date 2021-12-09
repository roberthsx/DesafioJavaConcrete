package concrete.com.DesafioJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import concrete.com.DesafioJava.dto.DadosLogin;
import concrete.com.DesafioJava.dto.UsuarioAutenticadoDTO;
import concrete.com.DesafioJava.model.Usuario;
import concrete.com.DesafioJava.service.UsuarioAuthService;

@RestController
public class AuthController {

	private UsuarioAuthService userAuthenticationService;

    @Autowired
    public AuthController(UsuarioAuthService userAuthenticationService){
        this.userAuthenticationService = userAuthenticationService;
    }

    public AuthController(){

    }


    @PostMapping("/login")
    public ResponseEntity<UsuarioAutenticadoDTO> autenticar(@RequestBody DadosLogin dadosLogin, @RequestHeader String Authorization){
        Usuario user = userAuthenticationService.autenticacao(dadosLogin, Authorization);
        return new ResponseEntity<UsuarioAutenticadoDTO>(UsuarioAutenticadoDTO.toDTO(user, "Bearer "), HttpStatus.ACCEPTED);
    }
}
