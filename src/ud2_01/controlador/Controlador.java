/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_01.controlador;

import java.util.ArrayList;
import ud2_01.controlador.dto.Empleado;
import ud2_01.modelo.mysql.MySql;
import java.sql.ResultSet;
import java.sql.SQLException;
import ud2_01.vista.Texto;
import ud2_01.vista.ventana.Ventana;

/**
 * Controlador. Punto inicial del programa
 *
 *
 * @author Jose Javier BO
 */
public class Controlador {

    //Referencia al modelo
    private static MySql modelo;

    //Referencia a la vista
    private static Ventana vista;

    //Lista de empleados en memoria
    public static ArrayList<Empleado> empleados = new ArrayList<Empleado>();

    //Datos de conexion a la base de datos
    private static String host = "localhost";
    private static int puerto = 3306;
    private static String usuario = "root";
    private static String password = "";
    private static String baseDatos = "trabajosempleados";
    private static String tabla = "empleados";

    //Columnas usadas durante la insercion y el update
    private static String[] columnasInsercion = {"nombre", "apellidos", "DNI", "sueldo"};

    /**
     * Salir del programa
     */
    public static void salir() {
        System.exit(0);
    }


    
    /**
     * Actualiza la lista de empleados en memoria cargandolos desde la base de
     * datos
     *
     * @return True si pudo actualizarlo, False si no pudo actualizarlo
     */
    public static boolean actualizarEmpleados() {

        //Conectar la base de datos
        if (conectar()) {

            //recoger todos los empleados
            ResultSet rs = modelo.select(tabla);

            if (rs != null) {
                //si hay resulset
                try {
                    //limpiar lista de empleados
                    empleados.clear();

                    //recoger los empleados del resultset
                    while (rs.next()) {
                        empleados.add(crearEmpleadoDeResultSet(rs));
                    }

                } catch (SQLException ex) {
                    empleados.clear();
                    vista.msgError(Texto.ERROR_ACTUALIZANDO);
                    return false;
                } finally {
                    modelo.desconectar();
                }
                //si el rs es nulo    
            } else {
                vista.msgError(Texto.ERROR_ACTUALIZANDO);
                return false;
            }
            //desconectar de la base de datos
            modelo.desconectar();
            return true;
        } else {
            //si no se pudo conectar retornar false
            return false;
        }

    }

    /**
     * Devuelve un empleado pidiendolo a la base de datos por su ID
     *
     * @param id Id a buscar
     * @return El empleado o null si no existe o no se ha podido obtener
     */
    public static Empleado getEmpleado(int id) {
        if (conectar()) {
            try {
                ResultSet rs = modelo.select(tabla, "ID", "" + id);
                if (rs != null && rs.next()) {
                    return crearEmpleadoDeResultSet(rs);
                } else {
                    return null;
                }
            } catch (SQLException ex) {
                return null;
            } finally {
                modelo.desconectar();
            }
        } else {
            return null;
        }
    }



    /**
     * Elimina un empleado
     *
     * @param id Id del empleado a eliminar
     * @return 1 si ha sido borrado. -1 si no ha sido borrado
     */
    public static int eliminar(int id) {
        if (conectar()) {
            int resultado = modelo.delete(tabla, "ID", "" + id);
            modelo.desconectar();
            return resultado;
        } else {
            return -1;
        }
    }

    /**
     * Inserta un empleado
     *
     * @param empleado El empleado a insertar
     *
     * @return 1 si ha insertado, -1 si no ha insertado
     */
    public static int insertar(Empleado empleado) {
        int resultado = -1;
        if (conectar()) {
            String[] valores = {empleado.getNombre(), empleado.getApellidos(), empleado.getDNI(), "" + empleado.getSueldo()};
            resultado = modelo.insert(tabla, columnasInsercion, valores);
            modelo.desconectar();
        }
        return resultado;
    }

    /**
     * Actualiza un empleado
     *
     * @param empleado El empleado a actualizar
     * @return 1 si se ha actualizado, -1 si no se ha actualizado
     */
    public static int update(Empleado empleado) {
        int resultado = -1;
        if (conectar()) {
            String[] valores = {empleado.getNombre(), empleado.getApellidos(), empleado.getDNI(), "" + empleado.getSueldo()};
            resultado = modelo.update(tabla, columnasInsercion, valores, "ID", "" + empleado.getID());
            modelo.desconectar();
        }
        return resultado;
    }

    
    
        /**
     * Ordena al modelo que se conecte a la base de datos
     *
     * @return True si ha conectado. False si no ha conectado
     */
    private static boolean conectar() {
        if (modelo.conectar(host, puerto, usuario, password, baseDatos)) {
            return true;
        } else {
            vista.msgError(Texto.ERROR_CONEXION);
            return false;
        }
    }

    
        /**
     * Transforma un resultset en un empleado
     *
     * @param rs El resultset
     * @return El empleado creado
     *
     * @throws SQLException Si se ha producidon un error obteniendo los datos
     * del resultset
     */
    private static Empleado crearEmpleadoDeResultSet(ResultSet rs) throws SQLException {
        int ID = rs.getInt("ID");
        String nombre = rs.getString("nombre");
        String apellidos = rs.getString("apellidos");
        String DNI = rs.getString("DNI");
        double sueldo = rs.getDouble("sueldo");
        return new Empleado(ID, nombre, apellidos, DNI, sueldo);
    }
    
    
    
    /**
     * Inicio del programa crea el modelo y la vista
     *
     * @param args
     */
    public static void main(String[] args) {

        //CREAR EL MODLEO
        modelo = new MySql();

        //CREAR LA VISTA
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vista = new Ventana();
                vista.setLocationRelativeTo(null);
                vista.setVisible(true);
                vista.actualizarEmpleados();
            }
        });
    }
}
