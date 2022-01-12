package concrete.com.DesafioJava.exception;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException() {
        super("Login Inválido.");
    }
}
