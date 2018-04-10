/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import daos.ColeccionDAO;
import daos.DBDAO;
import daos.GeneroDAO;
import daos.LibroColeccionDAO;
import daos.LibroDAO;
import daos.LibroGeneroDAO;
import daos.UsuarioDAO;
import exceptiones.LibreriaExcepciones;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Coleccion;
import modelo.Genero;
import modelo.Libro;
import modelo.Usuario;

/**
 *
 * @author THOR
 */
public class Test extends DBDAO {

    public static void main(String[] args) {

        //LOS METODOS COMENTADOS FUNCIONAN TODOS
        LibroDAO libroDAO = new LibroDAO();
        GeneroDAO generoDAO = new GeneroDAO();
        LibroGeneroDAO libGenDAO = new LibroGeneroDAO();
        LibroColeccionDAO libColDAO = new LibroColeccionDAO();

        Libro libro = new Libro(123456789, "Ready Player One", "Ernest Cline", "EN", 2015, 1, 15.20);
        Libro newLibro = new Libro(123456789, "Ready Player One", "Irene Cabezas", "EN", 2015, 1, 15.20);

        Libro libro2 = new Libro(987654321, "Ready Player Two", "Ernest Cline", "EN", 2015, 1, 15.20);
        Libro libro3 = new Libro(147258369, "Ready Player Three", "Ernest Cline", "EN", 2015, 1, 15.20);

        System.out.println("<---------------- TESTING FUNCIONES DE USUARIO ----------------->");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioTest = new Usuario("pepe");
        try {
            usuarioDAO.crearUsuario("Pepe", "avestruz");
        } catch (LibreriaExcepciones ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("*** LIST ALL USERS ***");
        try {
            usuarioDAO.showAllUsuariosDB();
        } catch (LibreriaExcepciones ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("*** DELETE USER ***");

        //usuarioDAO.borrarUsuario("Pepe");
        //usuarioDAO.showAllUsuariosDB();
        System.out.println("*** LOGIN USER WRONG ***");
        try {
            if (usuarioDAO.loginUsuario("Pepe", "123456") != null) {
                System.out.println("Login correcto");
            } else {
                System.out.println("Login maaaaal");
            }
        } catch (LibreriaExcepciones ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("*** GIVE USER ADMIN PRIVILEGES ***");
        try {
            usuarioDAO.changeUserPrivileges("Pepe", true);
            usuarioDAO.showAllUsuariosDB();
        } catch (LibreriaExcepciones ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("*** MODIFY PASSWORD ***");
        try {
            Usuario usuarioOld = usuarioDAO.existeUsuario("Pepe");
            Usuario usuarioNew = usuarioOld;
            usuarioNew.setIsAdmin(false);
            usuarioNew.setPassword("123456");
            usuarioDAO.modificarPasswordUsuario(usuarioOld, usuarioNew);
            usuarioDAO.showAllUsuariosDB();
        } catch (LibreriaExcepciones ex) {
            System.out.println(ex.getMessage());
        }
        
        
        System.out.println("<---------------- TESTING FUNCIONES DE GENERO ----------------->");
        try {
            generoDAO.crearGenero("Ciencia Ficcion");
            generoDAO.crearGenero("Policiaca");
        } catch (LibreriaExcepciones ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("Show all generos: ");
        try {
            generoDAO.showAllGeneros();
        } catch (LibreriaExcepciones ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("<---------------- TESTING FUNCIONES DE LIBRO ----------------->");
        System.out.println("*** CREAR LIBRO ***");
        String genero1 = "Ciencia Ficcion";
        String genero2 = "Policiaca";
        String genero3 = "Aventuras";
        ArrayList<String> generos = new ArrayList<>();
        generos.add(genero1);
        generos.add(genero2);
        generos.add(genero3);
        libroDAO.crearLibro(libro, generos);
        libroDAO.crearLibro(libro2, generos);
        libroDAO.crearLibro(libro3, generos);
        libroDAO.showAllLibros();

        libGenDAO.showAllLibroGenero();

        //       libroDAO.borrarLibro(3);
//        generoDAO.deleteGenero("Ciencia Ficcion");
//        libroDAO.modificarLibro(libro, newLibro);
        

        System.out.println("<---------------- TESTING FUNCIONES DE COLECCION ----------------->");

        ArrayList<Libro> libros = new ArrayList<>();
        libros.add(libro2);
        libros.add(libro3);

        ColeccionDAO coleccionDAO = new ColeccionDAO();
        try {
            coleccionDAO.crearColeccion(1, "Segunda coleccion", libros);
        } catch (LibreriaExcepciones ex) {
            System.out.println(ex.getMessage());
        }
        try {
            //TO-DO REVISAR AQUI
            ArrayList<Coleccion> coleccionesUsuario = coleccionDAO.getAllColeccionesUsuario(1);
            for (Coleccion col : coleccionesUsuario) {
                System.out.println("ID Usuario" + col.getIdUsuario() + "\nNombre Coleccion:" + col.getNombre());
            }
            libColDAO.showAllLibroColeccion();
            System.out.println("TODAS LAS COLECCIONES");
            coleccionDAO.showAllColeccionesDB();
        } catch (LibreriaExcepciones ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("Lista de libros por genero: 2");
        List<Libro> librosDadoGenero = libroDAO.getAllLibrosPorIdGenero(2);
        for (Libro libroGenero : librosDadoGenero) {
            System.out.println("Titulo Libro: " + libroGenero.getTitulo());
        }

//        dbdao.cerrarConexion();
    }
}
