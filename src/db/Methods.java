package db;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.util.ArrayList;
import java.util.List;
import jaxb.generated.LibreriaType;
import jaxb.generated.LibroType;
import jaxb.generated.UsuarioType;

public class Methods {

    //ADMINISTRACIÓN CONEXIÓN BBDD
    //Para cerrar conexión
    public static void cerrarConexion(ObjectContainer baseDatos) {
        try {
            baseDatos.close();
        } catch (Exception e) {
            System.out.println("No se pudo conectar con la BBDD");
        }
    }

    //ADMINISTRACION LIBROS
    //Para almacenar objetos en la bbdd
    //@param baseDatos el objeto que representa la bbdd en la que se almacenará el libro
    //@param LibroType libro es el libro que se desea almacenar
    public static void almacenarLibro(ObjectContainer baseDatos, LibroType libro) {
        if (existeLibroISBN(baseDatos, libro.getISBN()).size() == 0) {
            try {
                baseDatos.store(libro);
                System.out.println("Se ha guardado el libro " + libro.getTitulo());
            } catch (Exception e) {
                System.out.println("Se ha producido un error, no se ha podido insertar");
            }
        } else {
            System.out.println("Este libro ya existe en la basde de datos");
        }
    }

    //Permite mostrar un libro de la bbdd
    //@param baseDatos el objeto que representa la bbdd en la que se almacenará el libro
    public static void imprimirResultadoConsulta(ObjectSet resultado) {
        System.out.println("Recuperados " + resultado.size() + " libros");
        while (resultado.hasNext()) {
            System.out.println(resultado.next());
        }
    }

    //Permite consultar un libro de la bbdd
    //@param baseDatos el objeto que representa la bbdd en la que se almacenará el libro
    //@param titulo (hay que cmabiar por ISBN, que es el identificador único)
    private static List<LibroType> existeLibroISBN(ObjectContainer baseDatos, int isbn) {
        LibroType libro = new LibroType(isbn);
        List<LibroType> librosConISBN = new ArrayList<>();
        ObjectSet resultado = baseDatos.queryByExample(libro);
//        imprimirResultadoConsulta(resultado);
        for (int i = 0; i < resultado.size(); i++) {
            LibroType lib = (LibroType) resultado.next();
            librosConISBN.add(lib);
        }
        return librosConISBN;
    }

    //ADMINISTRACION DE USUARIO
    //ALMACENAJE DE UN USUARIO EN LA BBDD
    public static void nuevoUsuario(ObjectContainer baseDatos, UsuarioType user) {
        UsuarioType newUsuario = existeUsuario(baseDatos, user.getUsername());
        if (newUsuario == null) {
            try {
                baseDatos.store(user);
                System.out.println("Se han guardado los datos del nuevo usuario " + user.getNombre());
            } catch (Exception e) {
                System.out.println("Se ha producido un error, no se ha podido insertar");
            }
        } else {
            System.out.println("Ya existe un usuario con este username");
        }
    }

    private static UsuarioType existeUsuario(ObjectContainer baseDatos, String username) {
        UsuarioType user = new UsuarioType(username);
        ObjectSet resultado = baseDatos.queryByExample(user);
        if (resultado.size() != 0) {
            return (UsuarioType) resultado.next();
        } else {
            return null;
        }
    }

    public static void mostrarUsuarios(ObjectContainer baseDatos) {
        ObjectSet resultado = baseDatos.query(UsuarioType.class);
        for (int i = 0; i < resultado.size(); i++) {
            UsuarioType user = (UsuarioType) resultado.next();
            System.out.println("\nUsername" + user.getUsername());
        }
    }

    //ADMINISTRACION DE LIBRERIA
    //CREACION DE UNA LIBRERIA, SE AÑADE A LA BBDD DEL USUARIO
    public static void crearLibreriaParaUsuario(ObjectContainer baseDatos, LibreriaType libreria, UsuarioType user) {
        
//        if (libreriaExists == null) {
//            userToAddLibrary.getColecciones().add(libreria);
//            baseDatos.store(userToAddLibrary);
//            baseDatos.store(libreria);
//        } else {
//            System.out.println("Ya tienes una colección con ese nombre");
//        }

    }

    private static LibreriaType existeLibreriaPorNombre(ObjectContainer baseDatos, String nombreLibreria, String userName) {
        UsuarioType usuario = existeUsuario(baseDatos, userName);
        List<LibreriaType> libreriaUser = usuario.getColecciones();
        for (LibreriaType libreria : libreriaUser) {
            ObjectSet resultado = baseDatos.queryByExample(libreria);
            if (resultado.size() != 0) {
                return libreria;
            }
        }
        return null;
    }

    public static void mostrarLibreriasUsuario(ObjectContainer baseDatos, UsuarioType user) {
        UsuarioType userToAddLibrary = (UsuarioType) baseDatos.queryByExample(user);
        List<LibreriaType> colecciones = userToAddLibrary.getColecciones();
        for (LibreriaType libreria : colecciones) {
            System.out.println(libreria.getNombre());
        }
    }

}
