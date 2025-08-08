package org.sgv.modelo;

import org.sgv.modelo.interfaces.Autenticable;

public class Usuario extends Persona implements Autenticable {
    private int id; // ID en base de datos
    private String contraseña;
    private String rol;

    public Usuario(String nombre, String correo, String telefono, String contraseña, String rol) {
        super(nombre, correo, telefono);
        this.contraseña = contraseña;
        this.rol = rol;
    }

    @Override
    public boolean autenticar(String correo, String contraseña) {
        return this.correo.equals(correo) && this.contraseña.equals(contraseña);
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Usuario: " + nombre + ", Rol: " + rol);
    }

    @Override
    public boolean validarDatos() {
        return correo != null && contraseña != null && !correo.isBlank() && !contraseña.isBlank();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
