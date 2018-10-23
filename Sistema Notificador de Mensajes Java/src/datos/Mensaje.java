package datos;

import java.util.Calendar;
import java.util.Date;

public class Mensaje {
    private String fecha;
    private String luminosidad;
    private String humedad;
    private String temperatura;
    private String mensajeUsuario;

    // Contructores
    public Mensaje(String fecha, String luminosidad, String humedad, String temperatura, String mensajeUsuario) {
        this.fecha= fecha;
        this.luminosidad = luminosidad;
        this.humedad = humedad;
        this.temperatura = temperatura;
        this.mensajeUsuario = mensajeUsuario;
    }
    
    public Mensaje(String luminosidad, String humedad, String temperatura, String mensajeUsuario) {
        establecerFecha();
        this.luminosidad = luminosidad;
        this.humedad = humedad;
        this.temperatura = temperatura;
        this.mensajeUsuario = mensajeUsuario;
    }
    
    public Mensaje(int luminosidad, int humedad, int temperatura, String mensajeUsuario){
        establecerFecha();
        this.luminosidad = "luminosidad: "+ String.valueOf(luminosidad) +"%";
        this.humedad = "Humedad: "+ String.valueOf(humedad) +"%";        
        this.temperatura = "Temperatura: "+ String.valueOf(temperatura) +"°C";        
        this.mensajeUsuario = mensajeUsuario;        
    }
    
    /**
     * Método para establecer la fecha con base a la hora del equipo,
     * con el formato HH:MI:SS, DD/MM/YYYY
     * es decir: Horas:Minutos:Segundos, Dia/Mes/Anio
     */    
    private void establecerFecha() {
        Calendar calendar = Calendar.getInstance();
        int anio, mes, dia, hora, minutos, segundos;
        
        anio = calendar.get(Calendar.YEAR);
        mes = 1 + calendar.get(Calendar.MONTH);         // sumamos mas 1 porque calendar empieza con el mes 0
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        hora = calendar.get(Calendar.HOUR_OF_DAY);
        minutos = calendar.get(Calendar.MINUTE);
        segundos = calendar.get(Calendar.SECOND);
        
        this.fecha= ((hora <= 9) ?"0"+hora:""+hora) + ":" + 
                ((minutos <= 9) ?"0"+minutos:""+minutos) + ":" +
                ((segundos <= 9) ?"0"+segundos:""+segundos) + ", " +
                ((dia <= 9) ?"0"+dia:""+dia) + "/" +
                ((mes <= 9) ?"0"+mes:""+mes) + "/" +
                anio;        
    }

    public void setLuminosidad(String luminosidad) {
        this.luminosidad = luminosidad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }
    
    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public void setMensajeUsuario(String mensajeUsuario) {
        this.mensajeUsuario = mensajeUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public String getLuminosidad() {
        return luminosidad;
    }

    public String getHumedad() {
        return humedad;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public String getMensajeUsuario(){
        return mensajeUsuario;
    }
        
}

//Modulo Test
/*
class TestMensaje{
    
    public static void main (String [] args){
        Mensaje mensaje= new Mensaje("60", "30°C, Hum:50%", "Temp: 30°C","Lalalalalalallala");
        
    }
}
*/