package caja.clientes;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClienteUnificado implements Runnable {

    private Semaphore cola;
    private List<Long> tiempos;
    private AtomicBoolean[] cajas;
    private int cajaAsignada = 0;
    private List<Long> esperas;

    public ClienteUnificado(Semaphore cola, List<Long> tiempos, List<Long> esperas, AtomicBoolean[] cajas) {
        this.cola = cola;
        this.tiempos = tiempos;
        this.cajas = cajas;
        this.esperas = esperas;
    }

    @Override
    public void run() {
        long tllegada = System.currentTimeMillis();
        long tStart = 0;
        long tEnd;
        long tespera = 0;

        try {
            cola.acquire();
            long tadquirido = System.currentTimeMillis();
            tespera = tadquirido - tllegada;

            synchronized (esperas){
                esperas.add(tespera);
            }

            for (int i = 0; i < cajas.length; i++) {
                if (cajas[i].compareAndSet(true, false)) {
                    cajaAsignada = i;
                    break;
                }
            }

            System.out.printf("%s asignado a caja %d%n", Thread.currentThread().getName(), cajaAsignada);

            tStart = System.currentTimeMillis();

            Thread.sleep((long) (100 + Math.random() * 500));

            System.out.printf("%s realizando compra.%n", Thread.currentThread().getName());

            Thread.sleep((long) (100 + Math.random() * 500));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            tEnd = System.currentTimeMillis();
            cajas[cajaAsignada].set(true);
            cola.release();
        }

        long tTotal = tEnd - tStart;
        System.out.printf("%s atendido. Tiempo de espera: %d ms , Tiempo siendo atendido:  %d ms %n ", Thread.currentThread().getName(), tespera , tTotal);

        synchronized (tiempos) {
            tiempos.add(tTotal);
        }
    }
}
