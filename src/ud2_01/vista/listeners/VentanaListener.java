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
import ud2_01.vista.ventana.Ventana;

/**
 *  Listener de pulsado de botones y teclas
 * 
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class VentanaListener implements ActionListener, KeyListener {

    //Referencia a la ventana
    private Ventana ventana;

    /**
     * Constructor
     * 
     * @param ventana Referencia a la ventana principal
     */
    public VentanaListener(Ventana ventana) {
        this.ventana = ventana;
    }

    
    /**
     * Ejectua una accion dependiendo del action command del evernto recibido
     * Los elementos de la interfaz tienen especificado el action command para
     * funcionar en concordancia con este switch
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String ac = e.getActionCommand();

        switch (ac) {
            //Salir del programa
            case "salir" -> ventana.salir();
            //Actualizar empleados en memoria
            case "actualizar" -> ventana.actualizarEmpleados();
            //Iniciar la insercion de un nuevo empleado
            case "insertar" -> ventana.insertar();
            //Iniciar la edicion de un empleado 
            case "editar" -> ventana.editar();
            //Iniciar el borrado de un empleado
            case "eliminar" -> ventana.eliminar();
            //Quitar el filtro actual de la tabla
            case "quitarfiltro" -> ventana.quitarFiltro();
            //Seleccionar el modo de filtro por DNI
            case "dni" -> {
                ventana.filtroPorDni = true;
                ventana.filtrar();
            }
            //Seleccionar el modo de filtro por sueldo
            case "sueldo" -> {
                ventana.filtroPorDni = false;
                ventana.filtrar();
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Actualizar el filtrado conforme se escribe
     * @param e 
     */
    @Override
    public void keyReleased(KeyEvent e) {
        ventana.filtrar();
    }

}//end VentanaListener
