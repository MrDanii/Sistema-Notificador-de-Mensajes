package lectores;

import datos.Mensaje;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lector {           
    private final FileReader fr;
    private final BufferedReader br;    
    private int nMensajes;
    private String cadenaMensajes;
    private ArrayList<Mensaje> mensajes;    
    
    /**
     * inicializa nuestro archivo de lectura (FileReader) con la dirección por defecto del proyecto
     * @throws FileNotFoundException 
     */
    public Lector() throws FileNotFoundException{
        fr= new FileReader("src/archivos/Mensajes.txt");        
        br= new BufferedReader(fr); 
        inicializarMensajes();
    }
    
    /**
     * inicializa nuestro archivo de lectura (FileReader) por medio de una dirección
     * @param ubicacion dirección de nuestro archivo
     * @throws FileNotFoundException 
     */
    public Lector(String ubicacion) throws FileNotFoundException{
        fr= new FileReader(ubicacion);
        br= new BufferedReader(fr);        
        inicializarMensajes();
    }
    
    /**
     * inicializa nuestro archivo de lectura (FileReader) por medio de un objeto File
     * @param archivo objeto File
     * @throws FileNotFoundException 
     */
    public Lector(File archivo) throws FileNotFoundException{
        fr= new FileReader(archivo);
        br= new BufferedReader(fr);
        inicializarMensajes();
    }
    
    /**
     * 
     */
    private void inicializarMensajes(){
        this.nMensajes = 0;
        this.mensajes = new ArrayList<>();
        
        if(br != null){
            try {     
                String linea;
                cadenaMensajes = "";
                while((linea = br.readLine()) != null){
                    cadenaMensajes += linea+ "\n";
                    nMensajes ++;                    
                }
                nMensajes/= 2;
                System.out.println("Mensajes existentes: " + nMensajes + "\n\n"+cadenaMensajes);
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("El archivo no existe o no se abrió correctamente");
        }
        //Una vez que tenemos la cadena con los mensajes, los encapsulamos en un arraylist de tipo Mensaje.java
        for(int i=0; i< nMensajes; i++){
            
        }
    }
    
    public ArrayList<Mensaje> getMensajes(){
        StringTokenizer tokens = new StringTokenizer(cadenaMensajes, "\n", false);
        Mensaje mensajeActual;
        for(int i=0; i < nMensajes; i++){
            mensajeActual = new Mensaje(tokens.nextToken(), tokens.nextToken());
            mensajes.add(mensajeActual);  //y aniadimos ese mensaje, a nuestro arreglo de mensajes
        }                
        return mensajes;
    }
    
    public boolean hayMensajes(){
        System.out.println("nMensajes: "+ this.nMensajes);
        return nMensajes > 0;
    }
    
    public FileReader getFileReader(){
        return fr;
    }
    
    public BufferedReader getBufferedReader(){
        return br;
    }
}

//Modulo Test
/*
class TestLector{
    public static void main(String[] args){
        try {
            Lector lector=  new Lector();   
            lector.getMensajes();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestLector.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
}
*/