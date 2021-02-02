package Utils;

import Connection.Http;
import Request.RequestOperacionCliente;
import Responses.Cuenta.Cuenta;
import com.google.gson.Gson;
import java.sql.Timestamp;

public class DataBuilder {
    
    public static void CreateOPClient(Cuenta cuenta, String idOp, String idAccAbono, String valor) {
        RequestOperacionCliente request = new RequestOperacionCliente();
        Gson gson = new Gson();
        Http http = Http.getInstance();
        
        request.setIdCliente(cuenta.getData()[0].getIdCliente());
        request.setIdOperacion(idOp);
        request.setIdCuentaAbonada(idAccAbono);
        request.setValor(valor);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        request.setFechaOperacion("'" + ts.toString() + "'");
        
        String data2 = gson.toJson(request);
        http.POST("/clientAccount/create", data2);
    }
    
}
