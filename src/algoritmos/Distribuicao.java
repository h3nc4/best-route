package algoritmos;

/**
 * Interface que define o método de distribuição de rotas entre caminhões
 */
public interface Distribuicao {
    /**
     * Método que distribui as rotas entre os caminhões
     * 
     * @param rotas        vetor com as rotas a serem distribuídas
     * @param numCaminhoes número de caminhões disponíveis
     */
    public void distribuirRotas(int[] rotas, int numCaminhoes);
}
