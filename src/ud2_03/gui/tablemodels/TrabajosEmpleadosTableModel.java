/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_03.gui.tablemodels;

 import java.util.List;
import javax.swing.table.AbstractTableModel;
import ud2_03.controlador.dto.TrabajoEmpleado;
 
/**
 * Tablemodel para la tabla de  trabajos
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class TrabajosEmpleadosTableModel extends AbstractTableModel {

    //ATRIBUTOS:
    private List<TrabajoEmpleado> listaTrabajosEmpleados;//lista actual de trabajosempleados
     private String[] columnas;
    

    //METODOS:
    //Constructor
    public TrabajosEmpleadosTableModel(List<TrabajoEmpleado> listaTrabajosEmpleados, String[] columnas) {
        this.listaTrabajosEmpleados = listaTrabajosEmpleados;
        this.columnas=columnas;
    }

    @Override
    public int getRowCount() {
        return this.listaTrabajosEmpleados.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = listaTrabajosEmpleados.get(rowIndex).getID();
                break;
            case 1:
                value = listaTrabajosEmpleados.get(rowIndex).getIDtrabajo();
                break;
            case 2:
                value = listaTrabajosEmpleados.get(rowIndex).getIDempleado();
                break;
            default:
                value = null;
                throw new AssertionError();
        }
        return value;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    
}//end EncuestasTableModel
