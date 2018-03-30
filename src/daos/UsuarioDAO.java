/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;

/**
 * CLASE PARA LA ADMINISTRACION DE USUARIO
 *
 */
public class UsuarioDAO {

    private ObjectContainer db;

    public UsuarioDAO() {
    }
    //TODAS LAS FUNCIONES ABREN CONEXION Y CIERRAN DENTRO DE ELLAS MISMAS

    //CREAR USUARIO
    public void crearUsuario(String username, String password) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(getIdUsuarioLast());
        usuario.setUsername(username);
        abrirConexion();
        if (existeUsuario(usuario) == null) {
            try {
                usuario.setIsAdmin(false);
                usuario.setPassword(password);
                db.store(usuario);
            } catch (Exception ex) {
                System.out.println("Este usuario ya existe");
            }
        }
    }

    //BORRAR USUARIO
    public void borrarUsuario(String username) {
        abrirConexion();
        ObjectSet result = null;

        result = db.queryByExample(new Usuario(username));
        if (!result.isEmpty()) {
            Usuario userDelete = (Usuario) result.next();
            try {
                db.delete(userDelete);
                System.out.println("Deleted Usuario " + username);
            } catch (Exception ex) {
                System.out.println("DB: No se ha podido borrar el usuario " + username);
            }
        } else {
            System.out.println("No se ha encontrado el usuario indicado");
        }
        //CERRAMOS CONEXION
        cerrarConexion();
    }

    //LOGIN USUARIO
    public boolean loginUsuario(String username, String password) {
        Usuario usuario = existeUsuario(new Usuario(username));
        if (usuario.getUsername().equals(username) && usuario.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    //MODIFICAR USUARIO
    //SOLO SE PUEDE MODIFICAR LA PASSWORD DE MOMENTO
    public void modificarUsuario(Usuario usuarioOld, Usuario usuarioNew) {
        abrirConexion();
        if (usuarioOld.getUsername() == usuarioNew.getUsername()) {
            ObjectSet result = db.queryByExample(new Usuario(usuarioOld.getIdUsuario()));
            if (!result.isEmpty()) {
                Usuario newUsuario = (Usuario) result.next();
                newUsuario.setPassword(usuarioNew.getPassword());
                try {
                    db.store(newUsuario);
                    System.out.println("Actualizado");
                } catch (Exception ex) {
                    System.out.println("No se ha podido realizar el update");
                }
            }
        } else {
            System.out.println("El username no puede modificarse");
        }

        //CERRAMOS CONEXION
        cerrarConexion();
    }

    //EXISTE USUARIO
    public Usuario existeUsuario(Usuario usuario) {
        abrirConexion();

        Usuario userDB = new Usuario();

        ObjectSet resultado = db.queryByExample(usuario);
        if (!resultado.isEmpty()) {
            userDB = (Usuario) resultado.next();
        } else {
            userDB = null;
        }

        cerrarConexion();
        return userDB;
    }

    //RETORNA LISTA DE TODOS LOS USUARIOS EN BBDD
    public List<Usuario> getAllUsuariosDB() {
        abrirConexion();
        List<Usuario> usuariosDB = new ArrayList<>();
        ObjectSet resultado = db.query(Usuario.class);
        if (!resultado.isEmpty()) {
            for (int i = 0; i < resultado.size(); i++) {
                Usuario user = (Usuario) resultado.next();
                usuariosDB.add(user);
            }
        } else {
            System.out.println("No hay usuarios en la db");
        }
        cerrarConexion();
        return usuariosDB;
    }

    //CONVIERTE UN USUARIO NORMAL A ADMINISTRADOR
    public void makeUserAdmin(Usuario usuario) {
        abrirConexion();
        ObjectSet resultado = db.queryByExample(usuario);
        if (!resultado.isEmpty()) {
            Usuario userDB = (Usuario) resultado.next();
            if (!userDB.isIsAdmin()) {
                userDB.setIsAdmin(true);
                try {
                    db.store(userDB);
                } catch (Exception ex) {
                    System.out.println("Problema al guardar usuario");
                }
            }else{
                System.out.println("Este usuario ya es administrador");
            }
        }
        cerrarConexion();
    }

    //RETORNA EL ULTIMO IDCOLECCION PARA CREAR UNA NUEVA
    public int getIdUsuarioLast() {
        abrirConexion();
        ObjectSet resultado = db.query(Usuario.class);
        int total = resultado.size() + 1;
        cerrarConexion();
        return total;
    }

    public void abrirConexion() {
        try {
            db = Db4oEmbedded.openFile("libreria.db4o");
        } catch (Exception ex) {
            System.out.println("No se ha podido conectar con la base de datos");
        }
    }

    public void cerrarConexion() {
        try {
            db.close();
        } catch (Exception e) {
            System.out.println("No se pudo conectar con la BBDD");
        }
    }
}
