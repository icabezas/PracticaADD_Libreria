package sax;





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


