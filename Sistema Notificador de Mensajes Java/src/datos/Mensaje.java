package datos;

import java.util.Calendar;

public class Mensaje {

    private String fecha;
    private String mensajeUsuario;

    // Contructores
    /**
     * Inicializa nuestro Mensaje, con una fecha específica establecida por el
     * usuario
     *
     * @param fecha fecha
     * @param mensajeUsuario mensaje
     */
    public Mensaje(String fecha, String mensajeUsuario) {
        this.fecha = fecha;
        this.mensajeUsuario = mensajeUsuario;
    }

    /**
     * Inicializa el objeto con una fecha tomada por el sistema
     *
     * @param mensajeUsuario mensaje
     */
    public Mensaje(String mensajeUsuario) {
        establecerFecha();
        this.mensajeUsuario = mensajeUsuario;
    }

    /**
     * Método para establecer la fecha con base a la hora del equipo, con el
     * formato HH:MI:SS, DD/MM/YYYY es decir: Horas:Minutos:Segundos,
     * Dia/Mes/Anio
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

        this.fecha = ((hora <= 9) ? "0" + hora : "" + hora) + ":"
                + ((minutos <= 9) ? "0" + minutos : "" + minutos) + ":"
                + ((segundos <= 9) ? "0" + segundos : "" + segundos) + ", "
                + ((dia <= 9) ? "0" + dia : "" + dia) + "/"
                + ((mes <= 9) ? "0" + mes : "" + mes) + "/"
                + anio;
    }

    public String getFecha() {
        return fecha;
    }

    public String getMensajeUsuario() {
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
