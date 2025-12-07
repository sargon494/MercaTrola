package caja.clientes.unificado;

import caja.clientes.Mediaydesvi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class CajaUnificada {

    private long media;
    private long desviacion;

    public void colas(int numClientes, int numCajas) {

        // Semáforo: permite que como máximo numCajas clientes estén siendo atendidos a la vez
        Semaphore cola = new Semaphore(numCajas);

        // Cada posición representa una caja libre/ocupada
        AtomicBoolean[] cajasLibres = new AtomicBoolean[numCajas];
        for (int i = 0; i < numCajas; i++) {
            cajasLibres[i] = new AtomicBoolean(true); // True indica caja libre
        }

        List<Long> tiempos = new ArrayList<>();      // Tiempos siendo atendido
        List<Long> esperaslista = new ArrayList<>(); // Tiempos esperando en la cola unificada

        Thread[] clientes = new Thread[numClientes];

        System.out.println("MercaTrola abre sus puertas.");

        try {
            for (int i = 0; i < numClientes; i++) {
                // Cada cliente recibe el semáforo + listas compartidas + array de cajas
                clientes[i] = new Thread(new ClienteUnificado(cola, tiempos, esperaslista , cajasLibres), "Cliente " + i);
                clientes[i].start();
                Thread.sleep(50); // Pequeña pausa para simular llegadas intermitentes
            }

            // join() asegura que todas las simulaciones de clientes acaben antes de calcular estadísticas
            for (Thread cliente : clientes) {
                cliente.join();
            }

            System.out.println("MercaTrola cierra sus puertas.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cálculo estadístico externo
        Mediaydesvi calculo = new Mediaydesvi();

        System.out.println();
        media = (long) calculo.calcularmedia(esperaslista); // Media del tiempo de espera en cola
        System.out.println("Tiempo medio de espera : " + media + "ms");

        desviacion = calculo.calculardesviacion(esperaslista); // Desviación típica del tiempo de espera
        System.out.println("Desviacion tipica : " + desviacion + "ms");
        System.out.println();
    }

    public long getMedia() {
        return media;
    }

    public long getDesviacion() {
        return desviacion;
    }
}