package concrete.com.DesafioJava.service;

import concrete.com.DesafioJava.model.Usuario;
import concrete.com.DesafioJava.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    UsuarioService usuarioService;

    @Spy
    @InjectMocks
    TokenService tokenService;

    @Mock
    UsuarioRepository usuarioRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void testBuscaTodosUsuarios_RetornoOk()
    {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        Usuario usuario1 = new Usuario(1,"Teste1","teste1@teste.com.br","teste1");
        Usuario usuario2 = new Usuario(2,"Teste2","teste2@teste.com.br","teste2");
        Usuario usuario3 = new Usuario(3,"Teste3","teste3@teste.com.br","teste3");

        usuarios.add(usuario1);
        usuarios.add(usuario2);
        usuarios.add(usuario3);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        //test
        List<Usuario> empList = service.findall(usuario1);

        assertEquals(3, empList.size());
        verify(usuarioRepository, times(1)).findAll();
    }*/

    @Test
    void testCadastroUsuario_RetornOk()
    {
        //arrange
        Usuario usuario = new Usuario(1,"Teste1","teste1@teste.com.br","teste1");
        String token ="AAAAAAAAAAAAAAAAAAAAAMLheAAAAAAA0%2BuSeid%2BULvsea4JtiGRiSDSJSI%3DEUifiRBkKG5E2XzMDjRfl76ZC9Ub0wnz4XsNiRVBChTYbJcE3F";
        when(tokenService.generateToken(usuario)).thenReturn(token);

        //act
        usuarioService.cadastro(usuario);

        //assert
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testCadastroUsuario_RetornaException()
    {
        //arrange
        Usuario usuario = new Usuario(1,"Teste1","teste1@teste.com.br","teste1");
        String expectedMessage = "Erro ao cadastrar Usuario.";
        RuntimeException exceptionMock = new RuntimeException("Erro ao conectar ao servidor");
        when(tokenService.generateToken(usuario)).thenThrow(exceptionMock);

        //act
        Exception exception = assertThrows(RuntimeException.class, () -> usuarioService.cadastro(usuario));

        //assert
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
