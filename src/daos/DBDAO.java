package daos;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

/**
 *
 * @author THOR
 */
public class DBDAO {
    protected ObjectContainer db;
    
    protected DBDAO(){
    }
    
    protected void abrirConexion() {
        db = Db4oEmbedded.openFile("library.db4o");
    }

    protected void cerrarConexion() {
        try {
            db.close();
        } catch (Exception e) {
            System.out.println("No se pudo conectar con la BBDD");
        }
    }
}
