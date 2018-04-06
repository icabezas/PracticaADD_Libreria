/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import exceptiones.LibreriaExcepciones;
import java.util.ArrayList;
import java.util.List;
import modelo.Genero;
import modelo.Libro;
import modelo.LibroGenero;

/**
 *
 * @author THOR
 */
public class LibroDAO {

    public ObjectContainer db;

    public LibroDAO() {
    }

    //CREAR LIBRO
    public void crearLibro(Libro libro, ArrayList<String> nombresGeneros) {
        Genero genero;
        LibroGeneroDAO libroGeneroRelacion = new LibroGeneroDAO();
        GeneroDAO generoDAO = new GeneroDAO();
        ArrayList<Genero> generosList = new ArrayList<>();
        int lastID = getIdLibroLast();
        if (existeLibroPorISBN(libro.getIsbn()) == null) {
            libro.setIdLibro(lastID);
            try {
                for (String generoName : nombresGeneros) {
                    genero = generoDAO.existeGeneroNombre(generoName);
                    if (genero != null) {
                        generosList.add(genero);
                    }
                }
                libroGeneroRelacion.crearLibroGenero(lastID, generosList);
                abrirConexion();
                db.store(libro);
                cerrarConexion();
            } catch (Exception ex) {
                System.out.println("No se ha podido guardar el objeto Libro");
            }
        } else {
            System.out.println("Ya existe este libro");
        }

        //CERRAMOS CONEXION
    }

    //BORRAR LIBRO
    //TO-DO: BORRAR LIBROCOLECCION Y LIBROGENERO
    public void borrarLibro(int idLibro) throws LibreriaExcepciones {
        LibroColeccionDAO libroColeccionDAO = new LibroColeccionDAO();
        LibroGeneroDAO libroGeneroDAO = new LibroGeneroDAO();

        abrirConexion();
        ObjectSet result = db.queryByExample(new Libro(idLibro));
        if (!result.isEmpty()) {
            try {
                db.delete((Libro) result.next());
                cerrarConexion();
                libroColeccionDAO.borrarLibroColeccion(idLibro, true);
                libroGeneroDAO.borrarLibroGenero(idLibro, true);
                System.out.println("Deleted ");
            } catch (Exception ex) {
                throw new LibreriaExcepciones("Problema al borrar libro " + idLibro);
            }
        } else {
            throw new LibreriaExcepciones("No se ha encontrado el libro " + idLibro);
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
    public ArrayList<Libro> existenTodosLibros(ArrayList<Libro> libros) {
        LibroDAO libroDAO = new LibroDAO();
        Libro book;
        ArrayList<Libro> librosDB = new ArrayList<>();
        for (Libro libro : libros) {
            if (libroDAO.existeLibroPorISBN(libro.getIsbn()) != null) {
                librosDB.add(libroDAO.existeLibroPorISBN(libro.getIsbn()));
            }
        }
        return librosDB;
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
        ArrayList<Libro> libros = new ArrayList<>();

        //ABRIMOS CONEXION
        abrirConexion();
        System.out.println("LIBROS:");
        ObjectSet resultado = db.query(Libro.class);
        if (!resultado.isEmpty()) {
            for (int i = 0; i < resultado.size(); i++) {
                Libro libro = (Libro) resultado.next();
                libros.add(libro);
            }
            cerrarConexion();
            for (Libro book : libros) {
                System.out.println("LibroID: " + book.getIdLibro() + "\nTitulo: " + book.getTitulo() + "\nGeneros: ");
                for (Genero genero : book.getGenerosFromIDLibro(book.getIdLibro())) {
                    System.out.print(genero.getNombre() + " ");
                }
                System.out.println("");
            }
        } else {
            System.out.println("No hay libros en la DB");
        }
    }
}
