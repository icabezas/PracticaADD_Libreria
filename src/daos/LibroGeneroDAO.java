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
    public void crearLibroGenero(int idLibro, ArrayList<Genero> generos) throws LibreriaExcepciones {
        for (Genero genero : generos) {
            if (existeLibroGenero(idLibro, genero.getIdGenero()) == null) {
                LibroGenero libroGenero = new LibroGenero(idLibro, genero.getIdGenero());
                abrirConexion();
                try {
                    db.store(libroGenero);
                } catch (Exception ex) {
                    System.out.println("No se ha podido guardar el objeto LibroGenero");
                }
                cerrarConexion();
            } else {
                System.out.println("Ya existe la relacion de este libro con este genero");
            }
        }
    }

    //BORRAR LIBROGENERO puede ser por idGenero o por idLibro
    public void borrarLibroGenero(int id, boolean esPorLibro) {
        //ABRIMOS CONEXION
        abrirConexion();
        ObjectSet result = null;
        if (esPorLibro) {
            result = db.queryByExample(new LibroGenero(id, 0));
        } else {
            result = db.queryByExample(new LibroGenero(0, id));
        }
        if (!result.isEmpty()) {
            for (int i = 0; i < result.size(); i++) {
                LibroGenero libGen = (LibroGenero) result.next();
                try {
                    db.delete(libGen);
                    System.out.println("Deleted LibroGenero");
                } catch (Exception ex) {
                    System.out.println("DB: No se ha podido borrar objeto LibroGenero");
                }
            }

        } else {
            System.out.println("No se ha encontrado LibroGenero indicada");
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

    //RETORNA EL GENERO DE UN LIBRO DADO UN IDLIBRO
    public ArrayList<Genero> getLibroGenero(int idLibro) throws LibreriaExcepciones {

        ArrayList<Genero> generos = new ArrayList<>();
        Genero gen = new Genero();
        GeneroDAO generoDAO = new GeneroDAO();
        List<LibroGenero> libGenAllDB = getAllLibroGenero();
        for (LibroGenero libroGenero : libGenAllDB) {
            if (libroGenero.getIdLibro()== idLibro) {
                gen = generoDAO.getGeneroFromID(libroGenero.getIdGenero());
                generos.add(gen);
            }
        }
        return generos;
    }

    //RETORNA UNA LISTA DE LIBROGENERO POR IDGENERO
    public List<LibroGenero> getAllLibroGeneroPorIdGenero(int idGenero) {
        abrirConexion();
        List<LibroGenero> libGenList = new ArrayList<>();
        LibroGenero libGen = new LibroGenero(0, idGenero);
        ObjectSet resultado = db.queryByExample(libGen);
        if (!resultado.isEmpty()) {
            for (int i = 0; i < resultado.size(); i++) {
                libGen = (LibroGenero) resultado.next();
                libGenList.add(libGen);
            }
        } else {
            System.out.println("No existe este genero: 2");
            libGenList = null;
        }
        cerrarConexion();
        return libGenList;
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

    //RETORNA UNA LISTA DE TODOS LOS LIBROGENERO EN DB
    public List<LibroGenero> getAllLibroGenero() {
        //ABRIMOS CONEXION
        abrirConexion();

        List<LibroGenero> libGenAllDB = new ArrayList<>();
        ObjectSet resultado = db.query(LibroGenero.class);
        for (int i = 0; i < resultado.size(); i++) {
            LibroGenero libGen = (LibroGenero) resultado.next();
            libGenAllDB.add(libGen);
        }
        //CERRAMOS CONEXION
        cerrarConexion();

        return libGenAllDB;
    }
}
