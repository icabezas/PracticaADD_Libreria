package test;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import db.Methods;
import jaxb.generated.*;

public class test {
    public static void main(String[] args) {

        //ObjectFactory objectFactory = new ObjectFactory();

        ObjectContainer baseDatos= Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),"library.db4o");
        LibroType libro1=new LibroType("titulo1");
        try {
            Methods.almacenarLibro(baseDatos, libro1);
            Methods.consultarQBEPLibrosISBN(baseDatos,"titulo1");
        }finally {
            Methods.cerrarConexion(baseDatos);
        }


    }

}
