package db;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.util.ArrayList;
import java.util.List;
import jaxb.generated.LibreriaType;
import jaxb.generated.LibroType;
import jaxb.generated.UsuarioType;

public class Methods {

    public ObjectContainer db;

    //ADMINISTRACIÓN CONEXIÓN BBDD
    //ABRIR CONEXION
    public Methods() {
        db = Db4oEmbedded.openFile("library.db4o");
    }

    //Para cerrar conexión
    public void cerrarConexion() {
        try {
            db.close();
        } catch (Exception e) {
            System.out.println("No se pudo conectar con la BBDD");
        }
    }

    //ADMINISTRACION LIBROS
    //Para almacenar objetos en la bbdd
    //@param baseDatos el objeto que representa la bbdd en la que se almacenará el libro
    //@param LibroType libro es el libro que se desea almacenar
    public void almacenarLibro(LibroType libro) {
        LibroType newLibro = existeLibroISBN(libro.getISBN());
        if (newLibro == null) {
            try {
                System.out.println(libro.getTitulo() + " - " + libro.getAutor());
                db.store(libro);
                System.out.println("Se ha guardado el libro " + libro.getTitulo());
            } catch (Exception e) {
                System.out.println("Se ha producido un error, no se ha podido insertar");
            }
        } else {
            System.out.println("Este libro ya existe en la basde de datos");
        }
    }

    //Retorna una lista de todos los libros en bbdd
    public List<LibroType> getListaLibros() {
        List<LibroType> libros = new ArrayList<>();
        ObjectSet resultado = db.query(LibroType.class);
        System.out.println("SIZE LIBROS " + resultado.size());
        for (int i = 0; i < resultado.size(); i++) {
            LibroType libro = (LibroType) resultado.next();
            libros.add(libro);
        }
        return libros;
    }

    //Permite mostrar un libro de la bbdd
//    public void imprimirResultadoConsulta(ObjectSet resultado) {
//        System.out.println("Recuperados " + resultado.size() + " libros");
//        while (resultado.hasNext()) {
//            System.out.println(resultado.next());
//        }
//    }
    //Permite consultar un libro de la bbdd
    //@param baseDatos el objeto que representa la bbdd en la que se almacenará el libro
    //@param titulo (hay que cmabiar por ISBN, que es el identificador único)
    //RETOCAR MAAAAAAL
    private LibroType existeLibroISBN(int isbn) {
        LibroType libro = new LibroType(isbn);
        ObjectSet resultado = db.queryByExample(libro);
//        imprimirResultadoConsulta(resultado);
        libro = (LibroType) resultado.next();
        return libro;
    }

    //
    //ADMINISTRACION DE USUARIO
    //ALMACENAJE DE UN USUARIO EN LA BBDD
    public void nuevoUsuario(UsuarioType user) {
        UsuarioType newUsuario = existeUsuario(user.getUsername());
        if (newUsuario == null) {
            try {
                db.store(user);
                System.out.println("Se han guardado los datos del nuevo usuario " + user.getNombre());
            } catch (Exception e) {
                System.out.println("Se ha producido un error, no se ha podido insertar");
            }
        } else {
            System.out.println("Ya existe un usuario con este username");
        }
    }

    private UsuarioType existeUsuario(String username) {
        UsuarioType user = new UsuarioType(username);
        ObjectSet resultado = db.queryByExample(user);
        if (resultado.size() != 0) {
            return (UsuarioType) resultado.next();
        } else {
            return null;
        }
    }

    public void mostrarUsuarios() {
        ObjectSet resultado = db.query(UsuarioType.class);
        for (int i = 0; i < resultado.size(); i++) {
            UsuarioType user = (UsuarioType) resultado.next();
            System.out.println("\nUsername" + user.getUsername());
        }
    }

    //ADMINISTRACION DE LIBRERIA
    //CREACION DE UNA LIBRERIA, SE AÑADE A LA BBDD DEL USUARIO
    //MODIFICAR FUNCION, RECIBE LISTA DE LIBROS Y LAS METE EN UNA LIBRERIA
    public void crearLibreriaParaUsuario(List<LibroType> libros, String nombreLibreria, UsuarioType user) {
        if (existeColeccionUsuarioPorNombre(nombreLibreria, user.getNombre()) == null) {
            LibreriaType nuevaLibreria = new LibreriaType(libros, nombreLibreria);
            List<LibreriaType> colecciones = user.getColecciones();
            colecciones.add(nuevaLibreria);
            user.setColecciones(colecciones);
            db.store(user);
            db.store(nuevaLibreria);
            System.out.println("Coleccion: " + nombreLibreria + " añadida.");
        } else {
            System.out.println("Ya tienes una colección con ese nombre");
        }
    }

    private LibreriaType existeColeccionUsuarioPorNombre(String nombreLibreria, String userName) {
        UsuarioType usuario = existeUsuario(userName);
        List<LibreriaType> libreriaUser = usuario.getColecciones();
        for (LibreriaType libreria : libreriaUser) {
            if (libreria.getNombre().equals(nombreLibreria)) {
                return libreria;
            }
        }
        return null;
    }

    public List<LibreriaType> getLibreriasUsuario(UsuarioType user) {
        UsuarioType userToAddLibrary = existeUsuario(user.getUsername());
        List<LibreriaType> colecciones = userToAddLibrary.getColecciones();
        if (colecciones.isEmpty()) {
            colecciones = null;
            System.out.println("Este usuario no tiene colecciones");
        }
        return colecciones;
    }

}
