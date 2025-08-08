package org.sgv.baseDatos;

import org.sgv.modelo.Vehiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    public boolean registrarVehiculo(Vehiculo v) {
        String sql = "INSERT INTO vehiculos (marca, modelo, anio, color, precio, estado, ruta_imagen) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, v.getMarca());
            stmt.setString(2, v.getModelo());
            stmt.setString(3, v.getAnio());
            stmt.setString(4, v.getColor());
            stmt.setDouble(5, v.getPrecio());
            stmt.setString(6, v.getEstado());
            stmt.setString(7, v.getRutaImagen());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al registrar vehículo: " + e.getMessage());
            return false;
        }
    }

    public List<Vehiculo> obtenerTodosLosVehiculos() {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vehiculo v = new Vehiculo(
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("anio"),
                        rs.getString("color"),
                        rs.getDouble("precio"),
                        rs.getString("estado"),
                        rs.getString("ruta_imagen")
                );
                v.setId(rs.getInt("id"));
                lista.add(v);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al obtener vehículos: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizarVehiculo(Vehiculo v) {
        String sql = "UPDATE vehiculos SET marca=?, modelo=?, anio=?, color=?, precio=?, estado=?, ruta_imagen=? WHERE id=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, v.getMarca());
            stmt.setString(2, v.getModelo());
            stmt.setString(3, v.getAnio());
            stmt.setString(4, v.getColor());
            stmt.setDouble(5, v.getPrecio());
            stmt.setString(6, v.getEstado());
            stmt.setString(7, v.getRutaImagen());
            stmt.setInt(8, v.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar vehículo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarVehiculo(int id) {
        String sql = "DELETE FROM vehiculos WHERE id=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar vehículo: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarEstadoVehiculo(int id, String nuevoEstado) {
        String sql = "UPDATE vehiculos SET estado = ? WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar estado del vehículo: " + e.getMessage());
            return false;
        }
    }

    public List<Vehiculo> obtenerVehiculosPorEstado(String estado) {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos WHERE estado = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vehiculo v = new Vehiculo(
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("anio"),
                        rs.getString("color"),
                        rs.getDouble("precio"),
                        rs.getString("estado"),
                        rs.getString("ruta_imagen")
                );
                v.setId(rs.getInt("id"));
                lista.add(v);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al filtrar vehículos: " + e.getMessage());
        }

        return lista;
    }


}

