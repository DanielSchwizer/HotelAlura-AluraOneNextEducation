package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Reserva;

public class ReservaDAO {
    private Connection conn;

    public ReservaDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Reserva> list() {
        List<Reserva> resultado = new ArrayList<>();

        try {
            String sql = "SELECT id,  fecha_entrada, fecha_salida, valor, forma_pago FROM reservas";

            final PreparedStatement statement = conn
                    .prepareStatement(sql);

            try (statement) {
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try (resultSet) {
                    while (resultSet.next()) {
                        resultado.add(new Reserva(
                                resultSet.getInt("id"),
                                resultSet.getDate("fecha_entrada"),
                                resultSet.getDate("fecha_salida"),
                                resultSet.getInt("valor"),
                                resultSet.getString("forma_pago")));
                    }

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    public void guardar(Reserva reserva) {
        try {
            PreparedStatement statement;
            statement = conn.prepareStatement(
                    "INSERT INTO reservas"
                            + "(fecha_entrada, fecha_salida, valor, forma_pago)"
                            + " VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            try (statement) {
                statement.setDate(1, reserva.getFecha_entrada());
                statement.setDate(2, reserva.getFecha_salida());
                statement.setInt(3, reserva.getValor());
                statement.setString(4, reserva.getForma_pago());

                statement.execute();

                final ResultSet resultSet = statement.getGeneratedKeys();

                try (resultSet) {
                    while (resultSet.next()) {
                        reserva.setId(resultSet.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLastId() {
        int id = 0;
        try {
            PreparedStatement statement;
            statement = conn.prepareStatement("SELECT MAX(id) AS id FROM reservas");

            try (statement) {
                final ResultSet resultSet = statement.executeQuery();
                try (resultSet) {
                    while (resultSet.next()) {
                        id = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;

    }

    public int delete(int id) {
        try {
            final PreparedStatement statement = conn.prepareStatement("DELETE FROM reservas WHERE ID = ?");

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

    public int update(Date fechaEntrada, Date fechaSalida, int valor, String formaPago, int id) {
        try {
            final PreparedStatement statement = conn.prepareStatement(
                    "UPDATE reservas SET "
                            + " fecha_entrada = ?, "
                            + " fecha_salida = ?,"
                            + " valor = ?,"
                            + " forma_pago = ?"
                            + " WHERE id = ?");

            try (statement) {
                statement.setDate(1, fechaEntrada);
                statement.setDate(2, fechaSalida);
                statement.setInt(3, valor);
                statement.setString(4, formaPago);
                statement.setInt(5, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Reserva> getNumeroReserva(String id) {
        List<Reserva> resultado = new ArrayList<>();

        try {
            final PreparedStatement statement = conn
                    .prepareStatement(
                            "SELECT * FROM reservas where id = ?");

            try (statement) {
                statement.setInt(1, Integer.parseInt(id));
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try (resultSet) {
                    while (resultSet.next()) {
                        resultado.add(new Reserva(
                                resultSet.getInt("id"),
                                resultSet.getDate("fecha_entrada"),
                                resultSet.getDate("fecha_salida"),
                                resultSet.getInt("valor"),
                                resultSet.getString("forma_pago")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

}
