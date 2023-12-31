# Tratamento de Problemas NP - Melhor Rota

“Uma empresa de distribuição e logística possui uma frota composta por N caminhões. Semanalmente, esta empresa organiza suas entregas em M rotas, as quais devem ser distribuídas entre os caminhões disponíveis. A empresa deseja fazer a distribuição de maneira que cada caminhão cumpra a mesma quilometragem, evitando assim que ao final do período existam caminhões ociosos enquanto outros ainda estão executando várias rotas. Se não for possível cumprir a mesma quilometragem, que a diferença entre a quilometragem dos caminhões seja a menor possível, diminuindo o problema.

Por exemplo, suponha a existência de 3 caminhões e 10 rotas com as seguintes quilometragens: 35, 34, 33, 23, 21, 32, 35, 19, 26, 42. Dentre as distribuições D1 e D2 abaixo, D1 seria considerada melhor.

D1
Caminhão 1: rotas 21, 32, 42 - total 95km
Caminhão 2: rotas 35, 34, 26 - total 95km
Caminhão 3: rotas 23, 19, 35, 33 - total 110km

D2
Caminhão 1: rotas 35, 33, 32, 42 - total 142km
Caminhão 2: rotas 35, 19, 26 - total 80km
Caminhão 3: rotas 23, 34, 21 - total 78km”

Neste repositório, há 4 métodos de solução comuns para o problema apresentado.

Estes são: backtracking, algoritmo guloso, divisão e conquista e programação dinâmica.

Veja o [relatório](pdf/relatório.pdf) para mais informações.

Best-route is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

Best-route is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

See [LICENSE](LICENSE) for more information.
