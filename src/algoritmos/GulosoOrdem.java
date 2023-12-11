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
 * You should have received a copy of the GNU
 * General Public License along with Best-route. If not, see
 * <https://www.gnu.org/licenses/>.
*/

package algoritmos;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Classe que implementa o algoritmo guloso para distribuição de rotas entre
 * caminhões com base na ordem das rotas
 */
public class GulosoOrdem extends Guloso {

    /**
     * Construtor da classe GulosoOrdem
     */
    public GulosoOrdem() {
        super();
    }

    /**
     * Construtor da classe GulosoOrdem
     * 
     * @param rotas     vetor com as rotas a serem distribuídas
     * @param caminhoes número de caminhões disponíveis
     */
    private GulosoOrdem(int[] rotas, int caminhoes) {
        super(rotas, caminhoes);
    }

    @Override
    public void distribuirRotas(int[] rotas, int caminhoes) {
        new GulosoOrdem(rotas, caminhoes).distribuirRotas();
    }

    /**
     * Primeira estratégia gulosa: ordernar em ordem crescente de quilometragem
     * e distribuir as rotas em ordem para os caminhões
     * Ao adicionar uma rota para cada caminhão, a ordem da distribuição é invertida
     */
    private void distribuirRotas() {
        int[] resultados = new int[this.caminhoes];
        List<Integer>[] rotasAdc = IntStream.range(0, this.caminhoes)
                .mapToObj(i -> new LinkedList<Integer>())
                .toArray(List[]::new);
        for (int i = 0; i < this.caminhoes; i++)
            rotasAdc[i] = new LinkedList<>();
        boolean ordem = true;
        for (int i = 0; i < this.rotas.length; i++) {
            if (ordem) {
                resultados[i % this.caminhoes] += this.rotas[i];
                rotasAdc[i % this.caminhoes].add(this.rotas[i]);
            } else {
                resultados[(this.caminhoes - 1) - (i % this.caminhoes)] += this.rotas[i];
                rotasAdc[(this.caminhoes - 1) - (i % this.caminhoes)].add(this.rotas[i]);
            }
            if ((i + 1) % this.caminhoes == 0) // Reverte a ordem
                ordem = !ordem;
        }
        if (Distribuicao.PRINT)
            this.print(resultados, rotasAdc);
    }

    /**
     * Método que imprime os resultados da primeira estratégia
     * 
     * @param resultados vetor com as quilometragens acumuladas
     * @param rotas      vetor com as rotas distribuídas
     */
    private void print(int[] resultados, List<Integer>[] rotas) {
        for (int i = 0; i < resultados.length; i++)
            System.out.printf("Caminhão %d: rotas %s - total %dkm%n", (i + 1),
                    rotas[i].stream().map(Object::toString).collect(Collectors.joining(", ")), resultados[i]);
    }

    /**
     * Método main de teste
     * 
     * @param args argumentos da linha de comandos
     */
    public static void main(String[] args) {
        new GulosoOrdem().distribuirRotas(new int[] { 40, 36, 38, 29, 32, 28, 31, 35, 31, 30, 32, 30, 29, 39, 35, 38,
                39, 35, 32, 38, 32, 33, 29, 33, 29, 39, 28 }, 3);
        new GulosoOrdem().distribuirRotas(new int[] { 32, 51, 32, 43, 42, 30, 42, 51, 43, 51, 29, 25, 27, 32, 29, 55,
                43, 29, 32, 44, 55, 29, 53, 30, 24, 27 }, 3);
    }
}
