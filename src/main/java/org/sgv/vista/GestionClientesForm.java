package org.sgv.vista;


import org.sgv.baseDatos.ClienteDAO;
import org.sgv.modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class GestionClientesForm extends JFrame {
    private JLabel Nombre;
    private JTextField txtNombre;
    private JLabel Apellido;
    private JTextField txtApellido;
    private JLabel Correo;
    private JTextField txtCorreo;
    private JLabel Teléfono;
    private JTextField txtTelefono;
    private JLabel Dirección;
    private JTextField txtDireccion;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnVolver;
    private JTable tablaClientes;
    private JPanel panelMain;


    private ClienteDAO dao = new ClienteDAO();
    private int idSeleccionado = -1;

    public GestionClientesForm() {
        setTitle("Gestión de Clientes");
        setContentPane(panelMain);
        setSize(800, 500);
        setLocationRelativeTo(null); // Centrado
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        cargarClientesEnTabla();

        btnAgregar.addActionListener(e -> {
            if (camposValidos()) {
                Cliente nuevo = new Cliente(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtCorreo.getText(),
                        txtTelefono.getText(),
                        txtDireccion.getText()
                );


                boolean exito = dao.registrarCliente(nuevo);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "✅ Cliente agregado.");
                    limpiarCampos();
                    cargarClientesEnTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error: el correo ya está registrado.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "❗ Todos los campos son obligatorios.");
            }
        });

        btnEditar.addActionListener(e -> {
            if (idSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "❗ Seleccioná un cliente de la tabla.");
                return;
            }

            if (camposValidos()) {
                Cliente actualizado = new Cliente(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtCorreo.getText(),
                        txtTelefono.getText(),
                        txtDireccion.getText()
                );
                actualizado.setId(idSeleccionado);

                boolean exito = dao.actualizarCliente(actualizado);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "✅ Cliente actualizado.");
                    limpiarCampos();
                    cargarClientesEnTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al actualizar.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "❗ Todos los campos son obligatorios.");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (idSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "❗ Seleccioná un cliente de la tabla.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean exito = dao.eliminarCliente(idSeleccionado);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "✅ Cliente eliminado.");
                    limpiarCampos();
                    cargarClientesEnTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al eliminar.");
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());

        btnVolver.addActionListener(e -> dispose());

        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaClientes.getSelectedRow();
                if (fila != -1) {
                    idSeleccionado = (int) tablaClientes.getValueAt(fila, 0);
                    txtNombre.setText(tablaClientes.getValueAt(fila, 1).toString());
                    txtApellido.setText(tablaClientes.getValueAt(fila, 2).toString());
                    txtCorreo.setText(tablaClientes.getValueAt(fila, 3).toString());
                    txtTelefono.setText(tablaClientes.getValueAt(fila, 4).toString());
                    txtDireccion.setText(tablaClientes.getValueAt(fila, 5).toString());
                }
            }
        });
    }

    private void cargarClientesEnTabla() {
        List<Cliente> lista = dao.obtenerTodosLosClientes();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Correo");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Dirección");

        for (Cliente c : lista) {
            modelo.addRow(new Object[]{
                    c.getId(),
                    c.getNombre(),
                    c.getApellido(),
                    c.getCorreo(),
                    c.getTelefono(),
                    c.getDireccion()
            });
        }

        tablaClientes.setModel(modelo);
    }

    private boolean camposValidos() {
        return !txtNombre.getText().isBlank() &&
                !txtApellido.getText().isBlank() &&
                !txtCorreo.getText().isBlank() &&
                !txtTelefono.getText().isBlank() &&
                !txtDireccion.getText().isBlank();
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        idSeleccionado = -1;
        tablaClientes.clearSelection();
    }
}

