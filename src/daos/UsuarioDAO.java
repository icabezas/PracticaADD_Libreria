/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import exceptiones.LibreriaExcepciones;
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
    public void crearUsuario(String username, String password) throws LibreriaExcepciones {
        Usuario usuario = new Usuario();
        usuario.setUsername(username.toLowerCase());
        usuario.setIsAdmin(false);
        if (existeUsuario(username.toLowerCase()) == null) {
            usuario.setIdUsuario(getIdUsuarioLast());
            usuario.setPassword(password);
            abrirConexion();
            try {
                db.store(usuario);
            } catch (Exception ex) {
                cerrarConexion();
                throw new LibreriaExcepciones("No se ha podido almacenar el usuario");
            }
            cerrarConexion();
        } else {
            throw new LibreriaExcepciones("Este usuario ya existe");
        }
    }

    //BORRAR USUARIO
    public void borrarUsuario(String username) throws LibreriaExcepciones {
        abrirConexion();
        ObjectSet result = null;
        result = db.queryByExample(new Usuario(username.toLowerCase()));
        if (!result.isEmpty()) {
            Usuario userDelete = (Usuario) result.next();
            try {
                db.delete(userDelete);
                System.out.println("Deleted Usuario " + username.toLowerCase());
            } catch (Exception ex) {
                cerrarConexion();
                throw new LibreriaExcepciones("No se ha podido borrar el usuario " + username.toLowerCase());
            }
        } else {
            cerrarConexion();
            throw new LibreriaExcepciones("No se ha encontrado el usuario indicado");
        }
        //CERRAMOS CONEXION
        cerrarConexion();
    }

    //LOGIN USUARIO
    public Usuario loginUsuario(String username, String password) throws LibreriaExcepciones {
        Usuario loginUser = new Usuario(username.toLowerCase());
        Usuario usuario = existeUsuario(loginUser.getUsername().toLowerCase());
        if (usuario != null) {
            if (usuario.getUsername().equals(username.toString().toLowerCase()) && usuario.getPassword().equals(password)) {
                return usuario;
            } else {
                throw new LibreriaExcepciones("El nombre de usuario o la contraseña son incorrectos");
            }
        } else {
            throw new LibreriaExcepciones("El nombre de usuario o la contraseña son incorrectos");
        }
    }

    //MODIFICAR USUARIO
    //SOLO SE PUEDE MODIFICAR LA PASSWORD DE MOMENTO
    public void modificarPasswordUsuario(Usuario usuarioOld, Usuario usuarioNew) throws LibreriaExcepciones {
        if (usuarioOld.getUsername().equals(usuarioNew.getUsername())) {
            abrirConexion();
            ObjectSet result = db.queryByExample(new Usuario(usuarioOld.getIdUsuario()));
            if (!result.isEmpty()) {
                Usuario newUsuario = (Usuario) result.next();
                newUsuario.setPassword(usuarioNew.getPassword());
                try {
                    db.store(newUsuario);
                } catch (Exception ex) {
                    cerrarConexion();
                    throw new LibreriaExcepciones("No se ha podido modificar el usuario");
                }
            }
            cerrarConexion();
        } else {
            throw new LibreriaExcepciones("El username no se puede modificar");
        }
    }

    //EXISTE USUARIO
    public Usuario existeUsuario(String username) throws LibreriaExcepciones {
        abrirConexion();

        Usuario userDB = new Usuario();
        Usuario userExists = new Usuario(username.toLowerCase());
        ObjectSet resultado = db.queryByExample(userExists);
        if (!resultado.isEmpty()) {
            userDB = (Usuario) resultado.next();
        } else {
            userDB = null;
        }

        cerrarConexion();
        return userDB;
    }

    //RETORNA LISTA DE TODOS LOS USUARIOS EN BBDD
    public ArrayList<Usuario> getAllUsuariosDB() throws LibreriaExcepciones {
        abrirConexion();
        ArrayList<Usuario> usuariosDB = new ArrayList<>();
        ObjectSet resultado = db.query(Usuario.class);
        if (!resultado.isEmpty()) {
            for (int i = 0; i < resultado.size(); i++) {
                Usuario user = (Usuario) resultado.next();
                usuariosDB.add(user);
            }
        } else {
            cerrarConexion();
            throw new LibreriaExcepciones("No hay usuarios en la DB");
        }
        cerrarConexion();
        return usuariosDB;
    }

    //CONVIERTE UN USUARIO NORMAL A ADMINISTRADOR
    public void changeUserPrivileges(String username, boolean makeAdmin) throws LibreriaExcepciones {
        abrirConexion();
        Usuario usuario = new Usuario(username.toString().toLowerCase());
        ObjectSet resultado = db.queryByExample(usuario);
        if (!resultado.isEmpty()) {
            Usuario userDB = (Usuario) resultado.next();
            if (userDB.isIsAdmin() && makeAdmin) {
                cerrarConexion();
                throw new LibreriaExcepciones("Este usuario ya es administrador");
            } else if (userDB.isIsAdmin() && !makeAdmin) {
                userDB.setIsAdmin(false);
            } else if (!userDB.isIsAdmin() && makeAdmin) {
                userDB.setIsAdmin(true);
            } else if (!userDB.isIsAdmin() && !makeAdmin) {
                cerrarConexion();
                throw new LibreriaExcepciones("Este usuario no tiene privilegios");
            }
            try {
                db.store(userDB);
            } catch (Exception ex) {
                cerrarConexion();
                throw new LibreriaExcepciones("No se ha podido modificar los privilegios del usuario");
            }
        } else {
            cerrarConexion();
            throw new LibreriaExcepciones("No se ha encontrado el usuario " + username);
        }
        cerrarConexion();
    }

    //RETORNA EL ULTIMO IDCOLECCION PARA CREAR UNA NUEVA
    public int getIdUsuarioLast() throws LibreriaExcepciones {
        abrirConexion();
        ObjectSet resultado = db.query(Usuario.class);
        int total = resultado.size() + 1;
        cerrarConexion();
        return total;
    }

    public void abrirConexion() throws LibreriaExcepciones {
        try {
            db = Db4oEmbedded.openFile("libreria.db4o");
        } catch (Exception ex) {
            throw new LibreriaExcepciones("No se ha podido conectar con la base de datos");
        }
    }

    public void cerrarConexion() throws LibreriaExcepciones {
        try {
            db.close();
        } catch (Exception e) {
            throw new LibreriaExcepciones("No se ha podido conectar con la base de datos");
        }
    }

    //TESTING
    public void showAllUsuariosDB() throws LibreriaExcepciones {
        ArrayList<Usuario> usersDB = getAllUsuariosDB();
        for (Usuario user : usersDB) {
            System.out.println("ID USUARIO: " + user.getIdUsuario()
                    + "\nUSERNAME: " + user.getUsername()
                    + "\nPASSWORD: " + user.getPassword()
                    + "\nIS ADMIN: " + user.isIsAdmin());
        }
    }
}
