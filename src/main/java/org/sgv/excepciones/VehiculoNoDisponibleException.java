package org.sgv.excepciones;

public class VehiculoNoDisponibleException extends Exception {
    public VehiculoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}