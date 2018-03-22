package practicaadd_libreria;

import jaxb.UsuarioType;
import jaxb.LibreriaType;
import jaxb.LibroType;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import daos.LibroGeneroDAO;
import daos.Methods;
import java.util.ArrayList;
import java.util.List;
import modelo.LibroGenero;

public class PracticaADD_Libreria {

    public static void main(String[] args) {

        //ObjectFactory objectFactory = new ObjectFactory();
        List<String> autores1 = new ArrayList<>();
        List<String> autores2 = new ArrayList<>();
        autores1.add("Ernest Cline");
        autores1.add("Lovecraft");
        autores2.add("Andrei Kurkov");
        List<String> categoria1 = new ArrayList<>();
        categoria1.add("Ciencia ficción");

        LibroType libro3 = new LibroType("caca", autores2, categoria1, "en", 0, 0, 0, 0);
        LibroType libro1 = new LibroType("Ready Player One", autores1, categoria1, "Multi", 2011, Byte.MIN_VALUE, 0, 0);
        LibroType libro2 = new LibroType("Muerte Con Pinguino", autores2, categoria1, "Multi", 2010, 0, 0, 1);
        List<LibroType> libros = new ArrayList<>();
        libros.add(libro3);
//        libros.add(libro2);
        LibreriaType libreria1 = new LibreriaType(libros, "Mis favoritos");
        LibreriaType libreria2 = new LibreriaType(libros, "Coleccion1");
        List<LibreriaType> colecciones = new ArrayList<>();
        colecciones.add(libreria1);
        UsuarioType admin = new UsuarioType("admin", "admin", "admin", true, colecciones);

        Methods method = new Methods();
        method.abrirConexion("library.db4o");
        try {
//            for (LibroType libro : method.getListaLibros()){
 //               System.out.println(libro.getTitulo());
   //         }
 //           method.mostrarUsuarios();
   //         method.nuevoUsuario(admin);
//            method.mostrarUsuarios();
            method.almacenarLibro(libro1);
           method.almacenarLibro(libro2);

            
 //           method.crearLibreriaUser(libreria2, admin);
//            List<UsuarioType> usuario = method.existeUsuario("admin");
//            List<LibreriaType> libreriaUsuario = usuario.getColecciones();
//            for (LibreriaType coleccion : method.getLibreriasUsuario(admin)) {
//                System.out.println(coleccion.getNombre());
//                for (LibroType libro : coleccion.getLibro()) {
//                    System.out.println("- " + libro.getTitulo());
//                }
//            }

        } finally {
            method.cerrarConexion();
        }


}
