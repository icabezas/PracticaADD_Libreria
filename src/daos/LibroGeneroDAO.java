/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import com.db4o.ObjectSet;
import java.util.ArrayList;
import java.util.List;
import jaxb.LibroType;
import modelo.LibroGenero;

/**
 *
 * @author THOR
 */
public class LibroGeneroDAO extends DBDAO {

    public LibroGeneroDAO() {
    }

    //TODAS LAS FUNCIONES ABREN CONEXION Y CIERRAN DENTRO DE ELLAS MISMAS
    //CREAR LIBROGENERO
    public void crearLibroGenero(int idLibro, int idGenero) {
        //ABRIMOS CONEXION
        abrirConexion();

        if (existeLibroGenero(idLibro, idGenero) == null) {
            LibroGenero libroGenero = new LibroGenero(idLibro, idGenero);
            try {
                db.store(libroGenero);
            } catch (Exception ex) {
                System.out.println("No se ha podido guardar el objeto LibroGenero");
            }
        } else {
            System.out.println("Ya existe este objeto");
        }

        //CERRAMOS CONEXION
        cerrarConexion();
    }

    //TO-DO: COMPROBAR QUE SE PUEDE BORRAR LIBROGENERO
    //BORRAR LIBROGENERO 
    public void borrarLibroGenero(int idLibro, int idGenero) {
        //ABRIMOS CONEXION
        abrirConexion();

        LibroGenero dbLibroGenero = existeLibroGenero(idLibro, idGenero);
        if (dbLibroGenero != null) {
            try {
                db.delete(dbLibroGenero);
            } catch (Exception ex) {
                System.out.println("No se ha podido borrar");
            }
        }else{
            System.out.println("Problema al borrar");
        }

        //CERRAMOS CONEXION
        cerrarConexion();
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
}
