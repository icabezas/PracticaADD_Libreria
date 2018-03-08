package db;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import jaxb.generated.LibroType;

public class Methods {

    //Para cerrar conexión
    public static void cerrarConexion(ObjectContainer baseDatos){
        try{
            baseDatos.close();
        }catch(Exception e){
            System.out.println("No se pudo conectar con la BBDD");
        }
    }

    //Para almacenar objetos en la bbdd
    //@param baseDatos el objeto que representa la bbdd en la que se almacenará el libro
    //@param LibroType libro es el libro que se desea almacenar
    public static void almacenarLibro(ObjectContainer baseDatos, LibroType libro){
        try {
            baseDatos.store(libro);
            System.out.println("Se ha guardado el libro " + libro.getTitulo());
        }catch (Exception e) {
            System.out.println("Se ha producido un error, no se ha podido insertar");
        }
    }
    
    public static List<Libro> exiteLibroISBN(String isbn){
        
    }

    //Permite mostrar un libro de la bbdd
    //@param baseDatos el objeto que representa la bbdd en la que se almacenará el libro
    public static void imprimirResultadoConsulta(ObjectSet resultado){
        System.out.println("Recuperados " + resultado.size()+ " libros");
        while (resultado.hasNext()){
            System.out.println(resultado.next());
        }
    }

    //Permite consultar un libro de la bbdd
    //@param baseDatos el objeto que representa la bbdd en la que se almacenará el libro
    //@param titulo (hay que cmabiar por ISBN, que es el identificador único)
    public static void consultarQBEPLibrosISBN(ObjectContainer baseDatos, String isbn){
        LibroType libro = new LibroType(isbn);
        ObjectSet resultado = baseDatos.queryByExample(libro);
        imprimirResultadoConsulta(resultado);
    }

}
