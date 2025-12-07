package caja.clientes;
import java.util.List;

public class Mediaydesvi {

    public long calcularmedia (List<Long> datospasados){
        int largo = datospasados.size();
        long suma = 0;

        // Suma todos los valores de la lista
        for (long l : datospasados){
            suma += l;
        }

        // Media aritmética simple
        return suma / largo;
    }


    public long calculardesviacion(List<Long> datospasados){

        int largo = datospasados.size();
        long media = calcularmedia(datospasados); // reutiliza el método de media
        long suma = 0;

        // Acumula la suma de (valor - media)^2 → varianza
        for (long l : datospasados){
            suma += Math.pow(l - media , 2);
        }

        // Calcula la raíz de la varianza → desviación típica
        return (long) Math.sqrt(suma/largo);
    }
}
