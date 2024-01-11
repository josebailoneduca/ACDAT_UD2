/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package ud2_01.vista.tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import ud2_01.controlador.dto.Empleado;

/**
 *
 * @author Jose Javier Bailon Ortiz
 */
public class EmpleadosTableModel extends AbstractTableModel{
    private List<Empleado> empleados;
    private String[]columnas={"ID","Nombre","DNI","Sueldo"};
    
    public EmpleadosTableModel(List<Empleado>empleados) {
        this.empleados = empleados;
    }
    
    
    
    @Override
    public int getRowCount() {
        return empleados.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Empleado e = empleados.get(rowIndex);
        switch (columnIndex) {
            case 0 ->{ return e.getID();}
            case 1 ->{ return e.getNombre();}
            case 2 ->{ return e.getDNI();}
            case 3 ->{ return e.getSueldo();}
            default -> throw new AssertionError();
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0 ->{ return Integer.class;}
            case 1 ->{ return String.class;}
            case 2 ->{ return String.class;}
            case 3 ->{ return Double.class;}
            default -> throw new AssertionError();
        }    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

}//end EmpleadosTableModel
