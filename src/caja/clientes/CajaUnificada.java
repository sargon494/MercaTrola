package caja.clientes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class CajaUnificada {

    public static void colas(int numClientes, int numCajas) {

        // Semáforo: permite hasta "numCajas" clientes simultáneos
        Semaphore cola = new Semaphore(numCajas);

        // Array de cajas libres/ocupadas con AtomicBoolean
        AtomicBoolean[] cajasLibres = new AtomicBoolean[numCajas];
        for (int i = 0; i < numCajas; i++) {
            cajasLibres[i] = new AtomicBoolean(true); // todas las cajas comienzan libres
        }

        List<Long> tiempos = new ArrayList<>();

        Thread[] clientes = new Thread[numClientes];

        System.out.println("MercaTrola abre sus puertas.");

        try {
            for (int i = 0; i < numClientes; i++) {
                clientes[i] = new Thread(
                        new ClienteUnificado(cola, tiempos, cajasLibres),
                        "Cliente " + i
                );
                clientes[i].start();
                Thread.sleep(50);
            }

            // Esperar a que terminen todos
            for (Thread cliente : clientes) {
                cliente.join();
            }

            System.out.println("MercaTrola cierra sus puertas.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Mostrar tiempos
        System.out.println("\n--- TIEMPOS REGISTRADOS ---");
        tiempos.forEach(System.out::println);
    }

    public static void main(String[] args) {
        colas(30, 10);
    }
}


