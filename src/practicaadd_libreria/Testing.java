/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaadd_libreria;

import daos.ColeccionDAO;
import daos.DBDAO;
import daos.GeneroDAO;
import daos.LibroColeccionDAO;
import daos.LibroDAO;
import daos.LibroGeneroDAO;
import java.util.ArrayList;
import java.util.List;
import modelo.Coleccion;
import modelo.Genero;
import modelo.Libro;

/**
 *
 * @author THOR
 */
public class Testing extends DBDAO {

    public static void main(String[] args) {
        
        //LOS METODOS COMENTADOS FUNCIONAN TODOS

        LibroDAO libroDAO = new LibroDAO();
        GeneroDAO generoDAO = new GeneroDAO();
        LibroGeneroDAO libGenDAO = new LibroGeneroDAO();

        Libro libro = new Libro(123456789, "Ready Player One", "Ernest Cline", "EN", 2015, 1, 15.20);
        Libro newLibro = new Libro(123456789, "Ready Player One", "Irene Cabezas", "EN", 2015, 1, 15.20);

        Libro libro2 = new Libro(987654321, "Ready Player Two", "Ernest Cline", "EN", 2015, 1, 15.20);
        Libro libro3 = new Libro(147258369, "Ready Player Three", "Ernest Cline", "EN", 2015, 1, 15.20);

        generoDAO.crearGenero("Ciencia Ficcion");
        generoDAO.crearGenero("Policiaca");
        libroDAO.crearLibro(libro, new Genero(1, "Ciencia Ficcion"));
        libroDAO.crearLibro(libro2, new Genero(1, "Ciencia Ficcion"));
        libroDAO.crearLibro(libro3, new Genero(2, "Policiaca"));
        libroDAO.showAllLibros();
        generoDAO.showAllGeneros();
        libGenDAO.showAllLibroGenero();

        ArrayList<Libro> libros = new ArrayList<>();
        libros.add(libro2);
        libros.add(libro3);

        ColeccionDAO coleccionDAO = new ColeccionDAO();
        coleccionDAO.crearColeccion(1, "Mis favoritos", libros);

 //       libroDAO.borrarLibro(3);
//        generoDAO.deleteGenero("Ciencia Ficcion");
//        libroDAO.modificarLibro(libro, newLibro);
//        generoDAO.showAllGeneros();
//        libGenDAO.showAllLibroGenero();
        LibroColeccionDAO libColDAO = new LibroColeccionDAO();
//        libColDAO.crearLibroColeccion(1, 1);
//        libColDAO.borrarLibroColeccion(1, 1);
        libColDAO.showAllLibroColeccion();
        
        List<Libro> librosDadaColeccion = libColDAO.getLibrosDadaColeccion(1);
        System.out.println("Coleccion: 1");
        for(Libro lib : librosDadaColeccion){
            System.out.println("ID: " + lib.getIdLibro());
            System.out.println("NOMBRE: " + lib.getTitulo());
        }
        
        System.out.println("Lista de libros por genero: 2");
        List<Libro> librosDadoGenero = libroDAO.getAllLibrosPorIdGenero(2);
        for(Libro libroGenero : librosDadoGenero){
            System.out.println("Titulo Libro: " + libroGenero.getTitulo());
        }

//        dbdao.cerrarConexion();
    }
}
