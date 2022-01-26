package concrete.com.DesafioJava.model;

public class DadosLogin {

    private String email;
    private String senha;

    public DadosLogin(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public DadosLogin() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
