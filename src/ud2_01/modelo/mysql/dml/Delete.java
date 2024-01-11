/*
 *  LICENCIA ADRIÁN PR
 *  Aquí: nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt para cambiar la licencia.
 *  Aquí: nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java para editar el tipo de plantilla.  
 *  Lista de paquetes:
 */

package ud2_01.modelo.mysql.dml;

 
/*
    Los siguientes import ponerlos manualmente porque 
    quizás no los detecte Netbeans de forma automática
*/
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;


/**
 * Consultar
 * @author Adrián PR
 */
public class Delete {
 
    private String ultimoError="";
    
    public int deleteWhere(Connection c, String tabla, String campo,String valor){        
        PreparedStatement ps; 
        try{
            String sql="DELETE FROM "+tabla+" WHERE "+campo+"=?";
            ps = c.prepareStatement(sql);
            ps.setString(1, valor);
           return ps.executeUpdate();
        }catch(SQLException e){
            ultimoError = e.getMessage();
            return -1;//error
        }//end catch        
    }//end delete
}//end Consultar
