package algoritmos;

import java.util.Arrays;

public abstract class Guloso implements Distribuicao {
    /** Array com as rotas a serem distribuídas */
    int[] rotas;
    /** Número de caminhões disponíveis */
    int caminhoes;

    /**
     * Construtor da classe Guloso
     * 
     * @param rotas        vetor com as rotas a serem distribuídas
     * @param numCaminhoes número de caminhões disponíveis
     */
    public Guloso(int[] rotas, int caminhoes) {
        this.rotas = Arrays.copyOf(rotas, rotas.length);
        Arrays.sort(this.rotas);
        this.caminhoes = caminhoes;
    }
}