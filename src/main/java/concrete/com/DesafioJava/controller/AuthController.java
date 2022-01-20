package concrete.com.DesafioJava.controller;

import concrete.com.DesafioJava.dto.ResponseDTO;
import concrete.com.DesafioJava.mapper.ObjectMapperUtils;
import concrete.com.DesafioJava.model.DadosLogin;
import concrete.com.DesafioJava.service.interfaces.IUsuarioAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import concrete.com.DesafioJava.dto.DadosLoginDTO;
import concrete.com.DesafioJava.dto.UsuarioAutenticadoDTO;
import concrete.com.DesafioJava.model.Usuario;

@RestController
public class AuthController {

    @Autowired
    private IUsuarioAuthService _usuarioAutenticacaoService;

    @Autowired
    private ObjectMapperUtils modelMapper;

    public AuthController(IUsuarioAuthService usuarioAutenticacaoService) {
        this._usuarioAutenticacaoService = usuarioAutenticacaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> Autenticar(@RequestBody DadosLoginDTO dadosLoginDTO, @RequestHeader String Authorization) {
        var responseResponseDTO = new ResponseDTO();
        try {
            var usuario = _usuarioAutenticacaoService.autenticacao(modelMapper.map(dadosLoginDTO, DadosLogin.class), Authorization);
            if (usuario != null) {
                if (!usuario.getClass().getName().contains("Usuario")) {
                    responseResponseDTO.setMensagens((StringBuilder)usuario);
                    return new ResponseEntity(responseResponseDTO, HttpStatus.OK);
                }
                responseResponseDTO.setData(UsuarioAutenticadoDTO.toDTO((Usuario) usuario, "Bearer "));
                return new ResponseEntity<ResponseDTO>(responseResponseDTO, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<ResponseDTO>(responseResponseDTO, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
