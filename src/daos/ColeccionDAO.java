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
import modelo.Coleccion;
import modelo.Libro;

/**
 * CLASE PARA ADMINISTRAR COLECCIONES UTILIZA LIBROCOLECCIONDAO
 */
public class ColeccionDAO {

    private ObjectContainer db;

    public ColeccionDAO() {

    }

    //CREAR COLECCION
    public void crearColeccion(int idUsuario, String nombreColeccion, ArrayList<Libro> libros) throws LibreriaExcepciones {
        //COMPROBAR SI EXISTE LIBRO Y COLECCION PRIMERO
        LibroDAO libroDAO = new LibroDAO();
        ArrayList<Libro> librosDB = libroDAO.existenTodosLibros(libros);
        if (existeColeccionUsuarioNombre(idUsuario, nombreColeccion) == null && librosDB != null) {
            for (Libro libro : librosDB) {
                LibroColeccionDAO libroColeccion = new LibroColeccionDAO();
                libroColeccion.crearLibroColeccion(libro.getIdLibro(), getIdColeccionLast());
            }
            try {
                Coleccion coleccion = new Coleccion(getIdColeccionLast(), nombreColeccion, idUsuario);
                abrirConexion();
                db.store(coleccion);
            } catch (Exception ex) {
                cerrarConexion();
                throw new LibreriaExcepciones("No se ha podido guardar el objeto Coleccion");
            }
        } else {
            throw new LibreriaExcepciones("Ya existe este objeto LibroColeccion o no existe alguno de los libros en bbdd, quien sabe");
        }
        cerrarConexion();
    }

    //BORRAR COLECCION
    public void borrarColeccion(String nombreColeccion, int idUsuario) throws LibreriaExcepciones {
        abrirConexion();
        LibroColeccionDAO libroColeccionDAO = new LibroColeccionDAO();
        ObjectSet result = db.queryByExample(new Coleccion(idUsuario, nombreColeccion));
        if (!result.isEmpty()) {
            Coleccion coleccion = (Coleccion) result.next();
            try {
                db.delete(coleccion);
                cerrarConexion();
            } catch (Exception ex) {
                cerrarConexion();
                throw new LibreriaExcepciones("Problema al borrar la coleccion " + nombreColeccion);
            }
            try {
                libroColeccionDAO.borrarLibroColeccion(coleccion.getIdColeccion(), false);
            } catch (LibreriaExcepciones ex) {
                throw new LibreriaExcepciones("Problema al borrar la relacion de libroColeccion " + nombreColeccion);
            }
        } else {
            cerrarConexion();
            throw new LibreriaExcepciones("No se ha encontrado la coleccion con nombre " + nombreColeccion);
        }
    }
    //MODIFICAR COLECCION

    public void modificarColeccion(String nombreColeccion, List<Libro> libros) {
        //ESTO SE DEBERIA HACER EN LIBROCOLECCION
    }

    //EXISTE COLECCION DEL USUARIO CON ESE NOMBRE
    public Coleccion existeColeccionUsuarioNombre(int idUsuario, String nombreColeccion) throws LibreriaExcepciones {
        List<Coleccion> coleccionesUsuario = getAllColeccionesUsuario(idUsuario);
        Coleccion coleccionUsuario = null;
        for (Coleccion coleccion : coleccionesUsuario) {
            if (coleccion.getNombre().toLowerCase().equals(nombreColeccion.toLowerCase())) {
                coleccionUsuario = coleccion;
            }
        }
        return coleccionUsuario;
    }

    public ArrayList<Coleccion> getAllColeccionesUsuario(int idUsuario) throws LibreriaExcepciones {
        abrirConexion();
        ArrayList<Coleccion> coleccionesUsuario = new ArrayList<>();
        Coleccion coleccion = new Coleccion(0, "", idUsuario);
        ObjectSet resultado = db.query(Coleccion.class);
        if (!resultado.isEmpty()) {
            for (int i = 0; i < resultado.size(); i++) {
                coleccion = (Coleccion) resultado.next();
                if (coleccion.getIdUsuario() == idUsuario) {
                    coleccionesUsuario.add(coleccion);
                }
            }
        }
        cerrarConexion();
        return coleccionesUsuario;
    }

    //RETORNA EL ULTIMO IDCOLECCION PARA CREAR UNA NUEVA
    public int getIdColeccionLast() throws LibreriaExcepciones {
        abrirConexion();
        ObjectSet resultado = db.query(Coleccion.class);
        int total = resultado.size() + 1;
        cerrarConexion();
        return total;
    }

    //TESTING
    public void showAllColeccionesDB() throws LibreriaExcepciones {
        abrirConexion();
        ObjectSet resultado = db.query(Coleccion.class);
        if (!resultado.isEmpty()) {
            for (int i = 0; i < resultado.size(); i++) {
                Coleccion coleccion = (Coleccion) resultado.next();
                System.out.println("Nombre: " + coleccion.getNombre() + "\nID: " + coleccion.getIdColeccion() + "\nID USUARIO: " + coleccion.getIdUsuario());
            }
        } else {
            System.out.println("No hay colecciones");
        }
        cerrarConexion();
    }

    public void abrirConexion() throws LibreriaExcepciones {
        try {
            db = Db4oEmbedded.openFile("libreria.db4o");
        } catch (Exception ex) {
            throw new LibreriaExcepciones("No se ha podido conectar con la base de datos");
        }
    }

    public void cerrarConexion() throws LibreriaExcepciones {
        try {
            db.close();
        } catch (Exception e) {
            throw new LibreriaExcepciones("No se ha podido conectar con la base de datos");
        }
    }
}
