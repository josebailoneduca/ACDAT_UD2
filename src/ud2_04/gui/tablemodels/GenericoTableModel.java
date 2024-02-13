/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_04.gui.tablemodels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jose Javier BO
 */
public class GenericoTableModel extends AbstractTableModel {

    //ATRIBUTOS:
    private ArrayList<ArrayList<String>> items;//lista actual de trabajos
     private String[] columnas;
    

    //METODOS:
    //Constructor
    public GenericoTableModel(ArrayList<ArrayList<String>>  items, String[] columnas) {
        this.items = items;
        this.columnas=columnas;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;
       value = items.get(rowIndex).get(columnIndex);
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
