package Responses.Cuenta;

public class DataCuenta {

    private final int montoMaximoDiario = 3000000;
    private String idCuenta, idTarjeta;
    private int contraseña;
    private long numero, idCliente, numeroTarjeta, montoRetiradoPorDia;
    private double saldo;

    public String getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(String idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public int getContraseña() {
        return contraseña;
    }

    public void setContraseña(int contraseña) {
        this.contraseña = contraseña;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public long getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public int getMontoMaximoDiario() {
        return montoMaximoDiario;
    }

    public long getMontoRetiradoPorDia() {
        return montoRetiradoPorDia;
    }

    public void setNumeroTarjeta(long numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "DataCuenta{" + "montoMaximoDiario=" + montoMaximoDiario + ", idCuenta=" + idCuenta + ", idTarjeta=" + idTarjeta + ", contrase\u00f1a=" + contraseña + ", numero=" + numero + ", idCliente=" + idCliente + ", numeroTarjeta=" + numeroTarjeta + ", montoRetiradoxDia=" + montoRetiradoPorDia + ", saldo=" + saldo + '}';
    }

}
