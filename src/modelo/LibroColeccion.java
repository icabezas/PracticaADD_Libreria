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
public class LibroColeccion {
    private int idLibro;
    private int idColeccion;

    public LibroColeccion(int idLibro, int idColeccion) {
        this.idLibro = idLibro;
        this.idColeccion = idColeccion;
    }

    public LibroColeccion(int idColeccion) {
        this.idColeccion = idColeccion;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public int getIdColeccion() {
        return idColeccion;
    }

    public void setIdColeccion(int idColeccion) {
        this.idColeccion = idColeccion;
    }
}
