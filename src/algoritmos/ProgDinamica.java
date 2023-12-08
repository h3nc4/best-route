package algoritmos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe que implementa o algoritmo de programação dinâmica para distribuição
 * de rotas entre caminhões, colunas representam a quilometrágem média esperada
 * de cada caminhão (um por um), linhas são as rotas a serem adicionadas e
 * células são verdadeiras se a rota pode ser adicionada ao caminhão e falsas
 * caso contrário. Caso o valor chegue a ser verdadeiro na célula
 * T[<x>][<valor_aceitavel>], então as rotas verdadeiras são retiradas de T,
 * adicionadas a um caminhão e o processo é repetido até que todas as rotas
 * sejam distribuídas. Caso o valor chegue a ser falso na célula
 * T[<x>][<valor_aceitavel>], então ele será adicionado ao caminhão
 * com menor quilometragem média.
 */
public class ProgDinamica implements Distribuicao {
    /** Quilometragem média aceitável */
    private int aceitavel;
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
        this.aceitavel = aceitavel - (int) (aceitavel * 0.1);
    }

    @Override
    public void distribuirRotas(int[] rotas, int numCaminhoes) {
        new ProgDinamica(rotas, numCaminhoes).distribuir().print();
    }

    /**
     * Distribui as rotas entre os caminhões
     * 
     * @return A própria instância da classe
     */
    private ProgDinamica distribuir() {

    }

    /**
     * Resolve o problema da distribuição de rotas
     * 
     * @return A própria instância da classe
     */
    private int[] distribuirAtual() {
        // Inicializa a primeira linha da tabela
        for (int j = 0; j < T[0].length; j++)
            T[0][j] = true;
        // Inicializa a primeira coluna da tabela
        for (int i = 1; i < T.length; i++)
            T[i][0] = false;
        // Preenche a tabela atual
        for (int i = 1; i < T.length; i++)
            for (int j = 1; j < T[i].length; j++) {
                // Se o valor acima for verdadeiro
                T[i][j] = T[i - 1][j] ||
                // Se a célula *rotas* à esquerda for verdadeira, quer dizer que a rota
                // atual pode ser adicionada ao caminhão para preencher o valor atual
                        j >= this.rotas[i - 1] && T[i - 1][j - this.rotas[i - 1]];

                // Verificar se o valor atual é verdadeiro e se está em uma coluna aceitável
                if (T[i][j]) {
                    
                }
            }

        // Coletar as rotas que foram distribuídas em um array
        return Arrays.stream(this.rotas).filter(rota -> T[this.rotas.length][rota]).toArray();
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
        int[] rotas = { 10, 20, 30, 40, 50, 60, 70, 80, 90 };
        int caminhoes = 3;
        new ProgDinamica().distribuirRotas(rotas, caminhoes);
    }
}
