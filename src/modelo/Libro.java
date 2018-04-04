/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import daos.LibroGeneroDAO;
import exceptiones.LibreriaExcepciones;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THOR
 */
public class Libro {

    private int idLibro;
    private int isbn;
    private String titulo;
    private String autor;
    private String idioma;
    private int anyo;
    private int edicion;
    private double precio;

    public Libro(int idLibro) {
        this.idLibro = idLibro;
    }

    public Libro(int isbn, String titulo, String autor, String idioma, int anyo, int edicion, double precio) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.anyo = anyo;
        this.edicion = edicion;
        this.precio = precio;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public int getEdicion() {
        return edicion;
    }

    public void setEdicion(int edicion) {
        this.edicion = edicion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    private Genero getGenero(int idLibro) throws LibreriaExcepciones {
        LibroGeneroDAO libroGeneroDAO = new LibroGeneroDAO();
        return libroGeneroDAO.getLibroGenero(idLibro);
    }

    @Override
    public String toString() {
        Genero genero = null;
        String nombreGenero = "";
        try {
            genero = getGenero(this.idLibro);
            if(genero != null){
                nombreGenero = genero.getNombre();
            }
        } catch (LibreriaExcepciones ex) {
            System.out.println(ex.getMessage());
        }
        return "ID: " + this.idLibro + "\n Titulo: " + this.titulo + "\n Autor: " + this.autor + "\n Genero: " + nombreGenero;
    }

}
