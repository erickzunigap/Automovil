package org.sgv.modelo;

public class Vehiculo {
    private int id;
    private String marca;
    private String modelo;
    private String anio;
    private String color;
    private double precio;
    private String estado;
    private String rutaImagen;

    public Vehiculo(String marca, String modelo, String anio, String color, double precio, String estado, String rutaImagen) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.precio = precio;
        this.estado = estado;
        this.rutaImagen = rutaImagen;
    }

    // Getters y setters

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getMarca() { return marca; }

    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }

    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getAnio() { return anio; }

    public void setAnio(String anio) { this.anio = anio; }

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }

    public String getRutaImagen() { return rutaImagen; }

    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    @Override
    public String toString() {
        return marca + " " + modelo + " - " + anio;
    }

}

