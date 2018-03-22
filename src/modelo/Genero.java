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
public class Genero {

    private int idGenero;
    private String nombre;

    public Genero(int idGenero, String nombre) {
        this.idGenero = idGenero;
        this.nombre = nombre;
    }

    public Genero(String nombre) {
        this.nombre = nombre;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
