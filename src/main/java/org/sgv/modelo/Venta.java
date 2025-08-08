package org.sgv.modelo;

public class Venta {
    private int id;
    private int idCliente;
    private int idVehiculo;
    private double precio;
    private String fecha;
    private String nombreCliente;       // Solo para mostrar en historial
    private String descripcionVehiculo; // Solo para mostrar en historial

    public Venta(int idCliente, int idVehiculo, double precio) {
        this.idCliente = idCliente;
        this.idVehiculo = idVehiculo;
        this.precio = precio;
    }

    // Getters y Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getDescripcionVehiculo() { return descripcionVehiculo; }
    public void setDescripcionVehiculo(String descripcionVehiculo) { this.descripcionVehiculo = descripcionVehiculo; }
}
