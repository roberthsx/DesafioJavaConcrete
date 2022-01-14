package concrete.com.DesafioJava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import concrete.com.DesafioJava.dto.UsuarioAutenticadoDTO;
import concrete.com.DesafioJava.dto.UsuarioCadastroDTO;
import concrete.com.DesafioJava.model.Usuario;
import concrete.com.DesafioJava.service.UsuarioService;

@RestController
public class UsuarioController {
	
	private UsuarioService _usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this._usuarioService = usuarioService;
    }

    @PostMapping("/usuario")
    public ResponseEntity<UsuarioAutenticadoDTO> CadastrarUsuario(@RequestBody UsuarioCadastroDTO usuarioCadastroDTO){
        try{
            Usuario usuario = _usuarioService.Cadastro(usuarioCadastroDTO.toUsuario());
            if(usuario.getMensagensErros().length() >=1){
                return  new ResponseEntity(usuario.getMensagensErros().toString(), HttpStatus.OK);
            }
            return  new ResponseEntity<UsuarioAutenticadoDTO>(UsuarioAutenticadoDTO.toDTO(usuario, "Bearer "), HttpStatus.CREATED);
        }catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}