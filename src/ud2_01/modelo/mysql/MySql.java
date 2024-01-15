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
     * Select WHERE tal que campo=valor
     *
     * @param tabla Nombre de la tabla
     * @param campo Campo para la condicion
     * @param valor Valor para la condicion
     *
     * @return El resultset
     */
    public ResultSet select(String tabla, String campo, String valor) {
        return new Select().selectWhere(conexion, tabla, campo, valor);
    }

    /**
     * Inserta un registro en la tabla suministrada usando los campos y valores
     * suministrados
     *
     * @param tabla Nombre de la tabla
     * @param campos Listado de nombres de columnas
     * @param valores Listado de valores
     * @return 1 si ha insertado -1 si no ha insertado y -2 si no ha insertado por ser DNI duplicado
     */
    public int insert(String tabla, String[] campos, String[] valores) {
        return new Insert().insert(conexion, tabla, campos, valores);
    }

    /**
     * Borra tuplas de una tabla tal que campo=valor
     *
     * @param tabla Nombre de la tabla
     * @param campo Nombre del campo para la condicion
     * @param valor Valor para la condicion
     *
     * @return 0 si no ha borrado, >0 si ha borrado, -1 si ha habido un error
     */
    public int delete(String tabla, String campo, String valor) {
        return new Delete().deleteWhere(conexion, tabla, campo, valor);
    }

    /**
     * Actualiza los valores de las tuplas de la tabla especificada segun los
     * campos y valore especificados
     *
     * @param tabla Nombre de la tabla
     * @param campos Lista de nombres de los campos a actualizar
     * @param valores Lista de valores a asignar
     * @param campoClave Nombre del campo de la condicion
     * @param valorClave Valor de la condicion
     * 
     * @return 0 si no ha actualizado, >0 si ha actualizado, -1 si ha habido altun error
     */
    public int update(String tabla, String[] campos, String[] valores, String campoClave, String valorClave) {
        return new Update().updateWhere(conexion, tabla, campos, valores, campoClave, valorClave);
    }
}
