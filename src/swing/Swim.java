/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.UIManager;
import modelo.Usuario;

public class Swim {

    public static Usuario userSession;
    public static List<Image> icons;

    public static void main(String[] args) throws IOException {
        
        ponerIconosFrames();
        
        Interfaz principal = new Interfaz();

        principal.setLocationRelativeTo(null);

        principal.setVisible(true);

        principal.setIconImages(Swim.icons);

    }

    public static void ponerIconosFrames() throws MalformedURLException, IOException {
        //PARA PONER ICONOS EN LOS FRAMES TITLES
        URL url16 = new URL("https://i.imgur.com/5ickJfa.png");
        URL url24 = new URL("https://i.imgur.com/TjxzPiA.png");
        URL url32 = new URL("https://i.imgur.com/7WgtvVL.png");
        URL url64 = new URL("https://i.imgur.com/zXGQiH0.png");

        icons = new ArrayList<Image>();
        icons.add(ImageIO.read(url16));
        icons.add(ImageIO.read(url24));
        icons.add(ImageIO.read(url32));
        icons.add(ImageIO.read(url64));

        //PARA CAMBIAR EL TEMA DE SWING
//        try {
//            UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
