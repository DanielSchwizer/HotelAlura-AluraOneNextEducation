package models;

import java.sql.Date;

public class Huesped {
    private int id;
    private String nombre;
    private String apellido;
    private Date fecha_de_nacimiento;
    private String nacionalidad;
    private long telefono;
    private int reserva_id;


    public Huesped(int id,String nombre, String apellido, Date fechaNacimiento, String nacionalidad, long telefono){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_de_nacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.telefono = telefono;
    }

    public Huesped(String nombre, String apellido, Date fechaNacimiento, String nacionalidad, long telefono ){
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_de_nacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.telefono = telefono;
    }

    public Huesped(int id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, long telefono, int reserva_id) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_de_nacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.telefono = telefono;
        this.reserva_id = reserva_id;
    }

    public int getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public String getApellido(){
        return apellido;
    }

    public Date getFechaNacimiento(){
        return fecha_de_nacimiento;
    }

    public String getNacionalidad(){
        return nacionalidad;
    }

    public long getTelefono(){
        return telefono;
    }

    public int getReserva_id() {
        return reserva_id;
    }

    public void setReserva_id(int reserva_id) {
        this.reserva_id = reserva_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public String toString() {
        return String.format( "{ id: %d, nombre: %s, apellido: %s, nacionalidad: %s, telefono: %d, reserva_id: %d }", id,nombre,apellido,nacionalidad,telefono,reserva_id);
    }

}
