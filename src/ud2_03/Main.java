/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_03;

import ud2_03.controlador.Controlador;
/**
 *
 * @author Jose Javier BO
 */
public class Main {
    //Datos de conexion a la base de datos
    private static final String HOST = "localhost";
    private static final int PUERTO = 3306;
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String BASE_DATOS = "trabajosempleados";
    public static void main(String[] args) {
        new Controlador(HOST, PUERTO, USER, PASSWORD, BASE_DATOS);
    }
}
