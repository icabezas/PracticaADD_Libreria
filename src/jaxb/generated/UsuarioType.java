/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxb.generated;

import java.util.List;

/**
 *
 * @author THOR
 */
public class UsuarioType {
    
    private String username;
    private String nombre;
    private String password;
    private List<LibreriaType> colecciones;

    public UsuarioType() {
    }

    public UsuarioType(String username, String nombre, String password, List<LibreriaType> colecciones) {
        this.username = username;
        this.nombre = nombre;
        this.password = password;
        this.colecciones = colecciones;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<LibreriaType> getColecciones() {
        return colecciones;
    }

    public void setColecciones(List<LibreriaType> colecciones) {
        this.colecciones = colecciones;
    }
    
    
}
