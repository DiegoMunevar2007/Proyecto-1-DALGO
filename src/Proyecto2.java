import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Proyecto2 {

    public static int algormar(int[] pesos, int numeroJugadores, int numeroIntercambios) {
        int m = numeroIntercambios;
        int j = numeroJugadores;
        int[] dp = new int[(j + 1) * (m + 1)];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0; // dp[0][0] = 0

        for (int i = 0; i < pesos.length; i++) {
            int maxK = Math.min(j - 1, i);
            for (int k = maxK; k >= 0; k--) {
                int cost = i - k;
                if (cost > m) continue;
                int maxW = m - cost;
                if (maxW < 0) continue;

                int offsetK = k * (m + 1);
                int offsetKPlus1 = (k + 1) * (m + 1);

                for (int w = 0; w <= maxW; w++) {
                    int current = dp[offsetK + w];
                    if (current == Integer.MAX_VALUE) continue;
                    int newW = w + cost;
                    if (newW > m) continue;
                    if (dp[offsetKPlus1 + newW] > current + pesos[i]) {
                        dp[offsetKPlus1 + newW] = current + pesos[i];
                    }
                }
            }
        }

        int min = Integer.MAX_VALUE;
        int offsetJ = j * (m + 1);
        for (int w = 0; w <= m; w++) {
            if (dp[offsetJ + w] < min) {
                min = dp[offsetJ + w];
            }
        }
        return min;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("test_cases_2.txt"));

        int casos = sc.nextInt();
        long tiempoInicio = System.currentTimeMillis();
        for (int t = 0; t < casos; t++) {
            int n = sc.nextInt();
            int j = sc.nextInt();
            int m = sc.nextInt();
            int[] pesos = new int[n];
            for (int i = 0; i < n; i++) {
                pesos[i] = sc.nextInt();
            }
            int resultado = algormar(pesos, j, m);
            System.out.println(resultado);
        }
        sc.close();
        long tiempoFinal = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución: " + (tiempoFinal - tiempoInicio) + " milisegundos");
    }
}