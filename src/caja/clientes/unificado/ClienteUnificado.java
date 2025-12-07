package caja.clientes.unificado;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClienteUnificado implements Runnable {

    private Semaphore cola;           // Semaforo que controla cuántos clientes pueden pasar a elegir caja
    private List<Long> tiempos;       // Tiempos siendo atendido
    private AtomicBoolean[] cajas;    // Indica qué cajas están libres/ocupadas
    private int cajaAsignada = 0;     // Índice de caja asignada
    private List<Long> esperas;       // Tiempos de espera hasta adquirir semáforo

    public ClienteUnificado(Semaphore cola, List<Long> tiempos, List<Long> esperas, AtomicBoolean[] cajas) {
        this.cola = cola;
        this.tiempos = tiempos;
        this.cajas = cajas;
        this.esperas = esperas;
    }

    @Override
    public void run() {
        long tllegada = System.currentTimeMillis(); // Marca el momento en el que llega el cliente
        long tStart = 0;
        long tEnd;
        long tespera = 0;

        try {
            cola.acquire(); // Punto de sincronización: el cliente espera en la cola unificada

            long tadquirido = System.currentTimeMillis(); 
            tespera = tadquirido - tllegada; // Tiempo esperando antes de acceder a una caja

            synchronized (esperas){
                esperas.add(tespera); // Registrar el tiempo de espera
            }

            // Selección de caja libre usando operaciones atómicas 
            for (int i = 0; i < cajas.length; i++) {
                if (cajas[i].compareAndSet(true, false)) { // Si la caja estaba libre → ocuparla
                    cajaAsignada = i;
                    break; // En cuanto consiga una, termina la búsqueda
                }
            }

            System.out.printf("%s asignado a caja %d%n", Thread.currentThread().getName(), cajaAsignada);

            tStart = System.currentTimeMillis(); // Inicio del tiempo siendo atendido

            Thread.sleep((long) (100 + Math.random() * 500)); // Simulación compra previa

            System.out.printf("%s realizando compra.%n", Thread.currentThread().getName());

            Thread.sleep((long) (100 + Math.random() * 500)); // Simulación proceso principal

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            tEnd = System.currentTimeMillis(); // Fin del tiempo siendo atendido

            cajas[cajaAsignada].set(true); // Liberar la caja asignada
            cola.release(); // Liberar un permiso del semáforo
        }

        long tTotal = tEnd - tStart; // Tiempo total siendo atendido

        System.out.printf("%s atendido. Tiempo de espera: %d ms , Tiempo siendo atendido:  %d ms %n ",
                Thread.currentThread().getName(), tespera , tTotal);

        synchronized (tiempos) {
            tiempos.add(tTotal); // Registrar el tiempo de atención
        }
    }
}
