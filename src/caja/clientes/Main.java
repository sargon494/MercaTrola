package caja.clientes;

import caja.clientes.multiples.SimuladorColasMultiples;
import caja.clientes.unificado.CajaUnificada;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main() {
        CajaUnificada cu = new CajaUnificada();
        SimuladorColasMultiples sm = new SimuladorColasMultiples();
        int numClientes = 0;
        int numCajas = 0;

        Scanner sc = new Scanner(System.in);
        int option = 0;
        while (option != 5) {

            System.out.println("=== SIMULADOR DE COLAS ===");
            System.out.println("1. - Colas Multiples");
            System.out.println("2. - Cola Unificada");
            System.out.println("3. - Comparación de los dos metodos de colas");
            System.out.println("4. - Comparación 50 iteraciones.");
            System.out.println("5. - Salir");

            option = sc.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Introduce el número de clientes.");
                    numClientes = sc.nextInt();
                    System.out.println("Introduce el numero de cajas");
                    numCajas = sc.nextInt();
                    sm.simular(numClientes,numCajas);
                    break;

                case 2:
                    System.out.println("Introduce el número de clientes.");
                    numClientes = sc.nextInt();
                    System.out.println("Introduce el numero de cajas");
                    numCajas = sc.nextInt();
                    cu.colas(numClientes,numCajas);
                    break;

                case 3:
                    sm.simular(500, 10);
                    long mediaMultiples = sm.getMedia();
                    cu.colas(500,10);
                    long mediaUni = cu.getMedia();

                    if (mediaMultiples > mediaUni) {
                        System.out.println("El sistema de múltiples colas es más eficiente que el de una cola unificada.");
                    } else {
                        System.out.println("El sistema de una cola unificada es más eficiente que el de múltiples colas");
                    }
                    break;

                case 4:
                    int MM = 0;
                    int MU = 0;
                    long desviacionesMultiples;
                    long desviacionesUnificadas;

                    List<Long> totalMediasMultiples = new ArrayList<>();
                    List<Long> totalDesviacionesMultiples = new ArrayList<>();

                    List<Long> totalMediasUnificadas = new ArrayList<>();
                    List<Long> totalDesviacionesUnificadas = new ArrayList<>();

                    for (int i = 0 ; i < 50; i++) {
                        sm.simular(500, 10);
                        mediaMultiples = sm.getMedia();
                        totalMediasMultiples.add(mediaMultiples);

                        desviacionesMultiples = sm.getDesviacion();
                        totalDesviacionesMultiples.add(desviacionesMultiples);

                        cu.colas(500,10);
                        mediaUni = cu.getMedia();
                        totalMediasUnificadas.add(mediaUni);

                        desviacionesUnificadas = cu.getDesviacion();
                        totalDesviacionesUnificadas.add(desviacionesUnificadas);

                        if (mediaMultiples > mediaUni) {
                            MM++;
                        } else {
                            MU++;
                        }
                    }
                    System.out.println(totalMediasMultiples);
                    System.out.println(totalMediasUnificadas);

                    if (MM > MU) {
                        System.out.println("La cola múltiple ha resultado más eficiente tras 50 iteraciones");
                    } else {
                        System.out.println("La cola unificada ha resultado más eficiente tras 50 iteraciones");
                    }

                case 5:
                    System.out.println("Saliendo");
                    System.exit(0);

                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }
}
