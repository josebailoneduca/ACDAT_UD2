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
    private String baseDatos;
    
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
     * @param driver
     * @param url
     * @param user
     * @param password
     * @param baseDatos
     * @param vista
     * @param modelo 
     */
    public Controlador(String driver, String url, String user, String password, String baseDatos, Vista vista,Modelo modelo) {
        this.driver=driver;
        this.url=url;
        this.user = user;
        this.password = password;
        this.baseDatos = baseDatos;
        this.vista = vista;
        this.modelo = new Modelo();   
        this.conectar();
    }
     
        /**
     * Devuelve un array de string con los nombres de las tablas
     * 
     * @param ind Indice de la tabla
     * 
     * @return  Array con los nombres de las columnas
     */
    public String[] getTablas() {
        return lisTb.toArray(new String[0]);
    }
    
    
    /**
     * Devuelve un array de string con los nombres de las columnas de una tabla
     * 
     * @param ind Indice de la tabla
     * 
     * @return  Array con los nombres de las columnas
     */
    public String[] getColumnas(int ind) {
        return lisCol.get(ind).toArray(new String[0]);
    }
    
    /**
     * Devuelve un array de string con los tipos de las columnas de una tabla
     * 
     * @param ind Indice de la tabla
     * 
     * @return  Array con los nombres de las columnas
     */
    public int[] getTipos(int ind) {
        return lisTipo.get(ind).stream().mapToInt(i -> i).toArray();
    }

     /**
     * Devuevle una lista de objetos conteniendo todo el contenido de una tabla.
     * El tipo de objeto lo determina el indice de la tabla
     *
     * @return La lista de tuplas de la tabla
     */
    public ArrayList<Object> getAll(int tabla) {

        ArrayList<Object> items = new ArrayList<Object>();
        ResultSet rs = modelo.select(lisTb.get(tabla));
        if (rs != null) {
            //si hay resulset
            try {
                //recoger los datos del resultset
                while (rs.next()) {
                    switch (tabla) {
                        case 0 -> {items.add(crearEmpleadoDeResult(rs));}
                        case 1 -> {items.add(crearTrabajoDeResultSet(rs));}
                        case 2 -> {items.add(crearTrabajoEmpleadosDeResultSet(rs));}
                        default -> throw new AssertionError();
                    }
                }
            } catch (SQLException ex) {
                vista.msgError("Error recogiendo de la tabla "+lisTb.get(tabla));
                return null;
            }
            //si el rs es nulo    
        } else {
            vista.msgError("Error recogiendo de la tabla "+lisTb.get(tabla));
            return null;
        }
        return items;
    }
    
     /**
     * Devuelve la lista de objetos con los datos de tuplas de una tabla donde la columna indicada tiene el valor indicado
     * El tipo de objeto lo determina el indice de la tabla
     * 
     * @return La lista de tuplas
     */
    public ArrayList<Object> getTuplas(int tabla,int col, String valor) {

        ArrayList<Object> items = new ArrayList<Object>();
        ResultSet rs = modelo.select(lisTb.get(tabla), lisCol.get(tabla).get(col), valor);
        if (rs != null) {
            //si hay resulset
            try {
                //recoger los datos del resultset
                while (rs.next()) {
                    switch (tabla) {
                        case 0 -> {items.add(crearEmpleadoDeResult(rs));}
                        case 1 -> {items.add(crearTrabajoDeResultSet(rs));}
                        case 2 -> {items.add(crearTrabajoEmpleadosDeResultSet(rs));}
                        default -> throw new AssertionError();
                    }
                }
            } catch (SQLException ex) {
                vista.msgError("Error recogiendo de la tabla "+lisTb.get(tabla));
                return null;
            }
            //si el rs es nulo    
        } else {
            vista.msgError("Error recogiendo de la tabla "+lisTb.get(tabla));
            return null;
        }
        return items;
    }
    
 

    /**
     * Devuelve una tupla asumiento que la key es la primera key
     * 
     * @param tabla indice de la tabla
     * @param ID valor que debe tener la id
     * @return  La tupla encontrada o null si no existe
     */
    public Object getTupla(int tabla, int ID) {

            try {
                ResultSet rs = modelo.select(lisTb.get(tabla), lisCol.get(tabla).get(0), "" + ID);
                if (rs != null && rs.next()) {
                    //recoger primer resultado del resultset
                    switch (tabla) {
                        case 0 -> {return crearEmpleadoDeResult(rs);}
                        case 1 -> {return crearTrabajoDeResultSet(rs);}
                        case 2 -> {}
                        default -> throw new AssertionError();
                    }
                } else {
                    return null;
                }
            } catch (SQLException ex) {
                return null;
            }
            return null;
    }

 

    /**
     * Inserta en una tabla presuponiendo que la primera columna como la key 
     * 
     * @param tabla Indice de la tabla
     * @param valores multiples parametros con los valores
     * 
     * @return  El resultado de la insercion
     */
    public int insert(int tabla,String... valores) {
        //recoger lista de columnas
        String[] col = new String[lisCol.get(tabla).size()-1];
        for(int i=1;i<lisCol.get(tabla).size();i++){
            col[i-1]=lisCol.get(tabla).get(i);
        }
        //ejecutar la insercion
        return modelo.insert(lisTb.get(tabla), col,valores );
    }

    /**
     * Borra de una tabla indicada donde la key tenga el valor indicado.
     * Presupone que la key es la primera columna
     * 
     * @param ind_tab Indice de la tabla
     * @param valor Valor de la key para la tupla a borrar
     * 
     * @return  El resultado del borrado
     */
    public int delete(int ind_tab, String valor) {
        //ejecutar borrado
        int resultado = modelo.delete(lisTb.get(ind_tab), lisCol.get(ind_tab).get(0), valor);
        return resultado;
    }

    
    /**
     * Actualiza los datos de una tabla presuponiendo que la key es la primera columna
     * 
     * @param tabla Indice de la tabla
     * @param valores Valores a actualizar
     * 
     * @return  El resultado del update
     */
    public int update(int tabla,String... valores) {
        //recoger nombres de las tablas
        String[] col = lisCol.get(tabla).toArray(new String[0]);
        //update en tabla, con campos col, valores , key col[0] == valores[0]
        return modelo.update(lisTb.get(tabla), col, valores,col[0], valores[0]);
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
     * @return  True si ha conectado, false si no lo ha hecho
     */
    public boolean conectar() {
        
        //conectar
        boolean conectado = modelo.conectar(driver, url,  user,  password,  baseDatos);
        
        //recoger valores de tablas y nombres de columnas
        if (conectado) {
            lisTb = modelo.litstaTablas();
            lisCol = new ArrayList<ArrayList<String>>();
            for (String tabla : lisTb) {
                ArrayList<String> cols = modelo.listaColumnas(tabla);
                lisCol.add(cols);
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
     * @return  El string con los datos
     */
    public String getDatosConexion() {
        String SGBD = modelo.getSGBD();
        String URL = modelo.getUrlConexion();
        String USER = modelo.getusername();

        return "Usuario: " + USER + "  Host:" + URL + " - " + SGBD;
    }

    
    /**
     * Crea un objeto Trabajo a partir de un resultset
     * 
     * @param rs El resultset
     * @return El objeto formado
     */
    private Trabajo crearTrabajoDeResultSet(ResultSet rs) {
        //recoger nombres de columnas
        ArrayList<String> col = lisCol.get(1);
        int ID;
        try {
            //recogre datos del resultset
            ID = rs.getInt(col.get(0));
            String nombre = rs.getString(col.get(1));
            String descripcion = rs.getString(col.get(2));
            
            //construir y devolver el objeto
            return new Trabajo(ID, nombre, descripcion);
        } catch (SQLException ex) {
            return null;
        }
    }



    /**
     * Crea un objeto Empleado a partir de un resultset
     * 
     * @param rs El resultset
     * @return El objeto formado
     */
    private Object crearEmpleadoDeResult(ResultSet rs) {
        //recoger nombres de columnas
         ArrayList<String> col = lisCol.get(0);
        int ID;
        try {
            //recoger datos del resultset
            ID = rs.getInt(col.get(0));
            String nombre = rs.getString(col.get(1));
            String apellidos = rs.getString(col.get(2));
            String DNI = rs.getString(col.get(3));
            double sueldo = rs.getDouble(col.get(4));
            
            //construir y devolver el empleado
            return new Empleado(ID, nombre, apellidos, DNI, sueldo);
        } catch (SQLException ex) {
            return null;
        }
    }

    /**
     * Crea un objeto TrabajoEmpleado a partir de un resultset
     * 
     * @param rs El resultset
     * @return El objeto formado
     */
    private TrabajoEmpleado crearTrabajoEmpleadosDeResultSet(ResultSet rs) {
        //recoger nombre de colunas
        ArrayList<String> col = lisCol.get(2);
        int ID;
        int IDtrabajo;
        int IDempleado;
        try {
            //recoger datos de resultset
            ID = rs.getInt(col.get(0));
            IDtrabajo = rs.getInt(col.get(1));
            IDempleado = rs.getInt(col.get(2));
           
            //construir y devolver el objeto
            return new TrabajoEmpleado(ID, IDtrabajo, IDempleado);
        } catch (SQLException ex) {
            return null;
        }    }

    /**
     * Devuelve el último error registrado en el modelo
     * @return  El error
     */
    public String getUltimoError() {
        return modelo.getUltimoError();
    }

}
