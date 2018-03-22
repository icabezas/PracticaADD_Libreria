package jaxbFunc;

import jaxb.generated.LibreriaType;
import jaxb.generated.LibroType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.*;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Obj2XML {

    //Hay que pasarle como parámetro una libreria y el nombre que le queremos dar al fichero
    // RECUERDA poner el .xml
    public void obj2xml(LibreriaType misLibros, String nombrelibreria) {
        File miXML = new File(nombrelibreria);
        JAXBContext contexto = null;
        try {
            contexto = JAXBContext.newInstance(LibreriaType.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Marshaller m = null;
        try {
            m = contexto.createMarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        try {
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (PropertyException e) {
            e.printStackTrace();
        }
        try {
            m.marshal(misLibros, miXML);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
