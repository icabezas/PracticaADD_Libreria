/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import com.db4o.ObjectSet;
import modelo.Genero;
import modelo.Libro;

/**
 * DAO PARA LA ADMINISTRACION DE LIBROS
 *
 */
public class LibroDAO extends DBDAO {

    public LibroDAO() {

    }
    //TODAS LAS FUNCIONES ABREN CONEXION Y CIERRAN DENTRO DE ELLAS MISMAS

    //CREAR LIBRO
    public void crearLibro(Libro libro, Genero genero) {
        //ABRIMOS CONEXION
        abrirConexion();
        
        libro.setIdLibro(getIdLibroLast());
        
        if (existeLibroPorISBN(libro.getIsbn()) == null) {
            try {
                db.store(libro);
            } catch (Exception ex) {
                System.out.println("No se ha podido guardar el objeto LibroGenero");
            }
        } else {
            System.out.println("Ya existe este libro");
        }

        //CERRAMOS CONEXION
        cerrarConexion();
    }
    
    //RETORNA EL ULTIMO IDLIBRO PARA CREAR UNO NUEVO
    public int getIdLibroLast(){
        ObjectSet resultado = db.query(Libro.class);
        if (resultado.isEmpty()) {
            System.out.println("No hay libros");
            return 1;
        }
        return resultado.size()+1;
    }

    //BORRAR LIBRO
    public void borrarLibro(Libro libro) {
        Libro dbLibro = existeLibroPorISBN(libro.getIsbn());
        if (dbLibro != null) {
            try {
                db.delete(dbLibro);
            } catch (Exception ex) {
                System.out.println("No se ha podido borrar");
            }
        } else {
            System.out.println("Problema al borrar");
        }
    }

    //MODIFICAR DATOS LIBRO
    public void modificarLibro(Libro libroOld, Libro libroNew) {
        if (libroOld.getIsbn() == libroNew.getIsbn()) {
            Libro dbLibroOld = existeLibroPorISBN(libroOld.getIsbn());
            if(dbLibroOld != null){
                dbLibroOld.setAnyo(libroNew.getAnyo());
                dbLibroOld.setAutor(libroNew.getAutor());
                dbLibroOld.setEdicion(libroNew.getEdicion());
                dbLibroOld.setIdioma(libroNew.getIdioma());
                dbLibroOld.setPrecio(libroNew.getPrecio());
                dbLibroOld.setTitulo(libroNew.getTitulo());
                try{
                    db.store(dbLibroOld);
                }catch(Exception ex){
                    System.out.println("No se ha podido realizar el update");
                }
            }
        } else {
            System.out.println("El isbn no puede modificarse");
        }
    }

    //EXISTE LIBRO
    public Libro existeLibroPorISBN(int isbn) {
        Libro libro = new Libro(isbn);
        Libro dbLibro = null;

        ObjectSet resultado = db.queryByExample(libro);
        if (!resultado.isEmpty()) {
            dbLibro = (Libro) resultado.next();
        }

        return dbLibro;
    }
}
