/*
 *  LICENCIA JOSE JAVIER BO
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
 * DLM para insertar en la base de datos
 * @author Jose Javier Bailon Ortiz
 */
public class Insert {
 
    //ultimo error producido
    private static String ultimoError="";
    
    
    /**
     * Insertar un registro usando la conexion C. En la tabla especificada usando
     * como nombre de las columans los campos especificados y los valores especificados.
     * 
     * Los nombres de los campos y los valores deben especificarse en sendos arrays.
     * 
     * Dentro construye el sql usando esos valores para componer una sentencia SQL
     * de insercion
     * 
     * @param c Conexion
     * @param tabla Nombre de la tabla
     * @param campos Array de nombres de columnas
     * @param valores Array de valores
     * @return 1 si ha insertado -1 si no ha insertado
     * 
     * @throws IllegalArgumentException  Si la cantidad de elementos de campos es diferente al de valores
     */
    public static int insert(Connection c, String tabla, String[]campos,String[] valores) throws IllegalArgumentException{        
        
        //comprobar validez e longitud de arrays de campos y valores
        if (campos.length!=valores.length)
            throw new IllegalArgumentException("Numero diferente de campos y valores");

        
        //PREPARACION
        PreparedStatement ps; 
        try{
            //PREPARAR SQL
            String sql="INSERT INTO "+tabla+"("+
                    //introducir listado de campos
                    Arrays.stream(campos).collect(Collectors.joining(","))+
                    ") VALUES (";
            
                    //introducir parametros
                    for (int i=0;i<valores.length;i++)
                        sql+="?,";
                    
                    //cierre del sql
                    if (valores.length>0)
                        sql=sql.substring(0, sql.length()-1);
                    sql+=")";
                    
            //PREPARED STATEMENT
            ps = c.prepareStatement(sql);
            
            
            //VINCULAR VALORES A PARAMETROS DEL PREPARED STATEMENT
            for (int i=0;i<valores.length;i++)
                ps.setString(i+1, valores[i]);
            
            //EJECUTAR LA SENTENCIA
           return ps.executeUpdate();
        }catch(SQLException e){
            int ec=e.getErrorCode();
            ultimoError = e.getMessage();
            //si es error 1062  es por DNI duplicado
            if (ec==1062)
                return -2;
            else 
                return 1;
        }//end catch        
    }//end insertar
}//end Consultar
