/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
Lista de paquetes:
 */
package ud2_01.vista.dialogos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ud2_01.controlador.Controlador;
import ud2_01.controlador.dto.Empleado;
import ud2_01.vista.Texto;
import ud2_01.vista.ventana.Ventana;

/**
 * Formulario para la creacion y edicion de empleados.
 *
 * Se puede establecer su modo EDITAR y CREAR
 *
 * Cuando esta en modo crear se oculta el campo ID ya que se crea
 * automaticamente al insertar en la base de datos.
 *
 * Tiene dos botones GUARDAR y CANCELAR Al pulsarse cancelar se cierra el
 * dialogo. Al pulsarse guardar: - Si esta en modo editar hace un Update en el
 * controlador - Si esta en modo crear hace un Insertar en el controlador
 *
 * @author Jose Javier BO
 *
 * @see Controlador
 * @see ventana
 */
public class DialogoFormularioEmpleado extends javax.swing.JDialog implements ActionListener {

    //ID del modo editar
    public static int EDITAR = 0;
    //ID del modo crear
    public static int CREAR = 1;

    //Modo actual
    private final int modo;

    //Empleado siendo creado o modificado
    Empleado empleado;

    //referncia a la ventana principal
    Ventana ventana;

    /**
     * Creates new form FormularioEmpleado
     */
    public DialogoFormularioEmpleado(java.awt.Frame parent, int modo, Empleado empleado) {
        super(parent, true);
        initComponents();
        this.modo = modo;
        this.empleado = empleado;
        //si esta en modo editar y empleado es NULL se lanza una excepcion
        if (modo == EDITAR && empleado == null) {
            throw new IllegalArgumentException("Empleado nulo no permitodo para la edicion");
        }

        //agregar listener a botones
        btnCancelar.addActionListener(this);
        btnGuardar.addActionListener(this);

        //crear referencia a la ventana
        ventana = (Ventana) getParent();
        //configura el modo del formulario
        configurarModo();
    }

    /**
     * Configura el modo en el que trabaja el formulario. Si esta en modo CREAR
     * oculta el campo de ID y genera un empleado inicial vacio con id -1
     *
     * Si esta en modo EDITAR muestra todos los campos y los rellena con los
     * datos del empleado pasado en el constructor
     */
    private void configurarModo() {

        //CREAR
        if (modo == CREAR) {
            empleado = new Empleado(-1);
            lbID.setVisible(false);
            inputID.setVisible(false);

            //EDITAR    
        } else {
            //rellenar formulario
            inputID.setText("" + empleado.getID());
            inputNombre.setText(empleado.getNombre());
            inputApellidos.setText(empleado.getApellidos());
            inputDNI.setText(empleado.getDNI());
            inputSueldo.setValue(empleado.getSueldo());
            lbID.setVisible(true);
            inputID.setVisible(true);
        }
    }

    /**
     * Escucha de los botones. Si no es guardar es el cancelar
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String ac = e.getActionCommand();
        switch (ac) {
            case "guardar" ->
                guardar();
            default ->
                this.dispose();
        }
    }

    /**
     * Ejecuta el guarado de los datos que hay en el formulario.
     *
     * Valida los valores, genera un objeto empleado actualizado y luego actua
     * dependiendo del modo:
     *
     * -Modo EDITAR: Ejecuta el update en el controlador -Modo CREAR: Ejecuta
     * Insertar en el controlador
     */
    private void guardar() {

        //recoger valores del formulario
        String nombre = inputNombre.getText();
        String apellidos = inputApellidos.getText();
        String DNI = inputDNI.getText();
        double sueldo = (double) inputSueldo.getValue();

        //validacion de nombre y apellidos
        if (nombre.trim().length() < 1 || apellidos.trim().length() < 1) {
            ventana.msgError(Texto.ERROR_NOMBRE_APELLIDOS_VACIO);
            return;
        }

        //validacion de DNI
        if (DNI.length() != 9) {
            ventana.msgError(Texto.ERROR_FORMATO_DNI);
            return;
        }

        //actualizar valores de objeto empleado
        empleado.setNombre(nombre);
        empleado.setApellidos(apellidos);
        empleado.setDNI(DNI);
        empleado.setSueldo(sueldo);

        //MODO CREAR
        if (modo == CREAR) {
            //Si no ha podido insertar avisa al usuario
            int resultado = Controlador.insertar(empleado);
            if (resultado == -1) {
                ventana.msgError(Texto.ERROR_INSERTANDO_EMPLEADO);
            } else if (resultado == -2) {
                ventana.msgError(Texto.ERROR_INSERTANDO_EMPLEADO_DNI);

                //si ha podido insertar cierra el dialogo
            } else {
                dispose();
            }

            //MODO EDITAR
        } else if (Controlador.update(empleado) == -1) {
            //si no ha podido hacer el update avisa al usuario
            ventana.msgError(Texto.ERROR_ACTUALIZANDO_EMPLEADO);
        } else {

            //si ha podido hacer el update cierra el dialogo
            dispose();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTitulo = new java.awt.Label();
        inputID = new javax.swing.JTextField();
        inputNombre = new javax.swing.JTextField();
        inputApellidos = new javax.swing.JTextField();
        inputDNI = new javax.swing.JTextField();
        inputSueldo = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lbID = new javax.swing.JLabel();
        lbNombre = new javax.swing.JLabel();
        lbApellidos = new javax.swing.JLabel();
        lbDNI = new javax.swing.JLabel();
        lbSueldo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        lbTitulo.setAlignment(java.awt.Label.CENTER);
        lbTitulo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbTitulo.setText("CREAR EMPLEADO");

        inputID.setEditable(false);

        inputSueldo.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));

        btnGuardar.setText("GUARDAR");
        btnGuardar.setActionCommand("guardar");

        btnCancelar.setText("CANCELAR");
        btnCancelar.setActionCommand("cancelar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbID.setText("ID");

        lbNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre.setText("NOMBRE");

        lbApellidos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbApellidos.setText("APELLIDOS");

        lbDNI.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbDNI.setText("DNI");

        lbSueldo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbSueldo.setText("SUELDO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbApellidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbDNI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbSueldo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inputID)
                            .addComponent(inputNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                            .addComponent(inputApellidos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                            .addComponent(inputDNI, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                            .addComponent(inputSueldo))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbID)
                    .addComponent(inputID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbApellidos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDNI))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbSueldo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JTextField inputApellidos;
    private javax.swing.JTextField inputDNI;
    private javax.swing.JTextField inputID;
    private javax.swing.JTextField inputNombre;
    private javax.swing.JSpinner inputSueldo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbApellidos;
    private javax.swing.JLabel lbDNI;
    private javax.swing.JLabel lbID;
    private javax.swing.JLabel lbNombre;
    private javax.swing.JLabel lbSueldo;
    private java.awt.Label lbTitulo;
    // End of variables declaration//GEN-END:variables

}
