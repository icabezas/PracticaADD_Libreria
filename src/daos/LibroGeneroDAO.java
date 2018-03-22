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
import jaxb.LibroType;
import modelo.LibroGenero;

/**
 *
 * @author THOR
 */
public class LibroGeneroDAO {

    private ObjectContainer db;

    public LibroGeneroDAO() {
    }

    //TODAS LAS FUNCIONES ABREN CONEXION Y CIERRAN DENTRO DE ELLAS MISMAS
    //CREAR LIBROGENERO
    public void crearLibroGenero(int idLibro, int idGenero) {
        if (existeLibroGenero(idLibro, idGenero) == null) {
            LibroGenero libroGenero = new LibroGenero(idLibro, idGenero);
            try {
                abrirConexion();
                db.store(libroGenero);
            } catch (Exception ex) {
                System.out.println("No se ha podido guardar el objeto LibroGenero");
            }
        } else {
            System.out.println("Ya existe este objeto LibroGenero");
        }

        //CERRAMOS CONEXION
        cerrarConexion();
    }

    //TO-DO: COMPROBAR QUE SE PUEDE BORRAR LIBROGENERO
    //BORRAR LIBROGENERO 
    public void borrarLibroGenero(int idLibro, int idGenero) {
        //ABRIMOS CONEXION
//        abrirConexion();

        LibroGenero dbLibroGenero = existeLibroGenero(idLibro, idGenero);
        if (dbLibroGenero != null) {
            try {
                db.delete(dbLibroGenero);
            } catch (Exception ex) {
                System.out.println("No se ha podido borrar");
            }
        } else {
            System.out.println("Problema al borrar");
        }

        //CERRAMOS CONEXION
//        cerrarConexion();
    }

    //EXISTE LIBROGENERO
    public LibroGenero existeLibroGenero(int idLibro, int idGenero) {
        //ABRIMOS CONEXION
        abrirConexion();

        LibroGenero dbLibroGenero = null;
        LibroGenero libroGenero = new LibroGenero(idLibro, idGenero);
        ObjectSet resultado = db.queryByExample(libroGenero);
        if (!resultado.isEmpty()) {
            dbLibroGenero = (LibroGenero) resultado.next();
        }

        //CERRAMOS CONEXION
        cerrarConexion();
        return dbLibroGenero;
    }

    //GETID LAST LIBROGENERO
    public int getIdLibroGeneroLast() {
        abrirConexion();
        ObjectSet resultado = db.query(LibroGenero.class);
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
    public void showAllLibroGenero() {
        //ABRIMOS CONEXION
        abrirConexion();

        ObjectSet resultado = db.query(LibroGenero.class);
        for (int i = 0; i < resultado.size(); i++) {
            LibroGenero libGen = (LibroGenero) resultado.next();
            System.out.println("LibroID: " + libGen.getIdLibro() + "\nGeneroID: " + libGen.getIdGenero());
        }

        //CERRAMOS CONEXION
        cerrarConexion();
    }
}
