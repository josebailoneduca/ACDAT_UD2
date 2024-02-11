/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package ud2_03.gui.ventanas;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.SwingUtilities;

/**
 * Se encarga de llevar un seguimiento del tiempo y avisar de desconexionen caso de llegarse 
 * al limite definido. Si se llega al tiempo de desconexion se ordena a vista que inicie la desconexion.
 * 
 * Esta constantemente escuchando el movimiento del raton sobre la ventana.
 * Si el raton no pasa sobre la ventana cada segundo se incrementa el contador "tiempoTranscurrido"
 * Cuando el raton se mueve sobre la ventana se pone a 0.
 * 
 * Si en algun momento tiempoTranscurrido alcanza un valor superior al tiempoDesconexion - plazoAviso
 * se muestra un mensaje
 * 
 * Si en algun momento tiempoTranscurrido alcanza un valor igual o superior a "tiempoDesconexion"
 * se desconexta de la base de datos.
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Autodesconexion extends Thread implements MouseMotionListener{
    
    //contador de tiempo transcurrido
    private final AtomicInteger tiempoTranscurrido=new AtomicInteger(0);
    //tiempo en el que se desconectara
    private final int tiempoDesconexion=20000;
    //tiempo antes de tiempoDesconexion que si se alcanza se avisara de la futura desconexion
    private final int plazoAviso =10000;
    
    //referncia a la vista
    private final Vista vista;
    
    //referencia a si se esta conectado o no
    private final AtomicBoolean conectado = new AtomicBoolean(false);   
    
    
    /**
     * Constructor
     * @param vista  Referencia a la vista
     */
    public Autodesconexion(Vista vista) {
        this.vista=vista;
        vista.addMouseMotionListener(this);
    }

    
    /**
     * Carrera de control del paso de tiempo
     */
    @Override
    public void run() {
        while (true){
            try {
                //Tiempo restante para la desconexion
                int tiempoRestanteParaDesconexion= tiempoDesconexion-tiempoTranscurrido.get();
                //si tiempo restante es menor que plazo de aviso y se esta conectado se mostrará el mensaje
                if (tiempoRestanteParaDesconexion<plazoAviso && conectado.get()){
                       SwingUtilities.invokeLater(() -> {vista.mostrarAvisoDesconexion("Se desconectará en "+ (tiempoRestanteParaDesconexion/1000)+" segundos por ausencia de actividad");});
                }else{
                   SwingUtilities.invokeLater(() -> {vista.ocultarAvisoDesconexion();});
                }
                
                //ver si el tiempo transcurrido supero al tiempo de desconexion
                //para ejecutar la desconexion
                if (tiempoTranscurrido.get()>=tiempoDesconexion)
                    vista.desconectar();
                //esperar 1 segundo
                Thread.sleep(1000);
                tiempoTranscurrido.addAndGet(1000);
            } catch (InterruptedException ex) {
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //resetear el tiempo transcurrido al moverse el raton
        this.tiempoTranscurrido.set(0);


    }

    public void setConectado(boolean conectado) {
        //define el estado de conectado o no
        this.conectado.set(conectado);
    }

    
   

}//end Autodesconexion
