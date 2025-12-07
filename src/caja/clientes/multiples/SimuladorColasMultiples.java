package caja.clientes.multiples;

import caja.clientes.Mediaydesvi;

import java.util.ArrayList;
import java.util.List;

// Clase que realiza la simulación de varias cajas con su propia cola independiente.
public class SimuladorColasMultiples {
    private long media;
    private long desviacion;

    public  void simular(int numClientes, int numCajas) {

        System.out.println("MercaTrola abre sus puertas.");

        // Lista de cajas (cada una con su propia cola)
        CajaPorCliente[] cajas = new CajaPorCliente[numCajas];

        for (int i = 0; i < numCajas; i++) {
            cajas[i] = new CajaPorCliente(i); // Cada caja tiene su propio semáforo
        }

        // Registro de tiempos de espera
        List<Long> tiemposespera = new ArrayList<>(); // Esperas antes de entrar a su caja
        List<Long> tiemposcompra = new ArrayList<>(); // Tiempos siendo atendidos

        Thread[] clientes = new Thread[numClientes];

        // Asignación de clientes en cajas.
        for (int i = 0; i < numClientes; i++) {
            CajaPorCliente cajaElegida = cajas[i % numCajas];
            clientes[i] = new Thread(
                    new ClientePorCaja(cajaElegida, tiemposespera, tiemposcompra), "Cliente " + i);
            clientes[i].start();

            try {
                Thread.sleep(50); // Pequeña pausa para simular llegadas intermitentes
            } catch (InterruptedException e) {}
        }

        // Esperar a que todos terminen
        for (Thread cliente : clientes) {
            try {
                cliente.join();
            } catch (InterruptedException e) {}
        }

        System.out.println("MercaTrola cierra sus puertas.");
        System.out.println();

        // Cálculo de media y desviación sobre los tiempos de espera
        Mediaydesvi datosmedia = new Mediaydesvi();
        media = datosmedia.calcularmedia(tiemposespera);
        System.out.println("Tiempo medio de espera : " + media + "ms");
        desviacion = datosmedia.calculardesviacion(tiemposespera);
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