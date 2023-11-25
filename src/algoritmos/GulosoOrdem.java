package algoritmos;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe que implementa o algoritmo guloso para distribuição de rotas entre
 * caminhões
 */
public class GulosoOrdem extends Guloso {

    /**
     * Construtor da classe GulosoOrdem
     * 
     * @param rotas        vetor com as rotas a serem distribuídas
     * @param numCaminhoes número de caminhões disponíveis
     */
    public GulosoOrdem(int[] rotas, int caminhoes) {
        super(rotas, caminhoes);
    }

    /**
     * Primeira estratégia gulosa: ordernar em ordem crescente de quilometragem
     * e distribuir as rotas em ordem
     */
    public void distribuirRotas() {
        int[] resultados = new int[this.caminhoes];
        List<Integer>[] rotasAdc = new LinkedList[this.caminhoes];
        for (int i = 0; i < this.caminhoes; i++)
            rotasAdc[i] = new LinkedList<>();
        for (int i = 0; i < this.rotas.length; i++) {
            resultados[i % this.caminhoes] += this.rotas[i];
            rotasAdc[i % this.caminhoes].add(this.rotas[i]);
        }
        print(resultados, rotasAdc);
    }

    /**
     * Método que imprime os resultados da primeira estratégia
     * 
     * @param resultados vetor com as quilometragens acumuladas
     */
    private void print(int[] resultados, List<Integer>[] rotas) {
        for (int i = 0; i < resultados.length; i++)
            System.out.printf("Caminhão %d: rotas %s - total %dkm%n", (i + 1),
                    rotas[i].stream().map(Object::toString).collect(Collectors.joining(", ")), resultados[i]);
    }
}
