/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_02.modelo.mysql;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import ud2_02.modelo.mysql.dml.Select;


/**
 * Punto de entrada del modelo para MySql. Gestiona la conexion y sirve de
 * puente hacia los DML
 *
 * @author Jose Javier BO
 */
public class MySql {

    //CONEXION CON LA BASE DE DATOS
    private Connection conexion;

    //ultimo error producido
    private String ultimoError = "";
    
    //datos de conexion
    String host;
    int puerto;
    String user;
    String password;
    String baseDatos;

    public MySql(String host, int puerto, String user, String password, String baseDatos) {
        this.host = host;
        this.puerto = puerto;
        this.user = user;
        this.password = password;
        this.baseDatos = baseDatos;
    }
    
    
    
    /**
     * Conecta con la base MYSQL de datos usando los parametros suministrados
     *
     * @return True si conecta. False si no conecta
     */
    public boolean conectar() {
        //peparar url
        String url = "jdbc:mysql://" + host + ":" + puerto + "/";

        //nombre del driver
        String driver = "com.mysql.cj.jdbc.Driver";

        try {
            //asegurar carga del driver
            Class.forName(driver);//Nombre del driver

            //iniciar conexion
            conexion = DriverManager.getConnection(url + baseDatos, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            ultimoError = ex.getMessage();
            return false;
        }
        return true;
    }

    /**
     * Desconecta de la base de datos
     *
     * @return True si ha desconectado o no habia conexion, false si ha habido
     * error
     */
    public boolean desconectar() {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException ex) {
            ultimoError = ex.getMessage();
            return false;
        }
        return true;
    }//end desconectar

    /**
     * Devuelve el ultimo error registrado
     *
     * @return El mensaje del error
     */
    public String getUltimoError() {
        return ultimoError;
    }

    //OPERACIONES
    /**
     * Selecciona todos los elementos de la tabla
     *
     * @param tabla Nombre de la tabla
     *
     * @return El resultset
     */
    public ResultSet select(String tabla) {
        return new Select().selectAll(conexion, tabla);
    }

    /**
     * Devuelve los metadata
     * 
     * @return 
     */
    public DatabaseMetaData getMetadata(){
        try {
            return conexion.getMetaData();
        } catch (SQLException ex) {
            this.ultimoError=ex.getMessage();
            return null;
        }
    }

    
    public String getCatalog() {
            try {
        if (conexion!=null)
                return conexion.getCatalog();
        else
            this.ultimoError="La conexion no se ha creado";
            return null;
        } catch (SQLException ex) {
            this.ultimoError=ex.getMessage();
            return null;
        }
    }
}
