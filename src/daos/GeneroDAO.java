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

/**
 *
 * @author THOR
 */
public class GeneroDAO {

    private ObjectContainer db;

    public GeneroDAO() {
    }

    //CREAR GENERO
    public void crearGenero(String nombre) {
        if (existeGeneroNombre(nombre) == null) {

            //ABRIMOS CONEXION
            abrirConexion();

            Genero genero = new Genero(getIdGeneroLast(), nombre);
            try {
                db.store(genero);
            } catch (Exception ex) {
                System.out.println("No se ha podido guardar el objeto Genero");
            }
        } else {
            System.out.println("Ya existe este objeto Genero");
        }

        //CERRAMOS CONEXION
        cerrarConexion();
    }

    //BORRAR GENERO
    public void deleteGenero(String nombre) {
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
                System.out.println("Problema al borrar genero " + nombre);
            }
        } else {
            System.out.println("No se ha encontrado el género " + nombre);
        }

        //CERRAMOS CONEXION
        cerrarConexion();
    }

    //EXISTE GENERO
    public Genero existeGeneroNombre(String nombre) {
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
    
    //DEVUELVE EL ID PARA AÑADIR UN ELEMENTO (TAMAÑO ARRAY + 1)
    public int getIdGeneroLast() {
        ObjectSet resultado = db.query(Genero.class);
        int total = resultado.size() + 1;
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

    //TESTING
    public void showAllGeneros() {
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
