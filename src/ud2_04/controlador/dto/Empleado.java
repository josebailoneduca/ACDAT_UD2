/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_04.controlador.dto;

/**
 * DTO empleado
 * @author Jose Javier BO
 */
public class Empleado {
    
    //ATRIBUTOS
    int ID;
    String nombre;
    String apellidos;
    String DNI;
    double sueldo;
    
    
    //METODOS
    
    /**
     * Constructor
     * @param id
     * @param nombre
     * @param apellidos
     * @param dni
     * @param sueldo 
     */
    public Empleado(int id, String nombre, String apellidos, String dni, double sueldo) {
        this.ID = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.DNI = dni;
        this.sueldo = sueldo;
    }

    //GETTERS / SETTERS
    public int getId() {
        return ID;
    }

    public void setId(int id) {
        this.ID = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return DNI;
    }

    public void setDni(String dni) {
        this.DNI = dni;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }
    
    
}
