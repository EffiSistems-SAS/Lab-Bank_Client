package Responses.Tarjeta;

public class Data {

    private int idTarjeta;
    private String contraseña;
    private int numero;

    @Override
    public String toString() {
        return "Data{" + "idTarjeta=" + idTarjeta + ", contrase\u00f1a=" + contraseña + ", numero=" + numero + '}';
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

}
