package controller;

import java.sql.Date;
import java.util.List;

import dao.ReservaDAO;
import factory.ConnectionFactory;
import models.Reserva;

public class ReservaController {
    private ReservaDAO reservaDao;

    public ReservaController(){
        var factory = new ConnectionFactory();
        this.reservaDao = new ReservaDAO(factory.getDataSourceConn());
    }

    
    public void guardar(Reserva reserva, java.util.Date fechaEntrada, java.util.Date fechaSalida ) {
        reserva.setValor(fechaEntrada, fechaSalida);
        reservaDao.guardar(reserva);
    }

    public int getLastId(){
        return reservaDao.getLastId();
    }

    public List<Reserva> list(){
        return reservaDao.list();
    }

    
    public int delete(int id) {
        return reservaDao.delete(id);
    }

    public int update(Date fechaEntrada, Date fechaSalida,  int valor, String formaPago, int id){
        return reservaDao.update(fechaEntrada, fechaSalida, valor, formaPago, id);
    }

    public List<Reserva> getNumeroReserva(String id){
        return reservaDao.getNumeroReserva(id);
    }

}
