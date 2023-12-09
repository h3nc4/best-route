package app;

import algoritmos.*;

/**
 * Classe principal do projeto, usada para realizar os múltiplos casos de teste
 */
public class App {
    /** Algoritmos a serem testados */
    private Distribuicao[] alg;
    /** Número de caminhões */
    private int caminhoes;
    /** Tamanho dos conjuntos de rotas */
    private int conjunto;
    /** Número de execuções para cada conjunto de rotas */
    private int execucoes;
    /** Limite de tempo para a execução */
    private long limite;

    /**
     * Construtor da classe App
     * 
     * @param algoritmos Algoritmos a serem testados
     * @param caminhoes  Número de caminhões
     * @param inicio     Tamanho inicial dos conjuntos de rotas
     * @param execucoes  Número de execuções para cada conjunto de rotas
     * @param limite     Limite de tempo para a execução
     */
    public App(Distribuicao[] algoritmos, int caminhoes, int inicio, int execucoes, long limite) {
        this.alg = algoritmos;
        this.caminhoes = caminhoes;
        this.conjunto = inicio;
        this.execucoes = execucoes;
        this.limite = limite;
    }

    /**
     * Executa o algoritmo 'algoritmo' com o conjunto de rotas 'rotas' e retorna o
     * tempo de execução em milissegundos
     * 
     * @param rotas    Conjunto de rotas a serem distribuídas
     * @param algoritmo Algoritmo a ser utilizado
     * @return Tempo de execução em milissegundos
     */
    private long tempoExecucao(int[] rotas, int algoritmo) {
        long _inicio = System.currentTimeMillis();
        this.alg[algoritmo].distribuirRotas(rotas, this.caminhoes);
        return System.currentTimeMillis() - _inicio;
    }

    /**
     * Primeiro teste, aumenta o tamanho dos conjuntos de rotas em 1 até atingir o
     * tamanho T, um tamanho que não consiga ser resolvido em até 'this.limite'
     * segundos pelo algoritmo.
     * Executa 'this.execucoes' testes de cada tamanho para utilizar a média.
     */
    private void testar() {
        boolean lock = true;
        while (lock) {
            long tempoTotal = 0;
            for (int[] rotas : GeradorDeProblemas.geracaoDeRotas(this.conjunto, this.execucoes, 0.5)) {
                long tempoExecucao = this.tempoExecucao(rotas, 0);
                tempoTotal += tempoExecucao;
                if (tempoExecucao > this.limite) {
                    System.out.printf("%nTamanho %d ultrapassou o limite de tempo.%n", this.conjunto);
                    lock = false;
                    break; // Interrompe as execuções para esse tamanho
                }
            }
            if (lock)
                System.out.printf("%nTamanho %d - Tempo Médio: %d ms%n", this.conjunto, tempoTotal / this.execucoes);
            this.conjunto++;
        }
        this.segundoTeste();
    }

    /**
     * Segundo teste, aumenta o tamanho dos conjuntos de T em T até atingir o
     * tamanho
     * 10T, sempre executando 10 testes de cada tamanho para utilizar a média.
     * 
     * Utiliza os mesmos conjuntos de tamanho T utilizados no teste anterior
     */
    private void segundoTeste() {
        int limiteTamanho = this.conjunto * 10; // 10T
        int conjunto = this.conjunto;
        while (this.conjunto <= limiteTamanho) {
            long tempoTotal = 0;
            for (int[] rotas : GeradorDeProblemas.geracaoDeRotas(this.conjunto, this.execucoes, 0.5))
                tempoTotal += this.tempoExecucao(rotas, 1);
            System.out.printf("%nTamanho %d - Tempo Médio: %d ms%n", this.conjunto, tempoTotal / this.execucoes);
            this.conjunto += conjunto;
        }
    }

    public static void main(String[] args) {
        // new App(new Distribuicao[] { new Backtracking(), new GulosoAcumulado() }, 3, 6, 10, 30 * 1000).testar();

        // new App(new Distribuicao[] { new DivisaoConquista(), new ProgDinamica() }, 3, 6, 10, 30 * 1000).testar();
    }
}
