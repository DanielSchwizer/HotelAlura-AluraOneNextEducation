package controller;

import java.sql.Date;
import java.util.List;

import dao.HuespedDAO;
import factory.ConnectionFactory;
import models.Huesped;
import models.Reserva;

public class HuespedController {
    private HuespedDAO huespedDao;

    public HuespedController() {
        var factory = new ConnectionFactory();
        this.huespedDao = new HuespedDAO(factory.getDataSourceConn());
    }

    public int update(String nombre, String apellido, Date fechaNacimiento,String nacionalidad, int telefono, int id) {
        return huespedDao.update(nombre, apellido, fechaNacimiento, nacionalidad, telefono, id);
    }

    public int delete(int id) {
        return huespedDao.delete(id);
    }

    public List<Huesped> list() {
        return huespedDao.list();
    }

    public void guardar(Huesped huesped, int reservaId) {
        huesped.setReserva_id(reservaId);
        huespedDao.guardar(huesped);
    }

    public List<Huesped> list(Reserva reserva) {
        return huespedDao.list(reserva);
    }

    public List<Huesped> getHuespedApellido(String apellido){
        return huespedDao.getHuespedApellido(apellido);
    }

}
