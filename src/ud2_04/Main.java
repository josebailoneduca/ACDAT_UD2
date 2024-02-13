/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_04;

import java.util.HashMap;
import javax.swing.JOptionPane;
import ud2_04.controlador.Controlador;
import ud2_04.gui.ventanas.Vista;
import ud2_04.gui.dialogos.DialogoDatosConexion;
import ud2_04.modelo.Modelo;

/**
 *
 * @author Jose Javier BO
 */
public class Main {
    private static DialogoDatosConexion dialogo;
    private static boolean pedir = true;
    private static String DRIVER;
    private static String URL;
    private static String USER;
    private static String PASSWORD;

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

        while (pedir) {
            HashMap<String, String> dCon = pedirDatos();
            if (dCon != null) {
                DRIVER=dCon.get("DRIVER");
                URL=dCon.get("URL");
                USER=dCon.get("USER");
                PASSWORD=dCon.get("PASSWORD");
                if(conexionOk())
                    pedir=false;
                else
                    JOptionPane.showMessageDialog(null, "No se puede conectar");
            } else {
                System.exit(0);
            }
        }
        Vista vista = new Vista();
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        Modelo modelo = new Modelo();

        Controlador controlador = new Controlador(DRIVER, URL, USER, PASSWORD, vista, modelo);
        vista.setControlador(controlador);
    }

    private static HashMap<String, String> pedirDatos() {
        if(dialogo==null)
            dialogo = new DialogoDatosConexion(null, true);

        dialogo.setLocationRelativeTo(null);
        dialogo.mostrar();
        return dialogo.getDatos();
    }

    private static boolean conexionOk() {
        return Modelo.testConexion(DRIVER,URL,USER,PASSWORD);
    }
}
