/*
 *  LICENCIA Jose Javier BO
 *  Aquí: nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt para cambiar la licencia.
 *  Aquí: nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java para editar el tipo de plantilla.  
 *  Lista de paquetes:
 */

package ud2_03.modelo.dml;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DLM para hacer select en la base de datos. Tiene opcion de hacer un SELECT * y un SELECT * WHERE
 */
public class Select {
    
    //ultimo error producido
    private static String ultimoError="";
    
    
    /**
     * Ejecuta un SELECT * WHERE campo=valor
     * 
     * @param c Conexion
     * @param tabla Nombre de la tabla
     * @param campoClave Nombre del campo en el WHERE
     * @param valorClave Valor del campo en el WHERE
     * 
     * @return  El resultset fruto de la consulta
     */
    public  static ResultSet selectWhere(Connection c, String tabla, String campoClave, String valorClave){        
        PreparedStatement ps; 
        try{
           //Preparamos la sentencia
           ps = c.prepareStatement("SELECT * FROM "+tabla+" WHERE "+campoClave+"=?");
           ps.setString(1, valorClave);
           //Ejecutamos
           return ps.executeQuery();
        }catch(SQLException e){
           ultimoError = e.getMessage();
           return null;
        }
    }
 
    
    /**
     * Ejecuta un SELECT *
     * @param c Conexion
     * @param tabla Nombre de la table
     * @return  El resultset fruto de la consulta
     */
    public static ResultSet selectAll(Connection c, String tabla){        
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
