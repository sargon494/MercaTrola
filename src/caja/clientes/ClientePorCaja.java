package caja.clientes;

import java.util.List;

// Hilo que representa un cliente que usa la cola de una caja específica.
public class ClientePorCaja implements Runnable {

    private final CajaPorCliente caja;
    private final List<Long> tiempos;

    public ClientePorCaja(CajaPorCliente caja, List<Long> tiempos) {
        this.caja = caja;
        this.tiempos = tiempos;
    }

    @Override
    public void run() {
        long inicio = System.currentTimeMillis();

        // El cliente intenta usar la caja. Bloquea si está ocupada.
        caja.atenderClientes(Thread.currentThread().getName());

        long fin = System.currentTimeMillis();
        long tiempoEspera = fin - inicio;

        synchronized (tiempos) {
            tiempos.add(tiempoEspera);
        }

        System.out.printf("%s atendido. Tiempo total de espera: %d ms%n", Thread.currentThread().getName(), tiempoEspera);
    }
}
