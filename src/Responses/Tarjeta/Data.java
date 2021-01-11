package Responses.Tarjeta;

public class Data {

    private String idTarjeta;
    private String contraseña;
    private long numero;

    @Override
    public String toString() {
        return "Data{" + "idTarjeta=" + idTarjeta + ", contrase\u00f1a=" + contraseña + ", numero=" + numero + '}';
    }

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

}
