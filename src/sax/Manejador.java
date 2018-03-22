/*
 * Clase para manejar los eventos de SAX al leer el XML
 */
package sax;

import jaxb.generated.*;
import java.util.ArrayList;
import java.util.Collections;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author edu
 */
public class Manejador extends DefaultHandler {

    private ArrayList<LibroType> misLibros;
    private LibroType libroActual;
    private String elementoActual;
//    private Libro[] a;

    public Manejador() {
        misLibros = new ArrayList<>();
//        a = new Libro[4];
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atrbts) throws SAXException {
        if (qName.equals("libro")) {
            // Creamos un libro
            libroActual = new LibroType();
            // Guardar el atributo, en este caso lo parsea a INT por ser el ISBN, si necesitamos string, simplemente hay que quitarlo
            libroActual.setISBN(Integer.parseInt(atrbts.getValue(atrbts.getQName(0))));
        } else {
            elementoActual = qName;
        }

    }

    @Override
    public void endElement(String string, String string1, String qName) throws SAXException {
        if (qName.equals("libro")) {
            misLibros.add(libroActual);
        }
    }
/*"titulo",
    "autor",
    "categoria",
    "lenguaje",
    "anyo",
    "edicion",
    "precio"*/
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contenido = new String(ch).substring(start, start + length);
        switch (elementoActual) {
            case "titulo":
                libroActual.setTitulo(contenido);
                break;
            case "autor":
                libroActual.setAutor(Collections.singletonList(contenido));
                break;
            case "categoria":
                libroActual.setCategoria(Collections.singletonList(contenido));
                break;
            case "lenguaje":
                libroActual.setLenguaje(contenido);
                break;
            case "anyo":
                libroActual.setAnyo(Short.parseShort(contenido));
                break;
            case "edicion":
                libroActual.setEdicion(Byte.valueOf(contenido));
                break;
            case "precio":
                libroActual.setPrecio(Float.parseFloat(contenido));
                break;
        }
        // Borramos la variable atributo una vez leído el contenido
        elementoActual = "";
    }

    public ArrayList<LibroType> getMisLibros() {
        return misLibros;
    }

}
