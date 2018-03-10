package practicaadd_libreria;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import db.Methods;
import java.util.ArrayList;
import java.util.List;
import jaxb.generated.*;

public class PracticaADD_Libreria {

    public static void main(String[] args) {

        //ObjectFactory objectFactory = new ObjectFactory();
        ObjectContainer baseDatos = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "library.db4o");
        List<String> autores1 = new ArrayList<>();
        autores1.add("Ernest Cline");
        autores1.add("Lovecraft");
        List<String> categoria1 = new ArrayList<>();
        categoria1.add("Ciencia ficción");

        LibroType libro1 = new LibroType("Ready Player One", autores1, categoria1, "Multi", 2011, Byte.MIN_VALUE, 0, 0);
        LibroType libro2 = new LibroType("Libro2", autores1, categoria1, "Multi", 0, 0, 0, 0);
        List<LibroType> libros = new ArrayList<>();
        LibreriaType libreria1 = new LibreriaType(libros, "Libreria1");
        List<LibreriaType> colecciones = new ArrayList<>();
        colecciones.add(libreria1);
        UsuarioType admin = new UsuarioType("admin", "admin", "admin", true, colecciones);
        

        try {
            Methods.mostrarUsuarios(baseDatos);
            Methods.nuevoUsuario(baseDatos, admin);
            Methods.almacenarLibro(baseDatos, libro1);
            Methods.crearLibreriaParaUsuario(baseDatos, libreria1, admin);
            Methods.mostrarLibreriasUsuario(baseDatos, admin);
            
        } finally {
            Methods.cerrarConexion(baseDatos);
        }

    }

}
