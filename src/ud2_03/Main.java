/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_03;

import ud2_03.controlador.Controlador;
import ud2_03.gui.ventanas.Vista;
import ud2_03.modelo.Modelo;

/**
 *
 * @author Jose Javier BO
 */
public class Main {

    //Datos de conexion a la base de datos
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String HOST = "localhost";
    private static final int PUERTO = 3306;
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PUERTO + "/";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String BASE_DATOS = "trabajosempleados";

    public static void main(String[] args) {

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
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        Vista vista = new Vista();
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        Modelo modelo = new Modelo();
        
        Controlador controlador = new Controlador(DRIVER, URL, USER, PASSWORD, BASE_DATOS, vista, modelo);
        vista.setControlador(controlador);
    }
}
