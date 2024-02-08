/*
 *  LICENCIA Jose Javier BO
 *  Aquí: nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt para cambiar la licencia.
 *  Aquí: nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java para editar el tipo de plantilla.  
 *  Lista de paquetes:
 */

package ud2_03.modelo.dml;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;


/**
 * DLM para hacer update en la base de datos.
 * @author Jose Javier Bailon Ortiz
 */
public class Update {
 
    //ultimo error producido
    private static String ultimoError="";
    
    
    /**
     * Ejecuta un UPDATE WHERE
     * 
     * 
     * 
     * Los nombres de los campos y los valores deben especificarse en dos arrays.
     * 
     * Dentro construye el sql usando esos valores para componer una sentencia SQL
     * de update
     * 
     * 
     * @param c Conexion
     * @param tabla Nombre de la tabla
     * @param campos Lista de campos a actualizar
     * @param valores Lista de valores a asignar
     * @param campoClave Nombre del campo para WHERE
     * @param valorClave Valor para WHERE
     * @return Numero de filas modificadas o -1 si ha habido un error
     * 
     * @throws IllegalArgumentException  Si los arrays de campos y valores tienen longitud diferente
     */
    public static int updateWhere(Connection c, String tabla, String[]campos,String[] valores,String campoClave,String valorClave) throws IllegalArgumentException{        
        
        //comprobar validez de longitud de arrays de campos y valores
        if (campos.length!=valores.length)
            throw new IllegalArgumentException("Numero diferente de campos y valores");

        
        //PREPARACION DE LA SENTENCIA CONCATENANDO LOS NOMBRES DE LOS CAMPOS Y "=?"
        PreparedStatement ps; 
        try{
            //COMPONER SENTENCIA SQL
            String sql="UPDATE "+tabla+" SET "+
                    
            //campos y sus correspondientes =?
            Arrays.stream(campos).collect(Collectors.joining("=?, "))+
                    
            //=? final
            "=? ";
            
            //tramo WHERE de la sentencia
            sql+="WHERE "+campoClave+"=?";
            
            
            //CREAR EL PREPARED STATEMENT
            ps = c.prepareStatement(sql);

            //ASIGNAR VALORES A PARAMETROS
            int i=0;
            //parametros de campos
            for (i=0;i<valores.length;i++)
                ps.setString(i+1, valores[i]);
            //parametro del WHERE
            ps.setString(i+1, valorClave);
            
            //EJECUTAR PREPARED STATEMENT
           return ps.executeUpdate();
        }catch(SQLException e){
            ultimoError = e.getMessage();
            return -1;//error
        }//end catch        
    }//end insertar
}//end Consultar
