package caja.clientes.multiples;

import java.util.List;


public class ClientePorCaja implements Runnable {

    private final CajaPorCliente caja;
    private final List<Long> tiemposespera;
    private List<Long> tiemposcompra;

    public ClientePorCaja(CajaPorCliente caja, List<Long> tiemposespera, List <Long> tiemposcompra) {
        this.caja = caja;
        this.tiemposespera = tiemposespera;
        this.tiemposcompra = tiemposcompra;
    }

    @Override
    public void run() {
        caja.atenderClientes(Thread.currentThread().getName(), this.tiemposespera, tiemposcompra);
    }
}
