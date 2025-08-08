package org.sgv.vista;

import org.sgv.baseDatos.UsuarioDAO;
import org.sgv.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class GestionUsuariosForm extends JFrame{
    private JPanel panelMain;
    private JLabel Nombre;
    private JTextField txtNombre;
    private JLabel Correo;
    private JTextField txtCorreo;
    private JLabel Teléfono;
    private JTextField txtTelefono;
    private JLabel Contraseña;
    private JPasswordField txtContraseña;
    private JLabel Rol;
    private JComboBox <String> cmbRol;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnVolver;
    private JTable tablaUsuarios;

    private UsuarioDAO dao = new UsuarioDAO();
    private int idSeleccionado = -1; // ID del usuario seleccionado

    public GestionUsuariosForm() {
        setTitle("Gestión de Usuarios");
        setContentPane(panelMain);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        cmbRol.addItem("Administrador");
        cmbRol.addItem("Vendedor");

        cargarUsuariosEnTabla();

        btnAgregar.addActionListener(e -> {
            if (camposValidos()) {
                Usuario nuevo = new Usuario(
                        txtNombre.getText(),
                        txtCorreo.getText(),
                        txtTelefono.getText(),
                        txtContraseña.getText(),
                        (String) cmbRol.getSelectedItem()
                );

                boolean exito = dao.registrarUsuario(nuevo);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "✅ Usuario agregado correctamente.");
                    limpiarCampos();
                    cargarUsuariosEnTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error: el correo ya está registrado.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "❗ Todos los campos son obligatorios.");
            }
        });

        btnEditar.addActionListener(e -> {
            if (idSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "❗ Seleccioná un usuario de la tabla.");
                return;
            }

            if (camposValidos()) {
                Usuario actualizado = new Usuario(
                        txtNombre.getText(),
                        txtCorreo.getText(),
                        txtTelefono.getText(),
                        txtContraseña.getText(),
                        (String) cmbRol.getSelectedItem()
                );
                actualizado.setId(idSeleccionado);

                boolean exito = dao.actualizarUsuario(actualizado);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "✅ Usuario actualizado correctamente.");
                    limpiarCampos();
                    cargarUsuariosEnTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al actualizar el usuario.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "❗ Todos los campos son obligatorios.");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (idSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "❗ Seleccioná un usuario de la tabla.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean exito = dao.eliminarUsuario(idSeleccionado);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "✅ Usuario eliminado correctamente.");
                    limpiarCampos();
                    cargarUsuariosEnTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al eliminar el usuario.");
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());

        btnVolver.addActionListener(e -> dispose());

        tablaUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaUsuarios.getSelectedRow();
                if (fila != -1) {
                    idSeleccionado = (int) tablaUsuarios.getValueAt(fila, 0);
                    txtNombre.setText(tablaUsuarios.getValueAt(fila, 1).toString());
                    txtCorreo.setText(tablaUsuarios.getValueAt(fila, 2).toString());
                    txtTelefono.setText(tablaUsuarios.getValueAt(fila, 3).toString());
                    cmbRol.setSelectedItem(tablaUsuarios.getValueAt(fila, 4).toString());
                    txtContraseña.setText("••••••"); // No mostrar la real por seguridad
                }
            }
        });
    }

    private void cargarUsuariosEnTabla() {
        List<Usuario> lista = dao.obtenerTodosLosUsuarios();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Correo");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Rol");

        for (Usuario u : lista) {
            modelo.addRow(new Object[]{
                    u.getId(),
                    u.getNombre(),
                    u.getCorreo(),
                    u.getTelefono(),
                    u.getRol()
            });
        }

        tablaUsuarios.setModel(modelo);
    }

    private boolean camposValidos() {
        return !txtNombre.getText().isBlank() &&
                !txtCorreo.getText().isBlank() &&
                !txtTelefono.getText().isBlank() &&
                !txtContraseña.getText().isBlank();
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtContraseña.setText("");
        cmbRol.setSelectedIndex(0);
        idSeleccionado = -1;
        tablaUsuarios.clearSelection();
    }


}
