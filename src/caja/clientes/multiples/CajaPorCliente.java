package caja.clientes.multiples;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class CajaPorCliente {
    private final int idCaja;
    private final Semaphore cola;          // Semáforo que garantiza un único cliente atendido
    private final Random rnd = new Random();

    public CajaPorCliente(int idCaja) {
        this.idCaja = idCaja;
        this.cola  = new Semaphore(1, true); // Semáforo justo que evita inanición
    }

    // Método que gestiona tiempos de espera y atención
    public void atenderClientes(String nombreCliente, List<Long> esperas, List<Long> tiemposCompra) {
        long llegada = System.currentTimeMillis(); // Instante en que el cliente trata de usar la caja

        try {
            cola.acquire(); // Si la caja está ocupada, el cliente espera aquí
            long inicioAtencion = System.currentTimeMillis();

            long tiempoEspera = inicioAtencion - llegada;  // Tiempo en cola propia de la caja

            synchronized (esperas) {
                esperas.add(tiempoEspera); // Registrar espera en la cola de esa caja
            }

            System.out.printf("%s está siendo atendido en la caja %d %n", nombreCliente, idCaja);

            long inicioCompra = System.currentTimeMillis(); // Inicio del tiempo siendo atendido
            Thread.sleep(100 + rnd.nextInt(500)); // Simulación de compra
            Thread.sleep(100 + rnd.nextInt(500)); // Finalización de compra.
            long finCompra = System.currentTimeMillis();

            long tiempoCompra = finCompra - inicioCompra; // Duración de la compra

            synchronized (tiemposCompra) {
                tiemposCompra.add(tiempoCompra); // Registrar tiempo de servicio
            }

            System.out.printf("%s ha terminado en la caja %d (esperó %d ms)%n", nombreCliente, idCaja, tiempoEspera);

        } catch (InterruptedException e ) {
            Thread.currentThread().interrupt(); 
            throw new RuntimeException(e);
        } finally {
            cola.release(); // Libera la caja para el siguiente cliente
        }
    }
}
