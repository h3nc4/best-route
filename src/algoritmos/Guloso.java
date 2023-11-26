package algoritmos;

import java.util.Arrays;

/**
 * Classe abstrata que implementa a interface Distribuicao e serve de base para
 * implementar a estratégia gulosa
 */
public abstract class Guloso implements Distribuicao {
    /** Array com as rotas a serem distribuídas */
    int[] rotas;
    /** Número de caminhões disponíveis */
    int caminhoes;

    /**
     * Construtor da classe Guloso
     */
    public Guloso() {
    }

    /**
     * Construtor da classe Guloso
     * 
     * @param rotas        vetor com as rotas a serem distribuídas
     * @param numCaminhoes número de caminhões disponíveis
     */
    Guloso(int[] rotas, int caminhoes) {
        this.rotas = Arrays.copyOf(rotas, rotas.length);
        Arrays.sort(this.rotas);
        this.caminhoes = caminhoes;
    }
}
