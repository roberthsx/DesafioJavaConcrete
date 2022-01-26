package concrete.com.DesafioJava.controller.usuarioFactory;

import concrete.com.DesafioJava.dto.DadosLoginDTO;

public class dadosLoginDTOFactory {

    public static DadosLoginDTO DadosLoginDtoSimples() {
        return new DadosLoginDTO("teste1@test.com", "teste1");
    }

}
