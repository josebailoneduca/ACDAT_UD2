/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_01.vista.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import ud2_01.vista.dialogos.FormularioEmpleado;
import ud2_01.vista.venatana.Ventana;

/**
 *
 * @author Jose Javier Bailon Ortiz
 */
public class VentanaListener implements ActionListener, KeyListener {

    Ventana ventana;

    public VentanaListener(Ventana ventana) {
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ac = e.getActionCommand();

        switch (ac) {
            case "salir":
                ventana.salir();
                break;
            case "actualizar":
                ventana.actualizarEmpleados();
                break;
            case "insertar":
                FormularioEmpleado fe = new FormularioEmpleado(ventana, FormularioEmpleado.CREAR, null);
                fe.setLocationRelativeTo(ventana);
                fe.setVisible(true);
                ventana.actualizarEmpleados();
                break;
                
            case "editar":
                ventana.editar();
                break;
            case "eliminar":
                ventana.eliminar();
                break;

            case "quitarfiltro":
                ventana.quitarFiltro();
                break;
            case "dni":
                ventana.filtroPorDni = true;
                ventana.filtrar();
                break;
            case "sueldo":
                ventana.filtroPorDni = false;
                ventana.filtrar();
                break;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ventana.filtrar();
    }

}//end VentanaListener
