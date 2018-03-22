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
    public void crearColeccion(int idUsuario, String nombreColeccion, ArrayList<Libro> libros) {
        //COMPROBAR SI EXISTE LIBRO Y COLECCION PRIMERO
        if (existeColeccionUsuarioNombre(idUsuario, nombreColeccion) == null && existenTodosLibros(libros)) {
            Coleccion coleccion = new Coleccion(getIdColeccionLast(), nombreColeccion, idUsuario);
            for (Libro libro : libros) {
                LibroColeccionDAO libroColeccion = new LibroColeccionDAO();
                libroColeccion.crearLibroColeccion(libro.getIdLibro(), getIdColeccionLast());
            }
            try {
                abrirConexion();
                db.store(coleccion);
            } catch (Exception ex) {
                System.out.println("No se ha podido guardar el objeto Coleccion");
            }
        } else {
            System.out.println("Ya existe este objeto LibroColeccion o no existe alguno de los libros en bbdd, quien sabe");
        }
    }

    //BORRAR COLECCION
    //TO-DO: BORRAR LIBROCOLECCION
    
    
    //MODIFICAR COLECCION??
    
    
    //EXISTE COLECCION DEL USUARIO CON ESE NOMBRE
    public Coleccion existeColeccionUsuarioNombre(int idUsuario, String nombreColeccion) {
        abrirConexion();
        Coleccion dbColeccionUsuario = null;
        Coleccion coleccion = new Coleccion(idUsuario, nombreColeccion);
        ObjectSet resultado = db.queryByExample(coleccion);
        if (!resultado.isEmpty()) {
            dbColeccionUsuario = (Coleccion) resultado.next();
        }
        cerrarConexion();
        return dbColeccionUsuario;
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

    //RETORNA EL ULTIMO IDCOLECCION PARA CREAR UNA NUEVA
    public int getIdColeccionLast() {
        abrirConexion();
        ObjectSet resultado = db.query(Coleccion.class);
        int total = resultado.size() + 1;
        cerrarConexion();
        return total;
    }

    //PARA TEST
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
}
