package concrete.com.DesafioJava.controller;

import concrete.com.DesafioJava.dto.ResponseDTO;
import concrete.com.DesafioJava.dto.UsuarioCadastroDTO;
import concrete.com.DesafioJava.mapper.ObjectMapperUtils;
import concrete.com.DesafioJava.model.Usuario;
import concrete.com.DesafioJava.service.interfaces.IUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static concrete.com.DesafioJava.controller.usuarioFactory.usuarioDtoFactory.usuarioCadastroDTONomeVazio;
import static concrete.com.DesafioJava.controller.usuarioFactory.usuarioDtoFactory.usuarioCadastroDTOSimples;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerIntegracaoTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private IUsuarioService usuarioService;

    @MockBean
    private ObjectMapperUtils modelMapper;

    private String token;

    @BeforeEach
    public void init() {
        token = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NDE5OTAyOTUsInN1YiI6IlRlc3RlIEpXVCBBUEkiLCJleHAiOjE2NDE5OTIwOTV9." +
                "e3drFCO4E2IWvrUrqmujL5fXlwR1ArMVcef3qAjs84c";
    }

    @Test
    public void TestCadastrarUsuario_retorno_201() {
        //arrange
        UsuarioCadastroDTO usuarioCadastroDTO = usuarioCadastroDTOSimples();
        HttpEntity<UsuarioCadastroDTO> httpEntity = new HttpEntity<>(usuarioCadastroDTO);
        Usuario usuario = usuarioCadastroDTO.toUsuario();
        var usuarioRetorno = new Usuario(usuario.getNome(),usuario.getEmail(),usuario.getSenha());
        usuarioRetorno.setToken(token);
        usuarioRetorno.setId(new Long(1));
        when(modelMapper.map(any(), any())).thenReturn(usuario);
        when(usuarioService.Cadastro(usuario)).thenReturn(usuarioRetorno);

        //act
        ResponseEntity<ResponseDTO> response = this.testRestTemplate
                .exchange("/usuario", HttpMethod.POST, httpEntity, ResponseDTO.class);

        //assert
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void TestCadastrarUsuario_Quando_Dto_Nome_Vazio_Retorno_200_Com_Mensagem_de_erro() {
        //arrange
        UsuarioCadastroDTO usuarioCadastroDTO = usuarioCadastroDTONomeVazio();
        HttpEntity<UsuarioCadastroDTO> httpEntity = new HttpEntity<>(usuarioCadastroDTO);
        StringBuilder mensagemRetorno = new StringBuilder();
        mensagemRetorno.append("Usuário é obrigatório.");
        Usuario usuario = usuarioCadastroDTO.toUsuario();
        when(modelMapper.map(any(), any())).thenReturn(usuario);
        given(usuarioService.Cadastro(usuario)).willReturn(mensagemRetorno);
        //ResponseDTO esperado = new ResponseDTO();
        //esperado.setMensagens(mensagemRetorno);

        //act
        ResponseEntity<ResponseDTO> response = this.testRestTemplate
                .exchange("/usuario", HttpMethod.POST, httpEntity, ResponseDTO.class);

        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void TestCadastrarUsuario_E_Encontra_Throws_Retorno_500_Com_Mensagem_de_erros() {
        //arrange
        UsuarioCadastroDTO usuarioCadastroDTO = usuarioCadastroDTOSimples();
        HttpEntity<UsuarioCadastroDTO> httpEntity = new HttpEntity<>(usuarioCadastroDTO);
        var usuario = usuarioCadastroDTO.toUsuario();
        when(modelMapper.map(any(), any())).thenReturn(usuario);
        given(usuarioService.Cadastro(usuario)).willThrow(new RuntimeException("Erro ao se conectar com base de dados."));

        //act
        ResponseEntity<String> response = this.testRestTemplate
                .exchange("/usuario", HttpMethod.POST, httpEntity, String.class);

        //assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void TestCadastrarUsuario_E_Servico_Retorna_Null_Retorno_200_Com_Payload_Vazio() {
        //arrange
        UsuarioCadastroDTO usuarioCadastroDTO = usuarioCadastroDTOSimples();
        HttpEntity<UsuarioCadastroDTO> httpEntity = new HttpEntity<>(usuarioCadastroDTO);
        var usuario = usuarioCadastroDTO.toUsuario();
        when(modelMapper.map(any(), any())).thenReturn(usuario);
        given(usuarioService.Cadastro(usuario)).willReturn(null);

        //act
        ResponseEntity<String> response = this.testRestTemplate
                .exchange("/usuario", HttpMethod.POST, httpEntity, String.class);

        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
