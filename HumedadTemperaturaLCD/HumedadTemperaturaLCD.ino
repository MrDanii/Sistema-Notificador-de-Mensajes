/*
    Definimos las librerias que se van a utilizar, una para que
    la placa de arduino obtenga los valores de humedad y temperatura
    y otra para el funcionamiento de la pantalla LCD respectivamente
*/
#include "DHT.h"
//#include <LiquidCrystal.h>
#include <LiquidCrystal_I2C.h> // usamos la version 1.2.1 compatible con nuestra lcd 16x2

/*
   Definimos las variables y constantes junto con los pin utilizados en la placa arduino
*/

/*
// PIN's utilizados por la pantalla LCD
#define RS 12
#define E 11
#define D4 10
#define D5 9
#define D6 8
#define D7 7
*/

//LiquidCrystal lcd(RS, E, D4, D5, D6, D7); // Inicializamos la pantalla LCD
LiquidCrystal_I2C lcd(0x27, 2, 1, 0, 4, 5, 6, 7, 3, POSITIVE);  //Inicializamos la pantalla LCD

// PIN's utilizados por el sensor de humedad y temperatura
#define DHTPIN 2  // PIN digital donde se conecta el sensor
#define DHTTYPE DHT11 // Definimos el tipo de sensor de humedad y temperatura utilizado, en este caso sera DHT11
DHT dht(DHTPIN, DHTTYPE); // Inicializamos el sensor DHT11

//Variables usadas para guardar lo que imprimiremos en nuestra lcd
String mensajeEstadoTiempo;     // donde pondremos las 
String mensajeUsuario;

//Variables utilizadas para lectura de datos del sensor dht11
float humedad;
float temperatura;

// Variables utilizadas para lectura de datos de la fotorresistencia
const long A = 1000;     //Resistencia en oscuridad en KΩ
const int B = 15;        //Resistencia a la luz (10 Lux) en KΩ
const int RC = 10;       //Resistencia calibracion en KΩ
const int LDRPIN = A0;   //Pin analogico de entrada del LDR

int valorActual; // Variable donde se almacena el valor de luminosidad
int valorLuminosidad; // Variable que nos servira para guardar el valor calculado de luminosidad que se tenga actualmente

void setup() {
  lcd.begin(16, 4); // Definimos el tipo de pantalla LCD utilizada
  dht.begin(); // Comenzamos el sensor DHT
  Serial.begin(9600); // Inicializamos la comunicacion serie
  lcd.write("Bienvenido!!! :)");
}

/*
   Mandamos a llamar a los diferentes metodos para que cumplan con sus respectivas funcionalidades
   humedadTemperatura: Para leer y mostrar los datos del sensor DHT11 en pantalla LCD
   luminosidad: Calcula el valor en Luxes de luminosidad actual y los muestra en la pantalla LCD
*/
void loop() {
  enviarDatos();      //Datos que se envían a la interfaz de software
  recibirDatos();     //Datos que se reciben por medio de la interfaz de software
  //humedadTemperatura();
  //luminosidad();
}

void recibirDatos(){
  mensajeEstadoTiempo = Serial.readStringUntil('!');
    mensajeUsuario = Serial.readStringUntil('!');
    
    displayLCD();
    
  if(Serial.available() > 0){
    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print("cargando...");
    
    
    
  }
}

/**
 * Metodo que se encargar de enviar los datos del sensor DHT11 (humedad y temperatura) y
 * el valor de la fotoresistencia (luminosidad) por medio del puerto serie, para que 
 * la computadora los pueda leer
 */
void enviarDatos(){
  delay(2000);
  humedad = dht.readHumidity();
  temperatura = dht.readTemperature();
  valorActual = analogRead(LDRPIN); //valor de la fotoresistencia
  valorLuminosidad = ((long)valorActual * A * 10) / ((long)B * RC * (1024 - valorActual)); // Formula utilizada porque el LDR esta entre A0 y Vcc
  Serial.println(humedad);
  Serial.println(temperatura);  
  Serial.println(valorLuminosidad/10);
}

void displayLCD(){
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print(mensajeEstadoTiempo);
  lcd.setCursor(0,1);
  lcd.print(mensajeUsuario);
}

void humedadTemperatura() {
  float h = dht.readHumidity(); // Leemos la humedad relativa
  float t = dht.readTemperature(); // Leemos la temperatura que se encuentra en grados centigrados por defecto

  /*
      Comprobamos si ha habido un error al leer los datos de temperatura y humedad
      En caso contrario imprimimos los valores obtenidos desde el sensor DHT11 y mostramos
      los datos enviandolos a la pantalla LCD con sus respectivas coordenadas donde se podran
      visualizar
  */
  if (isnan(t) || isnan(h)) {
    lcd.clear();
    lcd.write("Error Al Leer Datos Del Sensor DHT11");
  } else {
    lcd.clear();
    lcd.setCursor(0, 1); // Ubicamos la coredanada donde se mostrara el valor en la pantalla LCD
    lcd.write("H:");
    lcd.print(h); // Imprimimos en pantalla el valor actualizado de humedad actual
    lcd.write(" %");
    lcd.setCursor(11, 1); // Ubicamos la coredanada donde se mostrara el valor en la pantalla LCD
    lcd.write("T:");
    lcd.print(t); // Imprimimos en pantalla el valor actualizado de temperatura actual
    lcd.write(" C");
    //delay(1000); // Esperamos 1 segundo entre datos mostrados por el sensor en pantalla LCD
  }
}

void luminosidad() {
  /*
      Leemos el valor relativo de luminosidad actual y lo guardamos en una variable.
      El valor leido (desde Vcc) aumenta de manera proporcional con respecto a la luz recibida
  */
  valorActual = analogRead(LDRPIN);

  valorLuminosidad = ((long)valorActual * A * 10) / ((long)B * RC * (1024 - valorActual)); // Formula utilizada porque el LDR esta entre A0 y Vcc
  lcd.setCursor(0, 2); // Ubicamos la coredanada donde se mostrara el valor en la pantalla LCD
  lcd.write("I:");
  lcd.print(valorLuminosidad); // Imprimimos en pantalla el valor actualizado de luminosidad actual
  lcd.write(" Lux");
  //delay(1000); // Esperamos 1 segundo entre datos mostrados de luminosidad en pantalla LCD
}
