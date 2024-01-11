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
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Consultar
 * @author Adrián PR
 */
public class Eliminar {
    
    //-Pedir datos
    public static String pedirID(){
        //Obtengo ID a eliminar:
        String idEliminar = JOptionPane.showInputDialog(null, 
                    "ID para eliminación:", "Operación:", 
                    JOptionPane.QUESTION_MESSAGE);       
        
        //Devuelvo ID:              
        return idEliminar;

    }//end pedirDatos
    
    //-Eliminar   
    public static int eliminar(Connection c, String sql){        
        PreparedStatement ps; 
        /* PreparedStatement           
           Las ? indican parámetros que la sentencia SQL va a requerir. 
           Nosotros tendremos que establecerlas ANTES de ejecutar la sentencia.
        */
        try{
           //1. Preparamos sentencia
           ps = c.prepareStatement(sql);          
           ps.setString(1, pedirID());//Establezco parámetro SQL             
                      
           //2. Ejecutamos
           /* Devuelve un entero:
                a)  número  --> filas insertadas/actualizadas/eliminadas
                b)  0       --> en caso que no se haya hecho nada
           */
           return ps.executeUpdate();
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "Error en operación", "Error", JOptionPane.ERROR_MESSAGE); 
           return -1;//error
        }//end catch        
    }//end insertar
    
    //MAIN:
    public static void main(String  args[]){
        //Variables y Creación de nueva conexión 
        /* ResultSet
           Contiene el resultado de la ejecución de una consulta SQL, y nos
           permite recorrer cada una de sus filas, columnas y trabajar directamente
           con ella como si fuese una tabla.
        */
        int resultado;        
        Conexion cx = new Conexion();
        
        //Conectamos con BD
        Connection cn = cx.conectar();
                        
        //Realizar operación:
        //-Probar a poner mal la consulta ...
        resultado = eliminar(cn, "DELETE FROM empleados WHERE id=?");
        
        //Informo sobre resultado
        switch (resultado){
            case -1 -> JOptionPane.showMessageDialog(null, "Excepción SQL", "Error", JOptionPane.ERROR_MESSAGE);
            case 0 -> JOptionPane.showMessageDialog(null, "No se ha podido realizar nada", "Error", JOptionPane.WARNING_MESSAGE);
            default -> JOptionPane.showMessageDialog(null, "Operación realizada con ÉXITO", "Todo OK", JOptionPane.INFORMATION_MESSAGE);            
        }//end switch
        
        
        //Desconectamos
        cx.desconectar();

    }//end Main
}//end Consultar
