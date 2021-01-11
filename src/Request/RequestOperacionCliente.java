package Request;

public class RequestOperacionCliente {

    private String idOperacion;
    private long idCliente;
    private String fechaOperacion, idCuentaAbonada, idOperacion_Cliente, Valor;

    public String getValor() {
        return Valor;
    }

    public void setValor(String Valor) {
        this.Valor = Valor;
    }

    public String getIdOperacion_Cliente() {
        return idOperacion_Cliente;
    }

    public void setIdOperacion_Cliente(String idOperacion_Cliente) {
        this.idOperacion_Cliente = idOperacion_Cliente;
    }

    public String getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(String fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public String getIdCuentaAbonada() {
        return idCuentaAbonada;
    }

    public void setIdCuentaAbonada(String idCuentaAbonada) {
        this.idCuentaAbonada = idCuentaAbonada;
    }

    public String getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(String idOperacion) {
        this.idOperacion = idOperacion;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

}
