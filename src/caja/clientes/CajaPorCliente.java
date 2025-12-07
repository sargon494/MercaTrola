package caja.clientes;

import java.util.Random;
import java.util.concurrent.Semaphore;

// Clase que representa una única caja con su propia cola en la que solo se atiende a 1 cliente a la vez.
public class CajaPorCliente {
    private final int idCaja;
    private final Semaphore cola;
    private final Random rnd = new Random();

    public CajaPorCliente(int idCaja) {
        this.idCaja = idCaja;
        this.cola  = new Semaphore(1, true); // Semáforo justo para evitar inanición.
    }

    public void atenderClientes(String nombreCliente){
       try {
           // El cliente entra en la cola.
           cola.acquire();

           System.out.printf("%s está siendo atendido en la caja %d%n", nombreCliente, idCaja);

           // Espera para simular la compra.
           Thread.sleep(100 + rnd.nextInt(500));

           System.out.printf("%s ha terminado en la caja %d%n", nombreCliente, idCaja);
       } catch (InterruptedException e ) {
           Thread.currentThread().interrupt();
           throw new RuntimeException(e);
       } finally {
           // Libera la caja para el siguiente cliente.
           cola.release();
       }
    }
}
