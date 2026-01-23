# Laboratorio 1 - Arquitectura de Software
## Ejercicio Introducción al paralelismo - Hilos - Caso BlackListSearch

**Elaborado por:**
Juan Carlos Leal Cruz

___

### Parte 1. Introducción a Hilos en Java

1. A continuación adjunto la evidencia de la creación de la clase CountThread
![img.png](img/MyLabImages/CountThreadImg.png)
2. Al completar el metodo main de la clase CountThreadsMain se realizaron ambas pruebas, tanto usando run como start y se obtuvo lo siguiente:
   1. **Con start()**
   ![img.png](img/MyLabImages/StartPart1.png)
   2. **Con run()**
   ![img.png](img/MyLabImages/RunPart1.png)
   3. **¿Cómo cambia la salida? ¿Por qué?**
   La salida cambia en la medida en que con start() la cuenta de los hilos sale de forma aleatoria tal como se evidencia en la imagen, mientras que haciendo uso de run() la cuenta sale completamente en orden.
   Lo anterior ocurre debido a que el método start funciona creando un hilo independeinte, de tal forma que cada hilo se ejecuta en paralelo y se imprimen los números conforme el sistema va ejecutando; por otro lado, run() se ejecuta sobre un hilo principal, de tal forma que es secuencial y por ello primero se ejecuta el hilo #1 para luego continuar con el siguiente

___

### Parte 2. Ejercicio Black List Search

