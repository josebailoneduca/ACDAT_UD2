/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_03.controlador;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ud2_03.controlador.dto.Empleado;
import ud2_03.controlador.dto.Trabajo;
import ud2_03.controlador.dto.TrabajoEmpleado;
import ud2_03.modelo.Modelo;
import ud2_03.gui.ventanas.Vista;

/**
 * Clase lógica
 *
 * @author Jose Javier BO
 */
public class Controlador {

    //indices de tablas
    //empleados=0;
    //trabajos=1;
    //trabajosempleados=2;
    //indices columans empleados
    //columnas
    //empleados
    //0 ID
    //1 nombre
    //2 apellidos
    //3 DNI
    //4 sueldo
    //
    //trabajos 
    //0 ID
    //1 nombre
    //2 descripcion
    //
    //trabajosempelados
    //0 ID
    //1 IDtrabajo
    //2 IDempleado
    public String[] getColumnas(int ind) {
        return lisCol.get(ind).toArray(new String[0]);
    }

        //datos de conexion
    private String driver;
    private String url;
    private String user;
    private String password;
    private String baseDatos;
    
    
    private Modelo modelo;
    private Vista vista;
    ArrayList<String> lisTb = new ArrayList<String>();
    ArrayList<ArrayList<String>> lisCol = new ArrayList<ArrayList<String>>();

    //ATRIBUTOS
    //Id a asignar al próximo trabajo que se cree
    public int proximaIdTrabajo = 1;

    //id a asignar al próximo empleado que se cree
    public int proximaIdEmpleado = 1;

    public Controlador(String driver, String url, String user, String password, String baseDatos, Vista vista,Modelo modelo) {
         this.driver=driver;
        this.url=url;
        this.user = user;
        this.password = password;
        this.baseDatos = baseDatos;
        this.vista = vista;
        this.modelo = new Modelo();
        
    }
 

        /**
     * Devuevle la lista de trabajos
     *
     * @return La lista de trabajos
     */
    public ArrayList<Object> getAll(int tabla) {

        ArrayList<Object> items = new ArrayList<Object>();
        ResultSet rs = modelo.select(lisTb.get(tabla));
        if (rs != null) {
            //si hay resulset
            try {
                //recoger los empleados del resultset
                while (rs.next()) {
                    switch (tabla) {
                        case 0 -> {items.add(crearEmpleadoDeResult(rs));}
                        case 1 -> {items.add(crearTrabajoDeResultSet(rs));}
                        case 2 -> {}
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
        //desconectar de la base de datos

        return items;

    }
    
            /**
     * Devuevle la lista de trabajos
     *
     * @return La lista de trabajos
     */
    public ArrayList<Object> getTuplas(int tabla,int col, String valor) {

        ArrayList<Object> items = new ArrayList<Object>();
        ResultSet rs = modelo.select(lisTb.get(tabla), lisCol.get(tabla).get(col), valor);
        if (rs != null) {
            //si hay resulset
            try {
                //recoger los empleados del resultset
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
        //desconectar de la base de datos

        return items;

    }
    
 

    /**
     * Devuelve una tupla asumiento que la key es la primera key
     *
     * @param numero Numero del trabajo
     *
     * @return El trabajo o null si no existe
     */
    public Object getTupla(int tabla, int ID) {

            try {
                ResultSet rs = modelo.select(lisTb.get(tabla), lisCol.get(tabla).get(0), "" + ID);
                if (rs != null && rs.next()) {

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
     * Presupongo la primera columna como la ID
     *
     * @param trabajo El trabajo a agregar
     */
    public int insert(int tabla,String... valores) {
        String[] col = new String[lisCol.get(tabla).size()-1];
        for(int i=1;i<lisCol.get(tabla).size();i++){
            col[i-1]=lisCol.get(tabla).get(i);
        }
        return modelo.insert(lisTb.get(tabla), col,valores );
    }


  

    public void salir() {
        modelo.desconectar();
        System.exit(0);
    }

    public boolean conectar() {
        boolean conectado = modelo.conectar(driver, url,  user,  password,  baseDatos);
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

    public boolean desconectar() {
        boolean desconectado = modelo.desconectar();
        return desconectado;
    }

    public String getDatosConexion() {
        String sgbd = modelo.getSGBD();
        String url = modelo.getUrlConexion();
        String user = modelo.getusername();

        return "Usuario: " + user + "  Host:" + url + " - " + sgbd;
    }

    private Trabajo crearTrabajoDeResultSet(ResultSet rs) {
        ArrayList<String> col = lisCol.get(1);
        int ID;
        try {
            ID = rs.getInt(col.get(0));
            String nombre = rs.getString(col.get(1));
            String descripcion = rs.getString(col.get(2));
            return new Trabajo(ID, nombre, descripcion);
        } catch (SQLException ex) {
            return null;
        }
    }

    public int delete(int ind_tab, String valor) {
        int resultado = modelo.delete(lisTb.get(ind_tab), lisCol.get(ind_tab).get(0), valor);
        return resultado;


    }

    public int update(int tabla,String... valores) {
        String[] col = lisCol.get(tabla).toArray(new String[0]);
        
        return modelo.update(lisTb.get(tabla), col, valores,col[0], valores[0]);
    }

    private Object crearEmpleadoDeResult(ResultSet rs) {
                ArrayList<String> col = lisCol.get(0);
        int ID;
        try {
            ID = rs.getInt(col.get(0));
            String nombre = rs.getString(col.get(1));
            String apellidos = rs.getString(col.get(2));
            String DNI = rs.getString(col.get(3));
            double sueldo = rs.getDouble(col.get(4));
            return new Empleado(ID, nombre, apellidos, DNI, sueldo);
        } catch (SQLException ex) {
            return null;
        }
    }

    private TrabajoEmpleado crearTrabajoEmpleadosDeResultSet(ResultSet rs) {
        ArrayList<String> col = lisCol.get(2);
        int ID;
        int IDtrabajo;
        int IDempleado;
        try {
            ID = rs.getInt(col.get(0));
            IDtrabajo = rs.getInt(col.get(1));
            IDempleado = rs.getInt(col.get(2));
           
            return new TrabajoEmpleado(ID, IDtrabajo, IDempleado);
        } catch (SQLException ex) {
            return null;
        }    }

    public String getUltimoError() {
        return modelo.getUltimoError();
    }

}
