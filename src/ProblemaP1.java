import java.util.*;
public class ProblemaP1 {

     public static int algormar(int[] pesos, int numeroJugadores, int numeroIntercambios) {
         int[][] dp = new int[numeroJugadores+1][numeroIntercambios + 1];
         for (int i = 0; i <= numeroJugadores; i++) {
             Arrays.fill(dp[i], Integer.MAX_VALUE);
         }
         // Caso donde los swaps son suficientes para ordenar todos los jugadores
         // Cota: Cantidad de swaps para bubble sort
         if (numeroIntercambios >= (pesos.length * pesos.length)) {
            ArrayList<Integer> lista = new ArrayList<Integer>();
            for (int i = 0; i < pesos.length; i++) {
                lista.add(pesos[i]);
            }
            Collections.sort(lista);
            int suma = 0;
            for (int i = 0; i < numeroJugadores; i++) {
                suma += lista.get(i);
            }
            return suma;
         }
         // Caso DP
         dp[0][0] = 0;
         for (int i = 0; i < pesos.length; i++) { // Recorrer los jugadores en orden
             // Recorremos el número de jugadores ya seleccionados en orden inverso:
             for (int k = Math.min(numeroJugadores - 1, i); k >= 0; k--) { // Tomamos el mínimo entre el número de jugadores y el índice actual para evitar mirar jugadores que no hemos visto.
                // Esto significa que no podemos seleccionar más jugadores que los que hemos visto.
                // Si el número de jugadores que hemos visto es menor que el número de jugadores que queremos seleccionar, no podemos seleccionar a nadie.
                if ((pesos.length - i) >= (numeroJugadores - k) ) {

                    int costo = i - k; // El costo de seleccionar al jugador i como el (k+1)-ésimo.
                    // Si el jugador es inalcanzable, no lo consideramos
                    if (costo <= numeroIntercambios) {
                        // Iteramos sobre los swaps posibles (w) para el jugador i
                        for (int w = 0; w <= numeroIntercambios - costo; w++) {
                            if (dp[k][w] != Integer.MAX_VALUE) {
                                int nuevoSwap = w + costo;
                                int nuevoCosto = dp[k][w] + pesos[i];
                                dp[k + 1][nuevoSwap] = Math.min(dp[k + 1][nuevoSwap], nuevoCosto);
                            }
                        }
                    }
                }
             }
         }
         
         int minimo = Integer.MAX_VALUE;
         for (int candidato: dp[numeroJugadores]){
             minimo = Math.min(minimo, candidato);
         }
         return minimo;
     }
 
 
     public static void main(String[] args) {
         Scanner sc = new Scanner(System.in);
         // Numero de casos de prueba
         int casos = sc.nextInt();
         // Procesar cada caso de prueba
        ArrayList<int[]> lista = new ArrayList<int[]>();
        ArrayList<Integer> listaJugadores = new ArrayList<Integer>();
        ArrayList<Integer> listaIntercambios = new ArrayList<Integer>();
         for (int t = 0; t < casos; t++) {
             int n = sc.nextInt();      // Numero de jugadores
             int j = sc.nextInt();      // Numero de jugadores a seleccionar
             int m = sc.nextInt();      // Swaaaaaaaaaaaaps
             int[] pesos = new int[n];
 
             for (int i = 0; i < n; i++) {
                 pesos[i] = sc.nextInt();
             }
                lista.add(pesos);
                listaJugadores.add(j);
                listaIntercambios.add(m);
         }
        for (int i = 0; i < casos; i++) {
            System.out.println(algormar(lista.get(i), listaJugadores.get(i), listaIntercambios.get(i)));  
     }
     sc.close();
 }
}