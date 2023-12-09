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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Classe que implementa o algoritmo de divisão e conquista para distribuição de
 * rotas entre caminhões
 */
public class DivisaoConquista implements Distribuicao {
    /** Caminhões que já foram distribuídos */
    private List<Integer>[] caminhoesDistribuidos;
    /** Array com as rotas a serem distribuídas */
    private int[] rotas;

    /**
     * Construtor da classe DivisaoConquista
     */
    public DivisaoConquista() {
    }

    /**
     * Construtor da classe DivisaoConquista
     * 
     * @param rotas        vetor com as rotas a serem distribuídas
     * @param numCaminhoes número de caminhões disponíveis
     */
    private DivisaoConquista(int[] rotas, int numCaminhoes) {
        this.rotas = Arrays.copyOf(rotas, rotas.length);
        Arrays.sort(this.rotas);
        this.caminhoesDistribuidos = IntStream.range(0, numCaminhoes)
                .mapToObj(i -> new LinkedList<Integer>())
                .toArray(List[]::new);
    }

    @Override
    public void distribuirRotas(int[] rotas, int numCaminhoes) {
        new DivisaoConquista(rotas, numCaminhoes).distribuir();
        // print(distribAtual);
    }

    /**
     * Resolve o problema da distribuição de rotas
     * 
     * @param n   tamanho do conjunto
     * @param min valor mínimo da soma desejada
     * @param max valor máximo da soma desejada
     * @param i   índice do caminhão atual
     * @return true se existe um subconjunto
     */
    private boolean distribuirAtual(int n, int min, int max, int i) {
        // Casos base
        if (min <= 0 && max >= 0)
            return true;
        if (n == 0) // Se não há mais rotas para distribuir
            return false;
        // Inclui ou não o último elemento caso sua inclusão não exceda os limites
        if (rotas[n - 1] <= max)
            if (distribuirAtual(n - 1, min, max, i))
                return true;
            else if (distribuirAtual(n - 1, min - rotas[n - 1], max - rotas[n - 1], i)) {
                this.caminhoesDistribuidos[i].add(rotas[n - 1]);
                return true;
            }
        // Caso contrário, continue verificando sem incluí-lo
        return distribuirAtual(n - 1, min, max, i);
    }

    /**
     * Distribui as rotas entre os caminhões
     * 
     * Chama o método distribuirAtual() e remove as rotas distribuídas do array de
     * rotas, adicionando-as ao array de caminhões distribuídos
     * 
     * Cria um novo array de rotas com as rotas que não foram distribuídas
     * 
     * @return A própria instância da classe
     */
    private void distribuir() {
        for (int i = 0; i < this.caminhoesDistribuidos.length; i++) {
            int media = Arrays.stream(this.rotas).sum() / this.caminhoesDistribuidos.length;
            // Distribuir as rotas
            this.distribuirAtual(this.rotas.length, (int) (media * 0.9), (int) (media * 1.1), i);
            // Criar um novo array de rotas com as rotas que não foram distribuídas
            List<Integer> caminhao = this.caminhoesDistribuidos[i];
            for (Integer rota : caminhao) {
                int index = IntStream.range(0, this.rotas.length)
                        .filter(i2 -> this.rotas[i2] == rota).findFirst().getAsInt();
                this.rotas = IntStream.concat(IntStream.of(this.rotas).limit(index),
                        IntStream.of(this.rotas).skip(index + 1)).toArray();
            }
        }
        if (this.rotas.length != 0)
            this.guloso();
        // this.print();
    }

    /**
     * Função gulosa para distribuir as rotas restantes
     */
    private void guloso() {
        for (int i = 0; i < this.rotas.length; i++)
            this.caminhoesDistribuidos[this.menorAcumulado()].add(this.rotas[i]);
    }

    /**
     * Retorna o caminhão com menor quilometragem acumulada para a função gulosa
     * 
     * @return Index do caminhão com menor quilometragem acumulada
     */
    private int menorAcumulado() {
        int menor = 0;
        for (int i = 1; i < this.caminhoesDistribuidos.length; i++)
            if (this.caminhoesDistribuidos[i].stream().mapToInt(Integer::intValue)
                    .sum() < this.caminhoesDistribuidos[menor].stream().mapToInt(Integer::intValue)
                            .sum())
                menor = i;
        return menor;
    }

    /**
     * Método que imprime a melhor distribuição de rotas
     * 
     * @param distribuicao distribuição atual das rotas
     */
    private void print() {
        for (int i = 0; i < this.caminhoesDistribuidos.length; i++) {
            System.out.printf("Caminhão %d: %s Total: %d\n", i + 1, this.caminhoesDistribuidos[i],
                    this.caminhoesDistribuidos[i].stream().mapToInt(Integer::intValue).sum());
        }
    }

    public static void main(String[] args) {
        int[] rotas = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9, 9 };
        new DivisaoConquista().distribuirRotas(rotas, 3);
    }
}
