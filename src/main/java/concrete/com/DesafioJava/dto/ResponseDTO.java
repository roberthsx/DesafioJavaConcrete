package concrete.com.DesafioJava.dto;

public class ResponseDTO {
    private Object data;
    private String mensagens;

    public ResponseDTO() {
        this.mensagens = "";
        this.data="";
    }

    public Object getData() {
            return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMensagens() {
        return mensagens.toString();
    }

    public void setMensagens(StringBuilder mensagens) {
        this.mensagens = mensagens.toString();
    }
}
