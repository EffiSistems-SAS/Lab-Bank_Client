package Responses.Tarjeta;

import Connection.Http;
import com.google.gson.Gson;

public class TarjetaDebito {

    private int status;
    private DataTarjeta[] data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataTarjeta[] getData() {
        return data;
    }

    public void setData(DataTarjeta[] data) {
        this.data = data;
    }

    public void bringData(String id) {
        Http http = Http.getInstance();
        String res = http.GET("/card/view/?id=" + id);
        Gson gson = new Gson();
        TarjetaDebito tarjeta = gson.fromJson(res, TarjetaDebito.class);
        this.data = tarjeta.getData();
        this.status = tarjeta.getStatus();
    }

}
