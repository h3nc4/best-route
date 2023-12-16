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

package app;

import java.util.LinkedList;
import java.util.List;

import algoritmos.*;

/**
 * Classe principal do projeto, usada para realizar os múltiplos casos de teste
 */
public class App {
    /** Algoritmos a serem testados */
    private Distribuicao[] alg;
    /** Algoritmo atual */
    private int algoritmo;
    /** Número de caminhões */
    private int caminhoes;
    /** Tamanho dos conjuntos de rotas */
    private int conjunto;
    /** Número de execuções para cada conjunto de rotas */
    private int execucoes;
    /** Limite de tempo para a execução */
    private long limite;
    /** Conjunto que alcançou o tamanho T */
    private List<int[]> T;
    /** Conjuntos do segundo teste */
    private List<List<int[]>> segundoTeste;

    /**
     * Construtor da classe App
     * 
     * @param algoritmos Algoritmos a serem testados
     * @param caminhoes  Número de caminhões
     * @param inicio     Tamanho inicial dos conjuntos de rotas
     * @param execucoes  Número de execuções para cada conjunto de rotas
     * @param limite     Limite de tempo para a execução
     */
    public App(Distribuicao[] algoritmos, int caminhoes, int inicio, int execucoes, long limite) {
        this.alg = algoritmos;
        this.algoritmo = 0;
        this.caminhoes = caminhoes;
        this.conjunto = inicio;
        this.execucoes = execucoes;
        this.limite = limite;
        this.segundoTeste = new LinkedList<>();
    }

    /**
     * Executa o algoritmo 'algoritmo' com o conjunto de rotas 'rotas' e retorna o
     * tempo de execução em milissegundos
     * 
     * @param rotas     Conjunto de rotas a serem distribuídas
     * @return Tempo de execução em milissegundos
     */
    private long tempoExecucao(int[] rotas) {
        long _inicio = System.currentTimeMillis();
        this.alg[this.algoritmo].distribuirRotas(rotas, this.caminhoes);
        return System.currentTimeMillis() - _inicio;
    }

    /**
     * Primeiro teste, aumenta o tamanho dos conjuntos de rotas em 1 até atingir o
     * tamanho T, um tamanho que não consiga ser resolvido em até 'this.limite'
     * segundos pelo algoritmo.
     * Executa 'this.execucoes' testes de cada tamanho para utilizar a média.
     */
    private void testar() {
        boolean lock = true;
        System.out.printf("Primeiro teste");
        while (lock) {
            long tempoTotal = 0;
            // T recebe o conjunto de rotas até o tamanho T
            this.T = GeradorDeProblemas.geracaoDeRotas(this.conjunto, this.execucoes, 0.5);
            for (int[] rotas : this.T) {
                long tempoExecucao = this.tempoExecucao(rotas);
                tempoTotal += tempoExecucao;
                if (tempoExecucao > this.limite) {
                    System.out.printf("%nTamanho %d ultrapassou o limite de tempo com %d.%n", this.conjunto,
                            tempoExecucao);
                    lock = false;
                    break; // Interrompe as execuções para esse tamanho
                }
            }
            if (lock) {
                System.out.printf("%nTamanho %d - Tempo Médio: %d ms%n", this.conjunto, tempoTotal / this.execucoes);
                this.conjunto++;
            }
        }
        this.segundoTeste();
        this.terceiroTeste();
        this.quartoTeste();
    }

    /**
     * Segundo teste, utiliza o mesmo conjunto de tamanho T utilizado no teste
     * anterior. Em seguida, aumenta o tamanho dos conjuntos de T em T até atingir o
     * tamanho 10T, sempre executando 10 testes de cada tamanho para utilizar a
     * média.
     */
    private void segundoTeste() {
        ++this.algoritmo;
        System.out.print("\nSegundo teste");
        int conjuntoLocal = this.conjunto,
                conjuntoInicial = this.conjunto, // Salva o tamanho inicial dos conjuntos de T
                limiteTamanho = this.conjunto * 10; // 10T
        long tempoTotal = 0;
        this.segundoTeste.add(this.T); // Salva o conjunto de rotas de tamanho T
        for (int[] rotas : this.T) // Utiliza os mesmos conjuntos de tamanho T
            tempoTotal += this.tempoExecucao(rotas);
        System.out.printf("%nTamanho %d - Tempo: %d ms%n", conjuntoLocal, tempoTotal / this.T.size());
        while (conjuntoLocal <= limiteTamanho) { // Aumenta o tamanho dos conjuntos de T em T
            conjuntoLocal += conjuntoInicial;
            tempoTotal = 0;
            List<int[]> conjunto = GeradorDeProblemas.geracaoDeRotas(conjuntoLocal, this.execucoes, 0.5);
            this.segundoTeste.add(conjunto); // Salva o conjunto de rotas de tamanho T
            for (int[] rotas : conjunto) // Executa 10 testes de cada tamanho
                tempoTotal += this.tempoExecucao(rotas);
            System.out.printf("%nTamanho %d - Tempo Médio: %d ms%n", conjuntoLocal, tempoTotal / this.execucoes);
        }
    }

    /**
     * Utiliza os mesmos conjuntos de tamanho T alcancados no primeiro teste e faz
     * apenas um teste
     */
    private void terceiroTeste() {
        this.algoritmo++;
        System.out.print("\nTerceiro teste");
        long tempoTotal = 0;
        for (int[] rotas : this.T) // Utiliza os mesmos conjuntos de tamanho T
            tempoTotal += this.tempoExecucao(rotas);
        System.out.printf("%nTamanho %d - Tempo: %d ms%n", this.conjunto, tempoTotal / this.T.size());
    }

    /**
     * Utiliza os mesmos conjuntos feitos no segundo teste
     */
    private void quartoTeste() {
        this.algoritmo++;
        System.out.print("\nQuarto teste");
        for (int i = 0; i < this.segundoTeste.size(); i++) {
            long tempoTotal = 0;
            for (int[] rotas : this.segundoTeste.get(i)) // Utiliza os mesmos conjuntos de tamanho T
                tempoTotal += this.tempoExecucao(rotas);
            System.out.printf("%nTamanho %d - Tempo Médio: %d ms%n", this.conjunto * (i + 1),
                    tempoTotal / this.segundoTeste.get(i).size());
        }
    }

    /**
     * Função principal do programa
     * 
     * @param args Argumentos da linha de comando
     */
    public static void main(String[] args) {
        new App(new Distribuicao[] { new Backtracking(),
                new GulosoAcumulado(),
                // new GulosoOrdem(),
                new DivisaoConquista(),
                new ProgDinamica() },
                3, 6, 10, 30 * 1000)
                .testar();
    }
}
