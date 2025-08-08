package org.sgv.baseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/sgv_db";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "Gc123456";

    public static Connection obtenerConexion() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            throw e;
        }
    }
}
