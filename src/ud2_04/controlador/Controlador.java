/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_04.controlador;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import ud2_04.controlador.dto.Empleado;
import ud2_04.controlador.dto.Trabajo;
import ud2_04.controlador.dto.TrabajoEmpleado;
import ud2_04.gui.ventanas.Vista;
import ud2_04.modelo.Modelo;

/**
 * Clase lógica
 *
 * @author Jose Javier BO
 */
public class Controlador {

    //ATRIBUTOS
    //datos de conexion
    private String driver;
    private String url;
    private String user;
    private String password;


    //referencias a modelo y vista
    private Modelo modelo;
    private Vista vista;

    //lista de nombres de las tablas
    private ArrayList<String> lisTb = new ArrayList<String>();
    //matriz de nombres de las columnas (fila indica la tabla, columna el nombre de la columna)
    private ArrayList<ArrayList<String>> lisCol = new ArrayList<ArrayList<String>>();
    //matriz de tipos de dato de las columnas
    private ArrayList<ArrayList<Integer>> lisTipo = new ArrayList<ArrayList<Integer>>();

    /**
     * Constructor con los datos de conexion y las referencias a vista y modelo
     *
     * @param driver
     * @param url
     * @param user
     * @param password
     * @param baseDatos
     * @param vista
     * @param modelo
     */
    public Controlador(String driver, String url, String user, String password, Vista vista, Modelo modelo) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;

        this.vista = vista;
        this.modelo = new Modelo();
        this.conectar();
    }

    /**
     * Devuelve un array de string con los nombres de las tablas
     *
     * @param ind Indice de la tabla
     *
     * @return Array con los nombres de las columnas
     */
    public String[] getTablas() {
        return lisTb.toArray(new String[0]);
    }

    /**
     * Devuelve un array de string con los nombres de las columnas de una tabla
     *
     * @param ind Indice de la tabla
     *
     * @return Array con los nombres de las columnas
     */
    public String[] getColumnas(int ind) {
        return lisCol.get(ind).toArray(new String[0]);
    }

    /**
     * Devuelve un array de string con los tipos de las columnas de una tabla
     *
     * @param ind Indice de la tabla
     *
     * @return Array con los nombres de las columnas
     */
    public int[] getTipos(int ind) {
        return lisTipo.get(ind).stream().mapToInt(i -> i).toArray();
    }
 
 

    /**
     * Salir del programa
     */
    public void salir() {
        modelo.desconectar();
        System.exit(0);
    }

    /**
     * Conecta a la base de datosy devuelve el resultado del intento de conexion
     *
     * @return True si ha conectado, false si no lo ha hecho
     */
    public boolean conectar() {

        //conectar
        boolean conectado = modelo.conectar(driver, url, user, password);

        //recoger valores de tablas y nombres de columnas
        if (conectado) {
            lisTb = modelo.litstaTablas();
            lisCol = new ArrayList<ArrayList<String>>();
            lisTipo = new ArrayList<ArrayList<Integer>>();
            for (String tabla : lisTb) {
                ArrayList<String> cols = modelo.listaColumnas(tabla);
                lisCol.add(cols);
                ArrayList<Integer> tipos = modelo.listaTipos(tabla);
                lisTipo.add(tipos);
            }
        } else {
            vista.msgError("Error conectando\n" + modelo.getUltimoError());
        }
        return conectado;
    }

    /**
     * Desconectar de la base de datos
     *
     * @return True exito, false fracaso
     */
    public boolean desconectar() {
        boolean desconectado = modelo.desconectar();
        return desconectado;
    }

    /**
     * Devuelve un string formado por los datos de conexion
     *
     * @return El string con los datos
     */
    public String getDatosConexion() {
        String SGBD = modelo.getSGBD();
        String URL = modelo.getUrlConexion();
        String USER = modelo.getusername();

        return "Usuario: " + USER + "  Host:" + URL + " - " + SGBD;
    }

     
 

    /**
     * Devuelve el último error registrado en el modelo
     *
     * @return El error
     */
    public String getUltimoError() {
        return modelo.getUltimoError();
    }

    public String[] getOperaciones(int tipo) {
        String[] numeros = {"=", "<>", "<", ">", "<=", ">="};
        String[] texto = {"LIKE", "=", "<>", "<", ">", "<=", ">="};

        switch (tipo) {
            case java.sql.Types.INTEGER:
            case java.sql.Types.DOUBLE:
            case java.sql.Types.BIGINT:
            case java.sql.Types.DECIMAL:
            case java.sql.Types.FLOAT:
            case java.sql.Types.NUMERIC:
            case java.sql.Types.REAL:
            case java.sql.Types.SMALLINT:
            case java.sql.Types.TINYINT:
                return numeros;
            case java.sql.Types.CHAR:
            case java.sql.Types.LONGNVARCHAR:
            case java.sql.Types.LONGVARCHAR:
            case java.sql.Types.NCHAR:
            case java.sql.Types.NVARCHAR:
            case java.sql.Types.VARCHAR:
                return texto;
            default:
                return numeros;

        }
    }

    public boolean ponerComillas(int tipo) {

        switch (tipo) {
            case java.sql.Types.INTEGER:
            case java.sql.Types.DOUBLE:
            case java.sql.Types.BIGINT:
            case java.sql.Types.DECIMAL:
            case java.sql.Types.FLOAT:
            case java.sql.Types.NUMERIC:
            case java.sql.Types.REAL:
            case java.sql.Types.SMALLINT:
            case java.sql.Types.TINYINT:
                return false;
            case java.sql.Types.CHAR:
            case java.sql.Types.LONGNVARCHAR:
            case java.sql.Types.LONGVARCHAR:
            case java.sql.Types.NCHAR:
            case java.sql.Types.NVARCHAR:
            case java.sql.Types.VARCHAR:
                return true;
            default:
                return true;

        }
    }

    public ResultSet ejecutarSentencia(String sentencia) {
        return modelo.ejecutarSentencia(sentencia);
    }
}
