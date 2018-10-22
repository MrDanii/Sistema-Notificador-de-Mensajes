package display;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class Menu extends JFrame{
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
}
