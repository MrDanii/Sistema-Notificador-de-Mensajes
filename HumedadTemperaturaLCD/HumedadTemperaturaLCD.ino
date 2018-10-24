/*
    Definimos las librerias que se van a utilizar, una para que
    la placa de arduino obtenga los valores de humedad y temperatura
    otra para el funcionamiento de la pantalla LCD y la libreria Time
    que nos permitira obtener una fecha y hora en tiempo real respectivamente
*/
#include "DHT.h"
#include <LiquidCrystal.h>
#include <TimeLib.h> 

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
#define pushSiguiente 5
#define pushAnterior 4

LiquidCrystal lcd(RS, E, D4, D5, D6, D7); // Inicializamos la pantalla LCD

// PIN's utilizados por el sensor de humedad y temperatura
#define DHTPIN A1  // PIN digital donde se conecta el sensor
#define DHTTYPE DHT11 // Definimos el tipo de sensor de humedad y temperatura utilizado, en este caso sera DHT11
DHT dht(DHTPIN, DHTTYPE); // Inicializamos el sensor DHT11

// Variables utilizadas para lectura de datos de la fotorresistencia
const long A = 1000;     //Resistencia en oscuridad en KΩ
const int B = 15;        //Resistencia a la luz (10 Lux) en KΩ
const int RC = 10;       //Resistencia calibracion en KΩ
const int LDRPIN = A0;   //Pin analogico de entrada del LDR

int valorActual; // Variable donde se almacena el valor de luminosidad
int valorLuminosidad; // Variable que nos servira para guardar el valor calculado de luminosidad que se tenga actualmente

int imprimir=0;
String Mensaje="";
int indice = 0;

void setup() {
  pinMode(10,OUTPUT);
digitalWrite(10,HIGH);
pinMode(pushSiguiente, INPUT);
pinMode(pushAnterior, INPUT);
  lcd.begin(20, 4); // Definimos el tipo de pantalla LCD utilizada
  dht.begin(); // Comenzamos el sensor DHT
  Serial.begin(9600); // Inicializamos la comunicacion serie
  //Anotamos la hora y la fecha desde la que nuestro programa empezará a contar.
  //Formato: hora, minutos, segundos, días, mes, año
  setTime(1,00,00,20,10,2018);
}

/*
   Mandamos a llamar a los diferentes metodos para que cumplan con sus respectivas funcionalidades
   humedadTemperatura: Para leer y mostrar los datos del sensor DHT11 en pantalla LCD
   luminosidad: Calcula el valor en Luxes de luminosidad actual y los muestra en la pantalla LCD
*/
void loop() {
  humedadTemperatura();
  luminosidad();
  fechaHora();
  LCD();
  while(analogRead(pushSiguiente) == HIGH){
    Serial.println("Siguiente");
  }
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

void fechaHora(){
  time_t t = now();//Declaramos la variable time_t t

//Imprimimos la fecha y lahora
lcd.setCursor(0,0);
  if(day(t)>=0 && day(t)<=9)
  { lcd.write("0");
    lcd.print(day(t)); }
  else
  {lcd.print(day(t));}  
  lcd.write(+ "/") ;
  
  if(month(t)>=0 && month(t)<=9)
  { lcd.write("0");
  lcd.print(month(t));}
  else
  {lcd.print(month(t));}
  lcd.write(+ "/") ;
  
  lcd.print(year(t)); 
  lcd.write(" ") ;

  if(hour(t)>=0 && hour(t)<=9)
  { lcd.write("0");
  lcd.print(hour(t)); }
  else
  {lcd.print(hour(t));}
  lcd.write(+ ":") ;

  if(minute(t)>=0 && minute(t)<=9)
  { lcd.write("0");
  lcd.print(minute(t));}
  else
  {lcd.print(minute(t));}
  lcd.write(":") ;
  
  if(second(t)>=0 && second(t)<=9)
  { lcd.write("0");
    lcd.print(second(t));}
  else
  {lcd.print(second(t));}
  delay(500);//Esperamos 1 segundo
}

void LCD(){
  int cuenta=0;
int caracteres=0;
while (Serial.available()>0){
Mensaje=Mensaje+Decimal_to_ASCII(Serial.read());
cuenta++;
}
caracteres=Mensaje.length();
//if (caracteres>20){
  if (Mensaje!=""){
 lcd.clear();
 //lcd.setCursor(0,0);
 lcd.print(Mensaje.substring(0,20));
 lcd.setCursor(0,1); 
 lcd.print(Mensaje.substring(20,40));
 lcd.setCursor(0,2); 
 lcd.print(Mensaje.substring(40,caracteres));
  delay(5000);   
    
}
//}
//else
//{
//if (Mensaje!=""){
// lcd.clear();
// lcd.setCursor(0,1);
// lcd.print(Mensaje);
// delay(5000);
//}
//}

Mensaje="";
}

char Decimal_to_ASCII(int entrada){
  char salida=' ';
  switch(entrada){
case 32: 
salida=' '; 
break; 
case 33: 
salida='!'; 
break; 
case 34: 
salida='"'; 
break; 
case 35: 
salida='#'; 
break; 
case 36: 
salida='$'; 
break; 
case 37: 
salida='%'; 
break; 
case 38: 
salida='&'; 
break; 
case 39: 
salida=' '; 
break; 
case 40: 
salida='('; 
break; 
case 41: 
salida=')'; 
break; 
case 42: 
salida='*'; 
break; 
case 43: 
salida='+'; 
break; 
case 44: 
salida=','; 
break; 
case 45: 
salida='-'; 
break; 
case 46: 
salida='.'; 
break; 
case 47: 
salida='/'; 
break; 
case 48: 
salida='0'; 
break; 
case 49: 
salida='1'; 
break; 
case 50: 
salida='2'; 
break; 
case 51: 
salida='3'; 
break; 
case 52: 
salida='4'; 
break; 
case 53: 
salida='5'; 
break; 
case 54: 
salida='6'; 
break; 
case 55: 
salida='7'; 
break; 
case 56: 
salida='8'; 
break; 
case 57: 
salida='9'; 
break; 
case 58: 
salida=':'; 
break; 
case 59: 
salida=';'; 
break; 
case 60: 
salida='<'; 
break; 
case 61: 
salida='='; 
break; 
case 62: 
salida='>'; 
break; 
case 63: 
salida='?'; 
break; 
case 64: 
salida='@'; 
break; 
case 65: 
salida='A'; 
break; 
case 66: 
salida='B'; 
break; 
case 67: 
salida='C'; 
break; 
case 68: 
salida='D'; 
break; 
case 69: 
salida='E'; 
break; 
case 70: 
salida='F'; 
break; 
case 71: 
salida='G'; 
break; 
case 72: 
salida='H'; 
break; 
case 73: 
salida='I'; 
break; 
case 74: 
salida='J'; 
break; 
case 75: 
salida='K'; 
break; 
case 76: 
salida='L'; 
break; 
case 77: 
salida='M'; 
break; 
case 78: 
salida='N'; 
break; 
case 79: 
salida='O'; 
break; 
case 80: 
salida='P'; 
break; 
case 81: 
salida='Q'; 
break; 
case 82: 
salida='R'; 
break; 
case 83: 
salida='S'; 
break; 
case 84: 
salida='T'; 
break; 
case 85: 
salida='U'; 
break; 
case 86: 
salida='V'; 
break; 
case 87: 
salida='W'; 
break; 
case 88: 
salida='X'; 
break; 
case 89: 
salida='Y'; 
break; 
case 90: 
salida='Z'; 
break; 
case 91: 
salida='['; 
break; 
case 92: 
salida=' '; 
break; 
case 93: 
salida=']'; 
break; 
case 94: 
salida='^'; 
break; 
case 95: 
salida='_'; 
break; 
case 96: 
salida='`'; 
break; 
case 97: 
salida='a'; 
break; 
case 98: 
salida='b'; 
break; 
case 99: 
salida='c'; 
break; 
case 100: 
salida='d'; 
break; 
case 101: 
salida='e'; 
break; 
case 102: 
salida='f'; 
break; 
case 103: 
salida='g'; 
break; 
case 104: 
salida='h'; 
break; 
case 105: 
salida='i'; 
break; 
case 106: 
salida='j'; 
break; 
case 107: 
salida='k'; 
break; 
case 108: 
salida='l'; 
break; 
case 109: 
salida='m'; 
break; 
case 110: 
salida='n'; 
break; 
case 111: 
salida='o'; 
break; 
case 112: 
salida='p'; 
break; 
case 113: 
salida='q'; 
break; 
case 114: 
salida='r'; 
break; 
case 115: 
salida='s'; 
break; 
case 116: 
salida='t'; 
break; 
case 117: 
salida='u'; 
break; 
case 118: 
salida='v'; 
break; 
case 119: 
salida='w'; 
break; 
case 120: 
salida='x'; 
break; 
case 121: 
salida='y'; 
break; 
case 122: 
salida='z'; 
break; 
case 123: 
salida='{'; 
break; 
case 124: 
salida='|'; 
break; 
case 125: 
salida='}'; 
break; 
case 126: 
salida='~'; 
break; 
  }
  return salida;
}
