package org.sgv.baseDatos;

import org.sgv.modelo.Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    public boolean registrarVenta(Venta venta) {
        String sql = "INSERT INTO ventas (id_cliente, id_vehiculo, precio, fecha) VALUES (?, ?, ?, NOW())";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, venta.getIdCliente());
            stmt.setInt(2, venta.getIdVehiculo());
            stmt.setDouble(3, venta.getPrecio());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al registrar venta: " + e.getMessage());
            return false;
        }
    }

    public List<Venta> obtenerHistorialVentas() {
        List<Venta> lista = new ArrayList<>();
        String sql = """
            SELECT v.id, c.nombre, c.apellido, ve.marca, ve.modelo, v.precio, v.fecha
            FROM ventas v
            INNER JOIN clientes c ON v.id_cliente = c.id
            INNER JOIN vehiculos ve ON v.id_vehiculo = ve.id
            ORDER BY v.fecha DESC
        """;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Venta venta = new Venta(0, 0, rs.getDouble("precio"));
                venta.setId(rs.getInt("id"));
                venta.setNombreCliente(rs.getString("nombre") + " " + rs.getString("apellido"));
                venta.setDescripcionVehiculo(rs.getString("marca") + " " + rs.getString("modelo"));
                venta.setFecha(rs.getString("fecha"));
                lista.add(venta);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al obtener historial de ventas: " + e.getMessage());
        }

        return lista;
    }
}

