/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax;

import daos.ColeccionDAO;
import daos.LibroDAO;
import exceptiones.LibreriaExcepciones;
import modelo.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import org.xml.sax.SAXException;
import swing.Swim;

/**
 *
 * @author edust
 */
public class ImportXML {



    public void jreport(){
        try {
            //Class.forName()
            /*String reportPath = "xmlReport.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(reportPath);
            JasperPrint jp = JasperFillManager.fillReport(jr,null);
            JasperViewer.viewReport(jp);*/

            /*JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/helloHasperXML.jrxml");
            JRXmlDataSource xmlDataSource = new JRXmlDataSource("src/main/resources/data.xml","users//user");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), xmlDataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "report_from_xml.pdf");


            JasperReport jasperReport = JasperCompileManager.compileReport("D:\\Stucom\\AAD\\AAD-Library\\xmlReport.jrxml");
            JRXmlDataSource xmlDataSource = new JRXmlDataSource("D:\\Stucom\\AAD\\AAD-Library\\libros.xml", "libros//libro");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), xmlDataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "jreport_from_xml.pdf");*/

            String reportPath = "D:\\Stucom\\AAD\\AAD-Library\\xmlReport.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
            JRXmlDataSource xmlDataSource = new JRXmlDataSource("D:\\Stucom\\AAD\\AAD-Library\\libros.xml", "libros//libro");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), xmlDataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "jreport_from_xml.pdf");



        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }


    public void xml2Obj(String rutaFichero) throws LibreriaExcepciones {
        try {

            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            Manejador m = new Manejador();
            parser.parse(new File(rutaFichero), m);
//            for (int i = 0; i < m.getMisLibros().size(); i++ ) {
//                System.out.println(m.getMisLibros().get(i).getTitulo());// a[i]
//            }
            for (Libro l : m.getMisLibros()) {
                //POOOOL esto son tus objetos, quita el print y usa los getters que te he dejado debajo
                System.out.println("Datos del libro");
                System.out.println("ISBN: " + l.getIsbn());
                System.out.println("Titulo: " + l.getTitulo());
                System.out.println("Autor: " + l.getAutor());
                System.out.println("categoria: " + l.getGenero());
                System.out.println("lenguaje: " + l.getIdioma());
                System.out.println("anyo: " + l.getAnyo());
                System.out.println("edicion: " + l.getEdicion());
                System.out.println("precio: " + l.getPrecio());
                System.out.println("*************************");
                
                //comprobar si existe ya el libro en la DB
                LibroDAO libroDAO = new LibroDAO();
                Libro libroDB = libroDAO.existeLibroPorISBN(l.getIsbn());
                if(libroDB == null){
                    libroDAO.crearLibro(l, (ArrayList<String>) l.getGenero());
                }
                
                /*"titulo",
                "autor",
                "categoria",
                "lenguaje",
                "anyo",
                "edicion",
                "precio"*/
            }
            //crear coleccion en db
                ColeccionDAO coleccionDAO = new ColeccionDAO();
                
                coleccionDAO.crearColeccion(Swim.userSession.getIdUsuario(), "Colección importada " + coleccionDAO.getIdColeccionLast(), m.getMisLibros());
                
                
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ImportXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param args the command line arguments
     */
}
