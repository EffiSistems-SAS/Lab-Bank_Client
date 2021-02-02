package Models;

import Connection.Http;
import Exceptions.OperacionInvalida;
import Exceptions.TarjetaVencida;
import Responses.Cuenta.Cuenta;
import Responses.Tarjeta.TarjetaDebito;
import Utils.DataBuilder;
import com.google.gson.Gson;
import java.util.Date;

public class Banco {

    private long idBanco;
    private String nombreBanco = "Banco";
    private static Http http = Http.getInstance();
    private static Gson gson = new Gson();
    private Atm atm;

    public void setAtm(Atm atm) {
        this.atm = atm;
        atm.setIdAtm("IDATM_001");
        atm.setDineroActual(3000000);
    }

    public static boolean verificarVeracidadTarjeta(TarjetaDebito tarjeta, String id) throws TarjetaVencida {
        Date actualDate = new Date();
        tarjeta.bringData(id);
        if (tarjeta.getStatus() == 200) {
            if (actualDate.before(tarjeta.getData()[0].getDate())) {
                return true;
            } else {
                throw new TarjetaVencida("Tarjeta vencida");
            }
        } else {
            return false;
        }
    }

    public static boolean validarIdentidadUsuario(TarjetaDebito tarjeta, String pass, String id) {
        tarjeta.bringData(id);
        if (tarjeta.getData()[0].getContraseña().equals(pass)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean verificarCuentaValida(Long id, Cuenta cuenta) throws OperacionInvalida {
        if (id == cuenta.getData()[0].getNumero()) {
            return true;
        }
        return false;
    }

    public static Cuenta getCuenta(String value) throws OperacionInvalida {
        String respuesta = http.GET("/account/getNumCuenta/?id=" + Long.parseLong(value));
        Cuenta cuentaAbonar = gson.fromJson(respuesta, Cuenta.class);
        return cuentaAbonar;
    }

    public static double getSaldo(String id) {
        String respuesta = http.GET("/account/get/?id=" + id);
        Cuenta cuenta = gson.fromJson(respuesta, Cuenta.class);
        DataBuilder.CreateOPClient(cuenta, "'OPID_003'", "NOACC", "NOVALUE");
        return cuenta.getData()[0].getSaldo();
    }
}
