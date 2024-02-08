/*
 *  LICENCIA Jose Javier BO
 *  Aquí: nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt para cambiar la licencia.
 *  Aquí: nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java para editar el tipo de plantilla.  
 *  Lista de paquetes:
 */

package ud2_02.modelo.mysql.dml;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DLM para hacer select en la base de datos. Tiene opcion de hacer un SELECT * y un SELECT * WHERE
 */
public class Select {
    
    //ultimo error producido
    private String ultimoError="";
     
    /**
     * Ejecuta un SELECT *
     * @param c Conexion
     * @param tabla Nombre de la table
     * @return  El resultset fruto de la consulta
     */
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
