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
public class LibroGenero {
    private int idLibro;
    private int idGenero;

    public LibroGenero(int idLibro) {
        this.idLibro = idLibro;
    }

    public LibroGenero(int idLibro, int idGenero) {
        this.idLibro = idLibro;
        this.idGenero = idGenero;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }
}
