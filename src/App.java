import algoritmos.*;

/**
 * Classe principal do projeto, usada para realizar os múltiplos casos de teste
 */
public class App {
    private static void testar(Distribuicao algoritmo, int caminhoes, int inicio, int execucoes, long limiteTempo) {
        int conjunto = inicio;
        boolean lock = true;
        while (lock) {
            long tempoTotal = 0;
            System.out.printf("%nTamanho %d: %n", conjunto);
            for (int[] rotas : GeradorDeProblemas.geracaoDeRotas(conjunto, execucoes, 0.5)) {
                long _inicio = System.currentTimeMillis();
                algoritmo.distribuirRotas(rotas, caminhoes);
                long tempoExecucao = System.currentTimeMillis() - _inicio;
                tempoTotal += tempoExecucao;
                if (tempoExecucao > limiteTempo) {
                    System.out.printf("%nTamanho %d ultrapassou o limite de tempo.%n", conjunto);
                    lock = false;
                    break; // Interrompe as execuções para esse tamanho
                }
            }

            long mediaTempo = tempoTotal / execucoes;
            System.out.printf("%nTamanho %d - Tempo Médio: %d ms%n", conjunto, mediaTempo);
            System.out.println("--------------------------------------------------");
            if (mediaTempo > limiteTempo) {
                System.out.printf("%nTamanho %d ultrapassou o limite de tempo.%n", conjunto);
                lock = false;
                break; // Interrompe os testes
            }
            conjunto++;
        }
    }

    public static void main(String[] args) {
        // testar(new Backtracking(), 3, 6, 10, 30 * 1000);

        // testar(new GulosoOrdem(), 3, 6, 10, 30 * 1000);
    }
}
