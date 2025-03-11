import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.util.Collections.min;

public class Proyecto {

    private static ArrayList<Integer> segundos = new ArrayList<>();
    public static int algormar(int[] pesos, int numeroJugadores, int numeroIntercambios) {
        Long tiempoInicio = System.currentTimeMillis();
        int[][] dp = new int[numeroJugadores+1][numeroIntercambios + 1];
        for (int i = 0; i <= numeroJugadores; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][0] = 0;
        for (int i = 0; i < pesos.length; i++) { // recorrer los jugadores en orden
            // Recorremos el número de jugadores ya seleccionados en orden inverso:
            for (int k = Math.min(numeroJugadores - 1, i); k >= 0; k--) { // Tomamos el mínimo entre el número de jugadores y el índice actual.

                int costo = i - k;  // El costo de asignar el jugador i en la posición k es (i - k)
                int maxW = numeroIntercambios - costo; // Solamente podemos hacer intercambios en el rango de 0 a numeroIntercambios - costo
                if (maxW < 0) {
                    continue;
                }
                for (int w = 0; w <= maxW; w++) { // Iterar por toda la cantidad de intercambios posibles
                    if (dp[k][w] < Integer.MAX_VALUE) {
                        int candidato = dp[k][w] + pesos[i];
                        if (candidato < dp[k+1][w + costo]) {
                            dp[k+1][w + costo] = candidato;
                        }
                    }
                }
            }
        }
        int minimo = Integer.MAX_VALUE;
        for (int candidato: dp[numeroJugadores]){
            minimo = Math.min(minimo, candidato);
        }
        Long tiempoFinal = System.currentTimeMillis();
        segundos.add((int) (tiempoFinal - tiempoInicio));
        return minimo;
    }


    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("test_cases_2.txt"));

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
        System.out.println(Arrays.toString(segundos.toArray()));
        sc.close();

    }
}