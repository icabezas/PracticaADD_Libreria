/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author THOR
 */
public class Usuario {
    private int idUsuario;
    private String username;
    private String password;
    private boolean isAdmin;

    public Usuario(int idUsuario, String username, String password, boolean isAdmin) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Usuario(String username) {
        this.username = username;
    }

    public Usuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getId_usuario() {
        return idUsuario;
    }

    public void setId_usuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    
}
