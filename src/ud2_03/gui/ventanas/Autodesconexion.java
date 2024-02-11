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
 *
 * @author Jose Javier Bailon Ortiz
 */
public class Autodesconexion extends Thread implements MouseMotionListener{
    private final int tiempoDesconexion=20000;
    private final int plazoAviso =10000;
    private final AtomicInteger tiempoTranscurrido=new AtomicInteger(0);
    private final Vista vista;
    private final AtomicBoolean conectado = new AtomicBoolean(false);   
    
    public Autodesconexion(Vista vista) {
        this.vista=vista;
        vista.addMouseMotionListener(this);
    }

    @Override
    public void run() {
        while (true){
            try {
                System.out.println(tiempoTranscurrido.addAndGet(1000));
                //ver si hay que avisar
                int tiempoRestanteParaDesconexion= tiempoDesconexion-tiempoTranscurrido.get();
                if (tiempoRestanteParaDesconexion<plazoAviso && conectado.get()){
                       SwingUtilities.invokeLater(() -> {vista.mostrarAvisoDesconexion("Se desconectará en "+ (tiempoRestanteParaDesconexion/1000)+" segundos por ausencia de actividad");});
                }else{
                   SwingUtilities.invokeLater(() -> {vista.ocultarAvisoDesconexion();});
                }
                //ver si hay que desconectar
                if (tiempoTranscurrido.get()>=tiempoDesconexion)
                    vista.desconectar();
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.tiempoTranscurrido.set(0);


    }

    public void setConectado(boolean conectado) {
        this.conectado.set(conectado);
    }

    
   

}//end Autodesconexion
