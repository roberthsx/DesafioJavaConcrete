package concrete.com.DesafioJava.dto;

import concrete.com.DesafioJava.model.Usuario;

public class UsuarioAutenticadoDTO {
	
	private String tipo;
    private String email;
    private String nome;
    private String token;

    public UsuarioAutenticadoDTO(String email, String nome, String token, String tipo) {

        this.email = email;
        this.nome = nome;
        this.token = token;
        this.tipo = tipo;
    }

    public UsuarioAutenticadoDTO(){}

    public static UsuarioAutenticadoDTO toDTO(Usuario user, String tipo) {
        return new UsuarioAutenticadoDTO(user.getEmail(), user.getNome(), user.getToken(), tipo);
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
}
