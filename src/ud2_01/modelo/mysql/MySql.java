/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_01.modelo.mysql;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import ud2_01.modelo.mysql.dml.Delete;
import ud2_01.modelo.mysql.dml.Insert;
import ud2_01.modelo.mysql.dml.Select;
import ud2_01.modelo.mysql.dml.Update;

/**
 *
 * @author Jose Javier BO
 */
public class MySql {

    Connection conexion;
    String ultimoError = "";

 

    public boolean conectar(String host, int puerto, String user, String password, String baseDatos) {
        String url = "jdbc:mysql://" + host + ":" + puerto + "/";
        String driver = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(driver);//Nombre del driver
            conexion = DriverManager.getConnection(url + baseDatos, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            ultimoError = ex.getMessage();
            return false;
        }
        return true;
    }

    public boolean desconectar() {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException ex) {
            ultimoError = ex.getMessage();
        }
        return true;
    }//end desconectar
    
    
    public String getUltimoError(){
        return ultimoError;
    }
    
    //OPERACIONES
    
    public ResultSet select(String tabla){
        return new Select().selectAll(conexion, tabla);
    }
    public ResultSet select(String tabla,String campo,String valor){
        return new Select().selectWhere(conexion, tabla,campo,valor);
    }
    
    public int insert(String tabla,String[] campos,String[] valores){
        return new Insert().insert(conexion, tabla,campos,valores);
    }

    public int delete(String tabla, String campo, String valor) {
        return new Delete().deleteWhere(conexion,tabla,campo,valor);
    }

    public int update(String tabla, String[] campos, String[] valores, String campo, String valor) {
        return new Update().updateWhere(conexion, tabla,campos,valores,campo,valor);
    }
}
