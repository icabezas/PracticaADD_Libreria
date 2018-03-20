/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxb;

//import java.util.List;

import java.util.List;



/**
 *
 * @author THOR
 */
public class UsuarioType {
    
    public String username;
    public String nombre;
    public String password;
    public List<LibreriaType> colecciones;
    public boolean isAdmin;

    public UsuarioType() {
    }
    public UsuarioType(String username) {
        this.username = username;
    }

    public UsuarioType(String username, String nombre, String password, boolean isAdmin, List<LibreriaType> colecciones) {
        this.username = username;
        this.nombre = nombre;
        this.password = password;
        this.isAdmin = isAdmin;
        this.colecciones = colecciones;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
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
