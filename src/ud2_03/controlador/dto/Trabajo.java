/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_03.controlador.dto;

/**
 * DTO Trabajo
 *
 * @author Jose Javier BO
 */
public class Trabajo {

    
    //ATRIBUTOS
    int ID;
    String nombre;
    String descripcion;
    
    
    
    
    
    //METODOS
    
    /**
     * Constructor
     * @param ID
     * @param nombre
     * @param descripcion 
     */
    
    public Trabajo(int ID, String nombre, String descripcion) {
        this.ID = ID;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }


    
    public Trabajo( String nombre, String descripcion) {
        this.ID = 0;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
 

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    
    
}
