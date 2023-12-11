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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Classe que implementa o algoritmo de programação dinâmica para distribuição
 * de rotas entre caminhões, colunas representam a quilometrágem média esperada
 * de cada caminhão (um por um), linhas são as rotas a serem adicionadas e
 * células são verdadeiras se a rota pode ser adicionada ao caminhão e falsas
 * caso contrário. Caso o valor chegue a ser verdadeiro na célula
 * T[x][valor_aceitavel], então as rotas verdadeiras são retiradas de T,
 * adicionadas a um caminhão e o processo é repetido até que todas as rotas
 * sejam distribuídas.
 */
public class ProgDinamica implements Distribuicao {
    /** Array com as rotas a serem distribuídas */
    private int[] rotas;
    /** Caminhões que já foram distribuídos */
    private List<Integer>[] caminhoesDistribuidos;
    /** Tabela de soluções */
    private boolean[][] T;

    /**
     * Construtor da classe ProgDinamica
     */
    public ProgDinamica() {
    }

    /**
     * Construtor da classe ProgDinamica
     * 
     * @param rotas        array com as rotas a serem distribuídas
     * @param numCaminhoes número de caminhões disponíveis
     */
    private ProgDinamica(int[] rotas, int numCaminhoes) {
        this.rotas = Arrays.copyOf(rotas, rotas.length);
        Arrays.sort(this.rotas);
        this.caminhoesDistribuidos = new LinkedList[numCaminhoes];
        int soma = Arrays.stream(rotas).sum();
        int aceitavel = soma / numCaminhoes;
        if (soma % numCaminhoes != 0) // caso não haja média exata
            aceitavel *= 1.1; // arrredonda em 10%
        this.T = new boolean[this.rotas.length + 1][aceitavel + 1];
    }

    @Override
    public void distribuirRotas(int[] rotas, int numCaminhoes) {
        ProgDinamica solucao = new ProgDinamica(rotas, numCaminhoes).distribuir();
        if (Distribuicao.PRINT)
            solucao.print();
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
    private ProgDinamica distribuir() {
        for (int i = 0; i < this.caminhoesDistribuidos.length; i++) {
            // Distribuir as rotas
            List<Integer> rotasDistribuidas = this.distribuirAtual();
            // Adicionar as rotas distribuídas ao array de caminhões distribuídos
            this.caminhoesDistribuidos[i] = rotasDistribuidas;
            // Criar um novo array de rotas com as rotas que não foram distribuídas
            for (Integer rota : rotasDistribuidas) {
                int index = IntStream.range(0, this.rotas.length)
                        .filter(i2 -> this.rotas[i2] == rota).findFirst().getAsInt();
                this.rotas = IntStream.concat(IntStream.of(this.rotas).limit(index),
                        IntStream.of(this.rotas).skip(index + 1)).toArray();
                // Criar uma nova tabela de soluções
            }
            if (this.caminhoesDistribuidos.length - i - 1 != 0) {
                int aceitavel = Arrays.stream(this.rotas).sum() / (this.caminhoesDistribuidos.length - i - 1);
                this.T = new boolean[this.rotas.length + 1][aceitavel + 1];
            }
        }
        if (this.rotas.length != 0)
            this.guloso();
        return this;
    }

    /**
     * Resolve o problema da distribuição de rotas
     * 
     * @return A própria instância da classe
     */
    private List<Integer> distribuirAtual() {
        // Inicializa a primeira linha da tabela
        for (int j = 1; j < this.T[0].length; j++)
            this.T[1][j] = false;
        // Inicializa a primeira coluna da tabela
        for (int i = 0; i < this.T.length; i++)
            this.T[i][0] = true;
        // Preenche a tabela atual
        for (int i = 1; i < this.T.length; i++)
            for (int j = 1; j < this.T[i].length; j++) {
                // Se o valor acima for verdadeiro
                this.T[i][j] = this.T[i - 1][j] ||
                // Se a célula *rotas* à esquerda for verdadeira, quer dizer que a rota
                // atual pode ser adicionada ao caminhão para preencher o valor atual
                        j >= this.rotas[i - 1] && this.T[i - 1][j - this.rotas[i - 1]];
            }

        // Retornar as rotas distribuídas
        return this.coletar();
    }

    /**
     * Coleta as rotas distribuídas
     * 
     * @return Array com as rotas para um caminhão
     */
    private List<Integer> coletar() {
        int i = this.T[0].length - 1; // Última coluna
        // verificar se a última célula da tabela é verdadeira, se não for, então
        // decrementar o valor de i até que seja verdadeira
        while (!this.T[this.T.length - 1][i])
            i--;

        // Encontrado o valor de i, então coletar as rotas que foram distribuídas
        List<Integer> rotasDistribuidas = new LinkedList<>();
        for (int j = this.T.length - 1; j > 0; j--)
            // Se a célula atual for verdadeira e a célula acima for falsa, então a rota
            // atual foi distribuída e assim subtrair o valor da rota atual de i
            if (this.T[j][i] && !this.T[j - 1][i]) {
                rotasDistribuidas.add(this.rotas[j - 1]);
                i -= this.rotas[j - 1];
            }

        // Retornar as rotas distribuídas em um array
        return rotasDistribuidas;
    }

    /**
     * Função de debug
     * 
     * Imprime a tabela de soluções
     */
    private void printarTabela() {
        System.out.println("Tabela de soluções:");
        for (int i = 0; i < this.T.length; i++) {
            for (int j = 0; j < this.T[i].length; j++)
                System.out.printf("%d ", this.T[i][j] ? 1 : 0);
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Imprime a melhor distribuição de rotas e a rota total de cada caminhão
     */
    private void print() {
        for (int i = 0; i < this.caminhoesDistribuidos.length; i++) {
            System.out.printf("Caminhão %d: rotas %s - total %dkm%n", i + 1,
                    this.caminhoesDistribuidos[i].stream().map(Object::toString).collect(Collectors.joining(", ")),
                    this.caminhoesDistribuidos[i].stream().mapToInt(Integer::intValue).sum());
        }
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
     * Método main de teste
     * 
     * @param args argumentos da linha de comandos
     */
    public static void main(String[] args) {
        new ProgDinamica().distribuirRotas(new int[] { 40, 36, 38, 29, 32, 28, 31, 35, 31, 30, 32, 30, 29, 39, 35, 38,
                39, 35, 32, 38, 32, 33, 29, 33, 29, 39, 28 }, 3);
        new ProgDinamica().distribuirRotas(new int[] { 32, 51, 32, 43, 42, 30, 42, 51, 43, 51, 29, 25, 27, 32, 29, 55,
                43, 29, 32, 44, 55, 29, 53, 30, 24, 27 }, 3);
    }
}
