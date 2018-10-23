package display;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Enumeration;
import javax.swing.JOptionPane;

public class Window extends javax.swing.JFrame {
    int caracteres = 140;
    private OutputStream Output = null;
    SerialPort serialPort;
    private final String PORT_NAME = "/dev/ttyS0";
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;
    
    Calendar calendar = Calendar.getInstance();

    public void ArduinoConnection() {
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
            showError("No se encuentra el puerto");
            System.exit(ERROR);
            return;
        }

        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            Output = serialPort.getOutputStream();

        } catch (Exception e) {
            showError(e.getMessage());
            System.exit(ERROR);
        }
    }

    private void EnviarDatos(String data) {
        try {
            Output.write(data.getBytes());

        } catch (IOException e) {
            showError("Error al enviar datos");
            System.exit(ERROR);
        }
    }

    public Window() {
        initComponents();
        letras();
        ArduinoConnection();
    }

    public void letras() {
        caracteres = 140 - jTextFieldMensaje.getText().length(); //Indica la catidad de carcteres
        //disponibles. En el LCD solo se permite imprimir 32 caracteres.

        if (caracteres <= 0) { //Si la cantidad de caracteres se ha agotado... 
            jLabelCaracteres.setText("Caracteres disponibles: 0"); //Se imprime que la cantidad de 
            //caracteres disponibles es 0
            String cadena = ""; //Se declara la variable que guardará el mensaje a enviar
            cadena = jTextFieldMensaje.getText(); //Se asigna el texto del TextField a la variable cadena
            cadena = cadena.substring(0, 140); //Se evita que por alguna razón la variable contenga
            //más de 32 caracteres, utilizando el substring que crea un string a partir de uno mayor.
            jTextFieldMensaje.setText(cadena); //se regresa la cadena con 32 caracteres al TextField
        } else {
            //Si la cantidad de caracteres disponibles es ayor a 0 solamente se imprimirá la cantidad
            //de caracteres disponibles
            jLabelCaracteres.setText("Caracteres disponibles: " + (caracteres));
        }
    }
    
    private void showError(String errorMessage){
		JOptionPane.showMessageDialog(null,
				errorMessage,
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
	}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldMensaje = new javax.swing.JTextField();
        jLabelCaracteres = new javax.swing.JLabel();
        jButtonEnviar = new javax.swing.JButton();
        jButtonLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextFieldMensaje.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTextFieldMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMensajeActionPerformed(evt);
            }
        });
        jTextFieldMensaje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldMensajeKeyReleased(evt);
            }
        });

        jLabelCaracteres.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelCaracteres.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelCaracteres.setText("Caracteres disponibles: ");

        jButtonEnviar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButtonEnviar.setText("Enviar texto");
        jButtonEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnviarActionPerformed(evt);
            }
        });

        jButtonLimpiar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButtonLimpiar.setText("Limpiar Texto");
        jButtonLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldMensaje)
                    .addComponent(jLabelCaracteres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jButtonEnviar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonLimpiar)
                .addContainerGap(97, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelCaracteres)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEnviar)
                    .addComponent(jButtonLimpiar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldMensajeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMensajeKeyReleased
        letras();
    }//GEN-LAST:event_jTextFieldMensajeKeyReleased

    private void jButtonEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnviarActionPerformed
        String year;
        year = String.valueOf(calendar.get(Calendar.YEAR));
        //System.out.println("" + calendar.get(Calendar.YEAR));
        EnviarDatos(year + jTextFieldMensaje.getText());
        jTextFieldMensaje.setText("");
        letras();
    }//GEN-LAST:event_jButtonEnviarActionPerformed

    private void jButtonLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimpiarActionPerformed
        jTextFieldMensaje.setText("");
        letras();
    }//GEN-LAST:event_jButtonLimpiarActionPerformed

    private void jTextFieldMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMensajeActionPerformed
        EnviarDatos(jTextFieldMensaje.getText());
        jTextFieldMensaje.setText("");
    }//GEN-LAST:event_jTextFieldMensajeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Window().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonEnviar;
    private javax.swing.JButton jButtonLimpiar;
    private javax.swing.JLabel jLabelCaracteres;
    private javax.swing.JTextField jTextFieldMensaje;
    // End of variables declaration//GEN-END:variables
}