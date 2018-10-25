package escritores;

import datos.Mensaje;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Escritor {

    private FileWriter libreta;
    private BufferedWriter hoja;
    private PrintWriter pluma;
    private String datos;

    public void escribir(String direccion) {
        try {
            libreta = new FileWriter(direccion);
            hoja = new BufferedWriter(libreta);
            pluma = new PrintWriter(hoja);

            pluma.print(datos);
            hoja.close();
            libreta.close();

        } catch (Exception ex) {
            System.out.println("El error es: " + ex);
        }
    }

    public void definirDatos(ArrayList<Mensaje> mensajes) {
        this.datos = "";
        if (mensajes != null) {
            for (Mensaje mensaje : mensajes) {
                this.datos += mensaje.getFecha() + "\n";
                this.datos += mensaje.getMensajeUsuario() + "\n";
            }
        }
        System.out.println(this.datos);
    }
}

//Modulo Test
/*
class testEscritor{
    public static void main(String[] args){
        Escritor escritor=  new Escritor();
        ArrayList<Mensaje> mensajes = new ArrayList<>();
        mensajes.add(new Mensaje("22:56:30, 19/10/2018",
                "Mensaje: We figWht for those who cannot :v"));
        mensajes.add(new Mensaje("12:05:50, 20/11/2018",
                "Mensaje: Fear is the first of many foes"));
         mensajes.add(new Mensaje("22:56:30, 19/10/2018",
                "Mensaje: Luchamos por aquellos que no pueden :v"));        
        
        escritor.definirDatos(mensajes);
        escritor.escribir("src/archivos/Mensajes_2.txt");

    }
}
*/
