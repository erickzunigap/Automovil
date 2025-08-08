package org.sgv.baseDatos;

import org.sgv.modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // REGISTRAR USUARIO
    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, telefono, contraseña, rol) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getContraseña());
            stmt.setString(5, usuario.getRol());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    // OBTENER TODOS LOS USUARIOS
    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("contraseña"),
                        rs.getString("rol")
                );
                u.setId(rs.getInt("id"));
                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al obtener usuarios: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre=?, correo=?, telefono=?, contraseña=?, rol=? WHERE id=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getContraseña());
            stmt.setString(5, usuario.getRol());
            stmt.setInt(6, usuario.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    public Usuario autenticarUsuario(String correo, String contraseña) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, contraseña);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("contraseña"),
                        rs.getString("rol")
                );
                usuario.setId(rs.getInt("id"));
                return usuario;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error en autenticación: " + e.getMessage());
        }

        return null; // Usuario no encontrado
    }


}
