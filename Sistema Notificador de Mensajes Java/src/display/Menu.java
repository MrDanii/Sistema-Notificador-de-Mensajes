package display;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.PanamaHitek_MultiMessage;
import datos.Mensaje;
import escritores.Escritor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import lectores.Lector;

public class Menu extends JFrame{
    private Lector lector;              //objeto con el que leeremos los mensajes iniciales (solo se ejecuta al inicio del programa)
    private Escritor escritor;          //objeto con el que escribiremos en nuestro archivo .txt cada vez que se añada o elimine un mensaje
    
    //objetos con la información de los mensajes guardados
    private Mensaje mensajeActual;      
    private ArrayList<Mensaje> mensajes;
    private int indiceMensaje;
    
    //Componentes de la interfaz gráfica
    private JLabel lTitulo, lFecha, lMensaje;
    private JButton bAnterior, bSiguiente, bNuevo, bEliminar;
    private JTextArea tMensaje;
    private JScrollPane sMensaje;
    
    //declaramos objetos para comenzar la comunicación serial entre arduino y java
    private PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    private PanamaHitek_MultiMessage multiMessage = new PanamaHitek_MultiMessage(3, ino);
    
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
       escritor = new Escritor();
       //inicializamos el objeto con el puerto correspondiente, en este caso "/dev/ttyUSB0"
        try {
            ino.arduinoRXTX("/dev/ttyUSB0", ABORT, new HandlerListenerPort());
        } catch (ArduinoException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       //Llamamos al método que inicializa los compontentes
       inicializarComponentes();    
       inicializarMensajes();
       
       
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
       
       lFecha = new JLabel("Fecha: ");
       lFecha.setFont(f);
       lFecha.setBounds(40,60,200,20);
       add(lFecha);
       
       lMensaje = new JLabel("Mensaje:");
       lMensaje.setFont(f);
       lMensaje.setBounds(40,90,100,20);
       add(lMensaje);
       
       tMensaje = new JTextArea();
       tMensaje.setFont(f);
       tMensaje.setToolTipText("Ingrese un mensaje de máximo 140 caracteres");
       
       sMensaje = new JScrollPane(tMensaje);
       sMensaje.setBounds(40,120,400,100);
       sMensaje.setHorizontalScrollBarPolicy(
               ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
       add(sMensaje);
       
       bAnterior = new JButton();
       bAnterior.setIcon(new ImageIcon("src/imagenes/izq.png"));
       bAnterior.setBounds(10,110,22,30);
       add(bAnterior);
       
       bSiguiente = new JButton("Siguiente");
       bSiguiente.setIcon(new ImageIcon("src/imagenes/der.png"));
       bSiguiente.setBounds(440,110,22,30);
       add(bSiguiente);
       
       bNuevo = new JButton("Nuevo Mensaje");
       bNuevo.setFont(f);
       bNuevo.setBounds(40,230,180,20);
       bNuevo.addActionListener(new HandlerBotonAgregar());
       add(bNuevo);
       
       bEliminar = new JButton("Eliminar Mensaje");
       bEliminar.setFont(f);
       bEliminar.setBounds(260,230,180,20);
       add(bEliminar);
    }
    
    private void inicializarMensajes(){
        try {
            lector = new Lector();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        mensajes = lector.getMensajes();      //obtenemos los mensajes guardados en el txt
        
                //Si existen mensajes
        if(!mensajes.isEmpty()){
            //Por defecto pondremos el primer mensaje almacenado
            mostrarMensaje(mensajes.get(0));
        }else{  //Si no existen mensajes
            tMensaje.setText("\tNo hay mensajes");
        }        
    }
    
    /**
     * Método que se encarga de poner el mensaje en cada componente de la interfaz
     * y en la pantalla lcd conectada a nuestro arduino
     * @param mensaje el mensaje que queramos mostrar en la interfaz gráfica y en la lcd
     */
    private void mostrarMensaje(Mensaje mensaje){
        //mostrar vista en la interfaz gráfica
        lFecha.setText(mensaje.getFecha());
        tMensaje.setText(mensaje.getMensajeUsuario());
        //enviamos los datos al arduino, para mostrar la vista en la pantalla lcd
        String mensajeDatos = "Fecha: "+mensaje.getFecha()+
                "; Humedad: "+ mensaje.getHumedad()+
                "; Temperatura: "+ mensaje.getTemperatura()+
                "; Luminosidad: "+ mensaje.getLuminosidad();
        
        try {
            ino.sendData(mensajeDatos+"!");            
            //ino.sendData(mensaje.getMensajeUsuario()+"!");
            System.out.println("Se enviaron los datos: \nmensajeDatos: "+mensajeDatos+
                    "\nmensajeUsuario: "+mensaje.getMensajeUsuario());
        } catch (ArduinoException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);            
        } catch (SerialPortException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }            
    }
    
    private class HandlerBotonAgregar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            new Window().setVisible(true);
            
//            String luminosidad, humedad, temperatura, mensajeUsuario;
//            mensajeUsuario = tMensaje.getText().replace('\n', ' '); //reemplaza los saltos de línea por espacion en blando
//            
//            //verificamos que el mensaje sea menor a 140 caracteres
//            if(mensajeUsuario.length() > 140){
//                JOptionPane.showMessageDialog(null, "El número de Caracteres es mayor a 140 caracteres");
//            }else{
//                try {    
//                    Mensaje mensaje;
//                    while(multiMessage.dataReceptionCompleted() != false){    
//
//                        luminosidad = multiMessage.getMessage(0);
//                        humedad = multiMessage.getMessage(1);
//                        temperatura = multiMessage.getMessage(2);                                            
//                        mensaje = new Mensaje(luminosidad, humedad, temperatura, mensajeUsuario);
//                        //mensajes.add(mensaje);
//                        System.out.println("Lum: "+luminosidad+ "\nhumedad: " +humedad+
//                                "\nTemepratura: "+ temperatura+ "\nmensajeUsuario: "+ mensajeUsuario+
//                                "\nFecha: "+ mensaje.getFecha());
//                        System.out.println(">>>>>>>>>> Numero mensajes: "+ mensajes.size());
//                        multiMessage.flushBuffer();                        
//                        mostrarMensaje(mensaje);
//                        mensajes.add(mensaje);
//                        escritor.definirDatos(mensajes);
//                        escritor.escribir("src/archivos/Mensajes_2.txt");
//                    }
//                    //aqui
//                    
//                } catch (ArduinoException ex) {
//                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (SerialPortException ex) {
//                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
        }        
    }
    
    private class HandlerBotonELiminar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
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
