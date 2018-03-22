package jaxbFunc;

/*
 * Ejemplo de JAXB
 */

import jaxb.generated.*;
import jaxbFunc.Obj2XML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author edu
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            LibreriaType  misLibros = new LibreriaType ();
            BufferedReader in = new BufferedReader(new FileReader("libros.txt"));
            LibroType libroActual;
            String linea;
            while ((linea = in.readLine()) != null) {
                libroActual = new LibroType();
                libroActual.setISBN(Integer.parseInt(linea));
                linea = in.readLine();
                libroActual.setTitulo(linea);
                linea = in.readLine();
                libroActual.setAutor(Collections.singletonList(linea));
                linea = in.readLine();
                libroActual.setCategoria(Collections.singletonList(linea));
                linea = in.readLine();
                libroActual.setLenguaje(linea);
                linea = in.readLine();
                libroActual.setAnyo(Short.parseShort(linea));
                linea = in.readLine();
                libroActual.setEdicion(Byte.valueOf(linea));
                linea = in.readLine();
                libroActual.setPrecio(Float.parseFloat(linea));
                misLibros.getLibro().add(libroActual);
                /*"titulo",
                "autor",
                "categoria",
                "lenguaje",
                "anyo",
                "edicion",
                "precio"*/
            }

            //Los objetos (LibreriaType), como soy un poco guau, ya me los traigo del txt en lugar de picarlos a mano

            //De Objetos a XML (EXPORTAR librería)
            Obj2XML o = new Obj2XML();
            o.obj2xml(misLibros, "libros.xml");

            //De txt a XML (Con esto podemos hacer varias cositas, importar y exportar)
            Txt2XML t = new Txt2XML();
            t.txt2xml("libros.txt", "librostxt.xml");

            //De objetos a PDF, necesita el path y una librería (LibreriaType)
            //OJOOOOO NECESITAMOS EL PATH ABSOLUTO O NO FUNCIONAAAA
            PDF pdf = new PDF();
            pdf.createPDF("D:\\Stucom\\AAD\\AAD-Library\\gitanada.pdf", misLibros);



        } catch (FileNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
