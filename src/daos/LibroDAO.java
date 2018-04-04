/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Genero;
import modelo.Libro;
import modelo.LibroGenero;

/**
 * DAO PARA LA ADMINISTRACION DE LIBROS
 *
 */
public class LibroDAO {

    public ObjectContainer db;

    public LibroDAO() {

    }
    //TODAS LAS FUNCIONES ABREN CONEXION Y CIERRAN DENTRO DE ELLAS MISMAS

    //CREAR LIBRO
    public void crearLibro(Libro libro, Genero genero) {
        //ABRIMOS CONEXION
//        abrirConexion();
        if (existeLibroPorISBN(libro.getIsbn()) == null) {
            libro.setIdLibro(getIdLibroLast());
            LibroGeneroDAO libroGeneroRelacion = new LibroGeneroDAO();
            try {
//                cerrarConexion();
//TO-DO HACER GENERO A PARTIR DEL NOMBRE
                libroGeneroRelacion.crearLibroGenero(getIdLibroLast(), genero.getIdGenero());
                abrirConexion();
                db.store(libro);
            } catch (Exception ex) {
                System.out.println("No se ha podido guardar el objeto Libro");
            }
        } else {
            System.out.println("Ya existe este libro");
        }

        //CERRAMOS CONEXION
        cerrarConexion();
    }

    //BORRAR LIBRO
    //TO-DO: BORRAR LIBROCOLECCION Y LIBROGENERO
    public void borrarLibro(int idLibro) {
        abrirConexion();
        ObjectSet result = db.queryByExample(new Libro(idLibro));
        if (!result.isEmpty()) {
            Libro found = (Libro) result.next();
            try {
                db.delete(found);
                LibroColeccionDAO libroColeccionDAO = new LibroColeccionDAO();
                LibroGeneroDAO libroGeneroDAO = new LibroGeneroDAO();
                cerrarConexion();
                //COMPROBAR SI FUNCIONA
                libroColeccionDAO.borrarLibroColeccion(idLibro, true);
                libroGeneroDAO.borrarLibroGenero(idLibro, true);

                System.out.println("Deleted " + found.toString());
            } catch (Exception ex) {
                System.out.println("Problema al borrar libro " + idLibro);
            }
        } else {
            System.out.println("No se ha encontrado el libro " + idLibro);
        }
    }

    //MODIFICAR DATOS LIBRO
    public void modificarLibro(Libro libroOld, Libro libroNew) {
        abrirConexion();
        if (libroOld.getIdLibro() == libroNew.getIdLibro()) {
            ObjectSet result = db.queryByExample(new Libro(libroOld.getIdLibro()));
            if (!result.isEmpty()) {
                Libro newLibro = (Libro) result.next();
                newLibro.setAnyo(libroNew.getAnyo());
                newLibro.setAutor(libroNew.getAutor());
                newLibro.setEdicion(libroNew.getEdicion());
                newLibro.setIdioma(libroNew.getIdioma());
                newLibro.setPrecio(libroNew.getPrecio());
                newLibro.setTitulo(libroNew.getTitulo());
                try {
                    db.store(newLibro);
                    System.out.println("Actualizado");
                } catch (Exception ex) {
                    System.out.println("No se ha podido realizar el update");
                }
            }
        } else {
            System.out.println("El isbn no puede modificarse");
        }

        //CERRAMOS CONEXION
        cerrarConexion();
    }

    //DEVUELVE LISTA DE TODOS LOS LIBROS EN BBDD
    public ArrayList<Libro> getListaLibrosBBDD() {
        abrirConexion();
        ArrayList<Libro> librosBBDD = new ArrayList<>();
        ObjectSet resultado = db.query(Libro.class);
        if (!resultado.isEmpty()) {
            for (int i = 0; i < resultado.size(); i++) {
                Libro libro = (Libro) resultado.next();
                librosBBDD.add(libro);
            }
        } else {
            librosBBDD = null;
        }
        cerrarConexion();
        return librosBBDD;
    }

    //RETORNA UNA LISTA DE LIBROS DADO UN IDGENERO
    public List<Libro> getAllLibrosPorIdGenero(int idGenero) {
        List<Libro> librosPorGenero = new ArrayList<>();
        LibroGeneroDAO libGenDAO = new LibroGeneroDAO();
        List<LibroGenero> libGenList = libGenDAO.getAllLibroGeneroPorIdGenero(idGenero);
        if (libGenList != null) {
            for (LibroGenero libroGenero : libGenList) {
                librosPorGenero.add(existeLibroPorID(libroGenero.getIdLibro()));
            }
        }

        return librosPorGenero;
    }

    //RETORNA EL ULTIMO IDLIBRO PARA CREAR UNO NUEVO
    public int getIdLibroLast() {
        abrirConexion();
        ObjectSet resultado = db.query(Libro.class);
        int total = resultado.size() + 1;
        cerrarConexion();
        return total;
    }

    //EXISTE LIBRO PRO ISBN
    public Libro existeLibroPorID(int idLibro) {
        abrirConexion();
        Libro libro = new Libro(idLibro);
        Libro dbLibro = null;

        ObjectSet resultado = db.queryByExample(libro);
        if (!resultado.isEmpty()) {
            dbLibro = (Libro) resultado.next();
        }
        cerrarConexion();
        return dbLibro;
    }

    //COMPRUEBA SI EXISTE UN LIBRO CON ESE ISBN
    public Libro existeLibroPorISBN(int isbn) {
        List<Libro> todosLibros = getListaLibrosBBDD();
        Libro lib = null;
        if (todosLibros != null) {
            for (Libro libro : todosLibros) {
                if (libro.getIsbn() == isbn) {
                    return libro;
                }
            }
        }
        return lib;
    }

    //EXISTEN LIBROS EN LA BBDD
    public boolean existenTodosLibros(ArrayList<Libro> libros) {
        boolean existenTodos = true;
        LibroDAO libroDAO = new LibroDAO();
        for (Libro libro : libros) {
            if (libroDAO.existeLibroPorID(libro.getIdLibro()) == null) {
                existenTodos = false;
                break;
            }
        }
        return existenTodos;
    }

    public void abrirConexion() {
        try {
            db = Db4oEmbedded.openFile("libreria.db4o");
        } catch (Exception ex) {
            System.out.println("No se ha podido conectar con la base de datos");
        }
    }

    public void cerrarConexion() {
        try {
            db.close();
        } catch (Exception e) {
            System.out.println("No se pudo conectar con la BBDD");
        }
    }

    //TESTING
    public void showAllLibros() {
        //ABRIMOS CONEXION
        abrirConexion();
        System.out.println("LIBROS:");
        ObjectSet resultado = db.query(Libro.class);
        for (int i = 0; i < resultado.size(); i++) {
            Libro libro = (Libro) resultado.next();
            System.out.println("LibroID: " + libro.getIdLibro() + "\nTitulo: " + libro.getTitulo());
        }

        //CERRAMOS CONEXION
        cerrarConexion();
    }
}
