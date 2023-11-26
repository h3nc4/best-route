package algoritmos;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Classe que implementa o algoritmo de divisão e conquista para distribuição de
 * rotas entre caminhões
 */
public class DivisaoConquista implements Distribuicao {

    /**
     * Construtor da classe DivisaoConquista
     */
    public DivisaoConquista() {
    }

    @Override
    public void distribuirRotas(int[] rotas, int numCaminhoes) {
        Arrays.sort(rotas);
        List<Integer>[] distribAtual = IntStream.range(0, numCaminhoes)
                .mapToObj(i -> new LinkedList<Integer>())
                .toArray(List[]::new);
        distribuir(rotas, rotas.length - 1, numCaminhoes - 1, distribAtual);
        // print(distribAtual);
    }

    /**
     * Método privado e recursivo que de facto distribui as rotas entre os caminhões
     * 
     * @param rotas        vetor com as rotas a serem distribuídas
     * @param rotaAtual    índice da rota a ser distribuída
     * @param caminhaoAtual índice do caminhão a ser usado
     * @param distribAtual distribuição atual das rotas
     */
    private static void distribuir(int[] rotas, int rotaAtual, int caminhaoAtual, List<Integer>[] distribAtual) {
        // todas as rotas foram distribuídas
        if (rotaAtual < 0)
            return;
        // Insere a rota atual no caminhão com menor quilometragem
        distribAtual[getMenor(distribAtual)].add(rotas[rotaAtual]);
        // Chama recursivamente para a próxima rota
        distribuir(rotas, rotaAtual - 1, caminhaoAtual, distribAtual);
    }

    /**
     * Encontrar caminhão com menor quilometragem
     * 
     * @param distribuicao distribuição atual das rotas
     * @return index do caminhão com menor quilometragem
     */
    private static int getMenor(List<Integer>[] distribuicao) {
        return IntStream.range(1, distribuicao.length).reduce(0,
                (indexMin, i) -> soma(distribuicao[i]) < soma(distribuicao[indexMin]) ? i
                        : indexMin);
    }

    /**
     * Somar as quilometragens de um caminhão
     * 
     * @param caminhao caminhão a ser somado
     * @return quilometragem total do caminhão
     */
    private static int soma(List<Integer> caminhao) {
        return caminhao.stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Método que imprime a melhor distribuição de rotas
     * 
     * @param distribuicao distribuição atual das rotas
     */
    private static void print(List<Integer>[] distribuicao) {
        for (int i = 0; i < distribuicao.length; i++) {
            System.out.printf("Caminhão %d: rotas %s - total %dkm%n", (i + 1),
                    distribuicao[i].stream().map(Object::toString).collect(Collectors.joining(", ")),
                    soma(distribuicao[i]));
        }
    }
}
