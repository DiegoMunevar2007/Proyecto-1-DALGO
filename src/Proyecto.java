import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Proyecto {



    // Java
    public static int algormar(int[] pesos, int numeroJugadores, int numeroIntercambios) {
        // dp[k][s] holds the best state (swaps and weight) for selecting k players with s swaps.
        Estado[][] dp = new Estado[numeroJugadores + 1][numeroIntercambios + 1];

        // Initialize states to infinity.
        for (int k = 0; k <= numeroJugadores; k++) {
            for (int s = 0; s <= numeroIntercambios; s++) {
                dp[k][s] = new Estado(Integer.MAX_VALUE, Long.MAX_VALUE);
            }
        }
        dp[0][0] = new Estado(0, 0); // Base state: 0 players, 0 swaps, 0 weight.

        // Iterate over players.
        for (int i = 0; i < pesos.length; i++) {
            // Traverse number of players selected in reverse order.
            for (int k = numeroJugadores - 1; k >= 0; k--) {
                // Iterate over possible swap counts.
                for (int s = 0; s <= numeroIntercambios; s++) {
                    if (dp[k][s].getPeso() != Long.MAX_VALUE) {  // Valid state.
                        int nuevoSwap = s + (i - k); // Additional swaps needed.
                        if (nuevoSwap <= numeroIntercambios) {
                            long nuevoPeso = dp[k][s].getPeso() + pesos[i];
                            // If new state has a lower weight, update dp.
                            if (nuevoPeso < dp[k + 1][nuevoSwap].getPeso()) {
                                dp[k + 1][nuevoSwap] = new Estado(nuevoSwap, nuevoPeso);
                            }
                        }
                    }
                }
            }
        }

        long minimo = Long.MAX_VALUE;
        // Find the minimum weight among all valid states selecting the required number of players.
        for (int s = 0; s <= numeroIntercambios; s++) {
            minimo = Math.min(minimo, dp[numeroJugadores][s].getPeso());
        }

        return (int)minimo;
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