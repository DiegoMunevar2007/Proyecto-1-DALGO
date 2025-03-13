 import java.io.File;
 import java.io.FileNotFoundException;
 import java.util.*;
 import static java.util.Collections.min;
 
 public class ProyectoAntiguo {
 
 
 
     public static int algormar(int[] pesos, int numeroJugadores, int numeroIntercambios) {
         Long tiempoInicio = System.currentTimeMillis();
         int[][] dp = new int[numeroJugadores+1][numeroIntercambios + 1];
         for (int i = 0; i <= numeroJugadores; i++) {
             Arrays.fill(dp[i], Integer.MAX_VALUE);
         }
         if (numeroIntercambios>= (numeroJugadores*(pesos.length*numeroJugadores))){
            ArrayList<Integer> lista = new ArrayList<Integer>();
            for (int i = 0; i < pesos.length; i++) {
                lista.add(pesos[i]);
            }
            Collections.sort(lista);
            int suma = 0;
            for (int i = 0; i < numeroJugadores; i++) {
                suma += lista.get(i);
            }
            Long tiempoFinal = System.currentTimeMillis();
            return suma;
         }
         dp[0][0] = 0;
         for (int i = 0; i < pesos.length; i++) { // recorrer los jugadores en orden
             // Recorremos el número de jugadores ya seleccionados en orden inverso:
             for (int k = Math.min(numeroJugadores - 1, i); k >= 0; k--) { // Tomamos el mínimo entre el número de jugadores y el índice actual.
                // Esto significa que no podemos seleccionar más jugadores que los que hemos visto.
                // Si el número de jugadores que hemos visto es menor que el número de jugadores que queremos seleccionar, no podemos seleccionar a nadie.
                if ((pesos.length - i) >= (numeroJugadores - k) ) {
                    
                    int costo = i - k; // El costo de seleccionar al jugador i como el (k+1)-ésimo.
                    if (costo <= numeroIntercambios) {
                    
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
         Long tiempoFinal = System.currentTimeMillis();
         return minimo;
     }
 
 
     public static void main(String[] args) throws FileNotFoundException {
         Scanner sc = new Scanner(new File("archivo.txt"));
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
             System.out.println(Integer.toString(t)+" "+Integer.toString(resultado));
         }
         sc.close();
         Long tiempoFinal = System.currentTimeMillis();
         System.out.println("Tiempo total: " + (tiempoFinal - tiempoInicio) + " ms");
 
     }
 }