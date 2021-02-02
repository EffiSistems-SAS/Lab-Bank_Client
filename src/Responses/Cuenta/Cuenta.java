package Responses.Cuenta;

import Connection.Http;
import com.google.gson.Gson;

public class Cuenta {

    private int status;
    private DataCuenta[] data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataCuenta[] getData() {
        return data;
    }

    public void setData(DataCuenta[] data) {
        this.data = data;
    }

    public void bringData(String id) {
        Http http = Http.getInstance();
        String res = http.GET("/account/get/?id=" + id);
        Gson gson = new Gson();
        Cuenta cuenta = gson.fromJson(res, Cuenta.class);
        this.data = cuenta.getData();
        this.status = cuenta.getStatus();
    }

    public boolean verificarCantidadRetiroDiario(int valor) {
        if (valor + data[0].getMontoRetiradoPorDia() > data[0].getMontoMaximoDiario()) {
            return false;
        }
        return true;
    }

    public boolean verificarRetiro(int valor) {
        return (valor > getData()[0].getSaldo());
    }

}
