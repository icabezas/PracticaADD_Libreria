package daos;

import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

/**
 *
 * @author THOR
 */
public class DBDAO {

    public DBDAO() {
        db = Db4oEmbedded.openFile("libreria.db4o");
    }

    public ObjectContainer db;

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
