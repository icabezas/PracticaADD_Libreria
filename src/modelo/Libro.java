/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import daos.LibroGeneroDAO;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author THOR
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "libroType", propOrder = {
        "titulo",
        "autor",
        "genero",
        "idioma",
        "anyo",
        "edicion",
        "precio",
        "idLibro"
})


public class Libro {

    @XmlElement(required = true)
    private String titulo;
    private String autor;
    private String genero;
    private String idioma;
    private int anyo;
    private int edicion;
    private double precio;
    @XmlAttribute(name = "ISBN", required = true)
    private int isbn;

    private int idLibro;

    public Libro() {
    }

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

    public Libro(int isbn, String titulo, String autor, String idioma, int anyo, int edicion, double precio, String genero) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.anyo = anyo;
        this.edicion = edicion;
        this.precio = precio;
        this.genero = genero;
    }

    public Libro(String titulo) { this.titulo = titulo; }

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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Genero getGenero(int idLibro) {
        LibroGeneroDAO libroGeneroDAO = new LibroGeneroDAO();
        return libroGeneroDAO.getLibroGenero(idLibro);
    }

    @Override
    public String toString() {
        return "ID: " + this.idLibro + "\n Titulo: " + this.titulo + "\n Autor: " + this.autor + "\n Genero: " + getGenero(this.idLibro).getNombre();
    }

}
