package jaxbFunc;

/*
 * Ejemplo de JAXB
 */

import jaxb.generated.*;
import jaxbFunc.ExportXML;
import  modelo.*;

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
            Libro libroActual;
            String linea;
            while ((linea = in.readLine()) != null) {
                libroActual = new Libro();
                libroActual.setIsbn(Integer.parseInt(linea));
                linea = in.readLine();
                libroActual.setTitulo(linea);
                linea = in.readLine();
                libroActual.setAutor(linea);
                linea = in.readLine();
                libroActual.setGenero(Collections.singletonList(linea));
                linea = in.readLine();
                libroActual.setIdioma(linea);
                linea = in.readLine();
                libroActual.setAnyo(Integer.parseInt(linea));
                linea = in.readLine();
                libroActual.setEdicion(Integer.parseInt(linea));
                linea = in.readLine();
                libroActual.setPrecio(Double.parseDouble(linea));
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
            ExportXML o = new ExportXML();
            o.obj2xml(misLibros, "libros.xml","");

            //De txt a XML (Con esto podemos hacer varias cositas, importar y exportar)
            ExportTXT t = new ExportTXT();
            t.txt2xml("libros.txt", "librostxt.xml","");

            //De objetos a PDF, necesita el path y una librería (LibreriaType)
            //OJOOOOO NECESITAMOS EL PATH ABSOLUTO O NO FUNCIONAAAA
            ExportPDF pdf = new ExportPDF();
            pdf.createPDF("D:\\Stucom\\AAD\\AAD-Library\\gitanada.pdf", misLibros);



        } catch (FileNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
