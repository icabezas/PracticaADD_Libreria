package daos;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import java.util.ArrayList;
import java.util.List;
import jaxb.LibreriaType;
import jaxb.LibroType;
import jaxb.UsuarioType;

public class Methods {

    public ObjectContainer db;

    //ADMINISTRACIÓN CONEXIÓN BBDD
    //ABRIR CONEXION
    public Methods() {
//        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
//        configuration.common().objectClass(UsuarioType.class).updateDepth(4);
//        configuration.common().objectClass(UsuarioType.class).cascadeOnActivate(true);
//        configuration.common().objectClass(LibreriaType.class).updateDepth(4);
//        configuration.common().objectClass(LibreriaType.class).cascadeOnActivate(true);
//        configuration.common().objectClass(LibroType.class).updateDepth(4);
//        configuration.common().objectClass(LibroType.class).cascadeOnActivate(true);
//        try {
////            Db4oEmbedded.openFile(configuration, "library.db4o");
//        db = Db4oEmbedded.openFile("library.db4o");
//        } catch (Exception ex) {
//            System.out.println("Can't connect to database" + ex.getMessage());
//        }
    }

    public void abrirConexion(String library){
        
//        DB4OFILENAME = context.getRealPath("/WEB-INF/repository/ngsiRegRepo.db4o");
        System.out.println(library);
        db = Db4oEmbedded.openFile(library);  
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
        LibroType newLibro = existeLibroISBN(libro.getISBN()).get(0);
        if (newLibro == null) {
            try {
                System.out.println(libro.getTitulo() + " - " + libro.getAutor());
                db.store(libro);
                db.commit();
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
    private List<LibroType> existeLibroISBN(int isbn) {
        LibroType libro = new LibroType(isbn);
        List<LibroType> librosConISBN = new ArrayList<>();
        ObjectSet resultado = db.queryByExample(libro);
//        imprimirResultadoConsulta(resultado);
        for (int i = 0; i < resultado.size(); i++) {
            LibroType lib = (LibroType) resultado.next();
            librosConISBN.add(lib);
        }
        return librosConISBN;
    }

    //
    //ADMINISTRACION DE USUARIO
    //ALMACENAJE DE UN USUARIO EN LA BBDD
    public void nuevoUsuario(UsuarioType user) {
        List<UsuarioType> newUsuario = existeUsuario(user.getUsername());
        if (newUsuario == null) {
            try {
                db.store(user);
                db.commit();
                System.out.println("Se han guardado los datos del nuevo usuario " + user.getNombre());
            } catch (Exception e) {
                System.out.println("Se ha producido un error, no se ha podido insertar");
            }
        } else {
            System.out.println("Ya existe un usuario con este username");
        }
    }

    public List<UsuarioType> existeUsuario(String username) {
        UsuarioType user = new UsuarioType(username);
        List<UsuarioType> users = new ArrayList<>();
        ObjectSet resultado = db.queryByExample(user);
//        imprimirResultadoConsulta(resultado);
        for (int i = 0; i < resultado.size(); i++) {
            UsuarioType userToAdd = (UsuarioType) resultado.next();
            users.add(user);
        }
        return users;
    }

    public void mostrarUsuarios() {
        ObjectSet resultado = db.query(UsuarioType.class);
        if (resultado.isEmpty()) {
            System.out.println("No hay usuarios");
        }
        for (int i = 0; i < resultado.size(); i++) {
            System.out.println(i + 1);
            UsuarioType user = (UsuarioType) resultado.next();
            System.out.println("\nUsername: " + user.getUsername() + " Colecciones: ");
//            for (LibreriaType lib : user.getColecciones()) {
//                System.out.println(lib.getNombre());
//            }

        }
    }

    //ADMINISTRACION DE LIBRERIA
    //CREACION DE UNA LIBRERIA, SE AÑADE A LA BBDD DEL USUARIO
    //MODIFICAR FUNCION, RECIBE LISTA DE LIBROS Y LAS METE EN UNA LIBRERIA
//    public void crearLibreriaParaUsuario(LibreriaType nuevaLibreria, UsuarioType user) {
//        List<UsuarioType> users = existeUsuario(user.getNombre());
//        UsuarioType userToAddLibrary = users.get(0);
//        if (existeColeccionUsuarioPorNombre(nuevaLibreria.getNombre(), user.getNombre()) == null) {
//            List<LibreriaType> colecciones = userToAddLibrary.getColecciones();
//            colecciones.add(nuevaLibreria);
//            userToAddLibrary.colecciones = colecciones;
//            db.ext().store(user);
//            db.ext().close();
////            db.store(nuevaLibreria);
//            System.out.println("Coleccion: " + nuevaLibreria.getNombre() + " añadida.");
//        } else {
//            System.out.println("Ya tienes una colección con ese nombre");
//        }
//    }
    public void modificarUserName(UsuarioType user) {
        ObjectSet result = db.queryByExample(user);
        if (result.hasNext()) {

        }
    }

    public void crearLibreriaUser(LibreriaType libreria, UsuarioType user) {
        UsuarioType us = new UsuarioType(user.getNombre());
        ObjectSet result = db.queryByExample(us);
        boolean existe = false;
        if (result.hasNext()) {
            UsuarioType usuario = (UsuarioType) result.next();
            List<LibreriaType> colecciones = usuario.getColecciones();
            for (LibreriaType lib : colecciones) {
                if (lib.getNombre().equals(libreria.getNombre())) {
                    existe = true;
                    System.out.println("Ya tienes una librería con este nombre");
                    break;
                }
            }
            if (!existe) {
                colecciones.add(libreria);
                usuario.setColecciones(colecciones);
                usuario.setNombre("pepe");
//                db.activate(usuario.colecciones, 5);
//                db.store(libreria);
                db.ext().store(usuario);
                db.ext().commit();
                System.out.println("Se ha añadido " + libreria.getNombre());
            }
        } else {
            System.out.println("Error, no se ha encontrado al usuario");
        }
    }

    private LibreriaType existeColeccionUsuarioPorNombre(String nombreLibreria, String userName) {
        UsuarioType usuario = existeUsuario(userName).get(0);
        List<LibreriaType> libreriaUser = getLibreriasUsuario(usuario);
        for (LibreriaType libreria : libreriaUser) {
            if (libreria.getNombre().equals(nombreLibreria)) {
                return libreria;
            }
        }
        return null;
    }

    public List<LibreriaType> getLibreriasUsuario(UsuarioType user) {
        UsuarioType userToAddLibrary = existeUsuario(user.getUsername()).get(0);
        List<LibreriaType> colecciones = userToAddLibrary.getColecciones();
        if (colecciones.isEmpty()) {
            colecciones = null;
            System.out.println("Este usuario no tiene colecciones");
        }
        return colecciones;
    }

}
