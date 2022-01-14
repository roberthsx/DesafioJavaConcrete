package concrete.com.DesafioJava.exception;

import java.util.function.Supplier;

public class ExistingEmailException extends RuntimeException{
    public ExistingEmailException() {
        super("Email já cadastrado.");
    }
}
