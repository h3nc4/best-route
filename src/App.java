import java.util.LinkedList;
import java.util.List;

import algoritmos.*;

/**
 * Classe principal do projeto, usada para realizar os múltiplos casos de teste
 */
public class App {
    private static List<List<int[]>> testar(Distribuicao alg, int caminhoes, int inicio, int execucoes, long limite) {
        int conjunto = inicio;
        boolean lock = true;
        List<List<int[]>> totalRotas = new LinkedList<>();
        while (lock) {
            long tempoTotal = 0;
            // System.out.printf("%nTamanho %d: %n", conjunto);
            List<int[]> listaRotas = GeradorDeProblemas.geracaoDeRotas(conjunto, execucoes, 0.5);
            totalRotas.add(listaRotas);
            for (int[] rotas : listaRotas) {
                long _inicio = System.currentTimeMillis();
                alg.distribuirRotas(rotas, caminhoes);
                long tempoExecucao = System.currentTimeMillis() - _inicio;
                tempoTotal += tempoExecucao;
                if (tempoExecucao > limite) {
                    System.out.printf("%nTamanho %d ultrapassou o limite de tempo.%n", conjunto);
                    lock = false;
                    break; // Interrompe as execuções para esse tamanho
                }
            }

            long mediaTempo = tempoTotal / execucoes;
            if (lock)
                System.out.printf("%nTamanho %d - Tempo Médio: %d ms%n", conjunto, mediaTempo);
            // System.out.println("--------------------------------------------------");
            if (mediaTempo > limite) {
                System.out.printf("%nTamanho %d ultrapassou o limite de tempo.%n", conjunto);
                lock = false;
                break; // Interrompe os testes
            }
            conjunto++;
        }
        return totalRotas;
    }

    public static void main(String[] args) {
        testar(new Backtracking(), 3, 6, 10, 30 * 1000);

        // testar(new GulosoOrdem(), 3, 6, 10, 30 * 1000);

        // testar(new GulosoAcumulado(), 3, 6, 10, 30 * 1000);

        // testar(new DivisaoConquista(), 3, 6, 10, 30 * 1000);
    }
}
