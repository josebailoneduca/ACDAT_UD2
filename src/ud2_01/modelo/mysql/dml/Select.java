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
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Consultar
 */
public class Select {
    private String ultimoError="";
    public  ResultSet selectWhere(Connection c, String tabla, String campo, String valor){        
        PreparedStatement ps; 
        try{
           //Preparamos la sentencia
           ps = c.prepareStatement("SELECT * FROM "+tabla+" WHERE "+campo+"=?");
           ps.setString(1, valor);
           //Ejecutamos
           return ps.executeQuery();
        }catch(SQLException e){
           ultimoError = e.getMessage();
           return null;
        }
    }
 
    public ResultSet selectAll(Connection c, String tabla){        
        PreparedStatement ps; 
        try{
            ps = c.prepareStatement("SELECT * FROM "+tabla);
           return ps.executeQuery();
        }catch(SQLException e){
           ultimoError=e.getMessage();
           return null;
        }
    }
}//end Consultar
