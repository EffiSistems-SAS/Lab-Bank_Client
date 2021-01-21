package Responses.Tarjeta;

public class Tarjeta {

    private int status;
    private Data[] data;

    @Override
    public String toString() {
        return "Tarjeta{" + "Status=" + status + ", data=" + data + '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int Status) {
        this.status = Status;
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

}
