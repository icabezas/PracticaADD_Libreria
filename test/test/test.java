package test;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import db.Methods;
import java.util.ArrayList;
import java.util.List;
import jaxb.generated.*;

public class test {
    public static void main(String[] args) {

        //ObjectFactory objectFactory = new ObjectFactory();

        ObjectContainer baseDatos= Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),"library.db4o");
        List<String> autores1 = new ArrayList<>();
        autores1.add("Ernest Cline");
        autores1.add("Lovecraft");
        List<String> categoria1 = new ArrayList<>();
        categoria1.add("Ciencia ficción");
        
        LibroType libro1 = new LibroType("Ready Player One", autores1,categoria1, "Multi", 2011, Byte.MIN_VALUE, 0, 0);
        try {
            Methods.almacenarLibro(baseDatos, libro1);
            Methods.existeLibroISBN(baseDatos,123456789);
        }finally {
            Methods.cerrarConexion(baseDatos);
        }


    }

}
