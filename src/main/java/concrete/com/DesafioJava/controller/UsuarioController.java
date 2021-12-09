package concrete.com.DesafioJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import concrete.com.DesafioJava.dto.UsuarioAutenticadoDTO;
import concrete.com.DesafioJava.dto.UsuarioCadastroDTO;
import concrete.com.DesafioJava.model.Usuario;
import concrete.com.DesafioJava.service.UsuarioService;

@RestController
public class UsuarioController {
	
	private UsuarioService userRegistrationService;

    @Autowired
    public UsuarioController(UsuarioService userRegistrationService){
        this.userRegistrationService = userRegistrationService;
    }

    public UsuarioController(){

    }

    @PostMapping("/user")
    public ResponseEntity<UsuarioAutenticadoDTO> registrate(@RequestBody UsuarioCadastroDTO userRegistrationDTO){
        Usuario user = userRegistrationService.registrate(userRegistrationDTO.toUser());
        return  new ResponseEntity<UsuarioAutenticadoDTO>(UsuarioAutenticadoDTO.toDTO(user, "Bearer "), HttpStatus.CREATED);
    }


}
