package db;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.util.ArrayList;
import java.util.List;
import jaxb.generated.LibroType;

public class Methods {

    //Para cerrar conexión
    public static void cerrarConexion(ObjectContainer baseDatos) {
        try {
            baseDatos.close();
        } catch (Exception e) {
            System.out.println("No se pudo conectar con la BBDD");
        }
    }

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
    public static List<LibroType> existeLibroISBN(ObjectContainer baseDatos, int isbn) {
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

}
