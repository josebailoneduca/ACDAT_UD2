/*
 *  LICENCIA ADRIÁN PR
 *  Aquí: nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt para cambiar la licencia.
 *  Aquí: nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java para editar el tipo de plantilla.  
 *  Lista de paquetes:
 */

package ud2_01.modelo.mysql.dml.tmp;

/*
    Los siguientes import ponerlos manualmente porque 
    quizás no los detecte Netbeans de forma automática
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Conexion
 * @author Adrián PR
 */
public class Conexion {
    //ATRIBUTOS:
    //--------------------------------------------------------------------------
    //- Datos de configuración del acceso a la BD
    private String bd = "trabajosempleados";
    private String url = "jdbc:mysql://localhost:3306/";
    private String user ="root"; //Por defecto
    private String pass = "";
    private String driver ="com.mysql.cj.jdbc.Driver";//La versión 8 va con "cj"
    //- Conexion
    private static Connection cx;//Clase Connection para control de la conexión
    //-Columnas de la tabla empleados
    private static String [] camposEmpleados = {"nombre", "apellidos", "dni", "sueldo"};
    //-Mensajes
    private String mConect = "Conexion a BD establecida";
    private String mDesconect = "Desconexion a BD realizada correctamente";
    private String mError = "Se ha producido un error";
    
    
    //MÉTODOS:
    //--------------------------------------------------------------------------
    //-Constructor:
    public Conexion() {}
    
    //-Conexion
    public Connection conectar(){
        try {
            Class.forName(driver);//Nombre del driver
            cx = DriverManager.getConnection(url+bd, user, pass);
            System.out.println(mConect);
            JOptionPane.showMessageDialog(null, mConect);
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, mError, "Fallo en conexión/desconexión", JOptionPane.ERROR_MESSAGE);
            System.out.println("NO SE HA PODIDO CONECTAR A LA Base de datos: "+bd);
            //Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cx;
    }//end conectar
    //-Desconexion
    public void desconectar(){
        try {
            cx.close();
            System.out.println(mDesconect);
            JOptionPane.showMessageDialog(null, mDesconect);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, mError, "Fallo en conexión/desconexión", JOptionPane.ERROR_MESSAGE);
            //Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//end desconectar
    
    
    //- Get de la conexión establecida a la BD
    // OJO: si no se ha llamado antes al método "conectar()" este objeto
    // estará a null!
    public static Connection getCx() {
        return cx;
    }
    
    //- Get de las columnas que tiene la tabla empleados
    public static String[] getCamposEmpleados() {
        return camposEmpleados;
    }    
    
    //-MAIN
    public static void main(String  args[]){
        Conexion cx = new Conexion();
        cx.conectar();
        cx.desconectar();
    }
}//end Conexion
