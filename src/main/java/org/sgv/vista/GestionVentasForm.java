package org.sgv.vista;

import org.sgv.baseDatos.ClienteDAO;
import org.sgv.baseDatos.VehiculoDAO;
import org.sgv.baseDatos.VentaDAO;
import org.sgv.modelo.Cliente;
import org.sgv.modelo.Vehiculo;
import org.sgv.modelo.Venta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class GestionVentasForm extends JFrame{
    private JLabel labelCliente;
    private JComboBox cmbClientes;
    private JLabel labelVehiculo;
    private JComboBox cmbVehiculos;
    private JLabel labelPrecio;
    private JTextField txtPrecio;
    private JButton btnRegistrarVenta;
    private JButton btnVolver;
    private JButton btnLimpiar;
    private JTable tablaVentas;
    private JPanel panelMain;

    private ClienteDAO clienteDAO = new ClienteDAO();
    private VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private VentaDAO ventaDAO = new VentaDAO();

    public GestionVentasForm() {
        setTitle("Gestión de Ventas");
        setContentPane(panelMain);
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        txtPrecio.setEditable(false);

        cargarClientes();
        cargarVehiculosDisponibles();
        cargarHistorialVentas();

        cmbVehiculos.addActionListener(e -> {
            Vehiculo seleccionado = (Vehiculo) cmbVehiculos.getSelectedItem();
            if (seleccionado != null) {
                txtPrecio.setText(String.valueOf(seleccionado.getPrecio()));
            }
        });

        btnRegistrarVenta.addActionListener(e -> {
            Cliente cliente = (Cliente) cmbClientes.getSelectedItem();
            Vehiculo vehiculo = (Vehiculo) cmbVehiculos.getSelectedItem();

            if (cliente == null || vehiculo == null) {
                JOptionPane.showMessageDialog(this, "❗ Debés seleccionar cliente y vehículo.");
                return;
            }

            Venta nueva = new Venta(cliente.getId(), vehiculo.getId(), vehiculo.getPrecio());

            boolean ventaOk = ventaDAO.registrarVenta(nueva);
            boolean estadoOk = vehiculoDAO.actualizarEstadoVehiculo(vehiculo.getId(), "Vendido");

            if (ventaOk && estadoOk) {
                JOptionPane.showMessageDialog(this, "✅ Venta registrada con éxito.");
                limpiarFormulario();
                cargarVehiculosDisponibles();
                cargarHistorialVentas();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al registrar la venta.");
            }
        });

        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnVolver.addActionListener(e -> dispose());
    }

    private void cargarClientes() {
        List<Cliente> lista = clienteDAO.obtenerTodosLosClientes();
        cmbClientes.removeAllItems();
        for (Cliente c : lista) {
            cmbClientes.addItem(c);
        }
    }

    private void cargarVehiculosDisponibles() {
        List<Vehiculo> lista = vehiculoDAO.obtenerVehiculosPorEstado("Disponible");
        cmbVehiculos.removeAllItems();
        for (Vehiculo v : lista) {
            cmbVehiculos.addItem(v);
        }
        txtPrecio.setText("");
    }

    private void cargarHistorialVentas() {
        List<Venta> lista = ventaDAO.obtenerHistorialVentas();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Cliente");
        modelo.addColumn("Vehículo");
        modelo.addColumn("Precio");
        modelo.addColumn("Fecha");

        for (Venta v : lista) {
            modelo.addRow(new Object[]{
                    v.getId(),
                    v.getNombreCliente(),
                    v.getDescripcionVehiculo(),
                    v.getPrecio(),
                    v.getFecha()
            });
        }

        tablaVentas.setModel(modelo);
    }

    private void limpiarFormulario() {
        cmbClientes.setSelectedIndex(-1);
        cmbVehiculos.setSelectedIndex(-1);
        txtPrecio.setText("");
        tablaVentas.clearSelection();
    }
}
