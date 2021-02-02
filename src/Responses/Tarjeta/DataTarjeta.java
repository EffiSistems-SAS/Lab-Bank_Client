package Responses.Tarjeta;

import java.util.Date;

public class DataTarjeta {

    private String idTarjeta;
    private String contraseña;
    private long numero;
    private int numeroIntentos;
    private Date fecha_expiracion;

    public String getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(String idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public int getNumeroIntentos() {
        return numeroIntentos;
    }

    public void setNumeroIntentos(int numeroIntentos) {
        this.numeroIntentos = numeroIntentos;
    }

    public void setDate(Date date) {
        this.fecha_expiracion = date;
    }

    public Date getDate() {
        return fecha_expiracion;
    }

    @Override
    public String toString() {
        return "DataTarjeta{" + "idTarjeta=" + idTarjeta + ", contrase\u00f1a=" + contraseña + ", numero=" + numero + ", numeroIntentos=" + numeroIntentos + ", fecha_expiracion=" + fecha_expiracion + '}';
    }

}
