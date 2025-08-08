package org.sgv.vista;

import org.sgv.modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalForm extends JFrame {
    private JLabel lblBienvenida;
    private JPanel panelMain;
    private JButton btnUsuarios;
    private JButton btnClientes;
    private JButton btnVehiculos;
    private JButton btnVentas;
    private JButton btnCerrarSesion;
    private JLabel LblCliente;
    private JLabel LblAuto;
    private JLabel LblUsuario;
    private JLabel LblVentas;

    private Usuario usuarioActual;

    public MenuPrincipalForm(Usuario usuario) {
        this.usuarioActual = usuario;

        setTitle("Menú Principal - SGV");
        setContentPane(panelMain);

        //Iconos
        ImageIcon IconoUsuario = new ImageIcon(getClass().getResource("/icons/Usuario.png"));
        LblUsuario.setIcon(IconoUsuario);

        ImageIcon IconoCliente = new ImageIcon(getClass().getResource("/icons/Cliente.png"));
        LblCliente.setIcon(IconoCliente);

        ImageIcon IconoAuto = new ImageIcon(getClass().getResource("/icons/Auto.png"));
        LblAuto.setIcon(IconoAuto);

        ImageIcon IconoVentas = new ImageIcon(getClass().getResource("/icons/Money.png"));
        LblVentas.setIcon(IconoVentas);


        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        lblBienvenida.setText("Bienvenido, " + usuario.getNombre() + " (" + usuario.getRol() + ")");

        // Mostrar opciones según el rol
        if (!usuario.getRol().equalsIgnoreCase("Administrador")) {
            btnUsuarios.setVisible(false);
        }

        // BOTONES FUNCIONALES
        btnUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionUsuariosForm(); // ← ¡Este sí abre la ventana!
            }
        });

        btnClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { new GestionClientesForm();
            }
        });

        btnVehiculos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { new GestionVehiculosForm();
            }
        });

        btnVentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { new GestionVentasForm();
            }
        });

        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra menú principal
                new LoginForm(); // Vuelve al login
            }
        });
    }


}
