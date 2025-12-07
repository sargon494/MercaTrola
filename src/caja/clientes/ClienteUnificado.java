package caja.clientes;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClienteUnificado implements Runnable {

    private Semaphore cola;
    private final List<Long> tiempos;
    private AtomicBoolean[] cajas;
    private int cajaAsignada = 0;

    public ClienteUnificado(Semaphore cola, List<Long> tiempos, AtomicBoolean[] cajas) {
        this.cola = cola;
        this.tiempos = tiempos;
        this.cajas = cajas;
    }

    @Override
    public void run() {
        long tStart = 0;
        long tEnd;

        try {
            cola.acquire();
            for (int i = 0; i < cajas.length; i++) {
                if (cajas[i].compareAndSet(true, false)) {
                    cajaAsignada = i;
                    break;
                }
            }

            System.out.printf("%s asignado a caja %d%n", Thread.currentThread().getName(), cajaAsignada);

            tStart = System.currentTimeMillis();

            Thread.sleep((long) (1000 + Math.random() * 5000));

            System.out.printf("%s realizando compra.%n", Thread.currentThread().getName());

            Thread.sleep((long) (1000 + Math.random() * 5000));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            tEnd = System.currentTimeMillis();
            cajas[cajaAsignada].set(true);
            cola.release();
        }

        long tTotal = tEnd - tStart;
        System.out.printf("%s atendido. Tiempo de espera: %d ms%n", Thread.currentThread().getName(), tTotal);

        synchronized (tiempos) {
            tiempos.add(tTotal);
        }
    }
}
