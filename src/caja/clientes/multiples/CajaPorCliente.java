package caja.clientes.multiples;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class CajaPorCliente {
    private final int idCaja;
    private final Semaphore cola;
    private final Random rnd = new Random();

    public CajaPorCliente(int idCaja) {
        this.idCaja = idCaja;
        this.cola  = new Semaphore(1, true); // semáforo justo
    }

    // Ahora recibe listas para guardar tiempos
    public void atenderClientes(String nombreCliente, List<Long> esperas, List<Long> tiemposCompra) {
        long llegada = System.currentTimeMillis();

        try {
            // Espera para entrar en la caja
            cola.acquire();
            long inicioAtencion = System.currentTimeMillis();

            long tiempoEspera = inicioAtencion - llegada;

            // Guardamos tiempo de espera
            synchronized (esperas) {
                esperas.add(tiempoEspera);
            }

            System.out.printf("%s está siendo atendido en la caja %d %n", nombreCliente, idCaja);

            // Simular tiempo de atención / compra
            long inicioCompra = System.currentTimeMillis();
            Thread.sleep(100 + rnd.nextInt(500));
            long finCompra = System.currentTimeMillis();

            long tiempoCompra = finCompra - inicioCompra;

            // Guardamos tiempo de compra
            synchronized (tiemposCompra) {
                tiemposCompra.add(tiempoCompra);
            }

            System.out.printf("%s ha terminado en la caja %d (esperó %d ms)%n", nombreCliente, idCaja, tiempoEspera);

        } catch (InterruptedException e ) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } finally {
            cola.release();
        }
    }
}