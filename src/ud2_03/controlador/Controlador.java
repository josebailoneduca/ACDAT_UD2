/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_03.controlador;

import java.util.ArrayList;
import ud2_03.controlador.dto.Empleado;
import ud2_03.controlador.dto.Trabajo;
import ud2_03.modelo.Modelo;
import ud2_03.gui.ventanas.VentanaPrincipal;

/**
 * Clase lógica
 *
 * @author Jose Javier BO
 */
public class Controlador {



    
    private Modelo modelo;
    
    ArrayList<String> listaTablas=new ArrayList<String>();
    ArrayList<ArrayList<String>> listasColumnas = new ArrayList<ArrayList<String>>();
    
    //ATRIBUTOS
    //Id a asignar al próximo trabajo que se cree
    public  int proximaIdTrabajo = 1;

    //id a asignar al próximo empleado que se cree
    public  int proximaIdEmpleado = 1;


    
    public Controlador(String host, int puerto, String user, String password, String baseDatos){
        modelo=new Modelo(host, puerto, user, password, baseDatos);
        modelo.conectar();
        abrirVista();
    }
    
    
    
    
    
    
    /**
     * Devuevle la lista de trabajos
     *
     * @return La lista de trabajos
     */
    public static ArrayList<Trabajo> getTrabajos() {
        return null;
    }

    /**
     * Devuelve la lista de empleados
     *
     * @return La lista de empleados
     */
    public static ArrayList<Empleado> getEmpleados() {
        return null;
    }

    
    
    
        public static String[] getColumnasTrabajos() {
            return null;
        
    }
    
            public static String[] getColumnasEmpleados() {
            return null;
    }

    /**
     * Devuelve el trabajo con numero igual al suministrado
     *
     * @param numero Numero del trabajo
     *
     * @return El trabajo o null si no existe
     */
    public static Trabajo getTrabajo(int ID) {
        
        //TODO CAMBIAR DEVOLVERLO DESDE BASE DE DATOS
//        for (Trabajo trabajo : listaTrabajos) {
//            if (trabajo.getId() == ID) {
//                return trabajo;
//            }
//        }
        return null;
    }

    /**
     * Devuelve el empleado con id igual a la suministrada
     *
     * @param id Id del empleado
     *
     * @return El emleado o null si no existe
     */
    public static Empleado getEmpleado(int id) {
        //TODO devolver empleado desde base de datos
//        for (Empleado empleado : listaEmpleados) {
//            if (empleado.getId() == id) {
//                return empleado;
//            }
//        }
        return null;
    }

    /**
     * Agrega un trabajo a la lista de trabajos
     *
     * @param trabajo El trabajo a agregar
     */
    public static void addTrabajo(Trabajo trabajo) {
        //TODO agregar trabajo a la base de datos
//        listaTrabajos.add(trabajo);
//        proximaIdTrabajo++;
    }

    /**
     * Agrega un empleado a la lista de empleados
     *
     * @param empleado El empleado a agregar
     */
    public static void addEmpleado(Empleado empleado) {
        //TODO agregar empleado a la base de datos
//        listaEmpleados.add(empleado);
//        proximaIdEmpleado++;
    }

    /**
     * Modifica un empleado existente en la lista de empleados
     *
     * @param e El empleado a modificar
     */
    public static void modificaEmpleado(Empleado e) {
        Empleado empleado = getEmpleado(e.getId());
        empleado.setNombre(e.getNombre());
        empleado.setApellidos(e.getApellidos());
        empleado.setDni(e.getDni());
        empleado.setSueldo(e.getSueldo());
    }

    /**
     * Elimina un empleado.
     * 
     * @param empleado  El empleado a eliminar
     */
    public static void eliminarEmpleado(Empleado empleado) {
//        listaEmpleados.remove(empleado);
        
        //TODO BORRAR EL EMPLEADO DE LA RELACION CON TRABAJOS
//        for (Trabajo trabajo : listaTrabajos) {
//            //trabajo.getEmpleados().remove(empleado);
//        }
    }
    
    /**
     * Elimina la asignacion de un empleado en un trabajo
     *
     * @param empleado El empleado
     * @param trabajo El trabajo
     */
    public static void desasignarEmpleadoDeTrabajo(int empleado, int trabajo) {
        //TODO
        //getTrabajo(trabajo).desasiganrEmpleado(getEmpleado(empleado));
    }

    /**
     * Asigna un empleado en un trabajo
     *
     * @param empleado El empleado
     * @param trabajo El trabajo
     */
    public static void asignarEmpleadoATrabajo(int empleado, int trabajo) {
        //TODO asignar un empleado a un trabajo
//        getTrabajo(trabajo).asignarEmpleado(getEmpleado(empleado));
    }

    private void abrirVista() {
        //agregar trabajo y empleados

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
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(()-> {
                VentanaPrincipal vp = new VentanaPrincipal(this);
                vp.setLocationRelativeTo(null);
                vp.setVisible(true);
        });
    }

    public void salir(){
        modelo.desconectar();
        System.exit(0);
    }

    public boolean conectar() {
        boolean conectado = modelo.conectar();
        if (conectado){
            listaTablas = modelo.litstaTablas();
            listasColumnas=new ArrayList<ArrayList<String>>();
            for (String tabla : listaTablas) {
                ArrayList<String> cols = modelo.listaColumnas(tabla);
                listasColumnas.add(cols);
            }
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
        
        return "Usuario: "+ user+"  Host:"+url+" - " +sgbd;
    }
 
}
