package caja.clientes;
import java.util.List;

public class Mediaydesvi {

    public long calcularmedia (List<Long> datospasados){
        int largo = datospasados.size();
        long suma = 0;
        for (long l : datospasados){
            suma += l;
        }

        return suma / largo;
    }


    public long calculardesviacion(List<Long> datospasados){

        int largo = datospasados.size();
        long media = calcularmedia(datospasados);
        long suma = 0;

        for (long l : datospasados){
            suma += Math.pow(l - media , 2);
        }

        return (long) Math.sqrt(suma/largo);
    }
}


