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

/**
 * Interface que define o método de distribuição de rotas entre caminhões
 */
public interface Distribuicao {
    /** Variável que define se o algoritmo deve imprimir as rotas distribuídas */
    public static final boolean PRINT = false;

    /**
     * Método que distribui as rotas entre os caminhões
     * 
     * @param rotas        vetor com as rotas a serem distribuídas
     * @param numCaminhoes número de caminhões disponíveis
     */
    public void distribuirRotas(int[] rotas, int numCaminhoes);
}
