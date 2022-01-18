package concrete.com.DesafioJava.controller;

import concrete.com.DesafioJava.dto.UsuarioCadastroDTO;
import concrete.com.DesafioJava.model.Usuario;
import concrete.com.DesafioJava.repository.UsuarioRepository;
import concrete.com.DesafioJava.service.UsuarioService;
import concrete.com.DesafioJava.service.interfaces.IUsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerIntegracaoTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private IUsuarioService usuarioService;

    @Test
    public void TestCadastrarUsuario_retorno_201() {
        //arrange
        UsuarioCadastroDTO usuarioCadastroDTO = usuarioCadastroDTOSimples();
        HttpEntity<UsuarioCadastroDTO> httpEntity = new HttpEntity<>(usuarioCadastroDTO);
        when(usuarioService.Cadastro(usuarioCadastroDTO.toUsuario())).thenReturn(usuarioCadastroDTO.toUsuario());

        //act
        ResponseEntity<Usuario> response = this.testRestTemplate
                .exchange("/usuario", HttpMethod.POST, httpEntity, Usuario.class);

        //assert
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody().getNome(), "teste1");
        assertNotNull(response.getBody().getToken());
    }

    @Test
    public void TestCadastrarUsuario_Quando_Dto_Nome_Vazio_Retorno_200_Com_Mensagem_de_erro() {
        //arrange
        UsuarioCadastroDTO usuarioCadastroDTO = usuarioCadastroDTONomeVazio();
        HttpEntity<UsuarioCadastroDTO> httpEntity = new HttpEntity<>(usuarioCadastroDTO);
        String esperado = "Usuário é obrigatório.";
        Usuario usuario = usuarioCadastroDTO.toUsuario();
        when(usuarioService.Cadastro(usuarioCadastroDTO.toUsuario())).thenReturn(usuario);
        //given(usuarioService.Cadastro(usuarioCadastroDTO.toUsuario())).willReturn(usuario);

        //act
        ResponseEntity<String> response = this.testRestTemplate
                .exchange("/usuario", HttpMethod.POST, httpEntity, String.class);

        //assert
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(esperado,response.getBody());
    }

    @Test
    public void TestCadastrarUsuario_E_Encontra_Throws_Retorno_500_Com_Mensagem_de_erros() {
        //arrange
        UsuarioCadastroDTO usuarioCadastroDTO = usuarioCadastroDTOSimples();
        HttpEntity<UsuarioCadastroDTO> httpEntity = new HttpEntity<>(usuarioCadastroDTO);
        String esperado = "Usuário é obrigatório.";
        var usuario = usuarioCadastroDTO.toUsuario();
        given(usuarioService.Cadastro(usuario)).willThrow(new RuntimeException("Erro ao se conectar com base de dados."));

        //act
        ResponseEntity<String> response = this.testRestTemplate
                .exchange("/usuario", HttpMethod.POST, httpEntity, String.class);

        //assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
        //assertEquals(esperado,response.getBody());
    }

}
