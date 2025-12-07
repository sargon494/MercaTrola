package caja.clientes.multiples;

import java.util.List;


public class ClientePorCaja implements Runnable {

    private final CajaPorCliente caja;
    private final List<Long> tiemposespera;   // Lista compartida para registrar esperas
    private List<Long> tiemposcompra;         // Lista compartida para registrar tiempo de compra

    public ClientePorCaja(CajaPorCliente caja, List<Long> tiemposespera, List <Long> tiemposcompra) {
        this.caja = caja;
        this.tiemposespera = tiemposespera;
        this.tiemposcompra = tiemposcompra;
    }

    @Override
    public void run() {
        // Se realiza toda la lógica en CajaPorCliente.
        caja.atenderClientes(Thread.currentThread().getName(), this.tiemposespera, tiemposcompra);
    }
}
