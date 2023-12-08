package algoritmos;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe que implementa o algoritmo de programação dinâmica para distribuição
 * de rotas entre caminhões, colunas representam a quilometrágem média esperada
 * de cada caminhão (um por um), linhas são as rotas a serem adicionadas e
 * células são verdadeiras se a rota pode ser adicionada ao caminhão e falsas
 * caso contrário. Caso o valor chegue a ser verdadeiro na célula
 * T[<x>][<valor_aceitavel>], então as rotas verdadeiras são retiradas de T,
 * adicionadas a um caminhão e o processo é repetido até que todas as rotas
 * sejam distribuídas.
 */
public class ProgDinamica implements Distribuicao {
    /** Array com as rotas a serem distribuídas */
    private int[] rotas;
    /** Caminhões que já foram distribuídos */
    private int[][] caminhoesDistribuidos;
    /** Tabela de soluções */
    private boolean[][] T;

    /**
     * Construtor da classe ProgDinamica
     */
    public ProgDinamica() {
    }

    /**
     * Construtor da classe ProgDinamica
     * 
     * @param rotas        array com as rotas a serem distribuídas
     * @param numCaminhoes número de caminhões disponíveis
     */
    private ProgDinamica(int[] rotas, int numCaminhoes) {
        this.rotas = Arrays.copyOf(rotas, rotas.length);
        Arrays.sort(this.rotas);
        this.caminhoesDistribuidos = new int[numCaminhoes][];
        int aceitavel = Arrays.stream(this.rotas).sum() / numCaminhoes;
        this.T = new boolean[this.rotas.length + 1][aceitavel + (int) (aceitavel * 0.1) + 1];
    }

    @Override
    public void distribuirRotas(int[] rotas, int numCaminhoes) {
        new ProgDinamica(rotas, numCaminhoes).distribuir().print();
    }

    /**
     * Distribui as rotas entre os caminhões
     * 
     * Chama o método distribuirAtual() e remove as rotas distribuídas do array de
     * rotas, adicionando-as ao array de caminhões distribuídos
     * 
     * Cria um novo array de rotas com as rotas que não foram distribuídas
     * 
     * @return A própria instância da classe
     */
    private ProgDinamica distribuir() {
        for (int i = 0; i < this.caminhoesDistribuidos.length; i++) {
            // Distribuir as rotas
            int[] rotasDistribuidas = this.distribuirAtual();
            // Adicionar as rotas distribuídas ao array de caminhões distribuídos
            this.caminhoesDistribuidos[i] = rotasDistribuidas;
            // Criar um novo array de rotas com as rotas que não foram distribuídas
            this.rotas = Arrays.stream(this.rotas)
                    .filter(r -> Arrays.stream(rotasDistribuidas).noneMatch(r2 -> r2 == r))
                    .toArray();
            // Criar uma nova tabela de soluções
            if (this.caminhoesDistribuidos.length - i - 1 != 0) {
                int aceitavel = Arrays.stream(this.rotas).sum() / (this.caminhoesDistribuidos.length - i - 1);
                this.T = new boolean[this.rotas.length + 1][aceitavel + (int) (aceitavel * 0.1) + 1];
            }
        }
        return this;
    }

    /**
     * Resolve o problema da distribuição de rotas
     * 
     * @return A própria instância da classe
     */
    private int[] distribuirAtual() {
        // Inicializa a primeira linha da tabela
        for (int j = 1; j < this.T[0].length; j++)
            this.T[1][j] = false;
        // Inicializa a primeira coluna da tabela
        for (int i = 0; i < this.T.length; i++)
            this.T[i][0] = true;
        this.printarTabela();
        // Preenche a tabela atual
        for (int i = 1; i < this.T.length; i++)
            for (int j = 1; j < this.T[i].length; j++) {
                // Se o valor acima for verdadeiro
                this.T[i][j] = this.T[i - 1][j] ||
                // Se a célula *rotas* à esquerda for verdadeira, quer dizer que a rota
                // atual pode ser adicionada ao caminhão para preencher o valor atual
                        j >= this.rotas[i - 1] && this.T[i - 1][j - this.rotas[i - 1]];
            }

        this.printarTabela();

        // Retornar as rotas distribuídas
        return this.coletar();
    }

    /**
     * Coleta as rotas distribuídas
     * 
     * @return Array com as rotas para um caminhão
     */
    private int[] coletar() {
        int i = this.T[0].length - 1; // Última coluna
        // verificar se a última célula da tabela é verdadeira, se não for, então
        // decrementar o valor de i até que seja verdadeira
        while (!this.T[this.T.length - 1][i])
            i--;

        // Encontrado o valor de i, então coletar as rotas que foram distribuídas
        List<Integer> rotasDistribuidas = new LinkedList<>();
        for (int j = this.T.length - 1; j > 0; j--)
            // Se a célula atual for verdadeira e a célula acima for falsa, então a rota
            // atual foi distribuída e assim subtrair o valor da rota atual de i
            if (this.T[j][i] && !this.T[j - 1][i]) {
                rotasDistribuidas.add(this.rotas[j - 1]);
                i -= this.rotas[j - 1];
            }

        // Retornar as rotas distribuídas em um array
        return rotasDistribuidas.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Função de debug
     * 
     * Imprime a tabela de soluções
     */
    private void printarTabela() {
        System.out.println("Tabela de soluções:");
        for (int i = 0; i < this.T.length; i++) {
            for (int j = 0; j < this.T[i].length; j++)
                System.out.printf("%d ", this.T[i][j] ? 1 : 0);
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Imprime a melhor distribuição de rotas
     */
    public void print() {
        System.out.println("Distribuição de rotas:");
        for (int i = 0; i < this.caminhoesDistribuidos.length; i++) {
            System.out.printf("Caminhão %d: ", i + 1);
            for (int j = 0; j < this.caminhoesDistribuidos[i].length; j++)
                System.out.printf("%d ", this.caminhoesDistribuidos[i][j]);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[] rotas = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int caminhoes = 3;
        new ProgDinamica().distribuirRotas(rotas, caminhoes);
    }
}
