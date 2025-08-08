package org.sgv.vista;

import org.sgv.baseDatos.VehiculoDAO;
import org.sgv.modelo.Vehiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class GestionVehiculosForm extends JFrame {
    private JLabel labelMarca;
    private JTextField txtMarca;
    private JLabel labelModelo;
    private JTextField txtModelo;
    private JLabel labelAnio;
    private JTextField txtAnio;
    private JLabel labelColor;
    private JTextField txtColor;
    private JLabel labelPrecio;
    private JTextField txtPrecio;
    private JLabel labelEstado;
    private JComboBox cmbEstado;
    private JLabel labelImagen;
    private JButton btnSeleccionarImagen;
    private JLabel lblPreviewImagen;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnVolver;
    private JButton btnLimpiar;
    private JTable tablaVehiculos;
    private JPanel panelMain;

    private VehiculoDAO dao = new VehiculoDAO();
    private int idSeleccionado = -1;
    private String rutaImagenSeleccionada = "";

    public GestionVehiculosForm() {
        setTitle("Gestión de Vehículos");
        setContentPane(panelMain);
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        cmbEstado.addItem("Disponible");
        cmbEstado.addItem("Vendido");
        cmbEstado.addItem("Reservado");

        cargarVehiculosEnTabla();

        btnSeleccionarImagen.addActionListener(e -> seleccionarImagen());

        btnAgregar.addActionListener(e -> {
            if (camposValidos()) {
                Vehiculo nuevo = new Vehiculo(
                        txtMarca.getText(),
                        txtModelo.getText(),
                        txtAnio.getText(),
                        txtColor.getText(),
                        Double.parseDouble(txtPrecio.getText()),
                        (String) cmbEstado.getSelectedItem(),
                        rutaImagenSeleccionada
                );

                boolean exito = dao.registrarVehiculo(nuevo);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "✅ Vehículo agregado.");
                    limpiarCampos();
                    cargarVehiculosEnTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al registrar vehículo.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "❗ Todos los campos son obligatorios.");
            }
        });

        btnEditar.addActionListener(e -> {
            if (idSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "❗ Seleccioná un vehículo.");
                return;
            }

            if (camposValidos()) {
                Vehiculo actualizado = new Vehiculo(
                        txtMarca.getText(),
                        txtModelo.getText(),
                        txtAnio.getText(),
                        txtColor.getText(),
                        Double.parseDouble(txtPrecio.getText()),
                        (String) cmbEstado.getSelectedItem(),
                        rutaImagenSeleccionada
                );
                actualizado.setId(idSeleccionado);

                boolean exito = dao.actualizarVehiculo(actualizado);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "✅ Vehículo actualizado.");
                    limpiarCampos();
                    cargarVehiculosEnTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al actualizar.");
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            if (idSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "❗ Seleccioná un vehículo.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este vehículo?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean exito = dao.eliminarVehiculo(idSeleccionado);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "✅ Vehículo eliminado.");
                    limpiarCampos();
                    cargarVehiculosEnTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al eliminar.");
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());

        btnVolver.addActionListener(e -> dispose());

        tablaVehiculos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaVehiculos.getSelectedRow();
                if (fila != -1) {
                    idSeleccionado = (int) tablaVehiculos.getValueAt(fila, 0);
                    txtMarca.setText(tablaVehiculos.getValueAt(fila, 1).toString());
                    txtModelo.setText(tablaVehiculos.getValueAt(fila, 2).toString());
                    txtAnio.setText(tablaVehiculos.getValueAt(fila, 3).toString());
                    txtColor.setText(tablaVehiculos.getValueAt(fila, 4).toString());
                    txtPrecio.setText(tablaVehiculos.getValueAt(fila, 5).toString());
                    cmbEstado.setSelectedItem(tablaVehiculos.getValueAt(fila, 6).toString());
                    rutaImagenSeleccionada = tablaVehiculos.getValueAt(fila, 7).toString();
                    mostrarImagen(rutaImagenSeleccionada);
                }
            }
        });
    }

    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            rutaImagenSeleccionada = archivo.getAbsolutePath();
            mostrarImagen(rutaImagenSeleccionada);
        }
    }

    private void mostrarImagen(String ruta) {
        ImageIcon icono = new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(150, 100, java.awt.Image.SCALE_SMOOTH));
        lblPreviewImagen.setIcon(icono);
    }

    private void cargarVehiculosEnTabla() {
        List<Vehiculo> lista = dao.obtenerTodosLosVehiculos();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Año");
        modelo.addColumn("Color");
        modelo.addColumn("Precio");
        modelo.addColumn("Estado");
        modelo.addColumn("Imagen");

        for (Vehiculo v : lista) {
            modelo.addRow(new Object[]{
                    v.getId(),
                    v.getMarca(),
                    v.getModelo(),
                    v.getAnio(),
                    v.getColor(),
                    v.getPrecio(),
                    v.getEstado(),
                    v.getRutaImagen()
            });
        }

        tablaVehiculos.setModel(modelo);
    }

    private boolean camposValidos() {
        return !txtMarca.getText().isBlank() &&
                !txtModelo.getText().isBlank() &&
                !txtAnio.getText().isBlank() &&
                !txtColor.getText().isBlank() &&
                !txtPrecio.getText().isBlank() &&
                !rutaImagenSeleccionada.isBlank();
    }

    private void limpiarCampos() {
        txtMarca.setText("");
        txtModelo.setText("");
        txtAnio.setText("");
        txtColor.setText("");
        txtPrecio.setText("");
        cmbEstado.setSelectedIndex(0);
        rutaImagenSeleccionada = "";
        lblPreviewImagen.setIcon(null);
        idSeleccionado = -1;
        tablaVehiculos.clearSelection();
    }


}
