package display;

import datos.Mensaje;
import escritores.Escritor;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.ERROR;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
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
import lectores.Lector;

public class Menu extends JFrame {

    private Lector lector;              //objeto con el que leeremos los mensajes iniciales (solo se ejecuta al inicio del programa)
    private final Escritor escritor;          //objeto con el que escribiremos en nuestro archivo .txt cada vez que se añada o elimine un mensaje

    //objetos con la información de los mensajes guardados
    public static ArrayList<Mensaje> mensajes;
    private int indiceMensaje;

    //Componentes de la interfaz gráfica
    private JLabel lTitulo, lFecha, lMensaje;
    private JButton bAnterior, bSiguiente, bNuevo, bEliminar;
    private JTextArea tMensaje;
    private JScrollPane sMensaje;

    /*declaramos objetos y las variables para comenzar
    la comunicación serial entre arduino y java
     */
    static SerialPort serialPort;
    private final String PORT_NAME = "/dev/ttyS0";
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;
    static OutputStream output = null;

    public Menu() {
        super("Mensajes");
        setLayout(null);
        escritor = new Escritor();
        //inicializamos el objeto con el puerto correspondiente, en este caso "/dev/ttyUSB0"

        //Llamamos al método que inicializa los compontentes
        inicializarComponentes();
        inicializarMensajes();
        conexionArduino();
    }

    /**
     * Método que inicializa los componentes de la interfaz gráfica y defiine
     * sus propiedades
     */
    private void inicializarComponentes() {

        Font f = new Font("Century Gothic", Font.BOLD, 15);

        lTitulo = new JLabel("Mensaje #1");
        lTitulo.setFont(f);
        lTitulo.setBounds(180, 20, 100, 20);
        add(lTitulo);

        lFecha = new JLabel("Fecha: ");
        lFecha.setFont(f);
        lFecha.setBounds(40, 60, 200, 20);
        add(lFecha);

        lMensaje = new JLabel("Mensaje:");
        lMensaje.setFont(f);
        lMensaje.setBounds(40, 90, 100, 20);
        add(lMensaje);

        tMensaje = new JTextArea();
        tMensaje.setFont(f);
        tMensaje.setEditable(false);
        tMensaje.setToolTipText("Ingrese un mensaje de máximo 140 caracteres");

        sMensaje = new JScrollPane(tMensaje);
        sMensaje.setBounds(40, 120, 400, 100);
        sMensaje.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(sMensaje);

        bAnterior = new JButton();
        bAnterior.setIcon(new ImageIcon("src/imagenes/izq.png"));
        bAnterior.setBounds(10, 110, 22, 30);
        bAnterior.addActionListener(new HandlerBotonAnterior());
        add(bAnterior);

        bSiguiente = new JButton("Siguiente");
        bSiguiente.setIcon(new ImageIcon("src/imagenes/der.png"));
        bSiguiente.setBounds(440, 110, 22, 30);
        bSiguiente.addActionListener(new HandlerBotonSiguiente());
        add(bSiguiente);

        bNuevo = new JButton("Nuevo Mensaje");
        bNuevo.setFont(f);
        bNuevo.setBounds(40, 230, 180, 20);
        bNuevo.addActionListener(new HandlerBotonAgregar());
        add(bNuevo);

        bEliminar = new JButton("Eliminar Mensaje");
        bEliminar.setFont(f);
        bEliminar.setBounds(260, 230, 180, 20);
        bEliminar.addActionListener(new HandlerBotonELiminar());
        add(bEliminar);
    }

    /*
     * Metodo que se utiliza para poder aplicar la comunicacion entre 
     * Java y el IDE Arduino recuperando los datos de cada variable
     * y asignando un id compatible con los nombres del puerto y asi
     * pueda ser reconocido para obtener una conexion exitosa
     */
    public void conexionArduino() {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();

            if (PORT_NAME.equals(currPortId.getName())) {
                portId = currPortId;
                break;
            }
        }

        if (portId == null) {
            System.out.println("No se encuentra el puerto");
            System.exit(ERROR);
            return;
        }

        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            output = serialPort.getOutputStream();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(ERROR);
        }
    }

    /**
     * Metodo para leer los datos de los mensajes almacenados en el archivo de
     * texto
     */
    private void inicializarMensajes() {
        try {
            lector = new Lector();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        mensajes = lector.getMensajes();      //obtenemos los mensajes guardados en el txt
        //Si existen mensajes
        if (!mensajes.isEmpty()) {
            //Por defecto pondremos el primer mensaje almacenado
            mostrarMensaje(mensajes.get(0));
        } else {  //Si no existen mensajes
            tMensaje.setText("\tNo hay mensajes");
        }
    }

    /**
     * Método que se encarga de poner el mensaje en cada componente de la
     * interfaz y en la pantalla lcd conectada a nuestro arduino
     *
     * @param mensaje el mensaje que queramos mostrar en la interfaz gráfica y
     * en la lcd
     */
    private void mostrarMensaje(Mensaje mensaje) {
        //mostrar vista en la interfaz gráfica
        lFecha.setText(mensaje.getFecha());
        tMensaje.setText(mensaje.getMensajeUsuario());
        //enviamos los datos al arduino, para mostrar la vista en la pantalla lcd
        String mensajeDatos = "" + mensaje.getFecha()
                + "" + mensaje.getMensajeUsuario();
        if (output != null) {
            try {
                output.write(mensajeDatos.getBytes());
            } catch (IOException e) {
                System.out.println("Error al enviar datos");
                System.exit(ERROR);
            }
        }
    }

    /**
     * Manejador para el boton de la interfaz al agregar un mensaje
     */
    private class HandlerBotonAgregar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new Window(serialPort, output).setVisible(true);
        }
    }

    /**
     * Manejador para el boton de la interfaz al eliminar un mensaje
     */
    private class HandlerBotonELiminar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //Si no hay mensajes le avisa al usuario que no existen mensajes
            if (mensajes.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay mensajes guardados");
            } else {      //si hay mensajes
                mensajes.remove(indiceMensaje);
                mostrarMensaje(mensajes.get(indiceMensaje));
                escritor.definirDatos(mensajes);
                escritor.escribir("src/archivos/Mensajes.txt");
                if (indiceMensaje >= mensajes.size() - 1) {
                    indiceMensaje--;
                }
                lTitulo.setText("Mensaje #" + (indiceMensaje + 1));
            }
        }
    }

    /**
     * Manejador para el boton de la interfaz al navegar por los mensajes que se
     * tengan actualmente en el archivo de texto
     */
    private class HandlerBotonSiguiente implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //si hay mensajes, buscará el siguiente mensaje
            if (!mensajes.isEmpty()) {
                indiceMensaje = ((indiceMensaje + 1) < mensajes.size()) ? indiceMensaje + 1 : indiceMensaje;
                mostrarMensaje(mensajes.get(indiceMensaje));
                lTitulo.setText("Mensaje #" + (indiceMensaje + 1));
            }
        }

    }

    /**
     * Manejador para el boton de la interfaz al navegar por los mensajes que se
     * tengan actualmente en el archivo de texto
     */
    private class HandlerBotonAnterior implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //SI hay mensajes regresará un mensaje
            if (!mensajes.isEmpty()) {
                indiceMensaje = ((indiceMensaje - 1) > 0) ? indiceMensaje - 1 : indiceMensaje;
                mostrarMensaje(mensajes.get(indiceMensaje));
                lTitulo.setText("Mensaje #" + (indiceMensaje));
            }
        }
    }

    /**
     * Método que muestra la ventana menú
     */
    public void mostrarMenu() {
        super.setSize(470, 300);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setVisible(true);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}