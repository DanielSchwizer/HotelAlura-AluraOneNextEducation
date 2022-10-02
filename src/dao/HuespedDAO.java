package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Huesped;
import models.Reserva;

public class HuespedDAO {
    private Connection conn;

    public HuespedDAO(Connection conn) {
        this.conn = conn;
    }

    public void guardar(Huesped huesped) {
        try {
            PreparedStatement statement;
            statement = conn.prepareStatement(
                    "INSERT INTO huespedes "
                            + "(nombre, apellido, fecha_de_nacimiento, nacionalidad,telefono, reserva_id)"
                            + " VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            try (statement) {
                statement.setString(1, huesped.getNombre());
                statement.setString(2, huesped.getApellido());
                statement.setDate(3, huesped.getFechaNacimiento());
                statement.setString(4, huesped.getNacionalidad());
                statement.setLong(5, huesped.getTelefono());
                statement.setInt(6, huesped.getReserva_id());

                statement.execute();

                final ResultSet resultSet = statement.getGeneratedKeys();

                try (resultSet) {
                    while (resultSet.next()) {
                        huesped.setId(resultSet.getInt(1));

                        System.out.println(String.format("Fue insertado el huesped: %s", huesped));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Huesped> list() {
        List<Huesped> resultado = new ArrayList<>();

        try {
            final PreparedStatement statement = conn
                    .prepareStatement(
                            "SELECT id, nombre, apellido,fecha_de_nacimiento, nacionalidad, telefono, reserva_id FROM huespedes");

            try (statement) {
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try (resultSet) {
                    while (resultSet.next()) {
                        resultado.add(new Huesped(
                                resultSet.getInt("id"),
                                resultSet.getString("nombre"),
                                resultSet.getString("apellido"),
                                resultSet.getDate("fecha_de_nacimiento"),
                                resultSet.getString("nacionalidad"),
                                resultSet.getLong("telefono"),
                                resultSet.getInt("reserva_id")));

                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

    public List<Huesped> list(Reserva reserva) {
        List<Huesped> resultado = new ArrayList<>();

        try {
            String sql = "SELECT id, nombre, apellido,fecha_de_nacimiento, nacionalidad, telefono "
                    + " FROM huespedes WHERE reserva_id = ?";

            final PreparedStatement statement = conn.prepareStatement(
                    sql);

            try (statement) {
                statement.setInt(1, reserva.getId());
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try (resultSet) {
                    while (resultSet.next()) {
                        resultado.add(new Huesped(
                                resultSet.getInt("id"),
                                resultSet.getString("nombre"),
                                resultSet.getString("apellido"),
                                resultSet.getDate("fecha_de_nacimiento"),
                                resultSet.getString("nacionalidad"),
                                resultSet.getInt("telefono")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

    public int delete(int id) {
        try {
            final PreparedStatement statement = conn.prepareStatement("DELETE FROM huespedes WHERE ID = ?");

            try (statement) {
                statement.setInt(1, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int update(String nombre, String apellido, Date fechaNacimiento, String nacionalidad, int telefono, int id) {
        try {
            final PreparedStatement statement = conn.prepareStatement(
                    "UPDATE huespedes SET "
                            + " nombre = ?, "
                            + " apellido = ?,"
                            + " fecha_de_nacimiento = ?,"
                            + " nacionalidad = ?,"
                            + " telefono = ?"
                            + " WHERE id = ?");

            try (statement) {
                statement.setString(1, nombre);
                statement.setString(2, apellido);
                statement.setDate(3, fechaNacimiento);
                statement.setString(4, nacionalidad);
                statement.setInt(5, telefono);
                statement.setInt(6, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Huesped> getHuespedApellido(String apellido) {
        List<Huesped> resultado = new ArrayList<>();

        try {
            final PreparedStatement statement = conn
                    .prepareStatement(
                            "SELECT * FROM huespedes where apellido = ?");

            try (statement) {
                statement.setString(1, apellido);
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try (resultSet) {
                    while (resultSet.next()) {
                        resultado.add(new Huesped(
                                resultSet.getInt("id"),
                                resultSet.getString("nombre"),
                                resultSet.getString("apellido"),
                                resultSet.getDate("fecha_de_nacimiento"),
                                resultSet.getString("nacionalidad"),
                                resultSet.getInt("telefono"),
                                resultSet.getInt("reserva_id")));

                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

}
