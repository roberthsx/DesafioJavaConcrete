package concrete.com.DesafioJava.model;

import javax.persistence.*;

@Entity
@Table(name="TBL_USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    @Column(name="nome", nullable=false, length=200)
	private String nome;
    @Column(name="email" , nullable=false, length=200)
	private String email;
    @Column(name="senha" , nullable=false)
	private String senha;
    @Column(name="token" , nullable=false)
	private String token;

    public Usuario() {
    }

    public Usuario(long id, String nome, String email, String senha) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}
	
	public Usuario(String nome, String email, String senha){
	        this.nome = nome;
	        this.email = email;
	        this.senha = senha;
	}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
    
}
