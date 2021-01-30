package Responses.Tarjeta;

public class TarjetaDebito {

    private int status;
    private DataTarjeta[] data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int Status) {
        this.status = Status;
    }

    public DataTarjeta[] getData() {
        return data;
    }

    public void setData(DataTarjeta[] data) {
        this.data = data;
    }

}
