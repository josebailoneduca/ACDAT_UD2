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
import ud2_01.vista.venatana.Ventana;

/**
 *
 * @author Jose Javier BO
 */
public class Controlador {

    private static MySql modelo;
    private static Ventana vista;
    public static ArrayList<Empleado> empleados = new ArrayList<Empleado>();
    private static String host = "localhost";
    private static int puerto = 3306;
    private static String usuario = "root";
    private static String password = "";
    private static String baseDatos = "trabajosempleados";
    private static String tabla = "empleados";
    private static String[] columnas = {"ID", "nombre", "apellidos", "DNI", "sueldo"};
    private static String[] columnasInsercion = {"nombre", "apellidos", "DNI", "sueldo"};

    public static void salir() {
        System.exit(0);
    }

    public static boolean actualizarEmpleados() {
        if (conectar()) {
            ResultSet rs = modelo.select(tabla);
            if (rs != null) {
                try {
                    empleados.clear();
                    while (rs.next()) {
                        empleados.add(crearEmpleadoDeResultSet(rs));
                    }
                } catch (SQLException ex) {
                    empleados.clear();
                    vista.msgError(Texto.ERROR_ACTUALIZANDO);
                    return false;
                }
            } else {
                vista.msgError(Texto.ERROR_ACTUALIZANDO);
                return false;
            }
            modelo.desconectar();
            return true;
        } else {
            return false;
        }

    }

    public static void main(String[] args) {
        modelo = new MySql();
//        String[] campos = columnas;
//        String[] valores= {"5","jose","bailón","74747474s","1590"};
        //int a = m.insert("empleados", campos, valores);
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
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

    private static boolean conectar() {
        if (modelo.conectar(host, puerto, usuario, password, baseDatos)) {
            return true;
        } else {
            vista.msgError(Texto.ERROR_CONEXION);
            return false;
        }
    }

    private static Empleado crearEmpleadoDeResultSet(ResultSet rs) throws SQLException {
        int ID = rs.getInt("ID");
        String nombre = rs.getString("nombre");
        String apellidos = rs.getString("apellidos");
        String DNI = rs.getString("DNI");
        double sueldo = rs.getDouble("sueldo");
        return new Empleado(ID, nombre, apellidos, DNI, sueldo);
    }

    public static int eliminar(int id) {
        conectar();
        int resultado = modelo.delete(tabla, "ID", "" + id);
        modelo.desconectar();
        return resultado;
    }

    public static int insertar(Empleado empleado) {
        conectar();
        String[] valores = {empleado.getNombre(), empleado.getApellidos(), empleado.getDNI(), "" + empleado.getSueldo()};
        int resultado = modelo.insert(tabla, columnasInsercion, valores);
        modelo.desconectar();
        return resultado;
    }

    public static int update(Empleado empleado) {
        String[] valores = {empleado.getNombre(), empleado.getApellidos(), empleado.getDNI(), "" + empleado.getSueldo()};
        return modelo.update(tabla,columnasInsercion,valores,"ID",""+empleado.getID());
    }

    public static Empleado getEmpleado(int id) {
        conectar();
        try {
        ResultSet rs = modelo.select(tabla, "ID", "" + id);
            if (rs.next()) {
                return crearEmpleadoDeResultSet(rs);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }finally{
        modelo.desconectar();
        }
        
    }
}
