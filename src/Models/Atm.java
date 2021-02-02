package Models;

import GUI.Login;
import Responses.Tarjeta.TarjetaDebito;

public class Atm {

    private Login login;
    private String idAtm;
    private final long montoMinimoRequerido = 1500000;
    private long dineroActual;
    private boolean disponible = true;
    private TarjetaDebito tarjeta = new TarjetaDebito();

    public void setIdAtm(String idAtm) {
        this.idAtm = idAtm;
    }

    public Atm(Banco banco) {
        login = Login.getLogin(this, banco);
        login.initTemplate();
    }

    public boolean verificarNumeroIntentos(String id) {
        tarjeta.bringData(id);
        if (tarjeta.getStatus() == 404) {
            return false;
        }
        if (tarjeta.getData()[0].getNumeroIntentos() >= 3) {
            return true;
        }
        return false;
    }

    public boolean verificarDineroAtm(long value) {
        return ((dineroActual - montoMinimoRequerido - value) < 0);
    }

    public void Bloquear() {
        disponible = false;
    }

    public TarjetaDebito getTarjeta() {
        return tarjeta;
    }

    public void setDineroActual(long dineroActual) {
        this.dineroActual = dineroActual;
    }

    public long getMontoMinimoRequerido() {
        return montoMinimoRequerido;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public long getDineroActual() {
        return dineroActual;
    }

    public String getIdAtm() {
        return idAtm;
    }

}
