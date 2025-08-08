package org.sgv.modelo;

public class Cliente extends Persona {
    private int id;
    private String apellido;
    private String direccion;

    public Cliente(String nombre, String apellido, String correo, String telefono, String direccion) {
        super(nombre, correo, telefono);
        this.apellido = apellido;
        this.direccion = direccion;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Cliente: " + nombre + " " + apellido + ", Dirección: " + direccion);
    }

    @Override
    public boolean validarDatos() {
        return correo != null && !correo.isBlank() && direccion != null && !direccion.isBlank();
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + correo + ")";
    }

}

