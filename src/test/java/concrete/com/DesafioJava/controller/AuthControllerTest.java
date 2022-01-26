package concrete.com.DesafioJava.controller;

import concrete.com.DesafioJava.dto.DadosLoginDTO;
import concrete.com.DesafioJava.dto.ResponseDTO;
import concrete.com.DesafioJava.mapper.ObjectMapperUtils;
import concrete.com.DesafioJava.model.DadosLogin;
import concrete.com.DesafioJava.model.Usuario;
import concrete.com.DesafioJava.service.interfaces.UsuarioAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;

import static concrete.com.DesafioJava.controller.usuarioFactory.dadosLoginDTOFactory.DadosLoginDtoSimples;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private UsuarioAuthService usuarioAuthService;

    @MockBean
    private ObjectMapperUtils modelMapper;

    private String token;
    private HttpHeaders headers;
    private String endpoint;

    @BeforeEach
    public void init() {
        token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NDE5OTAyOTUsInN1YiI6IlRlc3RlIEpXVCBBUEkiLCJleHAiOjE2NDE5OTIwO" +
                "TV9.e3drFCO4E2IWvrUrqmujL5fXlwR1ArMVcef3qAjs84c";
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        endpoint = "/login";
    }

    @Test
    public void TestAutenticar_retorno_ACCEPTED() {
        //arrange
        DadosLoginDTO dadosLoginDTO = DadosLoginDtoSimples();
        HttpEntity<DadosLoginDTO> httpEntity = new HttpEntity<>(dadosLoginDTO, headers);
        var dadosLogin = new DadosLogin(dadosLoginDTO.getEmail(), dadosLoginDTO.getSenha());
        var usuario = new Usuario("teste", dadosLogin.getEmail(), dadosLogin.getSenha());
        when(modelMapper.map(any(), any())).thenReturn(dadosLogin);
        given(usuarioAuthService.autenticacao(dadosLogin, token)).willReturn(usuario);

        //act
        ResponseEntity<ResponseDTO> response = this.testRestTemplate
                .exchange(endpoint, HttpMethod.POST, httpEntity, ResponseDTO.class);

        //assert
        assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals("", response.getBody().getMensagens());
        assertNotEquals("", response.getBody().getData());
    }

    @Test
    public void TestAutenticar_Quando_Sem_Token_Encontra_Throws_Retorno_BADREQUEST() {
        //arrange
        DadosLoginDTO dadosLoginDTO = DadosLoginDtoSimples();
        HttpEntity<DadosLoginDTO> httpEntity = new HttpEntity<>(dadosLoginDTO);

        //act
        ResponseEntity<ResponseDTO> response = this.testRestTemplate
                .exchange(endpoint, HttpMethod.POST, httpEntity, ResponseDTO.class);

        //assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void TestAutenticar_E_Servico_Retorna_Null_Retorno_OK_Com_Payload_Vazio() {
        //arrange
        DadosLoginDTO dadosLoginDTO = DadosLoginDtoSimples();
        HttpEntity<DadosLoginDTO> httpEntity = new HttpEntity<>(dadosLoginDTO, headers);
        var dadosLogin = new DadosLogin(dadosLoginDTO.getEmail(), dadosLoginDTO.getSenha());
        when(modelMapper.map(any(), any())).thenReturn(dadosLogin);
        given(usuarioAuthService.autenticacao(dadosLogin, token)).willReturn(null);

        //act
        ResponseEntity<ResponseDTO> response = this.testRestTemplate
                .exchange(endpoint, HttpMethod.POST, httpEntity, ResponseDTO.class);

        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("", response.getBody().getMensagens());
        assertEquals("", response.getBody().getData());
    }

    @Test
    public void TestAutenticar_E_Encontra_Throws_Retorno_INTERNAL_SERVER_ERROR() {
        //arrange
        DadosLoginDTO dadosLoginDTO = DadosLoginDtoSimples();
        HttpEntity<DadosLoginDTO> httpEntity = new HttpEntity<>(dadosLoginDTO, headers);
        var dadosLogin = new DadosLogin(dadosLoginDTO.getEmail(), dadosLoginDTO.getSenha());
        when(modelMapper.map(any(), any())).thenReturn(dadosLogin);
        given(usuarioAuthService.autenticacao(dadosLogin, token)).
                willThrow(new RuntimeException("Erro ao se conectar com base de dados."));

        //act
        ResponseEntity<ResponseDTO> response = this.testRestTemplate
                .exchange(endpoint, HttpMethod.POST, httpEntity, ResponseDTO.class);

        //assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void TestAutenticar_Quando_Dto_Nome_Vazio_Retorno_OK_Com_Mensagem_de_erro() {
        //arrange
        DadosLoginDTO dadosLoginDTO = DadosLoginDtoSimples();
        HttpEntity<DadosLoginDTO> httpEntity = new HttpEntity<>(dadosLoginDTO, headers);
        StringBuilder mensagemRetorno = new StringBuilder();
        mensagemRetorno.append("Email é obrigatório.");
        var dadosLogin = new DadosLogin(dadosLoginDTO.getEmail(), dadosLoginDTO.getSenha());
        when(modelMapper.map(any(), any())).thenReturn(dadosLogin);
        given(usuarioAuthService.autenticacao(dadosLogin, token)).willReturn(mensagemRetorno);

        //act
        ResponseEntity<ResponseDTO> response = this.testRestTemplate
                .exchange(endpoint, HttpMethod.POST, httpEntity, ResponseDTO.class);

        //assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals("", response.getBody().getMensagens());
        assertEquals("", response.getBody().getData());
    }
}