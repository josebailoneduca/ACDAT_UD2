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
import java.sql.PreparedStatement;

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
    public boolean conectar(String driver, String url, String user, String password) {
  
        try {
            //asegurar carga del driver
            Class.forName(driver);//Nombre del driver

            //iniciar conexion
            conexion = DriverManager.getConnection(url, user, password);
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
     * Devuelve el ultimo error registrado
     *
     * @return El mensaje del error
     */
    public String getUltimoError() {
        return ultimoError;
    }

 
    public ResultSet ejecutarSentencia(String sentencia) {
            PreparedStatement ps;
        try {
            ps = conexion.prepareStatement(sentencia);
           return ps.executeQuery();    
        } catch (SQLException ex) {
            this.ultimoError=ex.getMessage();
            return null;
        }
    }


    public static boolean testConexion(String DRIVER, String URL, String USER, String PASSWORD) {
  
        try {
            //asegurar carga del driver
            Class.forName(DRIVER);//Nombre del driver

            //iniciar conexion
            DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            return false;
        }
        return true;
    }

}
