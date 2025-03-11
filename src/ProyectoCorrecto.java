
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import static java.util.Collections.min;

public class ProyectoCorrecto {

    /**
     * Metodo para limpiar la lista de estados
     * Mira los pesos y los swaps para eliminar aquellos que no son necesarios dado a que ya existen otros mejores
     * Ya sea con la misma cantidad de swaps y menor peso o con menor cantidad de swaps
     * @param estados
     * @return La lista de estados limpiada
     */
    public static List<Estado> obtenerEstadosNoServibles(List<Estado> estados) {
        if (estados.isEmpty()) {
            return estados;
        }
        List<Estado> limpiados = new ArrayList<>();
        for (Estado estado: estados) {
            // Si la lista esta vacia o el peso del estado actual es menor al peso del ultimo estado en la lista
            if (limpiados.isEmpty() || estado.getPeso() < limpiados.get(limpiados.size() - 1).getPeso()) {
                limpiados.add(estado);
            }
        }
        return limpiados;
    }

    /**
     * Metodo para encontrar el peso minimo entre los estados entre el numero de jugadores dado y el numero de swaps.
     * @param pesos
     * @param numeroJugadores
     * @param numeroIntercambios
     * @return
     */
    public static int algormar(int[] pesos, int numeroJugadores, int numeroIntercambios) {
        // Hace una matriz de estados, donde el numero de jugadores es el numero de filas
        // y el numero de columnas es el numero de swaps que puede hacer
        List<Estado> dp[] = new ArrayList[numeroJugadores + 1];

        for (int i = 0; i <= numeroJugadores; i++) {
            dp[i] = new ArrayList<>();
        }
        // Estado base: 0 jugadores seleccionados, 0 swaps y 0 peso.
        int minimo = Integer.MAX_VALUE;
        dp[0].add(new Estado(0, 0));

        for (int i = 0; i < pesos.length; i++) { // recorrer los jugadores en orden
            // Recorremos el número de jugadores ya seleccionados en orden inverso:
            for (int k = Math.min(numeroJugadores - 1, i); k >= 0; k--) { // Tomamos el mínimo entre el número de jugadores y el índice actual.
                 // El costo de seleccionar al jugador i como el (k+1)-ésimo.
                int costo = i - k;
                // Los estados posibles para el número de jugadores k+1.
                for (Estado estado : dp[k]) {
                    // Si el número de swaps supera el límite, no se considera.
                    int nuevosSwaps = estado.getSwap() + costo;
                    if (nuevosSwaps > numeroIntercambios) {
                        continue;
                    }
                    int nuevoPeso = (int) (estado.getPeso() + pesos[i]);
                    Estado nuevoEstado = new Estado(nuevosSwaps, nuevoPeso);
                    dp[k + 1].add(nuevoEstado);
                }
            }
            // Limpiar la lista de estados para cada número de jugadores para dejar solo los estados que nos sirven.
            for (int k = 0; k <= numeroJugadores; k++) {
                dp[k] = obtenerEstadosNoServibles(dp[k]);
            }
        }
        // Encontrar el peso mínimo entre los estados finales.
        for (Estado estado : dp[numeroJugadores]) {
            minimo = (int) Math.min(minimo, estado.getPeso());
        }
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
            System.out.println((int) resultado);
        }
        sc.close();
        Long tiempoFinal = System.currentTimeMillis();
        System.out.println("Tiempo total: " + (tiempoFinal - tiempoInicio) + " ms");

    }
}
