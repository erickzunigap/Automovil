package org.sgv.modelo.interfaces;

public interface Autenticable {
    boolean autenticar(String correo, String contraseña);
}
