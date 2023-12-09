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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe que implementa o algoritmo de Backtracking para distribuição de rotas
 * entre caminhões
 */
public class Backtracking implements Distribuicao {
    /** Array com as rotas a serem distribuídas */
    private int[] rotas;
    /** Lista com as rotas distribuídas */
    private LinkedList<Integer>[] atualRotas; // LinkedList para acesso a removeFirstOccurrence
    /** Lista com as melhores rotas distribuídas */
    private List<List<Integer>> melhorRotas; // Usa arraylist para acesso veloz durante a impressão e cópia
    /** Número de caminhões disponíveis */
    private int caminhoes;
    /** Array com as distâncias atuais */
    private int[] distbAtual;
    /** Array com as melhores distâncias */
    private int[] melhorDistrb;

    /**
     * Construtor da classe Backtracking
     */
    public Backtracking() {
    }

    /**
     * Construtor da classe Backtracking
     * 
     * @param rotas        vetor com as rotas a serem distribuídas
     * @param numCaminhoes número de caminhões disponíveis
     */
    private Backtracking(int[] rotas, int numCaminhoes) {
        this.rotas = Arrays.copyOf(rotas, rotas.length);
        Arrays.sort(this.rotas); // Ordena as rotas para melhorar a poda
        this.caminhoes = numCaminhoes;
        this.atualRotas = new LinkedList[numCaminhoes];
        for (int i = 0; i < numCaminhoes; i++)
            this.atualRotas[i] = new LinkedList<>();
        this.distbAtual = new int[numCaminhoes];
        this.melhorDistrb = new int[numCaminhoes];
        Arrays.fill(this.melhorDistrb, Integer.MAX_VALUE);
    }

    @Override
    public void distribuirRotas(int[] rotas, int numCaminhoes) {
        new Backtracking(rotas, numCaminhoes).distribuir(0); // .print();
    }

    /**
     * Método privado e recursivo que de facto distribui as rotas entre os caminhões
     * 
     * @param q índice da rota a ser distribuída
     * @return instância da classe Backtracking
     */
    private Backtracking distribuir(int q) {
        // Poda de distribuições inferiores
        if (q == this.rotas.length) {
            // Verifica se a distribuição atual é melhor do que a melhor conhecida até agora
            if (Arrays.stream(this.distbAtual).max().getAsInt() < Arrays.stream(this.melhorDistrb).max().getAsInt()) {
                // Atualiza as melhores distâncias e rotas
                System.arraycopy(this.distbAtual, 0, this.melhorDistrb, 0, this.caminhoes);
                this.melhorRotas = Arrays.stream(atualRotas).map(ArrayList::new).collect(Collectors.toList());
            }
            return this;
        }

        // Loop através dos caminhões para tentar distribuir a rota atual
        for (int i = 0; i < this.caminhoes; i++) {
            LinkedList<Integer> caminhao = this.atualRotas[i];
            caminhao.add(this.rotas[q]); // Adiciona a rota ao caminhão atual
            this.distbAtual[i] += this.rotas[q]; // Atualiza a distância do caminhão atual
            this.distribuir(q + 1); // Chama recursivamente para a próxima rota
            caminhao.removeFirstOccurrence(this.rotas[q]); // Desfaz a adição para tentar outras distribuições
            this.distbAtual[i] -= this.rotas[q]; // Desfaz a atualização da distância
        }
        return this;
    }

    /**
     * Método que imprime a melhor distribuição de rotas
     */
    private void print() {
        for (int i = 0; i < this.caminhoes; i++)
            System.out.printf("Caminhão %d: rotas %s - total %dkm%n", i + 1,
                    this.melhorRotas.get(i).stream().map(Object::toString).collect(Collectors.joining(", ")),
                    this.melhorDistrb[i]);
    }
}
