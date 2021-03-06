package concrete.com.DesafioJava.service;

import concrete.com.DesafioJava.model.Usuario;
import concrete.com.DesafioJava.repository.UsuarioRepository;
import concrete.com.DesafioJava.service.interfaces.TokenService;
import concrete.com.DesafioJava.service.interfaces.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository _usuarioRepository;
    private TokenService _tokenService;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, TokenService tokenService) {
        this._usuarioRepository = usuarioRepository;
        this._tokenService = tokenService;
    }

    public Object Cadastro(Usuario usuario) {
        try {

            StringBuilder mensagem = new StringBuilder();
            var validacao = Valida(usuario);

            if (validacao.stream().count() >= 1) {
                validacao.forEach(mensagemErro -> mensagem.append(mensagemErro + " // "));
                return mensagem;
            } else {
                usuario.setToken(_tokenService.generateToken(usuario));
                return _usuarioRepository.save(usuario);
            }
        } catch (Exception exception) {
            throw new RuntimeException("Erro ao cadastrar Usuario.", exception);
        }
    }

    public ArrayList<String> Valida(Usuario usuario) {

        var mensagens = new ArrayList<String>();
        mensagens.addAll(validaObrigatorios(usuario));

        if (!validaEmailRegex(usuario)) {
            mensagens.add("Email inválido.");
        }

        return mensagens;
    }

    private ArrayList<String> validaObrigatorios(Usuario usuario) {

        var mensagens = new ArrayList<String>();
        if (!validaUsuario(usuario)) {
            mensagens.add("Usuário é obrigatório.");
        }

        if (!validaSenha(usuario)) {
            mensagens.add("Senha é obrigatória.");
        }

        if (!validaEmail(usuario)) {
            mensagens.add("Email é obrigatório.");
        }

        return mensagens;
    }

    private boolean validaUsuario(Usuario usuario) {

        if (usuario.getNome() != null && usuario.getNome() != "") {
            return true;
        }

        return false;
    }

    private boolean validaSenha(Usuario usuario) {

        if (usuario.getSenha() != null && usuario.getSenha() != "") {
            return true;
        }

        return false;
    }

    private boolean validaEmail(Usuario usuario) {

        if (usuario.getEmail() != null && usuario.getEmail() != "") {
            return true;
        }

        return false;
    }

    private boolean validaEmailRegex(Usuario usuario) {

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(usuario.getEmail());

        if (matcher.matches()) {
            return true;
        }

        return false;
    }
}