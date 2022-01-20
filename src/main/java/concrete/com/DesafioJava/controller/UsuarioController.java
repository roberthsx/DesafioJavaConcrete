package concrete.com.DesafioJava.controller;

import concrete.com.DesafioJava.dto.ResponseDTO;
import concrete.com.DesafioJava.mapper.ObjectMapperUtils;
import concrete.com.DesafioJava.service.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import concrete.com.DesafioJava.dto.UsuarioAutenticadoDTO;
import concrete.com.DesafioJava.dto.UsuarioCadastroDTO;
import concrete.com.DesafioJava.model.Usuario;

@RestController
public class UsuarioController {

    @Autowired
    private IUsuarioService _usuarioService;

    @Autowired
    private ObjectMapperUtils modelMapper;

    public UsuarioController(IUsuarioService usuarioService) {
        this._usuarioService = usuarioService;
    }

    @PostMapping("/usuario")
    public ResponseEntity<ResponseDTO> CadastrarUsuario(@RequestBody UsuarioCadastroDTO usuarioCadastroDTO) {
        var responseResponseDTO = new ResponseDTO();
        try {
            var usuario = _usuarioService.Cadastro(this.modelMapper.map(usuarioCadastroDTO, Usuario.class));
            if (usuario != null) {
                if (!usuario.getClass().getName().contains("Usuario")) {
                    responseResponseDTO.setMensagens((StringBuilder) usuario);
                    return new ResponseEntity(responseResponseDTO, HttpStatus.OK);
                }
                responseResponseDTO.setData(UsuarioAutenticadoDTO.toDTO((Usuario) usuario, "Bearer "));
                return new ResponseEntity<ResponseDTO>(responseResponseDTO, HttpStatus.CREATED);
            }
            return new ResponseEntity<ResponseDTO>(responseResponseDTO, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}