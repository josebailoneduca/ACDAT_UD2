/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
Lista de paquetes:
 */
package ud2_03.gui.ventanas;

import java.awt.CardLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import ud2_03.controlador.dto.Trabajo;
import ud2_03.controlador.dto.Empleado;
import ud2_03.gui.tablemodels.EmpleadosTableModel;
import ud2_03.gui.tablemodels.TrabajosTableModel;
import ud2_03.controlador.Controlador;
import ud2_03.controlador.dto.TrabajoEmpleado;
import ud2_03.gui.tablemodels.TrabajosEmpleadosTableModel;

/**
 *
 * @author Jose Javier BO
 */
public class Vista extends javax.swing.JFrame implements ListSelectionListener {

    //ATRIBUTOS
    private Trabajo trabajoSiendoEditado = null;
    private Empleado empleadoSiendoEditado = null;
    private boolean operacionesActivadas = false;
    private Controlador controlador=null;
    
    private Autodesconexion autodesconexion;
    /**
     * Creates new form VentanaPrincipal
     */
    public Vista() {
    }

    public void setControlador(Controlador controlador){
        this.controlador=controlador;
        initComponents();
        CardLayout cl = (CardLayout) this.panelCardsGeneral.getLayout();
        cl.show(panelCardsGeneral, "panelDesconectado");
        autodesconexion=new Autodesconexion(this);
        autodesconexion.start();
    }
//###############################################################
//<editor-fold defaultstate="collapsed" desc="INICIALIZACION">
 

    /**
     * Inicializa la tabla de trabajos conformando su modelo e introduciendo los datos
     * necesarios
     */
    private void inicializarTablaTrabajos() {
        
        ArrayList<Trabajo> listaTrabajos = new ArrayList<Trabajo>();
        controlador.getAll(1).stream().forEach((t) -> {listaTrabajos.add((Trabajo)t);});
        
        //crear modelo
        TrabajosTableModel ttm = new TrabajosTableModel(listaTrabajos, controlador.getColumnas(1));
        //establecer el modelo como modelo de la tabla
        this.tblTrabajos.setModel(ttm);

        //crear sorter
        TableRowSorter<TrabajosTableModel> rowSorter = new TableRowSorter<>(ttm);
        this.tblTrabajos.setRowSorter(rowSorter);

        //ordenacion por defecto inicial
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        rowSorter.setSortKeys(sortKeys);

        //listener de seleccion de fila
        this.tblTrabajos.getSelectionModel().addListSelectionListener(this);
    }
    
  

    /**
     * Establece un modelo preliminar para la tabla de empleados de un trabajo
     */
    private void inicializarTablaEmpleadosDeTrabajo(int idTrabajo) {
        ArrayList<TrabajoEmpleado> listaTrabajosEmpleados = new ArrayList<TrabajoEmpleado>();
        controlador.getTuplas(2, 1, ""+idTrabajo).stream().forEach((t) -> {listaTrabajosEmpleados.add((TrabajoEmpleado)t);});
        //crear modelo
        TrabajosEmpleadosTableModel tetm = new TrabajosEmpleadosTableModel(listaTrabajosEmpleados, controlador.getColumnas(2));
        //establecer el modelo como modelo de la tabla
        this.tblTrabajosEmpleados.setModel(tetm);

        //crear sorter
        TableRowSorter<TrabajosEmpleadosTableModel> rowSorter = new TableRowSorter<>(tetm);
        this.tblTrabajosEmpleados.setRowSorter(rowSorter);

        //ordenacion por defecto inicial
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        rowSorter.setSortKeys(sortKeys);

        //listener de seleccion de fila
        this.tblTrabajosEmpleados.getSelectionModel().addListSelectionListener(this);
    }

    /**
     * Inicializa la tabla de todos los empleados conformando su modelo e introduciendo los datos
     * necesarios
     */
    private void inicializarTablaTodosEmpleados() {
        ArrayList<Empleado> listaEmpleados = new ArrayList<Empleado>();
        controlador.getAll(0).stream().forEach((t) -> {listaEmpleados.add((Empleado)t);});
        //crear modelo
        EmpleadosTableModel etm = new EmpleadosTableModel(listaEmpleados, controlador.getColumnas(0));
        //establecer el modelo como modelo de la tabla
        this.tblEmpleados.setModel(etm);

        //crear sorter
        TableRowSorter<EmpleadosTableModel> rowSorter = new TableRowSorter<>(etm);
        this.tblEmpleados.setRowSorter(rowSorter);

        //ordenacion por defecto inicial
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        rowSorter.setSortKeys(sortKeys);

        //listener de seleccion de fila
        this.tblEmpleados.getSelectionModel().addListSelectionListener(this);

    }
 

//</editor-fold>
//########################################################################
//<editor-fold defaultstate="collapsed" desc="MENU">   

    /**
     * Elemento de menu de salir
     * @param evt 
     */
    private void miSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSalirActionPerformed
        if (confirmar("¿Salir del programa?"))
            System.exit(0);
    }//GEN-LAST:event_miSalirActionPerformed

    /**
     * Elemento de menu de trabajo
     * @param evt 
     */
    private void miTrabajosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miTrabajosActionPerformed
        //mostrar panel de resulados
        CardLayout cl = (CardLayout) this.panelCardsGeneral.getLayout();
        cl.show(panelCardsGeneral, "panelGeneralTrabajos");
    }//GEN-LAST:event_miTrabajosActionPerformed

    /**
     * Elemento de menu de empleados
     * @param evt 
     */
    private void miEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEmpleadosActionPerformed
        inicializarTablaTodosEmpleados();
        CardLayout cl = (CardLayout) this.panelCardsGeneral.getLayout();
        cl.show(panelCardsGeneral, "panelGeneralEmpleados");
        habilitarFiltrado();
    }//GEN-LAST:event_miEmpleadosActionPerformed

    //</editor-fold>
//#####################################################################
//<editor-fold defaultstate="collapsed" desc="TRABAJOS">  
    //############################################################
    //<editor-fold defaultstate="collapsed" desc="TRABAJO NUEVO">   

    /**
     * Boton agregar trabajo. Muestra y reinicia el formulario de agregar un
     * trabajo
     * @param evt 
     */
    private void btnAgregarTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTrabajoActionPerformed
        resetearFormNuevoTrabajo();
    }//GEN-LAST:event_btnAgregarTrabajoActionPerformed

    /**
     * Escucha de la tecla en la entrada de nombre de trabajo para deshabilitar
     * el boton de guardar trabajo si el nombre esta vacio
     * @param evt 
     */
    private void inputNombreTrabajoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputNombreTrabajoKeyReleased
        String txt = this.inputNombreTrabajo.getText();
        if (txt == null || txt != "")
            this.btnGuardarTrabajoNuevo.setEnabled(true);
        else
            this.btnGuardarTrabajoNuevo.setEnabled(false);
    }//GEN-LAST:event_inputNombreTrabajoKeyReleased

    
    /**
     * Boton de guardar el nuevo trabajo
     * @param evt 
     */
    private void btnGuardarTrabajoNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarTrabajoNuevoActionPerformed

        String nombre = inputNombreTrabajo.getText();
        String descripcion = inputDescripcionTrabajo.getText();
        if (nombre.length() > 0) {
            String[] valores={nombre,descripcion};
            controlador.insert(1,valores);
            this.inicializarTablaTrabajos();
            resetearFormNuevoTrabajo();
        }
    }//GEN-LAST:event_btnGuardarTrabajoNuevoActionPerformed
//</editor-fold>//</editor-fold>

    //############################################
    //<editor-fold defaultstate="collapsed" desc="TRABAJO ADMINISTRAR EMPLEADOS">   
    /**
     * Inicia la edicion de un trabajo
     * @param id Id del trabajo
     */
    private void editarEmpleadosTrabajo(int idTrabajo) {

        this.trabajoSiendoEditado = (Trabajo)controlador.getTupla(1,idTrabajo);
        if (trabajoSiendoEditado == null) {
            return;
        }

        CardLayout cl = (CardLayout) this.panelAccionesTrabajos.getLayout();
        cl.show(panelAccionesTrabajos, "panelGestionEmpleadosTrabajo");
        inicializarTablaEmpleadosDeTrabajo(idTrabajo);
        lbNombreTrabajo.setText(trabajoSiendoEditado.getNombre()+" - "+trabajoSiendoEditado.getDescripcion());

    }

    
    private void editarTrabajo(int id) {

        this.trabajoSiendoEditado = (Trabajo)controlador.getTupla(1,id);
        if (trabajoSiendoEditado == null) {
            return;
        }

        CardLayout cl = (CardLayout) this.panelAccionesTrabajos.getLayout();
        cl.show(panelAccionesTrabajos, "panelEditarTrabajo");
        this.lbEditarIdTrabajo.setText(""+trabajoSiendoEditado.getID());
        this.inputEditarNombreTrabajo.setText(trabajoSiendoEditado.getNombre());
        this.inputEditarDescripcionTrabajo.setText(trabajoSiendoEditado.getDescripcion());
        this.btnGuardarTrabajoNuevo.setEnabled(false);
    }
    
 

 

    /**
     * Recoge el trabajo seleccionado en la tabla de trabajos y si hay alguno seleccionado
     * recoge su id e inicia la edicion del trabajo
     * @param evt 
     */
    private void btnGestionarEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionarEmpleadosActionPerformed
        int filaSeleccionada = this.tblTrabajos.getSelectedRow();
        if (filaSeleccionada == -1) {
            return;//no hacer nada si no hay fila seleccionada
        }        //Hace conversion segun el modelo de tabla
        int filaCorrecta = this.tblTrabajos.convertRowIndexToModel(filaSeleccionada);
        int idTrabajo = (int) this.tblTrabajos.getValueAt(filaCorrecta, 0);
        editarEmpleadosTrabajo(idTrabajo);

    }//GEN-LAST:event_btnGestionarEmpleadosActionPerformed


    /**
     * Desasigna un empleado de un trabajo
     * @param evt 
     */
    private void btnDesasignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesasignarActionPerformed
        int filaSeleccionada = this.tblTrabajosEmpleados.getSelectedRow();
        if (filaSeleccionada == -1) {
            return;//no hacer nada si no hay fila seleccionada
        }        //Hace conversion segun el modelo de tabla
        int filaCorrecta = this.tblTrabajosEmpleados.convertRowIndexToModel(filaSeleccionada);
        int idTupla = (int) this.tblTrabajosEmpleados.getModel().getValueAt(filaCorrecta, 0);
        int idTrabajo =(int) this.tblTrabajosEmpleados.getModel().getValueAt(filaCorrecta, 1);
        controlador.delete(2, ""+idTupla);
        inicializarTablaEmpleadosDeTrabajo(idTrabajo);
        
    }//GEN-LAST:event_btnDesasignarActionPerformed
//</editor-fold>

//</editor-fold>
//####################################################################
//<editor-fold defaultstate="collapsed" desc="EMPLEADOS">  
//EMPLEADOS ############################################################
    //<editor-fold defaultstate="collapsed" desc="EMPLEADOS FILTRAR">  
    /**
     * Activa el filtro en funcion del contenido actual del campo de busquda
     * @param evt 
     */
    private void btnBuscarFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFiltroActionPerformed
        filtrarEmpleados();
    }//GEN-LAST:event_btnBuscarFiltroActionPerformed

    /**
     * Escucha de la tecla en el campo de busqueda. activa y desactiva el boton
     * de busqueda y filtra los empleados automaticamente cuando no hay testo
     * @param evt 
     */
    private void busquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaKeyReleased
        if (busqueda.getText().length() > 0)
            btnBuscarFiltro.setEnabled(true);
        else {
            btnBuscarFiltro.setEnabled(false);
            filtrarEmpleados();
        }
    }//GEN-LAST:event_busquedaKeyReleased

    /**
     * Habilita o deshabilita el filtrado en función de si hay o no empleados
     */
    private void habilitarFiltrado(){
        
        //TODO manejar filtrado
//        if (Controlador.getEmpleados().size()>0){
//            for (Component c : panelFiltros.getComponents()){
//                c.setEnabled(true);
//            }
//            if (busqueda.getText().length()==0)
//                btnBuscarFiltro.setEnabled(false);
//        }else{
//            for (Component c : panelFiltros.getComponents()){
//                c.setEnabled(false);
//            }
//        }
    }
    /**
     * Establece el filtro a usar por la tabla de empleados en funcion de lo 
     * seleccionado
     */
    private void filtrarEmpleados() {
        String txtFiltro = this.busqueda.getText();
        int indiceFiltro = 2;
        //gestion del radiobutton group para la seleccion de sexo
        ButtonModel seleccionado = this.rgFiltro.getSelection();
        if (seleccionado == null) {
            return;
        }
        String columna = seleccionado.getActionCommand();
        switch (columna) {
            case "Apellido":
                indiceFiltro = 2;
                break;
            case "DNI":
                indiceFiltro = 3;
                break;
            case "Sueldo":
                indiceFiltro = 4;
                break;
            default:
                throw new AssertionError();
        }

        RowFilter<EmpleadosTableModel, Integer> rf = RowFilter.regexFilter(txtFiltro, indiceFiltro);
        TableRowSorter<EmpleadosTableModel> rs = (TableRowSorter<EmpleadosTableModel>) tblEmpleados.getRowSorter();
        rs.setRowFilter(rf);
        tblEmpleados.getSelectionModel().clearSelection();
    }
    
    
     /**
     * Activa el filtro en funcion del contenido actual del campo de busquda
     * @param evt 
     */
	 private void btnFiltroLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltroLimpiarActionPerformed
        this.busqueda.setText("");
        btnBuscarFiltro.setEnabled(false);
        filtrarEmpleados();
    }//GEN-LAST:event_btnFiltroLimpiarActionPerformed
                                                   

//</editor-fold>

//##################################################
    //<editor-fold defaultstate="collapsed" desc="EMPLEADOS OPERACIONES UTILES">  
    
    /**
     * Funcion activada cuando se selecciona un empleado en la tabla de empleados.
     * Recoge el empleado de la logica y relleta la ficha de empleado.
     *  
     * @param idEmpleado Id del empleado 
     */
    private void empleadoSeleccionado(int idEmpleado) {
        empleadoSiendoEditado = (Empleado)controlador.getTupla(0,idEmpleado);
        if (operacionesActivadas)
            rellenarFichaEmpleado();
    }

    /**
     * Evento al pulsar el boton de activar y desactivar operaciones.
     * Activa o desactiva las operaciones
     *
     * @param evt
     */
    private void btnOperacionesActDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOperacionesActDesActionPerformed

        if (this.operacionesActivadas) {
            desactivarOperaciones();
        } else {
            activarOperaciones();
        }
    }//GEN-LAST:event_btnOperacionesActDesActionPerformed
   
   
    /**
     * Activa las operaciones. Pone los radiobuttons en habilitadoy activa la opcion
     * de modificar si hay algun empleado siendo editado. En caso contrario
     * habilita solo el radioutton de anadir usuario y activa la accion de anadir
     * usuario
     */
    private void activarOperaciones() {
        activarBotonOperaciones();
        if (empleadoSiendoEditado != null) {
            rbModificarEmpleado.setEnabled(true);
            rbAnadirEmpleado.setEnabled(true);
            rbAsignarEmpleado.setEnabled(true);
            rbEliminarEmpleado.setEnabled(true);
            this.rgOperacionesEmpleado.setSelected(rbModificarEmpleado.getModel(), true);
            activarOpcionModificarEmpleado();
        } else {
            rbAnadirEmpleado.setEnabled(true);
            this.rgOperacionesEmpleado.setSelected(rbAnadirEmpleado.getModel(), true);
            activarOpcionAnadirEmpleado();
        }
    }
    
    
    /**
     * Activa el botón de operaciones
     */
    private void activarBotonOperaciones(){
        this.operacionesActivadas = true;
        btnOperacionesActDes.setText("Operaciones: Desactivar");
    }
    
    /**
     * Desactiva el boton de operaciones
     */
    private void desactivarBotonOperaciones(){
                this.operacionesActivadas = false;
        btnOperacionesActDes.setText("Operaciones: Activar");
    }
    
    /**
     * Activa las operaciones
     */
    private void desactivarOperaciones() {
        this.operacionesActivadas = false;
        btnOperacionesActDes.setText("Operaciones: Activar");
        rbModificarEmpleado.setEnabled(false);
        rbAnadirEmpleado.setEnabled(false);
        rbAsignarEmpleado.setEnabled(false);
        rbEliminarEmpleado.setEnabled(false);
        desactivarBotonesOperaciones();
        vaciarFichaEmpleado();
        deshabilitarFichaEmpleado();
    }
    
    /**
     * Desactiva las operaciones
     */
    private void desactivarBotonesOperaciones() {
        btnGuardarCambios.setEnabled(false);
        btnGuardarEmpleado.setEnabled(false);
        btnEliminarEmpleado.setEnabled(false);
        lbOperacionesNtrabajo.setEnabled(false);
        inputNTrabajoEmpleado.setEnabled(false);
        btnAsignarEmpleado.setEnabled(false);
        setFichaNoEditable();

    }

    /**
     * Resetea el formulario de agregar un trabajo
     */
    private void resetearFormNuevoTrabajo() {
        CardLayout cl = (CardLayout) this.panelAccionesTrabajos.getLayout();
        cl.show(panelAccionesTrabajos, "panelAgregarTrabajo");
        //TODO comprobar si hay que prever proxima id trabajo
        //this.lbIdTrabajo.setText("" + Controlador.proximaIdTrabajo);
        this.inputNombreTrabajo.setText("");
        this.btnGuardarTrabajoNuevo.setEnabled(false);
    }


    /**
     * Rellena la ficha e empleado
     */
    private void rellenarFichaEmpleado() {
        inputNombreEmpleado.setText(empleadoSiendoEditado.getNombre());
        inputApellidosEmpleado.setText(empleadoSiendoEditado.getApellidos());
        inputDniEmpleado.setText(empleadoSiendoEditado.getDni());
        inputSueldoEmpleado.setText("" + empleadoSiendoEditado.getSueldo());
        inputIdEmpleado.setText("" + empleadoSiendoEditado.getId());
        inputIdEmpleado.setVisible(true);
        lbFichaEmpID.setVisible(true);
        habilitarFichaEmpleado();
        setFichaNoEditable();
    }

    
    /**
     * Vacia la ficha de empleado
     */
    private void vaciarFichaEmpleado() {
        habilitarFichaEmpleado();
        inputNombreEmpleado.setText("");
        inputApellidosEmpleado.setText("");
        inputDniEmpleado.setText("");
        inputSueldoEmpleado.setText("");
        inputIdEmpleado.setText("");
        setFichaNoEditable();
    }

    /**
     * Habilita la ficha de empeado
     */
    private void habilitarFichaEmpleado() {
        lbFichaEmpleado.setEnabled(true);
        lbFichaEmpNombre.setEnabled(true);
        lbFichaEmpApellidos.setEnabled(true);
        lbFichaEmpDNI.setEnabled(true);
        lbFichaEmpSueldo.setEnabled(true);
        lbFichaEmpID.setEnabled(true);
        inputNombreEmpleado.setEnabled(true);
        inputApellidosEmpleado.setEnabled(true);
        inputDniEmpleado.setEnabled(true);
        inputSueldoEmpleado.setEnabled(true);
        inputIdEmpleado.setEnabled(true);
    }

    /**
     * Deshabilita la ficha de empleado
     */
    private void deshabilitarFichaEmpleado() {
        lbFichaEmpleado.setEnabled(false);
        lbFichaEmpNombre.setEnabled(false);
        lbFichaEmpApellidos.setEnabled(false);
        lbFichaEmpDNI.setEnabled(false);
        lbFichaEmpSueldo.setEnabled(false);
        lbFichaEmpID.setEnabled(false);
        inputNombreEmpleado.setEnabled(false);
        inputApellidosEmpleado.setEnabled(false);
        inputDniEmpleado.setEnabled(false);
        inputSueldoEmpleado.setEnabled(false);
        inputIdEmpleado.setEnabled(false);
        setFichaNoEditable();
    }

    /**
     * Pone la ficha de empleado como NO editable
     */
    private void setFichaNoEditable() {
        inputNombreEmpleado.setEditable(false);
        inputApellidosEmpleado.setEditable(false);
        inputDniEmpleado.setEditable(false);
        inputSueldoEmpleado.setEditable(false);
    }

    /**
     * Pone la ficha de empleado como editable
     */
    private void setFichaSiEditable() {
        inputNombreEmpleado.setEditable(true);
        inputApellidosEmpleado.setEditable(true);
        inputDniEmpleado.setEditable(true);
        inputSueldoEmpleado.setEditable(true);
    }

    /**
     * Recoge los datos del formulario de empleado
     * @return Un objeto empleado con los datos
     */
    private Empleado recogerDatosFormularioEmpleado() {
        String nombre = this.inputNombreEmpleado.getText();
        if (nombre.length() < 1) {
            msgError("Nombre no válido");
            return null;
        }

        String apellidos = this.inputApellidosEmpleado.getText();
        if (apellidos.length() < 1) {
            msgError("Apellido no válido");
            return null;
        }

        String dni = this.inputDniEmpleado.getText();
        if (dni.length() != 9 ) {
            msgError("Dni no válido. Debe tener 9 caracteres");
            return null;
        }

        double sueldo = 0;
        try {
            sueldo = Double.parseDouble(this.inputSueldoEmpleado.getText());
            if (sueldo < 0) {
                throw new Exception();
            }
        } catch (Exception ex) {
            msgError("Sueldo no válido");
            return null;
        }
        int id=-1;
        try{
        id = Integer.parseInt(this.inputIdEmpleado.getText());
        }catch(NumberFormatException ex){
        
        }

        return new Empleado(id, nombre, apellidos, dni, sueldo);

    }
//</editor-fold>

//##########################################################
    //<editor-fold defaultstate="collapsed" desc="EMPLEADOS OPERACION MODIFICAR">  
    private void activarOpcionModificarEmpleado() {
        desactivarBotonesOperaciones();
        btnGuardarCambios.setEnabled(true);
        rellenarFichaEmpleado();
        setFichaSiEditable();
    }

    /**
     * Seleccion de la opcion de modificar empleado
     * @param evt 
     */
    private void rbModificarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbModificarEmpleadoActionPerformed
        activarOpcionModificarEmpleado();
    }//GEN-LAST:event_rbModificarEmpleadoActionPerformed


    /**
     * Guarda los cambios de un empleado modificado
     * @param evt 
     */
    private void btnGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCambiosActionPerformed
        Empleado e = recogerDatosFormularioEmpleado();
        if (e != null) {
            controlador.update(0, ""+e.getId(),e.getNombre(),e.getApellidos(),e.getDni(),""+e.getSueldo());
            msgInfo("Empleado modificado");
            inicializarTablaTodosEmpleados();
        }
    }//GEN-LAST:event_btnGuardarCambiosActionPerformed
//</editor-fold>//</editor-fold>

//#############################################################
    //<editor-fold defaultstate="collapsed" desc="EMPLEADOS OPERACION ANADIR">  
    
    /**
     * Activa la opcion de agregar un usuario
     */
    private void activarOpcionAnadirEmpleado() {
        activarBotonOperaciones();
        desactivarBotonesOperaciones();
        rbAnadirEmpleado.setEnabled(true);
        rgOperacionesEmpleado.setSelected( rbAnadirEmpleado.getModel(), true);
        btnGuardarEmpleado.setEnabled(true);
        vaciarFichaEmpleado();
        //ocultar id
        inputIdEmpleado.setVisible(false);
        lbFichaEmpID.setVisible(false);
        setFichaSiEditable();
        habilitarFiltrado();
    }

    /**
     * Seleccion de la opcion de agregar un empleado
     * @param evt 
     */
    private void rbAnadirEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAnadirEmpleadoActionPerformed
        activarOpcionAnadirEmpleado();
    }//GEN-LAST:event_rbAnadirEmpleadoActionPerformed

    
    /**
     * Guarda un empleado nuevo
     * @param evt 
     */
    private void btnGuardarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarEmpleadoActionPerformed
        Empleado e = recogerDatosFormularioEmpleado();
        if (e != null) {
            int res = controlador.insert(0, e.getNombre(),e.getApellidos(),e.getDni(),""+e.getSueldo());
           if (res==1){
            vaciarFichaEmpleado();
            setFichaSiEditable();
            msgInfo("Empleado añadido");
            inicializarTablaTodosEmpleados();
            activarOpcionAnadirEmpleado();
           }else if (res == -2){
               msgError("Ya existe un empleado con ese DNI");
           }else{
           msgError("No se pudo insertar el empleado");
           }
        }
    }//GEN-LAST:event_btnGuardarEmpleadoActionPerformed
//</editor-fold>

//###########################################################
    //<editor-fold defaultstate="collapsed" desc="EMPLEADOS OPERACION ELIMINAR">  
    /**
     * Activar opcion de eliminar empleado
     */
    private void activarOpcionEliminarEmpleado() {
        desactivarBotonesOperaciones();
        btnEliminarEmpleado.setEnabled(true);
        rellenarFichaEmpleado();
        setFichaNoEditable();
        habilitarFiltrado();
    }


    /**
     * Seleccion de la opcion de eliminar empleado
     * @param evt 
     */
    private void rbEliminarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEliminarEmpleadoActionPerformed
        activarOpcionEliminarEmpleado();
    }//GEN-LAST:event_rbEliminarEmpleadoActionPerformed

    /**
     * Elimina un empleado
     * @param evt 
     */
    private void btnEliminarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEmpleadoActionPerformed
        if (confirmar("Realmente desea borrar el empleado " + empleadoSiendoEditado.getId() + " " + empleadoSiendoEditado.getNombre() + " " + empleadoSiendoEditado.getApellidos())) {
            controlador.delete(0, ""+empleadoSiendoEditado.getId());
            msgInfo("Empleado eliminado");
            empleadoSiendoEditado=null;
            inicializarTablaTodosEmpleados();
            if (trabajoSiendoEditado!=null)
            inicializarTablaEmpleadosDeTrabajo(trabajoSiendoEditado.getID());
        }
    }//GEN-LAST:event_btnEliminarEmpleadoActionPerformed
//</editor-fold>

//############################################################
    //<editor-fold defaultstate="collapsed" desc="EMPLEADOS OPERACION ASIGNAR">
    
    /**
     * Activa la opcion de asignar un empleado
     */
    private void activarOpcionAsignarTrabajoAEmpleado() {
        desactivarBotonesOperaciones();
        btnAsignarEmpleado.setEnabled(true);
        inputNTrabajoEmpleado.setEnabled(true);
        inputNTrabajoEmpleado.setText("");
        lbOperacionesNtrabajo.setEnabled(true);
        rellenarFichaEmpleado();
        setFichaNoEditable();
    }

    /**
     * Seleccion de la opcion de asignar empleado
     * @param evt 
     */
    private void rbAsignarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAsignarEmpleadoActionPerformed
        activarOpcionAsignarTrabajoAEmpleado();
    }//GEN-LAST:event_rbAsignarEmpleadoActionPerformed

    /**
     * Asiga un empleado a un trabajo
     * @param evt 
     */
    private void btnAsignarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarEmpleadoActionPerformed
        int numeroTrabajo = 0;
        try {
            numeroTrabajo = Integer.parseInt(inputNTrabajoEmpleado.getText());
            if (numeroTrabajo == 0) {
                throw new Exception();
            }
        } catch (Exception ex) {
            msgError("Numero de trabajo erroneo");
            return;
        }
        
        int res = controlador.insert(2, ""+numeroTrabajo, ""+empleadoSiendoEditado.getId() );
        if (res ==-2)
            msgError("El empleado ("+empleadoSiendoEditado.getId()+")"+empleadoSiendoEditado.getNombre()+" "+empleadoSiendoEditado.getApellidos()+" ya está asignado al trabajo "+numeroTrabajo);
        else if (res ==-3)
            msgError("El trabajado "+numeroTrabajo+" no existe");
        else if (res==-1)
            msgError("Hubo un error asignando el trabajo");
        else{
        msgInfo("Empleado " + empleadoSiendoEditado.getId() + " " + empleadoSiendoEditado.getNombre() + " " + empleadoSiendoEditado.getApellidos() + " asignado a trabajo " + numeroTrabajo);
        inputNTrabajoEmpleado.setText("");
        inicializarTablaTrabajos();
        if (trabajoSiendoEditado!=null)
        inicializarTablaEmpleadosDeTrabajo(trabajoSiendoEditado.getID());
        }
    }//GEN-LAST:event_btnAsignarEmpleadoActionPerformed
    //</editor-fold>
//</editor-fold>

//#############################################################
//<editor-fold defaultstate="collapsed" desc="LISTENER TABLAS">
    /*listener clic tablas*/
    @Override
    public void valueChanged(ListSelectionEvent e) {

        //tabla trabajos
        if (e.getSource().equals(tblTrabajos.getSelectionModel())) {
            if (this.tblTrabajos.getSelectedRow() != -1) {
                btnGestionarEmpleados.setEnabled(true);
                btnEliminarTrabajo.setEnabled(true);
                btnEditarTrabajo.setEnabled(true);
            } else {
                btnGestionarEmpleados.setEnabled(false);
                btnEliminarTrabajo.setEnabled(false);
                btnEditarTrabajo.setEnabled(false);
            }
        } //tabla empleados de trabajo
        else if (e.getSource().equals(tblTrabajosEmpleados.getSelectionModel())) {
            if (this.tblTrabajosEmpleados.getSelectedRow() != -1) {
                btnDesasignar.setEnabled(true);
            } else {
                btnDesasignar.setEnabled(false);
            }
        } //tabla empleados
        else if (e.getSource().equals(tblEmpleados.getSelectionModel())) {
            int filaSeleccionada = this.tblEmpleados.getSelectedRow();
            if (filaSeleccionada != -1) {
                int filaCorrecta = this.tblEmpleados.convertRowIndexToModel(filaSeleccionada);
                int idEmpleado = (int) this.tblEmpleados.getModel().getValueAt(filaCorrecta, 0);
                empleadoSeleccionado(idEmpleado);
                if (operacionesActivadas) {
                    activarOperaciones();
                }
            } else {
                empleadoSiendoEditado=null;
                vaciarFichaEmpleado();
                deshabilitarFichaEmpleado();
                desactivarOperaciones();
            }
        }
    }
//</editor-fold>

//####################################################################
//<editor-fold defaultstate="collapsed" desc="MENSAJES INFORMACION">
    public void msgError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "error", JOptionPane.ERROR_MESSAGE);
    }

    public void msgInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "error", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean confirmar(String msg) {
        Object[] opciones = {"Sí", "No"};
        return JOptionPane.showOptionDialog(this,
                msg,
                "Aviso",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                "default") == JOptionPane.YES_OPTION;
    }
//</editor-fold>

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rgFiltro = new javax.swing.ButtonGroup();
        rgOperacionesEmpleado = new javax.swing.ButtonGroup();
        panelCardsGeneral = new javax.swing.JPanel();
        panelGeneralTrabajos = new javax.swing.JPanel();
        panelSuperiorTrabajos = new javax.swing.JPanel();
        lbGestionDeTrabajos = new javax.swing.JLabel();
        panelTablaTrabajos = new javax.swing.JPanel();
        tblTrabajosScroll = new javax.swing.JScrollPane();
        tblTrabajos = new javax.swing.JTable();
        panelOpcionesTrabajos = new javax.swing.JPanel();
        btnAgregarTrabajo = new javax.swing.JButton();
        btnGestionarEmpleados = new javax.swing.JButton();
        btnEliminarTrabajo = new javax.swing.JButton();
        btnEditarTrabajo = new javax.swing.JButton();
        panelAccionesTrabajos = new javax.swing.JPanel();
        panelGestionEmpleadosTrabajo = new javax.swing.JPanel();
        panelCentralAccionTrabajosGestionaEmpleados = new javax.swing.JPanel();
        lbGestionEmpleados = new javax.swing.JLabel();
        lbNombreTrabajo = new javax.swing.JLabel();
        jScrollPaneTblEmpleadosDeTrabajo = new javax.swing.JScrollPane();
        tblTrabajosEmpleados = new javax.swing.JTable();
        btnDesasignar = new javax.swing.JButton();
        panelAgregarTrabajo = new javax.swing.JPanel();
        lbAgregarTrabajo = new javax.swing.JLabel();
        panelAccionAgregarTrabajoInterno = new javax.swing.JPanel();
        lbNombreTrabajoNuevo = new javax.swing.JLabel();
        inputNombreTrabajo = new javax.swing.JTextField();
        btnGuardarTrabajoNuevo = new javax.swing.JButton();
        lbDescripcionTrabajo = new javax.swing.JLabel();
        inputDescripcionTrabajo = new javax.swing.JTextField();
        panelEditarTrabajo = new javax.swing.JPanel();
        lbEditarTrabajo = new javax.swing.JLabel();
        panelAccionEditarTrabajoInterno = new javax.swing.JPanel();
        lbEditarNTrabajoinfo = new javax.swing.JLabel();
        lbEditarIdTrabajo = new javax.swing.JLabel();
        lbEditarNombreTrabajo = new javax.swing.JLabel();
        inputEditarNombreTrabajo = new javax.swing.JTextField();
        btnGuardarEditarTrabajo = new javax.swing.JButton();
        lbEditarDescripcionTrabajo = new javax.swing.JLabel();
        inputEditarDescripcionTrabajo = new javax.swing.JTextField();
        panelGeneralEmpleados = new javax.swing.JPanel();
        panelSuperiorEmpleados = new javax.swing.JPanel();
        lbGestionDeEmpleados = new javax.swing.JLabel();
        panelTablaEmpleados = new javax.swing.JPanel();
        tblEmpleadosScroll = new javax.swing.JScrollPane();
        tblEmpleados = new javax.swing.JTable();
        panelFiltros = new javax.swing.JPanel();
        lbBuscarPor = new javax.swing.JLabel();
        rbFiltroApellido = new javax.swing.JRadioButton();
        busqueda = new javax.swing.JTextField();
        rbFiltroDNI = new javax.swing.JRadioButton();
        rbFiltroSueldo = new javax.swing.JRadioButton();
        btnBuscarFiltro = new javax.swing.JButton();
        btnFiltroLimpiar = new javax.swing.JButton();
        panelFichaYoperacionesEmpleado = new javax.swing.JPanel();
        panelFichaEmpleado = new javax.swing.JPanel();
        lbFichaEmpNombre = new javax.swing.JLabel();
        lbFichaEmpApellidos = new javax.swing.JLabel();
        lbFichaEmpDNI = new javax.swing.JLabel();
        lbFichaEmpSueldo = new javax.swing.JLabel();
        lbFichaEmpID = new javax.swing.JLabel();
        inputNombreEmpleado = new javax.swing.JTextField();
        inputApellidosEmpleado = new javax.swing.JTextField();
        inputDniEmpleado = new javax.swing.JTextField();
        inputSueldoEmpleado = new javax.swing.JTextField();
        inputIdEmpleado = new javax.swing.JTextField();
        lbFichaEmpleado = new javax.swing.JLabel();
        btnOperacionesActDes = new javax.swing.JButton();
        panelOperaciones = new javax.swing.JPanel();
        rbModificarEmpleado = new javax.swing.JRadioButton();
        rbAnadirEmpleado = new javax.swing.JRadioButton();
        rbEliminarEmpleado = new javax.swing.JRadioButton();
        rbAsignarEmpleado = new javax.swing.JRadioButton();
        btnGuardarCambios = new javax.swing.JButton();
        btnGuardarEmpleado = new javax.swing.JButton();
        btnEliminarEmpleado = new javax.swing.JButton();
        lbOperacionesNtrabajo = new javax.swing.JLabel();
        inputNTrabajoEmpleado = new javax.swing.JTextField();
        btnAsignarEmpleado = new javax.swing.JButton();
        panelDesconectado = new javax.swing.JPanel();
        panelEstado = new javax.swing.JPanel();
        lbDatosConexion = new javax.swing.JLabel();
        panelDesconexion = new javax.swing.JPanel();
        etiquetaDesconexion = new javax.swing.JLabel();
        barraDeMenu = new javax.swing.JMenuBar();
        menu = new javax.swing.JMenu();
        miConectar = new javax.swing.JMenuItem();
        miDesconectar = new javax.swing.JMenuItem();
        miEmpleados = new javax.swing.JMenuItem();
        miTrabajos = new javax.swing.JMenuItem();
        miSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(979, 781));

        panelCardsGeneral.setLayout(new java.awt.CardLayout());

        panelGeneralTrabajos.setName("panelGeneralTrabajos"); // NOI18N

        lbGestionDeTrabajos.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbGestionDeTrabajos.setText("GESTIÓN DE TRABAJOS");

        tblTrabajos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblTrabajosScroll.setViewportView(tblTrabajos);

        javax.swing.GroupLayout panelTablaTrabajosLayout = new javax.swing.GroupLayout(panelTablaTrabajos);
        panelTablaTrabajos.setLayout(panelTablaTrabajosLayout);
        panelTablaTrabajosLayout.setHorizontalGroup(
            panelTablaTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTablaTrabajosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tblTrabajosScroll)
                .addContainerGap())
        );
        panelTablaTrabajosLayout.setVerticalGroup(
            panelTablaTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTablaTrabajosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tblTrabajosScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnAgregarTrabajo.setText("Agregar Trabajo");
        btnAgregarTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTrabajoActionPerformed(evt);
            }
        });

        btnGestionarEmpleados.setText("GestionarEmpleados");
        btnGestionarEmpleados.setEnabled(false);
        btnGestionarEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionarEmpleadosActionPerformed(evt);
            }
        });

        btnEliminarTrabajo.setText("Eliminar Trabajo");
        btnEliminarTrabajo.setEnabled(false);
        btnEliminarTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTrabajoActionPerformed(evt);
            }
        });

        btnEditarTrabajo.setText("Editar Trabajo");
        btnEditarTrabajo.setEnabled(false);
        btnEditarTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarTrabajoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOpcionesTrabajosLayout = new javax.swing.GroupLayout(panelOpcionesTrabajos);
        panelOpcionesTrabajos.setLayout(panelOpcionesTrabajosLayout);
        panelOpcionesTrabajosLayout.setHorizontalGroup(
            panelOpcionesTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcionesTrabajosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOpcionesTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditarTrabajo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregarTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarTrabajo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGestionarEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelOpcionesTrabajosLayout.setVerticalGroup(
            panelOpcionesTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcionesTrabajosLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btnAgregarTrabajo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminarTrabajo)
                .addGap(18, 18, 18)
                .addComponent(btnEditarTrabajo)
                .addGap(18, 18, 18)
                .addComponent(btnGestionarEmpleados)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelSuperiorTrabajosLayout = new javax.swing.GroupLayout(panelSuperiorTrabajos);
        panelSuperiorTrabajos.setLayout(panelSuperiorTrabajosLayout);
        panelSuperiorTrabajosLayout.setHorizontalGroup(
            panelSuperiorTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSuperiorTrabajosLayout.createSequentialGroup()
                .addComponent(panelTablaTrabajos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOpcionesTrabajos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panelSuperiorTrabajosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbGestionDeTrabajos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSuperiorTrabajosLayout.setVerticalGroup(
            panelSuperiorTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorTrabajosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbGestionDeTrabajos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSuperiorTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelTablaTrabajos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOpcionesTrabajos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAccionesTrabajos.setLayout(new java.awt.CardLayout());

        panelGestionEmpleadosTrabajo.setName("panelGestionEmpleadosTrabajo"); // NOI18N

        lbGestionEmpleados.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbGestionEmpleados.setText("Gestion de empleados de trabajo:");

        lbNombreTrabajo.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        lbNombreTrabajo.setText(" ");

        tblTrabajosEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPaneTblEmpleadosDeTrabajo.setViewportView(tblTrabajosEmpleados);

        btnDesasignar.setText("Desasignar");
        btnDesasignar.setEnabled(false);
        btnDesasignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesasignarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCentralAccionTrabajosGestionaEmpleadosLayout = new javax.swing.GroupLayout(panelCentralAccionTrabajosGestionaEmpleados);
        panelCentralAccionTrabajosGestionaEmpleados.setLayout(panelCentralAccionTrabajosGestionaEmpleadosLayout);
        panelCentralAccionTrabajosGestionaEmpleadosLayout.setHorizontalGroup(
            panelCentralAccionTrabajosGestionaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCentralAccionTrabajosGestionaEmpleadosLayout.createSequentialGroup()
                .addGroup(panelCentralAccionTrabajosGestionaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCentralAccionTrabajosGestionaEmpleadosLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPaneTblEmpleadosDeTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCentralAccionTrabajosGestionaEmpleadosLayout.createSequentialGroup()
                        .addGroup(panelCentralAccionTrabajosGestionaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCentralAccionTrabajosGestionaEmpleadosLayout.createSequentialGroup()
                                .addGap(277, 277, 277)
                                .addComponent(btnDesasignar))
                            .addGroup(panelCentralAccionTrabajosGestionaEmpleadosLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbGestionEmpleados)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbNombreTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCentralAccionTrabajosGestionaEmpleadosLayout.setVerticalGroup(
            panelCentralAccionTrabajosGestionaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCentralAccionTrabajosGestionaEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCentralAccionTrabajosGestionaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbGestionEmpleados)
                    .addComponent(lbNombreTrabajo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPaneTblEmpleadosDeTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDesasignar)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelGestionEmpleadosTrabajoLayout = new javax.swing.GroupLayout(panelGestionEmpleadosTrabajo);
        panelGestionEmpleadosTrabajo.setLayout(panelGestionEmpleadosTrabajoLayout);
        panelGestionEmpleadosTrabajoLayout.setHorizontalGroup(
            panelGestionEmpleadosTrabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGestionEmpleadosTrabajoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelCentralAccionTrabajosGestionaEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelGestionEmpleadosTrabajoLayout.setVerticalGroup(
            panelGestionEmpleadosTrabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGestionEmpleadosTrabajoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(panelCentralAccionTrabajosGestionaEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAccionesTrabajos.add(panelGestionEmpleadosTrabajo, "panelGestionEmpleadosTrabajo");

        panelAgregarTrabajo.setName("panelAgregarTrabajo"); // NOI18N

        lbAgregarTrabajo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbAgregarTrabajo.setText("Agregar Trabajo");

        panelAccionAgregarTrabajoInterno.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        lbNombreTrabajoNuevo.setText("Nombre:");

        inputNombreTrabajo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputNombreTrabajoKeyReleased(evt);
            }
        });

        btnGuardarTrabajoNuevo.setText("Guardar Trabajo");
        btnGuardarTrabajoNuevo.setEnabled(false);
        btnGuardarTrabajoNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarTrabajoNuevoActionPerformed(evt);
            }
        });

        lbDescripcionTrabajo.setText("Descripcion:");

        javax.swing.GroupLayout panelAccionAgregarTrabajoInternoLayout = new javax.swing.GroupLayout(panelAccionAgregarTrabajoInterno);
        panelAccionAgregarTrabajoInterno.setLayout(panelAccionAgregarTrabajoInternoLayout);
        panelAccionAgregarTrabajoInternoLayout.setHorizontalGroup(
            panelAccionAgregarTrabajoInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAccionAgregarTrabajoInternoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnGuardarTrabajoNuevo)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelAccionAgregarTrabajoInternoLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(panelAccionAgregarTrabajoInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNombreTrabajoNuevo)
                    .addComponent(lbDescripcionTrabajo))
                .addGap(25, 25, 25)
                .addGroup(panelAccionAgregarTrabajoInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inputDescripcionTrabajo, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                    .addComponent(inputNombreTrabajo))
                .addGap(24, 24, 24))
        );
        panelAccionAgregarTrabajoInternoLayout.setVerticalGroup(
            panelAccionAgregarTrabajoInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAccionAgregarTrabajoInternoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelAccionAgregarTrabajoInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNombreTrabajoNuevo)
                    .addComponent(inputNombreTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAccionAgregarTrabajoInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbDescripcionTrabajo)
                    .addComponent(inputDescripcionTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(btnGuardarTrabajoNuevo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelAgregarTrabajoLayout = new javax.swing.GroupLayout(panelAgregarTrabajo);
        panelAgregarTrabajo.setLayout(panelAgregarTrabajoLayout);
        panelAgregarTrabajoLayout.setHorizontalGroup(
            panelAgregarTrabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAgregarTrabajoLayout.createSequentialGroup()
                .addGap(0, 424, Short.MAX_VALUE)
                .addComponent(lbAgregarTrabajo)
                .addGap(0, 456, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAgregarTrabajoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelAccionAgregarTrabajoInterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAgregarTrabajoLayout.setVerticalGroup(
            panelAgregarTrabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAgregarTrabajoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lbAgregarTrabajo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelAccionAgregarTrabajoInterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAccionesTrabajos.add(panelAgregarTrabajo, "panelAgregarTrabajo");

        panelEditarTrabajo.setName("panelEditarTrabajo"); // NOI18N

        lbEditarTrabajo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbEditarTrabajo.setText("Editar Trabajo");

        panelAccionEditarTrabajoInterno.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        lbEditarNTrabajoinfo.setText("NºTrabajo:");

        lbEditarNombreTrabajo.setText("Nombre:");

        inputEditarNombreTrabajo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputEditarNombreTrabajoKeyReleased(evt);
            }
        });

        btnGuardarEditarTrabajo.setText("Guardar Trabajo");
        btnGuardarEditarTrabajo.setEnabled(false);
        btnGuardarEditarTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarEditarTrabajoActionPerformed(evt);
            }
        });

        lbEditarDescripcionTrabajo.setText("Descripcion:");

        javax.swing.GroupLayout panelAccionEditarTrabajoInternoLayout = new javax.swing.GroupLayout(panelAccionEditarTrabajoInterno);
        panelAccionEditarTrabajoInterno.setLayout(panelAccionEditarTrabajoInternoLayout);
        panelAccionEditarTrabajoInternoLayout.setHorizontalGroup(
            panelAccionEditarTrabajoInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAccionEditarTrabajoInternoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnGuardarEditarTrabajo)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelAccionEditarTrabajoInternoLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(panelAccionEditarTrabajoInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbEditarNombreTrabajo)
                    .addComponent(lbEditarNTrabajoinfo)
                    .addComponent(lbEditarDescripcionTrabajo))
                .addGap(25, 25, 25)
                .addGroup(panelAccionEditarTrabajoInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAccionEditarTrabajoInternoLayout.createSequentialGroup()
                        .addComponent(lbEditarIdTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelAccionEditarTrabajoInternoLayout.createSequentialGroup()
                        .addGroup(panelAccionEditarTrabajoInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inputEditarDescripcionTrabajo, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(inputEditarNombreTrabajo))
                        .addGap(24, 24, 24))))
        );
        panelAccionEditarTrabajoInternoLayout.setVerticalGroup(
            panelAccionEditarTrabajoInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAccionEditarTrabajoInternoLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(panelAccionEditarTrabajoInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbEditarNTrabajoinfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbEditarIdTrabajo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAccionEditarTrabajoInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbEditarNombreTrabajo)
                    .addComponent(inputEditarNombreTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAccionEditarTrabajoInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbEditarDescripcionTrabajo)
                    .addComponent(inputEditarDescripcionTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(btnGuardarEditarTrabajo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelEditarTrabajoLayout = new javax.swing.GroupLayout(panelEditarTrabajo);
        panelEditarTrabajo.setLayout(panelEditarTrabajoLayout);
        panelEditarTrabajoLayout.setHorizontalGroup(
            panelEditarTrabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditarTrabajoLayout.createSequentialGroup()
                .addGap(0, 437, Short.MAX_VALUE)
                .addComponent(lbEditarTrabajo)
                .addGap(0, 468, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditarTrabajoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelAccionEditarTrabajoInterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelEditarTrabajoLayout.setVerticalGroup(
            panelEditarTrabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditarTrabajoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lbEditarTrabajo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelAccionEditarTrabajoInterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAccionesTrabajos.add(panelEditarTrabajo, "panelEditarTrabajo");

        javax.swing.GroupLayout panelGeneralTrabajosLayout = new javax.swing.GroupLayout(panelGeneralTrabajos);
        panelGeneralTrabajos.setLayout(panelGeneralTrabajosLayout);
        panelGeneralTrabajosLayout.setHorizontalGroup(
            panelGeneralTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSuperiorTrabajos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelAccionesTrabajos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelGeneralTrabajosLayout.setVerticalGroup(
            panelGeneralTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGeneralTrabajosLayout.createSequentialGroup()
                .addComponent(panelSuperiorTrabajos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelAccionesTrabajos, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelCardsGeneral.add(panelGeneralTrabajos, "panelGeneralTrabajos");

        panelGeneralEmpleados.setName("panelGeneralEmpleados"); // NOI18N

        lbGestionDeEmpleados.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbGestionDeEmpleados.setText("GESTIÓN DE EMPLEADOS");

        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblEmpleadosScroll.setViewportView(tblEmpleados);

        javax.swing.GroupLayout panelTablaEmpleadosLayout = new javax.swing.GroupLayout(panelTablaEmpleados);
        panelTablaEmpleados.setLayout(panelTablaEmpleadosLayout);
        panelTablaEmpleadosLayout.setHorizontalGroup(
            panelTablaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tblEmpleadosScroll)
        );
        panelTablaEmpleadosLayout.setVerticalGroup(
            panelTablaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTablaEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tblEmpleadosScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        lbBuscarPor.setText("Buscar por:");

        rgFiltro.add(rbFiltroApellido);
        rbFiltroApellido.setSelected(true);
        rbFiltroApellido.setText("Apellido");
        rbFiltroApellido.setActionCommand("Apellido");

        busqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaKeyReleased(evt);
            }
        });

        rgFiltro.add(rbFiltroDNI);
        rbFiltroDNI.setText("DNI");
        rbFiltroDNI.setActionCommand("DNI");

        rgFiltro.add(rbFiltroSueldo);
        rbFiltroSueldo.setText("Sueldo");
        rbFiltroSueldo.setActionCommand("Sueldo");

        btnBuscarFiltro.setText("Buscar");
        btnBuscarFiltro.setEnabled(false);
        btnBuscarFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFiltroActionPerformed(evt);
            }
        });

        btnFiltroLimpiar.setText("X");
        btnFiltroLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltroLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFiltrosLayout = new javax.swing.GroupLayout(panelFiltros);
        panelFiltros.setLayout(panelFiltrosLayout);
        panelFiltrosLayout.setHorizontalGroup(
            panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFiltrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFiltrosLayout.createSequentialGroup()
                        .addComponent(rbFiltroSueldo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscarFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelFiltrosLayout.createSequentialGroup()
                        .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFiltrosLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lbBuscarPor))
                            .addComponent(rbFiltroDNI))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelFiltrosLayout.createSequentialGroup()
                        .addComponent(rbFiltroApellido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFiltroLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelFiltrosLayout.setVerticalGroup(
            panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFiltrosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lbBuscarPor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbFiltroApellido)
                    .addComponent(busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltroLimpiar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelFiltrosLayout.createSequentialGroup()
                        .addComponent(rbFiltroDNI)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbFiltroSueldo))
                    .addComponent(btnBuscarFiltro))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelSuperiorEmpleadosLayout = new javax.swing.GroupLayout(panelSuperiorEmpleados);
        panelSuperiorEmpleados.setLayout(panelSuperiorEmpleadosLayout);
        panelSuperiorEmpleadosLayout.setHorizontalGroup(
            panelSuperiorEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorEmpleadosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbGestionDeEmpleados)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelSuperiorEmpleadosLayout.createSequentialGroup()
                .addComponent(panelTablaEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelSuperiorEmpleadosLayout.setVerticalGroup(
            panelSuperiorEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbGestionDeEmpleados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSuperiorEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTablaEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        panelFichaEmpleado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        panelFichaEmpleado.setForeground(new java.awt.Color(204, 204, 204));

        lbFichaEmpNombre.setText("Nombre:");
        lbFichaEmpNombre.setEnabled(false);

        lbFichaEmpApellidos.setText("Apellidos:");
        lbFichaEmpApellidos.setEnabled(false);

        lbFichaEmpDNI.setText("DNI:");
        lbFichaEmpDNI.setEnabled(false);

        lbFichaEmpSueldo.setText("Sueldo:");
        lbFichaEmpSueldo.setEnabled(false);

        lbFichaEmpID.setText("ID:");
        lbFichaEmpID.setEnabled(false);

        inputNombreEmpleado.setEditable(false);
        inputNombreEmpleado.setEnabled(false);

        inputApellidosEmpleado.setEditable(false);
        inputApellidosEmpleado.setEnabled(false);

        inputDniEmpleado.setEditable(false);
        inputDniEmpleado.setEnabled(false);

        inputSueldoEmpleado.setEditable(false);
        inputSueldoEmpleado.setEnabled(false);

        inputIdEmpleado.setEditable(false);
        inputIdEmpleado.setEnabled(false);

        javax.swing.GroupLayout panelFichaEmpleadoLayout = new javax.swing.GroupLayout(panelFichaEmpleado);
        panelFichaEmpleado.setLayout(panelFichaEmpleadoLayout);
        panelFichaEmpleadoLayout.setHorizontalGroup(
            panelFichaEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFichaEmpleadoLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(panelFichaEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFichaEmpleadoLayout.createSequentialGroup()
                        .addComponent(lbFichaEmpNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addComponent(inputNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFichaEmpleadoLayout.createSequentialGroup()
                        .addGroup(panelFichaEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbFichaEmpApellidos)
                            .addComponent(lbFichaEmpDNI)
                            .addComponent(lbFichaEmpSueldo)
                            .addComponent(lbFichaEmpID))
                        .addGap(18, 18, 18)
                        .addGroup(panelFichaEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inputApellidosEmpleado)
                            .addComponent(inputDniEmpleado)
                            .addComponent(inputSueldoEmpleado)
                            .addGroup(panelFichaEmpleadoLayout.createSequentialGroup()
                                .addComponent(inputIdEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(26, 26, 26))
        );
        panelFichaEmpleadoLayout.setVerticalGroup(
            panelFichaEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFichaEmpleadoLayout.createSequentialGroup()
                .addGroup(panelFichaEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelFichaEmpleadoLayout.createSequentialGroup()
                        .addGroup(panelFichaEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFichaEmpleadoLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lbFichaEmpNombre)
                                .addGap(23, 23, 23))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFichaEmpleadoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(inputNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(panelFichaEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputApellidosEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbFichaEmpApellidos))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFichaEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputDniEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbFichaEmpDNI))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inputSueldoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbFichaEmpSueldo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFichaEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inputIdEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbFichaEmpID))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbFichaEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbFichaEmpleado.setText("Ficha de empleado");
        lbFichaEmpleado.setEnabled(false);

        btnOperacionesActDes.setText("Operaciones: Activar");
        btnOperacionesActDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOperacionesActDesActionPerformed(evt);
            }
        });

        panelOperaciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        rgOperacionesEmpleado.add(rbModificarEmpleado);
        rbModificarEmpleado.setSelected(true);
        rbModificarEmpleado.setText("Modificar");
        rbModificarEmpleado.setActionCommand("modificar");
        rbModificarEmpleado.setEnabled(false);
        rbModificarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbModificarEmpleadoActionPerformed(evt);
            }
        });

        rgOperacionesEmpleado.add(rbAnadirEmpleado);
        rbAnadirEmpleado.setText("Añadir");
        rbAnadirEmpleado.setActionCommand("anadir");
        rbAnadirEmpleado.setEnabled(false);
        rbAnadirEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAnadirEmpleadoActionPerformed(evt);
            }
        });

        rgOperacionesEmpleado.add(rbEliminarEmpleado);
        rbEliminarEmpleado.setText("Eliminar");
        rbEliminarEmpleado.setActionCommand("eliminar");
        rbEliminarEmpleado.setEnabled(false);
        rbEliminarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEliminarEmpleadoActionPerformed(evt);
            }
        });

        rgOperacionesEmpleado.add(rbAsignarEmpleado);
        rbAsignarEmpleado.setText("Asignar");
        rbAsignarEmpleado.setActionCommand("asignar");
        rbAsignarEmpleado.setEnabled(false);
        rbAsignarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAsignarEmpleadoActionPerformed(evt);
            }
        });

        btnGuardarCambios.setText("Guardar Cambios");
        btnGuardarCambios.setEnabled(false);
        btnGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCambiosActionPerformed(evt);
            }
        });

        btnGuardarEmpleado.setText("Guardar Empleado");
        btnGuardarEmpleado.setEnabled(false);
        btnGuardarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarEmpleadoActionPerformed(evt);
            }
        });

        btnEliminarEmpleado.setText("Eliminar Empleado");
        btnEliminarEmpleado.setEnabled(false);
        btnEliminarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEmpleadoActionPerformed(evt);
            }
        });

        lbOperacionesNtrabajo.setText("Nº TRABAJO");
        lbOperacionesNtrabajo.setEnabled(false);

        inputNTrabajoEmpleado.setEnabled(false);

        btnAsignarEmpleado.setText("Asignar");
        btnAsignarEmpleado.setEnabled(false);
        btnAsignarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOperacionesLayout = new javax.swing.GroupLayout(panelOperaciones);
        panelOperaciones.setLayout(panelOperacionesLayout);
        panelOperacionesLayout.setHorizontalGroup(
            panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOperacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbModificarEmpleado)
                    .addComponent(rbAnadirEmpleado)
                    .addComponent(rbEliminarEmpleado)
                    .addComponent(rbAsignarEmpleado))
                .addGap(42, 42, 42)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAsignarEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                        .addComponent(lbOperacionesNtrabajo)
                        .addGap(18, 18, 18)
                        .addComponent(inputNTrabajoEmpleado))
                    .addComponent(btnEliminarEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addComponent(btnGuardarEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addComponent(btnGuardarCambios, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        panelOperacionesLayout.setVerticalGroup(
            panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOperacionesLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbModificarEmpleado)
                    .addComponent(btnGuardarCambios))
                .addGap(18, 18, 18)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbAnadirEmpleado)
                    .addComponent(btnGuardarEmpleado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbEliminarEmpleado)
                    .addComponent(btnEliminarEmpleado))
                .addGap(33, 33, 33)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbAsignarEmpleado)
                    .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbOperacionesNtrabajo)
                        .addComponent(inputNTrabajoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAsignarEmpleado)
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout panelFichaYoperacionesEmpleadoLayout = new javax.swing.GroupLayout(panelFichaYoperacionesEmpleado);
        panelFichaYoperacionesEmpleado.setLayout(panelFichaYoperacionesEmpleadoLayout);
        panelFichaYoperacionesEmpleadoLayout.setHorizontalGroup(
            panelFichaYoperacionesEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFichaYoperacionesEmpleadoLayout.createSequentialGroup()
                .addGroup(panelFichaYoperacionesEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFichaYoperacionesEmpleadoLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(lbFichaEmpleado))
                    .addGroup(panelFichaYoperacionesEmpleadoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelFichaEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(122, 122, 122)
                .addGroup(panelFichaYoperacionesEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFichaYoperacionesEmpleadoLayout.createSequentialGroup()
                        .addComponent(btnOperacionesActDes)
                        .addGap(147, 147, 147))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFichaYoperacionesEmpleadoLayout.createSequentialGroup()
                        .addComponent(panelOperaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );
        panelFichaYoperacionesEmpleadoLayout.setVerticalGroup(
            panelFichaYoperacionesEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFichaYoperacionesEmpleadoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelFichaYoperacionesEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbFichaEmpleado)
                    .addComponent(btnOperacionesActDes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFichaYoperacionesEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFichaEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOperaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout panelGeneralEmpleadosLayout = new javax.swing.GroupLayout(panelGeneralEmpleados);
        panelGeneralEmpleados.setLayout(panelGeneralEmpleadosLayout);
        panelGeneralEmpleadosLayout.setHorizontalGroup(
            panelGeneralEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGeneralEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGeneralEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSuperiorEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFichaYoperacionesEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelGeneralEmpleadosLayout.setVerticalGroup(
            panelGeneralEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGeneralEmpleadosLayout.createSequentialGroup()
                .addComponent(panelSuperiorEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelFichaYoperacionesEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelCardsGeneral.add(panelGeneralEmpleados, "panelGeneralEmpleados");

        panelDesconectado.setName("panelDesconectado"); // NOI18N

        javax.swing.GroupLayout panelDesconectadoLayout = new javax.swing.GroupLayout(panelDesconectado);
        panelDesconectado.setLayout(panelDesconectadoLayout);
        panelDesconectadoLayout.setHorizontalGroup(
            panelDesconectadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1052, Short.MAX_VALUE)
        );
        panelDesconectadoLayout.setVerticalGroup(
            panelDesconectadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 685, Short.MAX_VALUE)
        );

        panelCardsGeneral.add(panelDesconectado, "panelDesconectado");

        getContentPane().add(panelCardsGeneral, java.awt.BorderLayout.CENTER);

        panelEstado.setBackground(new java.awt.Color(255, 255, 255));
        panelEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        lbDatosConexion.setText("Desconectado");
        lbDatosConexion.setToolTipText("");
        panelEstado.add(lbDatosConexion);

        getContentPane().add(panelEstado, java.awt.BorderLayout.SOUTH);

        panelDesconexion.setBackground(new java.awt.Color(255, 218, 83));
        panelDesconexion.setForeground(new java.awt.Color(222, 222, 115));
        panelDesconexion.setLayout(new java.awt.BorderLayout());

        etiquetaDesconexion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelDesconexion.add(etiquetaDesconexion, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelDesconexion, java.awt.BorderLayout.NORTH);

        menu.setText("Menu");

        miConectar.setText("Conectar");
        miConectar.setActionCommand("conectar");
        miConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miConectarActionPerformed(evt);
            }
        });
        menu.add(miConectar);

        miDesconectar.setText("Desconectar");
        miDesconectar.setActionCommand("desconectar");
        miDesconectar.setEnabled(false);
        miDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDesconectarActionPerformed(evt);
            }
        });
        menu.add(miDesconectar);

        miEmpleados.setText("Empleados");
        miEmpleados.setEnabled(false);
        miEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEmpleadosActionPerformed(evt);
            }
        });
        menu.add(miEmpleados);

        miTrabajos.setText("Trabajos");
        miTrabajos.setEnabled(false);
        miTrabajos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miTrabajosActionPerformed(evt);
            }
        });
        menu.add(miTrabajos);

        miSalir.setText("Salir");
        miSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSalirActionPerformed(evt);
            }
        });
        menu.add(miSalir);

        barraDeMenu.add(menu);

        setJMenuBar(barraDeMenu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void miDesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDesconectarActionPerformed
        desconectar();
    }//GEN-LAST:event_miDesconectarActionPerformed

    public void desconectar(){
                    if (controlador.desconectar()){
            miConectar.setEnabled(true);
            miDesconectar.setEnabled(false);
            miTrabajos.setEnabled(false);
            miEmpleados.setEnabled(false);
            panelEstado.setBackground(Color.WHITE);
             lbDatosConexion.setText("Desconectado");
                         CardLayout cl = (CardLayout) this.panelCardsGeneral.getLayout();
            cl.show(panelCardsGeneral, "panelGeneralTrabajos");
            autodesconexion.setConectado(false);
            cl.show(panelCardsGeneral, "panelDesconectado");
        }}
    private void miConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miConectarActionPerformed
        if (controlador.conectar()){
            miConectar.setEnabled(false);
            miDesconectar.setEnabled(true);
            miTrabajos.setEnabled(true);
            miEmpleados.setEnabled(true);
            panelEstado.setBackground(new Color(153,202,153));
            lbDatosConexion.setText("Conectado: "+controlador.getDatosConexion());
            //inicializar tablas
            inicializarTablaTrabajos();
            CardLayout cl = (CardLayout) this.panelCardsGeneral.getLayout();
            cl.show(panelCardsGeneral, "panelGeneralTrabajos");
            panelEstado.setToolTipText("Conectado");
            autodesconexion.setConectado(true);
        }else{
            lbDatosConexion.setText("Desconectado");
            panelEstado.setBackground(Color.RED);  
            CardLayout cl = (CardLayout) this.panelCardsGeneral.getLayout();
            cl.show(panelCardsGeneral, "panelDesconectado");
            panelEstado.setToolTipText(controlador.getUltimoError());
        }
        
    }//GEN-LAST:event_miConectarActionPerformed

    private void btnEliminarTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTrabajoActionPerformed
       int filaSeleccionada = this.tblTrabajos.getSelectedRow();
        if (filaSeleccionada == -1) {
            return;//no hacer nada si no hay fila seleccionada
        }        //Hace conversion segun el modelo de tabla
        int filaCorrecta = this.tblTrabajos.convertRowIndexToModel(filaSeleccionada);
        int idTrabajo = (int) this.tblTrabajos.getValueAt(filaCorrecta, 0);
        if (controlador.delete(1,""+idTrabajo)>0)
            inicializarTablaTrabajos();
    }//GEN-LAST:event_btnEliminarTrabajoActionPerformed

    private void inputEditarNombreTrabajoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputEditarNombreTrabajoKeyReleased
        String txt = this.inputEditarNombreTrabajo.getText();
        if (txt == null || !txt.equals( ""))
            this.btnGuardarEditarTrabajo.setEnabled(true);
        else
            this.btnGuardarEditarTrabajo.setEnabled(false);
    }//GEN-LAST:event_inputEditarNombreTrabajoKeyReleased

    private void btnGuardarEditarTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarEditarTrabajoActionPerformed
        String id = ""+trabajoSiendoEditado.getID();
        String nombre = inputEditarNombreTrabajo.getText();
        String descripcion = inputEditarDescripcionTrabajo.getText();
        if (nombre.length() > 0) {
            String[] valores={id,nombre,descripcion};
            if(controlador.update(1,valores)>0);
                msgInfo("Trabajo actualizado");
            this.inicializarTablaTrabajos();
        }
    }//GEN-LAST:event_btnGuardarEditarTrabajoActionPerformed

    private void btnEditarTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarTrabajoActionPerformed
               int filaSeleccionada = this.tblTrabajos.getSelectedRow();
        if (filaSeleccionada == -1) {
            return;//no hacer nada si no hay fila seleccionada
        }        //Hace conversion segun el modelo de tabla
        int filaCorrecta = this.tblTrabajos.convertRowIndexToModel(filaSeleccionada);
        int idTrabajo = (int) this.tblTrabajos.getValueAt(filaCorrecta, 0);
        editarTrabajo(idTrabajo);
        btnGuardarEditarTrabajo.setEnabled(true);
        
    }//GEN-LAST:event_btnEditarTrabajoActionPerformed


    // <editor-fold defaultstate="collapsed" desc="ATRIBUTOS AUTOGENERADOS">  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraDeMenu;
    private javax.swing.JButton btnAgregarTrabajo;
    private javax.swing.JButton btnAsignarEmpleado;
    private javax.swing.JButton btnBuscarFiltro;
    private javax.swing.JButton btnDesasignar;
    private javax.swing.JButton btnEditarTrabajo;
    private javax.swing.JButton btnEliminarEmpleado;
    private javax.swing.JButton btnEliminarTrabajo;
    private javax.swing.JButton btnFiltroLimpiar;
    private javax.swing.JButton btnGestionarEmpleados;
    private javax.swing.JButton btnGuardarCambios;
    private javax.swing.JButton btnGuardarEditarTrabajo;
    private javax.swing.JButton btnGuardarEmpleado;
    private javax.swing.JButton btnGuardarTrabajoNuevo;
    private javax.swing.JButton btnOperacionesActDes;
    private javax.swing.JTextField busqueda;
    private javax.swing.JLabel etiquetaDesconexion;
    private javax.swing.JTextField inputApellidosEmpleado;
    private javax.swing.JTextField inputDescripcionTrabajo;
    private javax.swing.JTextField inputDniEmpleado;
    private javax.swing.JTextField inputEditarDescripcionTrabajo;
    private javax.swing.JTextField inputEditarNombreTrabajo;
    private javax.swing.JTextField inputIdEmpleado;
    private javax.swing.JTextField inputNTrabajoEmpleado;
    private javax.swing.JTextField inputNombreEmpleado;
    private javax.swing.JTextField inputNombreTrabajo;
    private javax.swing.JTextField inputSueldoEmpleado;
    private javax.swing.JScrollPane jScrollPaneTblEmpleadosDeTrabajo;
    private javax.swing.JLabel lbAgregarTrabajo;
    private javax.swing.JLabel lbBuscarPor;
    private javax.swing.JLabel lbDatosConexion;
    private javax.swing.JLabel lbDescripcionTrabajo;
    private javax.swing.JLabel lbEditarDescripcionTrabajo;
    private javax.swing.JLabel lbEditarIdTrabajo;
    private javax.swing.JLabel lbEditarNTrabajoinfo;
    private javax.swing.JLabel lbEditarNombreTrabajo;
    private javax.swing.JLabel lbEditarTrabajo;
    private javax.swing.JLabel lbFichaEmpApellidos;
    private javax.swing.JLabel lbFichaEmpDNI;
    private javax.swing.JLabel lbFichaEmpID;
    private javax.swing.JLabel lbFichaEmpNombre;
    private javax.swing.JLabel lbFichaEmpSueldo;
    private javax.swing.JLabel lbFichaEmpleado;
    private javax.swing.JLabel lbGestionDeEmpleados;
    private javax.swing.JLabel lbGestionDeTrabajos;
    private javax.swing.JLabel lbGestionEmpleados;
    private javax.swing.JLabel lbNombreTrabajo;
    private javax.swing.JLabel lbNombreTrabajoNuevo;
    private javax.swing.JLabel lbOperacionesNtrabajo;
    private javax.swing.JMenu menu;
    private javax.swing.JMenuItem miConectar;
    private javax.swing.JMenuItem miDesconectar;
    private javax.swing.JMenuItem miEmpleados;
    private javax.swing.JMenuItem miSalir;
    private javax.swing.JMenuItem miTrabajos;
    private javax.swing.JPanel panelAccionAgregarTrabajoInterno;
    private javax.swing.JPanel panelAccionEditarTrabajoInterno;
    private javax.swing.JPanel panelAccionesTrabajos;
    private javax.swing.JPanel panelAgregarTrabajo;
    private javax.swing.JPanel panelCardsGeneral;
    private javax.swing.JPanel panelCentralAccionTrabajosGestionaEmpleados;
    private javax.swing.JPanel panelDesconectado;
    private javax.swing.JPanel panelDesconexion;
    private javax.swing.JPanel panelEditarTrabajo;
    private javax.swing.JPanel panelEstado;
    private javax.swing.JPanel panelFichaEmpleado;
    private javax.swing.JPanel panelFichaYoperacionesEmpleado;
    private javax.swing.JPanel panelFiltros;
    private javax.swing.JPanel panelGeneralEmpleados;
    private javax.swing.JPanel panelGeneralTrabajos;
    private javax.swing.JPanel panelGestionEmpleadosTrabajo;
    private javax.swing.JPanel panelOpcionesTrabajos;
    private javax.swing.JPanel panelOperaciones;
    private javax.swing.JPanel panelSuperiorEmpleados;
    private javax.swing.JPanel panelSuperiorTrabajos;
    private javax.swing.JPanel panelTablaEmpleados;
    private javax.swing.JPanel panelTablaTrabajos;
    private javax.swing.JRadioButton rbAnadirEmpleado;
    private javax.swing.JRadioButton rbAsignarEmpleado;
    private javax.swing.JRadioButton rbEliminarEmpleado;
    private javax.swing.JRadioButton rbFiltroApellido;
    private javax.swing.JRadioButton rbFiltroDNI;
    private javax.swing.JRadioButton rbFiltroSueldo;
    private javax.swing.JRadioButton rbModificarEmpleado;
    private javax.swing.ButtonGroup rgFiltro;
    private javax.swing.ButtonGroup rgOperacionesEmpleado;
    private javax.swing.JTable tblEmpleados;
    private javax.swing.JScrollPane tblEmpleadosScroll;
    private javax.swing.JTable tblTrabajos;
    private javax.swing.JTable tblTrabajosEmpleados;
    private javax.swing.JScrollPane tblTrabajosScroll;
    // End of variables declaration//GEN-END:variables

    void mostrarAvisoDesconexion(String texto) {
        panelDesconexion.setVisible(true);
        etiquetaDesconexion.setText(texto);
    }

// </editor-fold>     

    void ocultarAvisoDesconexion() {
        panelDesconexion.setVisible(false);
    }
}//fin de clase
