/*
 * Clase para manejar los eventos de SAX al leer el XML
 */
package sax;

import modelo.*;
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

    private ArrayList<Libro> misLibros;
    private Libro libroActual;
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
            libroActual = new Libro();
            // Guardar el atributo, en este caso lo parsea a INT por ser el ISBN, si necesitamos string, simplemente hay que quitarlo
            libroActual.setIsbn(Integer.parseInt(atrbts.getValue(atrbts.getQName(0))));
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
                libroActual.setAutor(contenido);
                break;
            case "genero":
                libroActual.setGenero(contenido);
                break;
            case "idioma":
                libroActual.setIdioma(contenido);
                break;
            case "anyo":
                libroActual.setAnyo(Integer.parseInt(contenido));
                break;
            case "edicion":
                libroActual.setEdicion(Integer.parseInt(contenido));
                break;
            case "precio":
                libroActual.setPrecio(Float.parseFloat(contenido));
                break;
        }
        // Borramos la variable atributo una vez leído el contenido
        elementoActual = "";
    }

    public ArrayList<Libro> getMisLibros() {
        return misLibros;
    }

}
