/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package ud2_01.controlador.dto;

/**
 *
 * @author Jose Javier Bailon Ortiz
 */
public class Empleado {
    private int ID;
    private String nombre;
    private String apellidos;
    private String DNI;
    private double sueldo;

    public Empleado(int ID, String nombre, String apellidos, String DNI, double sueldo) {
        this.ID = ID;
        this.nombre = nombre;
        this.apellidos=apellidos;
        this.DNI = DNI;
        this.sueldo = sueldo;
    }

    public Empleado(int ID) {
        this.ID = ID;
        this.nombre="";
        this.DNI="";
        this.sueldo=0;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }
    
}//end Empleado
