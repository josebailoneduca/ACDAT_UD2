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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Consultar
 * @author Adrián PR
 */
public class Consultar {
    //MÉTODOS:
    //-Consultar   
    public static ResultSet consulta(Connection c, String sql){        
        PreparedStatement ps; 
        /* PreparedStatement
           Nos permite (entre otras muchas cosas) ejecutar diferententes 
           sentencias SQL, ya sean:
           - Consultar --> executeQuery()
           - Insertar, Actualizar(modificar) o Eliminar  es decir todo el DML
             (Data Manipulated Language) -> executeUpdate()
        */
        try{
           ps = c.prepareStatement(sql);
           return ps.executeQuery();
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "Error en consulta", "Error", JOptionPane.ERROR_MESSAGE); 
           return null;
        }
        
    }
    
    //-Main
    public static void main(String  args[]){
        //Variables y Creación de nueva conexión 
        /* ResultSet
           Contiene el resultado de la ejecución de una consulta SQL, y nos
           permite recorrer cada una de sus filas, columnas y trabajar directamente
           con ella como si fuese una tabla.
        */
        ResultSet rs;        
        Conexion cx = new Conexion();
        
        //Conectamos con BD
        Connection cn = cx.conectar();
        
        //Realizar consulta:
        //-Probar a poner mal la consulta ...
        rs = consulta(cn, "SELECT * FROM empleados");
        
        //Muestro consulta
        try{
            String msgTexto="----RESULADO CONSULTA----\n";
            String fila="";
            while(rs.next()){
                fila +="ID:"; fila +=  rs.getString("ID"); 
                fila+=" Nombre:"; fila +=  rs.getString("nombre");
                fila+=" Apellidos:"; fila +=  rs.getString("apellidos");
                fila+=" DNI:"; fila +=  rs.getString("DNI");
                fila+=" Sueldo:"; fila +=  rs.getString("sueldo");
                fila += "\n";
                msgTexto += fila;//agrego fila
                fila ="";//reseteo fila
            }
            msgTexto += "-----------------------";
            System.out.println(msgTexto);
        }catch(SQLException|NullPointerException e){
            JOptionPane.showMessageDialog(null, "Error leyendo consulta", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        //Desconectamos
        cx.desconectar();

    }//end Main
}//end Consultar
