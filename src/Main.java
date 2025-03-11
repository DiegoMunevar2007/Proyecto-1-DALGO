import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    // A helper class to store the weight and original position.
    static class Element implements Comparable<Element> {
        int weight;
        int pos;

        Element(int weight, int pos) {
            this.weight = weight;
            this.pos = pos;
        }

        @Override
        public int compareTo(Element other) {
            if (this.weight != other.weight) {
                return Integer.compare(this.weight, other.weight);
            }
            return Integer.compare(this.pos, other.pos);
        }
    }

    public static void main(String[] args) {
        try {
            // Open the file "texto.txt"
            Scanner sc = new Scanner(new File("test_cases_2.txt"));
            Long tiempoInicio = System.currentTimeMillis();
            int T = sc.nextInt();
            for (int t = 0; t < T; t++) {
                int n = sc.nextInt();
                int j = sc.nextInt();
                int m = sc.nextInt();

                int[] pesos = new int[n];
                for (int i = 0; i < n; i++) {
                    pesos[i] = sc.nextInt();
                }

                // Create an array of Element objects with weight and original index.
                Element[] elements = new Element[n];
                for (int i = 0; i < n; i++) {
                    elements[i] = new Element(pesos[i], i);
                }
                // Sort by weight and then by original index.
                Arrays.sort(elements);

                // Use a large number to simulate infinity.
                long INF = Long.MAX_VALUE / 2;
                long[] dp = new long[j + 1];
                Arrays.fill(dp, INF);
                dp[0] = 0;

                // Dynamic programming: for each element update the dp table.
                for (Element el : elements) {
                    // Process in reverse order to avoid overwriting needed dp values.
                    for (int k = j; k >= 1; k--) {
                        if (dp[k - 1] != INF && dp[k - 1] + el.pos < dp[k]) {
                            dp[k] = dp[k - 1] + el.pos;
                        }
                    }
                }

                // Calculate the required swap sum.
                long requiredSwapSum = dp[j] - (long) j * (j - 1) / 2;
                long total = INF;

                if (requiredSwapSum <= m) {
                    // If condition holds, sum the weights of the first j elements.
                    total = 0;
                    for (int i = 0; i < j; i++) {
                        total += elements[i].weight;
                    }
                } else {
                    // Otherwise, try to find a valid solution by considering different k values.
                    for (int k = j; k >= 1; k--) {
                        if (dp[k] <= m + (long) k * (k - 1) / 2) {
                            long candidate = 0;
                            // Sum weights of the first k elements.
                            for (int i = 0; i < k; i++) {
                                candidate += elements[i].weight;
                            }

                            int remaining = j - k;
                            if (remaining > 0) {
                                int ptr = k;
                                int count = 0;
                                // Find the maximum original position among the first k elements.
                                int lastPos = -1;
                                for (int i = 0; i < k; i++) {
                                    if (elements[i].pos > lastPos) {
                                        lastPos = elements[i].pos;
                                    }
                                }
                                // Try to add additional elements (keeping original order)
                                while (ptr < n && count < remaining) {
                                    if (elements[ptr].pos > lastPos) {
                                        candidate += elements[ptr].weight;
                                        lastPos = elements[ptr].pos;
                                        count++;
                                    }
                                    ptr++;
                                }
                                if (count == remaining) {
                                    total = candidate;
                                    break;
                                }
                            } else {
                                total = candidate;
                                break;
                            }
                        }
                    }
                    if (total == INF) {
                        total = 0;
                    }
                }
                System.out.println(total);
            }
            sc.close();
            Long tiempoFinal = System.currentTimeMillis();
            System.out.println("Tiempo de ejecución: " + (tiempoFinal - tiempoInicio) + " milisegundos");
        } catch (FileNotFoundException e) {
            System.err.println("File texto.txt not found.");
        }
    }
}
