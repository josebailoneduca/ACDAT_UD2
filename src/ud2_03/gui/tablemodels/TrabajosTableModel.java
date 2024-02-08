/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_03.gui.tablemodels;

 import java.util.List;
import javax.swing.table.AbstractTableModel;
import ud2_03.controlador.dto.Trabajo;
 
/**
 * Tablemodel para la tabla de  trabajos
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class TrabajosTableModel extends AbstractTableModel {

    //ATRIBUTOS:
    private List<Trabajo> listaTrabajos;//lista actual de trabajos
     private String[] columnas = new String[]{"ID", "Nombre", "Descripcion"};
    

    //METODOS:
    //Constructor
    public TrabajosTableModel(List<Trabajo> listaTrabajos, String[] columnas) {
        this.listaTrabajos = listaTrabajos;
        this.columnas=columnas;
    }

    @Override
    public int getRowCount() {
        return this.listaTrabajos.size();
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
                value = listaTrabajos.get(rowIndex).getId();
                break;
            case 1:
                value = listaTrabajos.get(rowIndex).getNombre();
                break;
            case 2:
                value = listaTrabajos.get(rowIndex).getDescripcion();
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
