/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_02.controlador;

import ud2_02.modelo.mysql.MySql;
import java.sql.DatabaseMetaData;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.stream.Collectors;

/**
 *
 * @author Jose Javier BO
 */
public class Controlador {

    //Referencia al modelo
    private MySql modelo;

    //Datos de conexion a la base de datos
    private final String HOST = "localhost";
    private final int PUERTO = 3306;
    private final String USUARIO = "root";
    private final String PASSWORD = "";
    private final String BASE_DATOS = "trabajosempleados";

    /**
     * Constructor
     */
    public Controlador() {
        modelo = new MySql(HOST, PUERTO, USUARIO, PASSWORD, BASE_DATOS);
    }

    /**
     * Muestra las propiedades de la base de datos
     */
    public void mostrarPropiedades() {

        //conectar
        if (!conectar()) {
            System.exit(0);
        }

        //coger metadatos de la base de datos
        DatabaseMetaData metadatosDB = modelo.getMetadata();

        //si no hay metadatos mostrar el error y salir
        if (metadatosDB == null) {
            System.out.println("ERROR OBTENIENDO METADATOS");
            System.out.println(modelo.getUltimoError());
            System.exit(0);
        }

        try {

            //METADATOS DE LA BASE DE DATOS
            System.out.println("DRIVER");
            System.out.println("-".repeat(50));
            System.out.println("Nombre: " + metadatosDB.getDriverName());
            System.out.println("Version: " + metadatosDB.getDriverVersion());
            System.out.println();
            System.out.println("MOTOR BASE DE DATOS");
            System.out.println("-".repeat(50));
            System.out.println("Nombre: " + metadatosDB.getDatabaseProductName());
            System.out.println("Version: " + metadatosDB.getDatabaseProductVersion());
            System.out.println("Cadena de conexion: " + metadatosDB.getURL());
            System.out.println("Usuario de acceso: " + metadatosDB.getUserName());
            System.out.println();
            System.out.println("TABLAS");
            System.out.println("-".repeat(50));

            //TABLAS
            ResultSet rsTablas = metadatosDB.getTables(modelo.getCatalog(), null, null, null);

            if (rsTablas == null) {
                System.out.println("Problema accediento a metadatos de tablas.");
                System.exit(0);
            }
            
            //mostrar numero de tablas
                rsTablas.last();
                System.out.println("Cantidad de tablas: " + rsTablas.getRow());

            //recorrer tablas mostrando detalles
            rsTablas.beforeFirst();
            while (rsTablas.next()) {
                //Mostrar los detalles de una tabla
                detallesTabla(rsTablas, metadatosDB);
            }//FIN WHILE TABLAS

        } catch (SQLException ex) {
            System.out.println("ERROR OBTENIENDO METADATOS");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            System.exit(0);
        }

        //desconectar de la base de datos
        modelo.desconectar();
    }

    /**
     * Muestra los detalles de una tabla
     *
     * @param rsTablas Resultset de tablas con el cursor en la posicion de la
     * tabla a mostrar en detalle
     * @param metadatosDB Objeto de metadatos de las bases de datos
     * @throws SQLException
     */
    private void detallesTabla(ResultSet rsTablas, DatabaseMetaData metadatosDB) throws SQLException {
        
        // nombre de la tabla
        String nombreTabla = rsTablas.getString("TABLE_NAME");
        System.out.println();
        System.out.println("Tabla: " + nombreTabla);
        ResultSet rsTabla = modelo.select(nombreTabla);
        
        
        if (rsTabla != null) {
            //cantidad columnas
            ResultSetMetaData metadatosTabla = rsTabla.getMetaData();
            int cantColumnas = metadatosTabla.getColumnCount();
            System.out.println("Cantidad de columnas: " + cantColumnas);

            //listado columnas con tipo de dato
            for (int i = 1; i < cantColumnas + 1; i++) {
                System.out.println("Columna: \"" + metadatosTabla.getColumnName(i) + "\"  -  Tipo dato: " + metadatosTabla.getColumnTypeName(i) + " (" + metadatosTabla.getPrecision(i) + ")");
            }

            //Primary key
            ResultSet rsPk = metadatosDB.getPrimaryKeys(null, null, nombreTabla);
            ArrayList<String> pks = new ArrayList<String>();
            if (rsPk != null) {
                while (rsPk.next()) {
                    pks.add(rsPk.getString("COLUMN_NAME"));
                }
            }
            //texto de la lista de PK
            String listaDePk = (pks.isEmpty()) ? "No hay" : pks.stream().collect(Collectors.joining(",")) ;
            
            System.out.print("Primary key: " + listaDePk);
            System.out.println();
            //Foreing keys
            ResultSet rsFk = metadatosDB.getImportedKeys(null, null, nombreTabla);
            if (rsFk != null) {
                rsFk.last();
                if (rsFk.getRow() > 0) {
                    rsFk.beforeFirst();
                    System.out.println("Foreign keys:");
                    while (rsFk.next()) {
                        System.out.println("\t" + rsFk.getString("FKCOLUMN_NAME") + "  --->   " + rsFk.getString("PKTABLE_NAME") + "." + rsFk.getString("PKCOLUMN_NAME"));
                    }
                }
            }
        }

        System.out.println();
    }

    /**
     * Ordena al modelo que se conecte a la base de datos
     *
     * @return True si ha conectado. False si no ha conectado
     */
    private boolean conectar() {
        if (modelo.conectar()) {
            return true;
        } else {
            System.out.println("ERROR CONECTANDO A LA BASE DE DATOS");
            return false;
        }
    }

    /**
     * Inicio del programa
     *
     * @param args
     */
    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        controlador.mostrarPropiedades();
    }
}
