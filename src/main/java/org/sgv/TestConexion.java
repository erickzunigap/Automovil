package org.sgv;

import org.sgv.baseDatos.ConexionBD;

import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            if (conn != null) {
                System.out.println("✅ Conexión exitosa a MySQL.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
