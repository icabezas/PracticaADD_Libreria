package jaxbFunc;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import jaxb.generated.LibreriaType;
import sun.font.FontFamily;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExportPDF {

    //OJOOOOO NECESITAMOS EL PATH ABSOLUTO O NO FUNCIONAAAA
    //public static  final String DEST= "D:\\Stucom\\AAD\\AAD-Library\\gitanada.pdf";

    public void createPDF(String destination, LibreriaType misLibros)throws IOException {

        File file = new File(destination);
        file.getParentFile().mkdirs();

        //Inicializamos pdf writter
        FileOutputStream fos = new FileOutputStream(destination);
        PdfWriter writer = new PdfWriter(fos);

        //Inicializamos el documento PDF
        PdfDocument pdf = new PdfDocument(writer);

        //Inicializamos documento
        Document document = new Document(pdf);

        //////

        document.add(new Paragraph("*****************Library AAD*****************"));
        document.add(new Paragraph(""));
        document.add(new Paragraph("Eduardo Caballero"));
        document.add(new Paragraph("Irene Cabezas"));
        document.add(new Paragraph("Pol Galbarro"));
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));

        for (int i=0; i<misLibros.getLibro().size(); i++){
            document.add(new Paragraph("***************************************************"));
            document.add(new Paragraph("Book " + (i+1)));
            document.add(new Paragraph("ISBN: " + misLibros.getLibro().get(i).getIsbn()));
            document.add(new Paragraph("Title: " + misLibros.getLibro().get(i).getTitulo()));
            document.add(new Paragraph("Author: "+ misLibros.getLibro().get(i).getAutor()));
            document.add(new Paragraph("Category: "+misLibros.getLibro().get(i).getGenero()));
            document.add(new Paragraph("Language: "+misLibros.getLibro().get(i).getIdioma()));
            document.add(new Paragraph("Year: "+misLibros.getLibro().get(i).getAnyo()));
            document.add(new Paragraph("Print: "+misLibros.getLibro().get(i).getEdicion()));
            document.add(new Paragraph("Price: "+misLibros.getLibro().get(i).getPrecio()));
        }

        /////

        //Cerramos el documento
        document.close();
    }

}
