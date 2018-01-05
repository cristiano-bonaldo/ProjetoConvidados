package cvb.com.br.convidados.model;

public class Convidado {

    public static final int C_CONVIDADO_NAO_CONFIRMADO = 0;
    public static final int C_CONVIDADO_PRESENTE       = 1;
    public static final int C_CONVIDADO_AUSENTE        = 2;

    private long id;
    private String name;
    private int status;

    public Convidado(String name, int status) {
        this.id = -1;
        this.name = name;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
