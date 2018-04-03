/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import modelo.Usuario;

public class Swim {

    public static Usuario userSession;

    public static void main(String[] args) {

        Interfaz principal = new Interfaz();

        // para centrarlo
        principal.setLocationRelativeTo(null);

        principal.setVisible(true);

    }
}
