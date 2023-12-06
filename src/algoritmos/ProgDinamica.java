package algoritmos;

import java.util.Arrays;

/**
 * Classe que implementa o algoritmo de programação dinâmica para distribuição
 * de rotas entre caminhões, colunas são os caminhões, linhas são as rotas e
 * células são
 */
public class ProgDinamica implements Distribuicao {
    /**
     * Classe auxiliar para a programação dinâmica
     */
    private static class Frota {
        /** Array com as rotas de cada caminhão */
        private int[] rotas;

        /**
         * Construtor da classe Frota
         */
        private Frota() {
            this.rotas = new int[0];
        }

        /**
         * Construtor da classe Frota, copia o array de rotas e adiciona um novo
         * caminhão
         * 
         * @param rotas array com as rotas de cada caminhão
         */
        private Frota(int[] rotas) {
            this.rotas = Arrays.copyOf(rotas, rotas.length + 1);
        }

        /**
         * Adiciona uma rota para o caminhão com menor quilometragem
         * 
         * @param rota quilometragem da rota a ser adicionada
         */
        private void add(int rota) {
            this.rotas[Arrays.stream(this.rotas).min().getAsInt()] += rota;
        }

        @Override
        public String toString() {
            return Arrays.toString(rotas);
        }
    }

    /** Array com as rotas a serem distribuídas */
    private int[] rotas;
    /** Número de caminhões disponíveis */
    private int caminhoes;
    /** Tabela de soluções */
    private Frota[][] T;

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
        this.caminhoes = numCaminhoes;
        this.T = new Frota[numCaminhoes + 1][rotas.length + 1];
        for (int i = 0; i <= numCaminhoes; i++)
            this.T[i][0] = new Frota();
        for (int i = 0; i <= rotas.length; i++)
            this.T[0][i] = new Frota();
    }

    @Override
    public void distribuirRotas(int[] rotas, int numCaminhoes) {
        new ProgDinamica(rotas, numCaminhoes).distribuir().print();
    }

    /**
     * Resolve o problema da distribuição de rotas
     * 
     * @return A própria instância da classe
     */
    private ProgDinamica distribuir() {
        for (int i = 1; i <= caminhoes; i++)
            for (int j = 1; j <= rotas.length; j++)
                // TODO
                return this;
    }

    /**
     * Imprime a melhor distribuição de rotas
     */
    public void print() {
        System.out.println("Melhor distribuição: " + T[caminhoes][rotas.length]);
    }

    public static void main(String[] args) {
        int[] rotas = { 10, 20, 30, 40, 50, 60, 70, 80, 90 };
        int caminhoes = 3;
        new ProgDinamica().distribuirRotas(rotas, caminhoes);
    }
}