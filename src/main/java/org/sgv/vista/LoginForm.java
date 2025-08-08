package org.sgv.vista;

import org.sgv.baseDatos.UsuarioDAO;
import org.sgv.excepciones.UsuarioNoEncontradoException;
import org.sgv.modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame{
    private JPanel panelMain;
    private JLabel Correo;
    private JTextField txtCorreo;
    private JLabel Contraseña;
    private JPasswordField txtContraseña;
    private JButton btnLogin;
    private JLabel lblMensaje;
    private JLabel LblIconLogin;
    private JLabel LblManos;

    private UsuarioDAO dao = new UsuarioDAO(); // ← AQUÍ estaba el error que no puse antes

    public LoginForm() {
        setTitle("Inicio de Sesión");


        ImageIcon IconoLogin = new ImageIcon(getClass().getResource("/icons/LoginIcon2.png"));
        LblIconLogin.setIcon(IconoLogin);

        ImageIcon IconoManos = new ImageIcon(getClass().getResource("/icons/Manos2.png"));
        LblManos.setIcon(IconoManos);

        setContentPane(panelMain);
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correo = txtCorreo.getText();
                String contraseña = new String(txtContraseña.getPassword());

                Usuario usuario = dao.autenticarUsuario(correo, contraseña);
                if (usuario != null) {
                    JOptionPane.showMessageDialog(null, "✅ Bienvenido " + usuario.getNombre());
                    dispose(); // Cierra login
                    new MenuPrincipalForm(usuario); // Abre menú principal
                } else {
                    lblMensaje.setText("❌ Credenciales no válidas.");
                }
            }
        });
    }



}
