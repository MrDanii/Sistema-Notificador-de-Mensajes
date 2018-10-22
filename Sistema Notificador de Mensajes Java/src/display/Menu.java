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
import javax.swing.JTextArea;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class Menu extends JFrame{
    JLabel LTITULO;
    JButton BANTERIOR, BSIGUIENTE, BNUEVO, BELIMINAR, BAGREGAR, BCANCELAR;
    JTextArea TMENSAJE;
    private PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    /*
    private SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    */       
    public Menu(){
        
        super("Mensajes");
        setLayout(null);
       
        Font f=new Font("Century Gothic", Font.BOLD, 15);
        
        LTITULO = new JLabel("Mensaje #1");
        LTITULO.setFont(f);
        LTITULO.setBounds(105,20,100,20);
        add(LTITULO);
       
        TMENSAJE = new JTextArea();
        TMENSAJE.setFont(f);
        TMENSAJE.setBounds(47,50,200,150);
        add(TMENSAJE);
        
        BANTERIOR = new JButton();
        BANTERIOR.setIcon(new ImageIcon("src/imagenes/izq.png"));
        BANTERIOR.setBounds(15,110,22,30);
        add(BANTERIOR);
        
        BSIGUIENTE = new JButton("Siguiente");
        BSIGUIENTE.setIcon(new ImageIcon("src/imagenes/der.png"));
        BSIGUIENTE.setBounds(257,110,22,30);
        add(BSIGUIENTE);
        
        BNUEVO = new JButton("Nuevo Mensaje");
        BNUEVO.setFont(f);
        BNUEVO.setBounds(45,250,200,20);
        add(BNUEVO);
        
        BELIMINAR = new JButton("Eliminar Mensaje");
        BELIMINAR.setFont(f);
        BELIMINAR.setBounds(45,300,200,20);
        add(BELIMINAR);
         
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
    
    public static void main(String[] args) {
            Menu obj = new Menu(); 
               obj.setSize( 300,400);
               obj.setLocationRelativeTo(null);
               obj.setResizable(false);
               obj.setVisible( true );
               obj.setDefaultCloseOperation(EXIT_ON_CLOSE);
               
    }
}
