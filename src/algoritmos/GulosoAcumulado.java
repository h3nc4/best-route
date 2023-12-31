/*
 *  Copyright 2023 Henrique Almeida
 * 
 * This file is part of Best-route.
 * 
 * Best-route is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Best-route is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser
 * General Public License along with Best-route. If not, see
 * <https://www.gnu.org/licenses/>.
*/

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
     * @param rotas     vetor com as rotas a serem distribuídas
     * @param caminhoes número de caminhões disponíveis
     */
    private GulosoAcumulado(int[] rotas, int caminhoes) {
        super(rotas, caminhoes);
    }

    /**
     * Classe auxiliar para a segunda estratégia gulosa
     */
    private static class Caminhao implements Comparable<Caminhao> {
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

        @Override
        public int compareTo(Caminhao outro) {
            return Integer.compare(this.acumulado, outro.acumulado);
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
        PriorityQueue<Caminhao> fila = new PriorityQueue<>(this.caminhoes);
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
        if (Distribuicao.PRINT)
            print(fila, rotasAdc);
    }

    /**
     * Método que imprime os resultados da segunda estratégia
     * 
     * @param fila  fila de prioridade com os caminhões
     * @param rotas rotas adicionadas a cada caminhão
     */
    private static void print(PriorityQueue<Caminhao> fila, List<Integer>[] rotas) {
        fila.forEach(caminhao -> System.out.printf("Caminhão %d: rotas %s - total %dkm%n", caminhao.numero,
                rotas[caminhao.numero - 1].stream().map(Object::toString).collect(Collectors.joining(", ")),
                caminhao.acumulado));
    }

    /**
     * Método main de teste
     * 
     * @param args argumentos da linha de comandos
     */
    public static void main(String[] args) {
        long inicio = System.nanoTime();
        new GulosoAcumulado()
                .distribuirRotas(new int[] { 40, 36, 38, 29, 32, 28, 31, 35, 31, 30, 32, 30, 29, 39, 35, 38,
                        39, 35, 32, 38, 32, 33, 29, 33, 29, 39, 28 }, 3);
        new GulosoAcumulado()
                .distribuirRotas(new int[] { 32, 51, 32, 43, 42, 30, 42, 51, 43, 51, 29, 25, 27, 32, 29, 55,
                        43, 29, 32, 44, 55, 29, 53, 30, 24, 27 }, 3);
        System.out.printf("Tempo de execução: %fms%n", (System.nanoTime() - inicio) / 1e6);
    }
}
