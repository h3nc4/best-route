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

/**
 * Classe abstrata que implementa a interface Distribuicao e serve de base para
 * implementar a estratégia gulosa
 */
public abstract class Guloso implements Distribuicao {
    /** Array com as rotas a serem distribuídas */
    int[] rotas;
    /** Número de caminhões disponíveis */
    int caminhoes;

    /**
     * Construtor da classe Guloso
     */
    public Guloso() {
    }

    /**
     * Construtor da classe Guloso
     * 
     * @param rotas     vetor com as rotas a serem distribuídas
     * @param caminhoes número de caminhões disponíveis
     */
    Guloso(int[] rotas, int caminhoes) {
        this.rotas = Arrays.copyOf(rotas, rotas.length);
        Arrays.sort(this.rotas);
        this.caminhoes = caminhoes;
    }
}
