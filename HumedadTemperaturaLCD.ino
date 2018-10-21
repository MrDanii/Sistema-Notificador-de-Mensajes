#include <LiquidCrystal.h>

/*
   Definimos las variables y constantes junto con los pin utilizados en la placa arduino
*/

// PIN's utilizados por la pantalla LCD
#define RS 12
#define E 11
#define D4 10
#define D5 9
#define D6 8
#define D7 7

LiquidCrystal lcd(RS, E, D4, D5, D6, D7); // Inicializamos la pantalla LCD

// Variables utilizadas para lectura de datos de la fotorresistencia
const long A = 1000;     //Resistencia en oscuridad en KΩ
const int B = 15;        //Resistencia a la luz (10 Lux) en KΩ
const int RC = 10;       //Resistencia calibracion en KΩ
const int LDRPIN = A0;   //Pin analogico de entrada del LDR

int valorActual; // Variable donde se almacena el valor de luminosidad 
int valorLuminosidad; // Variable que nos servira para guardar el valor calculado de luminosidad que se tenga actualmente

void setup() {
  lcd.begin(20, 4); // Definimos el tipo de pantalla LCD utilizada
  Serial.begin(9600); // Inicializamos la comunicacion serie
}

void loop() {
  luminosidad();
}

void luminosidad(){
  /*
   *  Leemos el valor relativo de luminosidad actual y lo guardamos en una variable.
   *  El valor leido (desde Vcc) aumenta de manera proporcional con respecto a la luz recibida
   */
  valorActual = analogRead(LDRPIN);

  valorLuminosidad = ((long)valorActual * A * 10) / ((long)B * RC * (1024 - valorActual)); // Formula utilizada porque el LDR esta entre A0 y Vcc 
  lcd.setCursor(0, 2); // Ubicamos la coredanada donde se mostrara el valor en la pantalla LCD
  lcd.write("I:");
  lcd.print(valorLuminosidad); // Imprimimos en pantalla el valor actualizado de luminosidad actual
  lcd.write(" Lux");
  //delay(1000); // Esperamos 1 segundo entre datos mostrados de luminosidad en pantalla LCD
}
