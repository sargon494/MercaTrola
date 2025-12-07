package caja.clientes;

import caja.clientes.multiples.CajaPorCliente;

import java.util.concurrent.Semaphore;

public class Clientes{
    private int idCliente;
    private CajaPorCliente numCaja;
    private Semaphore cola;

    public Clientes(int idCliente, CajaPorCliente caja){
        this.idCliente = idCliente;
        this.numCaja = caja;
    }
}
