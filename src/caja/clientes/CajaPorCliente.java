package caja.clientes;

import java.util.Random;

public class CajaPorCliente {
    private int idCaja;

    public CajaPorCliente(int caja) {
        this.idCaja = caja;
    }

    public synchronized void atenderClientes(Clientes cliente){
        try {
            System.out.println("Cliente " + Thread.currentThread().getName() + " está siendo atendido en la caja: " + idCaja);
            Thread.sleep(new Random().nextInt(500) + 100);
            System.out.println("Cliente " + Thread.currentThread().getName() + " ha terminado en la caja: " + idCaja);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
