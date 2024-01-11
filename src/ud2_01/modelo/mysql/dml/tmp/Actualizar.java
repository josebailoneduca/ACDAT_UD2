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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Consultar
 * @author Adrián PR
 */
public class Actualizar {
    
    //-Pedir datos
    public static String [] pedirDatos(){
        String [] campos = Conexion.getCamposEmpleados();
        String campo;
        List<String> datos = new ArrayList();
        int col = 0;//Aquí empezamos en 0 porque recorro [] propio
        
        //Recojo cada uno de los campos de la operación:
        //- ID sobre el que actualizar:
        String idActualizar = JOptionPane.showInputDialog(null, 
                    "ID sobre el que actualizar:", "Operación:", 
                    JOptionPane.QUESTION_MESSAGE);
        //- Campos a modificar
        while(col <  campos.length){
            campo = JOptionPane.showInputDialog(null, 
                    campos[col]+":", "Operación:", 
                    JOptionPane.QUESTION_MESSAGE);
            datos.add(campo);
            col++;            
        }
        //- Añado el ID al final (debe seguir el orden de los parámetros
        // de la sentencia SQL
        datos.add(idActualizar);        
        
        //Devuelvo los datos obtenidos
        //- OJO!: Conversión a array[], usa un template "T[]"       
        return datos.toArray(String[]::new);//Funciona perfect!
        //return datos.toArray(new String[0]);//Es lo mismo
    }//end pedirDatos
    
    //-Actualizar  
    public static int actualizar(Connection c, String sql){        
        PreparedStatement ps; 
        /* PreparedStatement           
           Las ? indican parámetros que la sentencia SQL va a requerir. 
           Nosotros tendremos que establecerlas ANTES de ejecutar la sentencia.
        */
        try{
           //Preparamos sentencia
           ps = c.prepareStatement(sql);
           
           //1- Pedimos datos de operación:
           String [] datos = pedirDatos();

           //2- Establezco parámetros SQL
           /*OJO:
             Aquí empezamos en 1 porque los parametros del "PreparedStatement" 
             empiezan en 1 
           */
           int col = 1;           
           while(col <=  datos.length){
               //Establecer parámetro SQL correspondiente:               
               ps.setString(col, datos[col-1]); //cuidado "datos" empieza en 0              
               //sig. columna
               col++;            
           }  
                      
           //Ejecutamos
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
        resultado = actualizar(cn, "UPDATE empleados SET nombre=?, apellidos=?, dni=?, sueldo=? WHERE id=?");
        
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
