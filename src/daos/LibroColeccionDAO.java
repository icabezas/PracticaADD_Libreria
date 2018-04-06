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
import modelo.Libro;
import modelo.LibroColeccion;

/**
 *
 * @author THOR
 */
public class LibroColeccionDAO {

    private ObjectContainer db;

    public LibroColeccionDAO() {
    }

    //CREAR LIBROCOLECCION
    public void crearLibroColeccion(int idLibro, int idColeccion) throws LibreriaExcepciones {
        //COMPROBAR SI EXISTE RELACION LIBRO Y COLECCION PRIMERO
        if (existeLibroColeccion(idLibro, idColeccion) == null) {
            abrirConexion();
            try {
                db.store(new LibroColeccion(idLibro, idColeccion));
            } catch (Exception ex) {
                cerrarConexion();
                throw new LibreriaExcepciones("No se ha podido guardar el objeto LibroColeccion");
            }
            cerrarConexion();
        } else {
            throw new LibreriaExcepciones("Ya existe este objeto LibroColeccion");
        }
    }

    //BORRAR LIBROCOLECCION por idColeccion o por idLibro
    public void borrarLibroColeccion(int id, boolean esPorLibro) throws LibreriaExcepciones {
        abrirConexion();
        ObjectSet result = null;
        if (esPorLibro) {
            result = db.queryByExample(new LibroColeccion(id, 0));
        } else {
            result = db.queryByExample(new LibroColeccion(id));
        }
        if (!result.isEmpty()) {
            for (int i = 0; i < result.size(); i++) {
                try {
                    db.delete((LibroColeccion) result.next());
                    System.out.println("Deleted LibroColeccion");
                } catch (Exception ex) {
                    cerrarConexion();
                    throw new LibreriaExcepciones("DB: No se ha podido borrar objeto LibroColeccion");
                }
            }
        } else {
            System.out.println("No se ha encontrado libro/coleccion indicada");
        }
        cerrarConexion();
    }

    //GET LIBROS DE UNA COLECCION
    public List<Libro> getLibrosDadaColeccion(int idColeccion) {
        LibroDAO libDAO = new LibroDAO();
        LibroColeccion libroColeccion = new LibroColeccion(idColeccion);
        List<LibroColeccion> libColeccionList = new ArrayList<>();
        List<Libro> libros = new ArrayList<>();
        abrirConexion();
        ObjectSet resultado = db.queryByExample(libroColeccion);
        if (!resultado.isEmpty()) {
            for (int i = 0; i < resultado.size(); i++) {
                libColeccionList.add((LibroColeccion) resultado.next());
            }
        } else {
            libros = null;
        }
        cerrarConexion();
        for (LibroColeccion libCol : libColeccionList) {
            Libro libro = libDAO.existeLibroPorID(libCol.getIdLibro());
            libros.add(libro);
        }

        return libros;
    }

    //EXISTE LIBROGENERO
    public LibroColeccion existeLibroColeccion(int idLibro, int idColeccion) {
        abrirConexion();
        LibroColeccion libroColeccion = new LibroColeccion(idLibro, idColeccion);
        ObjectSet resultado = db.queryByExample(libroColeccion);
        if (!resultado.isEmpty()) {
            libroColeccion = (LibroColeccion) resultado.next();
        } else {
            libroColeccion = null;
        }
        cerrarConexion();
        return libroColeccion;
    }

    //GETID LAST LIBROGENERO
    public int getIdLibroColeccionLast() {
        abrirConexion();
        ObjectSet resultado = db.query(LibroColeccion.class);
        int total = resultado.size() + 1;
        cerrarConexion();
        return total;
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

    //PARA TEST
    public void showAllLibroColeccion() {
        //ABRIMOS CONEXION
        abrirConexion();

        ObjectSet resultado = db.query(LibroColeccion.class);
        if (!resultado.isEmpty()) {
            for (int i = 0; i < resultado.size(); i++) {
                LibroColeccion libCol = (LibroColeccion) resultado.next();
                System.out.println("LibroID: " + libCol.getIdLibro() + "\nColeccionID:" + libCol.getIdColeccion());
            }
        }
        //CERRAMOS CONEXION
        cerrarConexion();
    }
}
