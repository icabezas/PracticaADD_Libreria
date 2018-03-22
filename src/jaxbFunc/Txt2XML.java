package jaxbFunc;

import jaxb.generated.LibreriaType;
import jaxb.generated.LibroType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Txt2XML {


    //Esta función sólo hay que decirle la ruta donde está el txt y el nombre que le queremos dar al fichero
    // RECUERDA poner el .xml
    public void txt2xml(String rutaFichero, String nombrelibreria) {
        try {
            LibreriaType misLibros = new LibreriaType ();
            BufferedReader in = new BufferedReader(new FileReader(rutaFichero));
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

            //Pol, esto es lo correcto, pero te lo dejo con el código repetido para que lo entiendas.
            /*Obj2XML o = new Obj2XML();
            o.obj2xml(misLibros, "libros.xml");*/
            //Si ves que lo entiendes bien, borro lo de abajo y se descomenta esto

            File miXML = new File(nombrelibreria);
            JAXBContext contexto = JAXBContext.newInstance(LibreriaType.class);
            Marshaller m = contexto.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(misLibros, miXML);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Txt2XML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Txt2XML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(Txt2XML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
