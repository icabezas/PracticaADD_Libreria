package sax;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class test {

    public static void main(String[] args) throws IOException {

        //EJEMPLO PARA POL, para ejecutar SAX sólo tienes que pasarle la ruta como parámetro. :*
        //De xml a objetos (IMPORTAR librería)
        Utils utils = new Utils();
        utils.xml2Obj("libreria.xml");

    }
}


