package caja.clientes.multiples;

import java.util.ArrayList;
import java.util.List;

// Clase que realiza la simulación de varias cajas con su propia cola independiente.
public class SimuladorColasMultiples {

    public static void simular(int numClientes, int numCajas) {

        System.out.println("MercaTrola abre sus puertas.");

        // Lista de cajas (cada una con su propia cola)
        CajaPorCliente[] cajas = new CajaPorCliente[numCajas];

        for (int i = 0; i < numCajas; i++) {
            cajas[i] = new CajaPorCliente(i);
        }

        // Registro de tiempos de espera
        List<Long> tiempos = new ArrayList<>();

        Thread[] clientes = new Thread[numClientes];

        // Asignación de clientes en cajas.
        for (int i = 0; i < numClientes; i++) {
            CajaPorCliente cajaElegida = cajas[i % numCajas];

            clientes[i] = new Thread(
                    new ClientePorCaja(cajaElegida, tiempos),
                    "Cliente " + i
            );

            clientes[i].start();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Esperar a que todos terminen
        for (Thread cliente : clientes) {
            try {
                cliente.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("MercaTrola cierra sus puertas.");

        // Mostrar tiempos registrados
        System.out.println("\n--- TIEMPOS REGISTRADOS ---");
        tiempos.forEach(System.out::println);
    }

    public static void main(String[] args) {
        simular(30, 10);
    }
}
