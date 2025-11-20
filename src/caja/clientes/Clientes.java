package caja.clientes;

public class Clientes  extends Thread{
    private int idCliente;
    private long tiempoEspera;
    private CajaPorCliente numCaja;

    public Clientes(int idCliente, CajaPorCliente caja){
        this.idCliente = idCliente;
        this.numCaja = caja;
    }


    @Override
    public void run(){
        long llegada = System.currentTimeMillis();

        numCaja.atenderClientes(this);

        long salida = System.currentTimeMillis();
        this.tiempoEspera = salida - llegada;

    }


    public int getIdCliente(){
        return idCliente;
    }
}
