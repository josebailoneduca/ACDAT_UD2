/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_03.controlador.dto;

import java.util.ArrayList;

/**
 * DTO Trabajo
 *
 * @author Jose Javier BO
 */
public class TrabajoEmpleado {

    
    //ATRIBUTOS
    int ID;
    int IDtrabajo;
    int IDempleado;
    
    
    
    
    
    //METODOS
    
    /**
     * Constructor
     * @param ID
     * @param numero
     * @param nombre 
     */
    
    public TrabajoEmpleado(int ID, int IDtrabajo, int IDempleado) {
        this.ID = ID;
        this.IDtrabajo = IDtrabajo;
        this.IDempleado = IDempleado;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIDtrabajo() {
        return IDtrabajo;
    }

    public void setIDtrabajo(int IDtrabajo) {
        this.IDtrabajo = IDtrabajo;
    }

    public int getIDempleado() {
        return IDempleado;
    }

    public void setIDempleado(int IDempleado) {
        this.IDempleado = IDempleado;
    }


    
    
    
}
