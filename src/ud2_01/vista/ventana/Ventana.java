/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
Lista de paquetes:
 */
package ud2_01.vista.ventana;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableRowSorter;
import ud2_01.controlador.Controlador;
import ud2_01.controlador.dto.Empleado;
import ud2_01.vista.Texto;
import ud2_01.vista.dialogos.DialogoFormularioEmpleado;
import ud2_01.vista.listeners.VentanaListener;
import ud2_01.vista.tablemodels.EmpleadosTableModel;

/**
 * Ventana principal. Punto de entrada hacia la vista. Ademas de tener lo
 * esencial para controlar la interfaz grafica tiene metodos para lanzar
 * jOptionPane de error e informacion.
 *
 * Los elementos son escuchados por VentanaListener y luego se ejecutan metodos
 * de esta misma ventana.
 *
 * @author Jose Javier BO
 * @see VentanaListener
 */
public class Ventana extends javax.swing.JFrame {

    //Listener de los elementos de la ventana
    private VentanaListener listener;

    //Modo actual del filtro de la tabla. True es por DNI, False es por sueldo
    public boolean filtroPorDni = true;

    /**
     * Creates new form Ventana
     */
    public Ventana() {
        initComponents();
        eventos();
        configTablaEmpleados();
    }

    /**
     * Configura la asignacion de listener
     */
    private void eventos() {
        //listener
        this.listener = new VentanaListener(this);

        //botones
        miSalir.addActionListener(listener);
        btnInsert.addActionListener(listener);
        btnDelete.addActionListener(listener);
        btnEdit.addActionListener(listener);
        btnRefresh.addActionListener(listener);
        btnQuitarFiltro.addActionListener(listener);

        //radio buttons
        rbDni.addActionListener(listener);
        rbSueldo.addActionListener(listener);

        //teclas en el campo de filtro
        inputFiltro.addKeyListener(listener);
    }

    /**
     * Configuracion inicial de la tabla de empleados. El Tablemodel estara
     * vinculado con el array de Empleados de controlador. Tiene establecido un
     * tableshorter y limitada la seleccion a una sola fila
     */
    private void configTablaEmpleados() {
        EmpleadosTableModel etm = new EmpleadosTableModel(Controlador.empleados);
        tablaEmpleados.setModel(etm);

        //permitir solo seleccionar 1 fila
        tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //crear sorter
        TableRowSorter<EmpleadosTableModel> rowSorter = new TableRowSorter<>(etm);
        tablaEmpleados.setRowSorter(rowSorter);

        //ordenacion por defecto inicial
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        rowSorter.setSortKeys(sortKeys);

    }

    /**
     * Actualiza el contenido de la tabla de empleados
     */
    private void actualizarTablaEmpleados() {
        //actualizar los datos de la tabla 
        try {
            EmpleadosTableModel etm = (EmpleadosTableModel) tablaEmpleados.getModel();
            etm.fireTableDataChanged();
        } catch (ClassCastException ex) {
        }
    }

    /**
     * Actualiza el filtrado de la tabla
     */
    public void filtrar() {
        //seleccionar columna de filtro (3 dni, 4 sueldo)
        int indiceFiltro = 3;
        if (!filtroPorDni) {
            indiceFiltro = 4;
        }

        //generar un filtro acorde a los parametros del inputFiltro y la columna
        RowFilter<EmpleadosTableModel, Integer> rf = RowFilter.regexFilter(inputFiltro.getText(), indiceFiltro);
        TableRowSorter<EmpleadosTableModel> rs = (TableRowSorter<EmpleadosTableModel>) tablaEmpleados.getRowSorter();
        rs.setRowFilter(rf);

        //eliminar la posible seleccion que hubiera en la tabla
        tablaEmpleados.getSelectionModel().clearSelection();
    }

    /**
     * Pide a controlador que recarge los empleados de la base de datos y lanza
     * el refrescro de la tabla
     */
    public void actualizarEmpleados() {
        Controlador.actualizarEmpleados();
        actualizarTablaEmpleados();
    }

    /**
     * Vacia el campo de texto del filtro y lo actualiza en tabla
     */
    public void quitarFiltro() {
        inputFiltro.setText("");
        filtrar();
    }

    /**
     * Elimina el empleado seleccionado en la tabla. Recoge la id del empleado y
     * pide a controlador que lo elimine.
     */
    public void eliminar() {

        //recoger id del empleado
        int idSeleccionada = getIdEmpleadoSeleccionado();
        if (idSeleccionada == -1) {
            msgInfo(Texto.SELECCIONE_EMPLEADO);
            return;
        }

        //comprobar si existe en la base de datos
        Empleado empleado = Controlador.getEmpleado(idSeleccionada);
        if (empleado == null) {
            msgInfo(Texto.EMPLEADO_NO_EXISTE);
            return;
        }

        //Pedir confirmacion al usuario
        boolean confirmado = confirmar(Texto.DESEA_ELIMINAR_EMPLEADO + " (" + empleado.getID() + ") " + empleado.getNombre() + " " + empleado.getApellidos() + "?");

        //ordenar a controlador la eliminacion
        if (confirmado && Controlador.eliminar(idSeleccionada) != -1) {
            msgInfo(Texto.EMPLEADO_ELIMINADO);
            actualizarEmpleados();
        }
    }

    /**
     * Devuelve la id del empleado seleccionado en la tabla
     *
     * @return La id del empleado
     */
    private int getIdEmpleadoSeleccionado() {
        int idEmpleado = -1;
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada != -1) {
            idEmpleado = (int) tablaEmpleados.getValueAt(filaSeleccionada, 0);
        }
        return idEmpleado;
    }

    /**
     * Inicia la edicion de un empleado. Recoge la id del empleado seleccionado
     * en la tabla. Usando ese ID pide a controlador que lo recoga de la base de
     * datos Usando ese empleado recogido abre el DialogoFormularioEmpleado el
     * cual se encarga del resto. Una vez se cierra el dialogo actualiza los
     * empleados.
     *
     * @see DialogoFormularioEmpleado
     */
    public void editar() {
        //id del empleado
        int idEmpleado = getIdEmpleadoSeleccionado();

        if (idEmpleado == -1) {
            msgInfo(Texto.SELECCIONE_EMPLEADO);
            return;
        }

        //empleado que hay en BD
        Empleado empleado = Controlador.getEmpleado(idEmpleado);

        if (empleado == null) {
            msgInfo(Texto.EMPLEADO_NO_EXISTE);
            return;
        }

        //abrir dialogo de edicion
        DialogoFormularioEmpleado fe = new DialogoFormularioEmpleado(this, DialogoFormularioEmpleado.EDITAR, empleado);
        fe.setLocationRelativeTo(this);
        fe.setVisible(true);

        //actualizar tabla
        actualizarEmpleados();
    }

    /**
     * Genera el DialogoFormularioEmpleado para insertar un nuevo empleado. El
     * dialogo se encarga del resto. Una vez el dialogo se cierra se actualiza
     * la tabla de empleados
     *
     * @see DialogoFormularioEmpleado
     */
    public void insertar() {
        DialogoFormularioEmpleado fe = new DialogoFormularioEmpleado(this, DialogoFormularioEmpleado.CREAR, null);
        fe.setLocationRelativeTo(this);
        fe.setVisible(true);
        actualizarEmpleados();
    }

    /**
     * Confirma si se desea salir del programa. Si el usuario lo afirma pide a
     * controlador que cierre el programa
     */
    public void salir() {
        if (confirmar("¿Desea salir?")) {
            Controlador.salir();
        }
    }

    /**
     * Muestra un mensaje de error
     * @param msg  El mensaje
     */
    public void msgError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje de informacion
     * @param msg  El mensaje
     */
    public void msgInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de confirmacion
     *
     * @param msg El mensaje a mostrar
     * @return True si ha pulsado SI. False si ha pulsado NO
     */
    private boolean confirmar(String msg) {
        int respuesta = JOptionPane.showConfirmDialog(this, msg, "", JOptionPane.YES_NO_OPTION);
        return respuesta == JOptionPane.YES_OPTION;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grbFiltro = new javax.swing.ButtonGroup();
        scrollTabla = new javax.swing.JScrollPane();
        tablaEmpleados = new javax.swing.JTable();
        panelCrud = new javax.swing.JPanel();
        btnInsert = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        panelFiltro = new javax.swing.JPanel();
        panelTipoFiltro = new javax.swing.JPanel();
        lbFiltro = new javax.swing.JLabel();
        rbDni = new javax.swing.JRadioButton();
        rbSueldo = new javax.swing.JRadioButton();
        inputFiltro = new javax.swing.JTextField();
        btnQuitarFiltro = new javax.swing.JButton();
        barraMenu = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        miSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        scrollTabla.setViewportView(tablaEmpleados);

        panelCrud.setLayout(new java.awt.GridLayout(4, 1, 0, 10));

        btnInsert.setText("Insertar");
        btnInsert.setActionCommand("insertar");
        panelCrud.add(btnInsert);

        btnDelete.setText("Eliminar");
        btnDelete.setActionCommand("eliminar");
        panelCrud.add(btnDelete);

        btnEdit.setActionCommand("editar");
        btnEdit.setLabel("Editar");
        panelCrud.add(btnEdit);

        btnRefresh.setText("Actualizar lista");
        btnRefresh.setToolTipText("");
        btnRefresh.setActionCommand("actualizar");
        panelCrud.add(btnRefresh);

        panelFiltro.setLayout(new java.awt.GridLayout(3, 0, 0, 10));

        lbFiltro.setText("Filtrar:");
        panelTipoFiltro.add(lbFiltro);

        grbFiltro.add(rbDni);
        rbDni.setSelected(true);
        rbDni.setText("DNI");
        rbDni.setActionCommand("dni");
        panelTipoFiltro.add(rbDni);

        grbFiltro.add(rbSueldo);
        rbSueldo.setText("Sueldo");
        rbSueldo.setActionCommand("sueldo");
        panelTipoFiltro.add(rbSueldo);

        panelFiltro.add(panelTipoFiltro);
        panelFiltro.add(inputFiltro);

        btnQuitarFiltro.setText("Quitar filtro");
        btnQuitarFiltro.setActionCommand("quitarfiltro");
        panelFiltro.add(btnQuitarFiltro);

        menuArchivo.setText("Archivo");

        miSalir.setText("Salir");
        miSalir.setActionCommand("salir");
        menuArchivo.add(miSalir);

        barraMenu.add(menuArchivo);

        setJMenuBar(barraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCrud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(panelCrud, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(panelFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnQuitarFiltro;
    private javax.swing.JButton btnRefresh;
    private javax.swing.ButtonGroup grbFiltro;
    private javax.swing.JTextField inputFiltro;
    private javax.swing.JLabel lbFiltro;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuItem miSalir;
    private javax.swing.JPanel panelCrud;
    private javax.swing.JPanel panelFiltro;
    private javax.swing.JPanel panelTipoFiltro;
    private javax.swing.JRadioButton rbDni;
    private javax.swing.JRadioButton rbSueldo;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tablaEmpleados;
    // End of variables declaration//GEN-END:variables

}
