import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Proyecto {



    public static int algormar(int[] pesos, int numeroJugadores, int numeroIntercambios) {

        Estado[] dp = new Estado[numeroJugadores + 1]; // Estado es una combinación de peso y swap que era lo que usaba DP[][] en la versión anterior
        for (int k = 0; k <= numeroJugadores; k++) { //Almacena el mejor estado (mínimo peso y swap acumulado) al haber seleccionado k jugadores
            dp[k] = new Estado(Integer.MAX_VALUE, Long.MAX_VALUE); //Inicializamos todos los estados como infinito
        }
        dp[0] = new Estado(0, 0); // Estado base: 0 jugadores, 0 peso, 0 swaps.

        for (int i = 0; i < pesos.length; i++) { // Recorremos cada jugador en orden

            for (int k = numeroJugadores - 1; k >= 0; k--) { // Recorremos en orden inverso para no usar al mismo jugador dos veces

                if (dp[k].getPeso() != Long.MAX_VALUE) { // Si el estado con k jugadores es válido: (No es infinito)

                    long nuevoSwap = dp[k].getSwap() + (i - k); // Al agregar al jugador i como el (k+1)-ésimo, se usan (i - k) swaps adicionales

                    if (nuevoSwap <= numeroIntercambios) { // Verificamos que no se exceda el límite
                        long nuevoPeso = dp[k].getPeso() + pesos[i];
                        if (nuevoPeso < dp[k + 1].getPeso()) {
                            dp[k + 1] = new Estado((int) nuevoSwap, nuevoPeso); // Actualizamos dp[k+1] si encontramos un estado con menor peso
                        }
                    }
                }
            }
        }
        int minimo = Integer.MAX_VALUE;
        for (int w = 0; w <= numeroIntercambios; w++) {
            minimo = (int) Math.min(minimo, dp[numeroJugadores].getPeso());
        }
        Long tiempoFinal = System.currentTimeMillis();
        return minimo;
    }


    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("test_cases_2.txt"));
        Long tiempoInicio = System.currentTimeMillis();
        // Numero de casos de prueba
        int casos = sc.nextInt();
        // Procesar cada caso de prueba

        for (int t = 0; t < casos; t++) {
            int n = sc.nextInt();      // Numero de jugadores
            int j = sc.nextInt();      // Numero de jugadores a seleccionar
            int m = sc.nextInt();      // Swaaaaaaaaaaaaps
            int[] pesos = new int[n];

            for (int i = 0; i < n; i++) {
                pesos[i] = sc.nextInt();
            }
            int resultado = algormar(pesos, j, m);
            // Imprimir el resultado
            System.out.println((int) resultado);
        }
        sc.close();
        Long tiempoFinal = System.currentTimeMillis();
        System.out.println("Tiempo total: " + (tiempoFinal - tiempoInicio) + " ms");

    }
}