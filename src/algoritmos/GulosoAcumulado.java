package algoritmos;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Classe que implementa a segunda estratégia gulosa para distribuição de rotas
 * entre caminhões com base na quilometragem acumulada
 */
public class GulosoAcumulado extends Guloso {

    /**
     * Construtor da classe GulosoAcumulado
     */
    public GulosoAcumulado() {
        super();
    }

    /**
     * Construtor da classe GulosoAcumulado
     * 
     * @param rotas        vetor com as rotas a serem distribuídas
     * @param numCaminhoes número de caminhões disponíveis
     */
    private GulosoAcumulado(int[] rotas, int caminhoes) {
        super(rotas, caminhoes);
    }

    /**
     * Classe auxiliar para a segunda estratégia gulosa
     */
    private static class Caminhao {
        /** Index do caminhão */
        private int numero;
        /** Quilometragem acumulada */
        private int acumulado;

        /**
         * Construtor da classe Caminhao
         * 
         * @param numero index do caminhão
         */
        private Caminhao(int numero) {
            this.numero = numero;
            this.acumulado = 0;
        }
    }

    @Override
    public void distribuirRotas(int[] rotas, int caminhoes) {
        new GulosoAcumulado(rotas, caminhoes).distribuirRotas();
    }

    /**
     * Segunda estratégia gulosa: alocar para o caminhão com menor quilometragem
     * acumulada
     */
    private void distribuirRotas() {
        PriorityQueue<Caminhao> fila = new PriorityQueue<>(this.caminhoes,
                (a, b) -> Integer.compare(a.acumulado, b.acumulado));
        List<Integer>[] rotasAdc = IntStream.range(0, this.caminhoes)
                .mapToObj(i -> new LinkedList<Integer>())
                .toArray(List[]::new);
        for (int i = 0; i < this.caminhoes; i++) {
            rotasAdc[i] = new LinkedList<>();
            fila.add(new Caminhao(i + 1));
        }
        for (int i = this.rotas.length - 1; i >= 0; i--) {
            Caminhao caminhao = fila.poll();
            caminhao.acumulado += this.rotas[i];
            fila.add(caminhao);
            rotasAdc[caminhao.numero - 1].add(this.rotas[i]);
        }
        // print(new PriorityQueue<>(fila), rotasAdc);
    }

    /**
     * Método que imprime os resultados da segunda estratégia
     * 
     * @param fila fila de prioridade com os caminhões
     */
    private static void print(PriorityQueue<Caminhao> fila, List<Integer>[] rotas) {
        while (!fila.isEmpty()) {
            Caminhao caminhao = fila.poll();
            System.out.printf("Caminhão %d: rotas %s - total %dkm%n", caminhao.numero,
                    rotas[caminhao.numero - 1].stream().map(Object::toString).collect(Collectors.joining(", ")),
                    caminhao.acumulado //
            );
        }
    }
}
