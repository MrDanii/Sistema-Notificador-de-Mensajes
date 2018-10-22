package display;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class Menu extends JFrame{
    
    //Componentes de la interfaz gráfica
    JLabel lTitulo, lFecha, lLuminosidad, lTemperatura, lHumedad, lMensaje;
    JButton bAnterior, bSiguiente, bNuevo, bEliminar, bAgregar, bCancelar;
    JTextArea tMensaje;
    JScrollPane sMensaje;
    
    
    private PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    /*
    private SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            throw new UnsupportedOperationException("Not supported yet."); //To 
            change body of generated methods, choose Tools | Templates.
        }
    };
    */       
    public Menu(){
        
       super("Mensajes");
       setLayout(null);
       
       //Llamamos al método que inicializa los compontentes
       inicializarComponentes(); 
      
        try {
            ino.arduinoRXTX("/dev/ttyUSB0", ABORT, new HandlerListenerPort());
        } catch (ArduinoException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private class HandlerListenerPort implements SerialPortEventListener{
        @Override
        public void serialEvent(SerialPortEvent spe) {
            
        }        
    }
    
    /**
     * Método que inicializa los componentes de la interfaz gráfica
     */
    private void inicializarComponentes(){
        
       Font f=new Font("Century Gothic", Font.BOLD, 15);
        
       lTitulo = new JLabel("Mensaje #1");
       lTitulo.setFont(f);
       lTitulo.setBounds(180,20,100,20);
       add(lTitulo);
       
       lFecha = new JLabel("Fecha: 22/10/18");
       lFecha.setFont(f);
       lFecha.setBounds(40,60,200,20);
       add(lFecha);
       
       lLuminosidad = new JLabel("Luminosidad: 85%");
       lLuminosidad.setFont(f);
       lLuminosidad.setBounds(250,60,200,20);
       add(lLuminosidad);
       
       lTemperatura = new JLabel("Temperatura: 25°C");
       lTemperatura.setFont(f);
       lTemperatura.setBounds(40,90,200,20);
       add(lTemperatura);
       
       lHumedad = new JLabel("Humedad: 45%");
       lHumedad.setFont(f);
       lHumedad.setBounds(250,90,200,20);
       add(lHumedad);
       
       lMensaje = new JLabel("Mensaje:");
       lMensaje.setFont(f);
       lMensaje.setBounds(40,120,100,20);
       add(lMensaje);
       
       tMensaje = new JTextArea();
       tMensaje.setFont(f);
       tMensaje.setToolTipText("Ingrese un mensaje de máximo 140 caracteres");
       
       sMensaje = new JScrollPane(tMensaje);
       sMensaje.setBounds(40,150,400,50);
       sMensaje.setHorizontalScrollBarPolicy(
               ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
       add(sMensaje);
       
       bAnterior = new JButton();
       bAnterior.setIcon(new ImageIcon("src/display/izq.png"));
       bAnterior.setBounds(10,110,22,30);
       add(bAnterior);
       
       bSiguiente = new JButton("Siguiente");
       bSiguiente.setIcon(new ImageIcon("src/display/der.png"));
       bSiguiente.setBounds(440,110,22,30);
       add(bSiguiente);
       
       bNuevo = new JButton("Nuevo Mensaje");
       bNuevo.setFont(f);
       bNuevo.setBounds(40,230,180,20);
       add(bNuevo);
       
       bEliminar = new JButton("Eliminar Mensaje");
       bEliminar.setFont(f);
       bEliminar.setBounds(260,230,180,20);
       add(bEliminar);
    }
    
    /**
     * Método que muestra la ventana menú
     */
    public void mostrarMenu(){
        super.setSize( 470,300);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setVisible( true );
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    

}
