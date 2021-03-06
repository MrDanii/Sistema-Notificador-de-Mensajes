package display;

import datos.Mensaje;
import escritores.Escritor;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JOptionPane;

public class Window extends javax.swing.JFrame {

    int caracteres = 140;
    private final Escritor escritor;

    SerialPort serialPort;
    private OutputStream Output = null;

    /**
     * Constructor de la Clase Window
     */
    public Window(SerialPort serialPort, OutputStream output) {
        escritor = new Escritor();
        initComponents();
        letras();
        this.Output = output;
    }

    /**
     * Metodo que nos ayuda a realizar la comunicacion entre java y arduino para
     * el encio de mensajes por medio del puerto serial
     *
     * @param data
     */
    private void EnviarDatos(String data) {
        try {
            Output.write(data.getBytes());

        } catch (IOException e) {
            showError("Error al enviar datos");
            System.exit(ERROR);
        }
    }

    /**
     * Metodo que nos sirve para la lectura de la cadena de mensajes que se
     * enviara por la interfaz de usuario
     */
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

    /**
     * Metodo que se ejecuta para notificar al surgir errores de comunicacion
     * entre java y el IDE arduino
     *
     * @param errorMessage
     */
    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null,
                errorMessage,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldMensaje = new javax.swing.JTextField();
        jLabelCaracteres = new javax.swing.JLabel();
        jButtonEnviar = new javax.swing.JButton();
        jButtonLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

    /**
     * Evento para identificar las teclas presionadas para generar los mensajes
     *
     * @param evt
     */
    private void jTextFieldMensajeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMensajeKeyReleased
        letras();
    }//GEN-LAST:event_jTextFieldMensajeKeyReleased

    /**
     * Evento que se utiliza para obtener el mensaje escrito desde la interfaz
     * de usuario obteniendo fecha y hora para asi poder almacenarlo en un
     * archivo de texto
     *
     * @param evt
     */
    private void jButtonEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnviarActionPerformed
        Mensaje mensajeActual = new Mensaje(jTextFieldMensaje.getText());
        String fecha = mensajeActual.getFecha();
        System.out.println(fecha);
        EnviarDatos(fecha + jTextFieldMensaje.getText());
        jTextFieldMensaje.setText("");
        Menu.mensajes.add(mensajeActual);
        escritor.definirDatos(Menu.mensajes);
        escritor.escribir("src/archivos/Mensajes.txt");
        letras();
    }//GEN-LAST:event_jButtonEnviarActionPerformed

    /**
     * Evento para limpiar el textfield donde se escribira el mensaje despues de
     * seer enviado por el usuario
     *
     * @param evt
     */
    private void jButtonLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimpiarActionPerformed
        jTextFieldMensaje.setText("");
        letras();
    }//GEN-LAST:event_jButtonLimpiarActionPerformed

    /**
     * Evento para poder enviar el mensaje escrito por el usuario hacia el IDE
     * arduino y pueda ser visualizado
     *
     * @param evt
     */
    private void jTextFieldMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMensajeActionPerformed
        EnviarDatos(jTextFieldMensaje.getText());
        jTextFieldMensaje.setText("");
    }//GEN-LAST:event_jTextFieldMensajeActionPerformed

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
                new Window(Menu.serialPort, Menu.output).setVisible(true);
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
