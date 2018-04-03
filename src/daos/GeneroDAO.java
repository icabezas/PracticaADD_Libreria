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

/**
 *
 * @author THOR
 */
public class GeneroDAO {

    private ObjectContainer db;

    public GeneroDAO() {
    }

    //CREAR GENERO
    public void crearGenero(String nombre) throws LibreriaExcepciones {
        if (existeGeneroNombre(nombre) == null) {

            Genero genero = new Genero(getIdGeneroLast(), nombre);
            try {
                abrirConexion();
                db.store(genero);
            } catch (Exception ex) {
                cerrarConexion();
                throw new LibreriaExcepciones("No se ha podido guardar el objeto Genero");
            }
        } else {
            throw new LibreriaExcepciones("Ya existe este objeto Genero");
        }

        //CERRAMOS CONEXION
        cerrarConexion();
    }

    //BORRAR GENERO
    public void deleteGenero(String nombre) throws LibreriaExcepciones {
        //ABRIMOS CONEXION
        abrirConexion();

        ObjectSet result = db.queryByExample(new Genero(nombre));
        if (!result.isEmpty()) {
            Genero genero = (Genero) result.next();
            try {
                LibroGeneroDAO libroGeneroDAO = new LibroGeneroDAO();
                //PROBAR SI FUNCIONA
                libroGeneroDAO.borrarLibroGenero(genero.getIdGenero(), false);
                db.delete(genero);
                System.out.println("Deleted " + genero.getNombre());
            } catch (Exception ex) {
                cerrarConexion();
                throw new LibreriaExcepciones("Problema al borrar genero " + nombre);
            }
        } else {
            throw new LibreriaExcepciones("No se ha encontrado el genero " + nombre);
        }

        //CERRAMOS CONEXION
        cerrarConexion();
    }

    //EXISTE GENERO
    public Genero existeGeneroNombre(String nombre) throws LibreriaExcepciones {
        //ABRIMOS CONEXION
        abrirConexion();

        Genero genero = new Genero(nombre);
        Genero dbGenero = null;
        ObjectSet resultado = db.queryByExample(genero);
        if (!resultado.isEmpty()) {
            dbGenero = (Genero) resultado.next();
        }

        //CERRAMOS CONEXION
        cerrarConexion();
        return dbGenero;
    }

    //DEVUELVE UNA LISTA DE TODOS LOS GENEROS
    public List<Genero> getListAllGeneros() {
        List<Genero> generoList = new ArrayList<>();
        generoList = null;
        ObjectSet resultado = db.query(Genero.class);
        if (!resultado.isEmpty()) {
            for (int i = 0; i < resultado.size(); i++) {
                Genero genero = (Genero) resultado.next();
                generoList.add(genero);
            }
        }
        return generoList;
    }

    //DEVUELVE OBJETO GENERO A PARTIR DE IDGENERO
    public Genero getGeneroFromID(int idGenero) throws LibreriaExcepciones {
        Genero genero = new Genero(idGenero, "");
        Genero genDB = null;
        abrirConexion();
        ObjectSet resultado = db.queryByExample(genero);
        if (!resultado.isEmpty()) {
            genDB = (Genero) resultado.next();
        }
        cerrarConexion();
        return genDB;
    }

    //DEVUELVE EL ID PARA AÑADIR UN ELEMENTO (TAMAÑO ARRAY + 1)
    public int getIdGeneroLast() throws LibreriaExcepciones {
        abrirConexion();
        ObjectSet resultado = db.query(Genero.class);
        int total = resultado.size() + 1;
        cerrarConexion();
        return total;
    }

    public void abrirConexion() throws LibreriaExcepciones {
        try {
            db = Db4oEmbedded.openFile("libreria.db4o");
        } catch (Exception ex) {
            throw new LibreriaExcepciones("No se pudo conectar con la BBDD");
        }
    }

    public void cerrarConexion() throws LibreriaExcepciones {
        try {
            db.close();
        } catch (Exception e) {
            throw new LibreriaExcepciones("No se pudo conectar con la BBDD");
        }
    }

    //TESTING
    public void showAllGeneros() throws LibreriaExcepciones {
        //ABRIMOS CONEXION
        abrirConexion();

        System.out.println("GENEROS:");
        ObjectSet resultado = db.query(Genero.class);
        for (int i = 0; i < resultado.size(); i++) {
            Genero genero = (Genero) resultado.next();
            System.out.println("GeneroID: " + genero.getIdGenero() + "\nNombre: " + genero.getNombre());
        }

        //CERRAMOS CONEXION
        cerrarConexion();
    }
}
