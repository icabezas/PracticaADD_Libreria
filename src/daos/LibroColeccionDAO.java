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
import modelo.Coleccion;
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
    public void crearLibroColeccion(int idLibro, int idColeccion) {
        //ABRIMOS CONEXION
        abrirConexion();

        //COMPROBAR SI EXISTE RELACION LIBRO Y COLECCION PRIMERO
        if (existeLibroColeccion(idLibro, idColeccion) == null) {
            LibroColeccion libroColeccion = new LibroColeccion(idLibro, idColeccion);
            try {
                db.store(libroColeccion);
            } catch (Exception ex) {
                System.out.println("No se ha podido guardar el objeto LibroColeccion");
            }
        } else {
            System.out.println("Ya existe este objeto LibroColeccion");
        }

        //CERRAMOS CONEXION
        cerrarConexion();
    }

    //BORRAR LIBROGENERO 
    public void borrarLibroColeccion(int idLibro, int idColeccion) {
        //ABRIMOS CONEXION
        abrirConexion();

        ObjectSet result = db.queryByExample(new LibroColeccion(idLibro, idColeccion));
        if (!result.isEmpty()) {
            LibroColeccion libCol = (LibroColeccion) result.next();
            try {
                db.delete(libCol);
                System.out.println("Deleted LibroColeccion");
            } catch (Exception ex) {
                System.out.println("DB: No se ha podido borrar objeto LibroColeccion");
            }
        } else {
            System.out.println("No se ha encontrado LibroColeccion indicada");
        }
        //CERRAMOS CONEXION
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
                LibroColeccion coleccionBBDD = (LibroColeccion) resultado.next();
                libColeccionList.add(coleccionBBDD);
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
        LibroColeccion dbLibroColeccion = null;
        LibroColeccion libroColeccion = new LibroColeccion(idLibro, idColeccion);
        ObjectSet resultado = db.queryByExample(libroColeccion);
        if (!resultado.isEmpty()) {
            dbLibroColeccion = (LibroColeccion) resultado.next();
        }
        return dbLibroColeccion;
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
        for (int i = 0; i < resultado.size(); i++) {
            LibroColeccion libCol = (LibroColeccion) resultado.next();
            System.out.println("LibroID: " + libCol.getIdLibro() + "\nColeccionID:" + libCol.getIdColeccion());
        }

        //CERRAMOS CONEXION
        cerrarConexion();
    }
}
