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
public class Insert {
 
    private String ultimoError="";
    
    public int insert(Connection c, String tabla, String[]campos,String[] valores) throws IllegalArgumentException{        
        if (campos.length!=valores.length)
            throw new IllegalArgumentException("Numero diferente de campos y valores");

        
        PreparedStatement ps; 
        try{
            String sql="INSERT INTO "+tabla+"("+
                    Arrays.stream(campos).collect(Collectors.joining(","))+
                    ") VALUES (";
                    for (int i=0;i<valores.length;i++)
                        sql+="?,";
                    if (valores.length>0)
                        sql=sql.substring(0, sql.length()-1);
                    sql+=")";
            ps = c.prepareStatement(sql);
            for (int i=0;i<valores.length;i++)
                ps.setString(i+1, valores[i]);
           return ps.executeUpdate();
        }catch(SQLException e){
            ultimoError = e.getMessage();
            return -1;//error
        }//end catch        
    }//end insertar
}//end Consultar
