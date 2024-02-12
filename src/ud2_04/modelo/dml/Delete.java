/*
 *  LICENCIA Jose Javier BO
 *  Aquí: nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt para cambiar la licencia.
 *  Aquí: nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java para editar el tipo de plantilla.  
 *  Lista de paquetes:
 */

package ud2_04.modelo.dml;

 
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * DML para borrar registros
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Delete {
 
    //ultimo error producido
    private static String ultimoError="";
    
    
    /**
     * Borra un registro usando la conexion C de la tabla especificada 
     * WHERE campo = valor
     * 
     * @param c Conexion
     * @param tabla Tabla a usar
     * @param campoClave Nombre del campo de la setntencia WHERE
     * @param valorClave Valor del campo de la sentencia WHERE
     * 
     * @return Cantidad de filas modificadas o -1 si hay algun error
     */
    public static int deleteWhere(Connection c, String tabla, String campoClave,String valorClave){        
        PreparedStatement ps; 
        try{
            String sql="DELETE FROM "+tabla+" WHERE "+campoClave+"=?";
            ps = c.prepareStatement(sql);
            ps.setString(1, valorClave);
           return ps.executeUpdate();
        }catch(SQLException e){
            ultimoError = e.getMessage();
            return -1;//error
        }//end catch        
    }//end delete
}//end Consultar
