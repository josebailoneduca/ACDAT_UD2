/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_04.modelo;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ud2_04.modelo.dml.Delete;
import ud2_04.modelo.dml.Insert;
import ud2_04.modelo.dml.Select;
import ud2_04.modelo.dml.Update;


/**
 * Punto de entrada del modelo para MySql. Gestiona la conexion y sirve de
 * puente hacia los DML
 *
 * @author Jose Javier BO
 */
public class Modelo {

    //CONEXION CON LA BASE DE DATOS
    private Connection conexion;

    //ultimo error producido
    private String ultimoError = "";



    public Modelo() {
    }

    /**
     * Conecta con la base MYSQL de datos usando los parametros suministrados
     *
     * @return True si conecta. False si no conecta
     */
    public boolean conectar(String driver, String url, String user, String password, String baseDatos) {
  
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
     * Devuelve los metadatos de la base de datos
     *
     * @return los metadatos
     */
    public DatabaseMetaData getMetadata() {
        try {
            return conexion.getMetaData();
        } catch (SQLException ex) {
            this.ultimoError = ex.getMessage();
            return null;
        }
    }

    /**
     * Devuelve el catalog
     *
     * @return El catalog
     */
    public String getCatalog() {
        try {
            if (conexion != null) {
                return conexion.getCatalog();
            } else {
                this.ultimoError = "La conexion no se ha creado";
            }
            return null;
        } catch (SQLException ex) {
            this.ultimoError = ex.getMessage();
            return null;
        }
    }
    
    /**
     * Devuelve informacion sobre el SGBD/databaseproductname
     * @return 
     */
    public String getSGBD() {
        DatabaseMetaData md = getMetadata();
        try {
            return md.getDatabaseProductName();
        } catch (SQLException ex) {
            return "";
        }
    }

    
    /**
     * Devuelve la URL de la conexion
     * @return 
     */
    public String getUrlConexion() {
        DatabaseMetaData md = getMetadata();
        try {
            return md.getURL();
        } catch (SQLException ex) {
            return "";
        }
    }

    
    /**
     * Devuelve el nombre de usuario de conexion a la base de datos.
     * @return 
     */
    public String getusername() {
        DatabaseMetaData md = getMetadata();
        try {
            return md.getUserName();
        } catch (SQLException ex) {
            return "";
        }
    }

    
    
    /**
     * Devuelve la lista de columnas de una tabla
     * @param tabla
     * @return 
     */
    public ArrayList<String> listaColumnas(String tabla) {
        ArrayList<String> list = new ArrayList<String>();

        try {
        ResultSet rsTabla = select(tabla);
        if (rsTabla != null) {
            //cantidad columnas
            ResultSetMetaData metadatosTabla;
                metadatosTabla = rsTabla.getMetaData();
            int cantColumnas = metadatosTabla.getColumnCount();
            //listado columnas con tipo de dato
            for (int i = 1; i < cantColumnas + 1; i++) {
                list.add(metadatosTabla.getColumnName(i));
            }
        }
            } catch (SQLException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
        /**
     * Devuelve la lista de columnas de una tabla
     * @param tabla
     * @return 
     */
    public ArrayList<Integer> listaTipos(String tabla) {
        ArrayList<Integer> list = new ArrayList<Integer>();

        try {
        ResultSet rsTabla = select(tabla);
        if (rsTabla != null) {
            //cantidad columnas
            ResultSetMetaData metadatosTabla;
                metadatosTabla = rsTabla.getMetaData();
            int cantColumnas = metadatosTabla.getColumnCount();
            //listado columnas con tipo de dato
            for (int i = 1; i < cantColumnas + 1; i++) {
                list.add(metadatosTabla.getColumnType(i));
            }
        }
            } catch (SQLException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    /**
     * Devuelve una lista con las tablas de la base de datos
     * 
     * @return  La lista
     */
    public ArrayList<String> litstaTablas() {
        ArrayList<String> lista = new ArrayList<String>();
        //recoger metadatos
        DatabaseMetaData md = getMetadata();
        //extraer y recorrer las tablas seun el catalog
        try {
            ResultSet rsTablas = md.getTables(getCatalog(), null, null, null);
            while (rsTablas.next()) {
                lista.add(rsTablas.getString("TABLE_NAME"));
            }
        } catch (SQLException ex) {
            ultimoError = ex.getMessage();
        }
        return lista;
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
        return Select.selectAll(conexion, tabla);
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
        return Select.selectWhere(conexion, tabla, campo, valor);
    }

    /**
     * Inserta un registro en la tabla suministrada usando los campos y valores
     * suministrados
     *
     * @param tabla Nombre de la tabla
     * @param campos Listado de nombres de columnas
     * @param valores Listado de valores
     * @return 1 si ha insertado -1 si no ha insertado y -2 si no ha insertado
     * por ser DNI duplicado
     */
    public int insert(String tabla, String[] campos, String[] valores) {
        return Insert.insert(conexion, tabla, campos, valores);
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
        return Delete.deleteWhere(conexion, tabla, campo, valor);
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
     * @return 0 si no ha actualizado, >0 si ha actualizado, -1 si ha habido
     * altun error
     */
    public int update(String tabla, String[] campos, String[] valores, String campoClave, String valorClave) {
        return Update.updateWhere(conexion, tabla, campos, valores, campoClave, valorClave);
    }


}
