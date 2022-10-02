package models;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class Reserva {
    private int id;
    private Date fecha_entrada;
    private Date fecha_salida;
    private int valor;
    private String forma_pago;
    private final int precio = 20;

    public Reserva() {
    }

    public Reserva(Date fechaEntrada, Date fechaSalida, String formaPago) {
        this.fecha_entrada = fechaEntrada;
        this.fecha_salida = fechaSalida;
        this.forma_pago = formaPago;
    }

    public Reserva(int id, Date fechaEntrada, Date fechaSalida, int valor, String formaPago) {
        this.id = id;
        this.fecha_entrada = fechaEntrada;
        this.fecha_salida = fechaSalida;
        this.valor = valor;
        this.forma_pago = formaPago;
    }

    public int getId() {
        return id;
    }

    public Date getFecha_entrada() {
        return fecha_entrada;
    }

    public Date getFecha_salida() {
        return fecha_salida;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(java.util.Date fechaEntrada, java.util.Date fechaSalida) {
        ZoneId zone = ZoneId.systemDefault();
        LocalDate fechaDesde = fechaEntrada.toInstant().atZone(zone).toLocalDate();
        LocalDate fechaHasta = fechaSalida.toInstant().atZone(zone).toLocalDate();
        int days = (int) ChronoUnit.DAYS.between(fechaDesde, fechaHasta);
        if (days < 0) {
            throw new RuntimeException();
        } else if (days > 0) {
            this.valor = precio * days;
            return;
        }
        this.valor = precio;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFechaEntrada(Date fe) {
        this.fecha_entrada = fe;
    }

    public void setFechaSalida(Date fe) {
        this.fecha_salida = fe;
    }

    public void setTipoPago(String formaPago) {
        this.forma_pago = formaPago;
    }

    @Override
    public String toString() {
        return "id :" + id + " fecha entrada: " + fecha_entrada + " fecha_salida: " + fecha_salida + " Valor: " + valor
                + " forma de pago: " + forma_pago;
    }
}
