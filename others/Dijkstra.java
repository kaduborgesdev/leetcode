import java.util.*;

public class Djikstra {

    public static void main(String[] args) {
        Cidade brasilia = new Cidade("Brasília", new HashMap<>());
        Cidade beloHorizonte = new Cidade("Belo Horizonte", new HashMap<>());
        Cidade rioDeJaneiro = new Cidade("Rio de Janeiro", new HashMap<>());
        Cidade vitoria = new Cidade("Vitória", new HashMap<>());
        Cidade saoPaulo = new Cidade("São Paulo", new HashMap<>());
        Cidade salvador = new Cidade("Salvador", new HashMap<>());

        beloHorizonte.verticesAdjacentes().put(brasilia, 2000.0);
        beloHorizonte.verticesAdjacentes().put(saoPaulo, 220.0);
        brasilia.verticesAdjacentes().put(salvador, 100.0);
        rioDeJaneiro.verticesAdjacentes().put(beloHorizonte, 80.0);
        rioDeJaneiro.verticesAdjacentes().put(salvador, 1.0);
        saoPaulo.verticesAdjacentes().put(beloHorizonte, 100.00);
        saoPaulo.verticesAdjacentes().put(vitoria, 410.00);
        saoPaulo.verticesAdjacentes().put(brasilia, 140.00);
        vitoria.verticesAdjacentes().put(brasilia, 500.0);
        vitoria.verticesAdjacentes().put(beloHorizonte, 200.0);
        vitoria.verticesAdjacentes().put(rioDeJaneiro, 100.0);

        System.out.println(calculaMenorCaminho(beloHorizonte, salvador));
    }

    public static List<Cidade> calculaMenorCaminho(Cidade origem, Cidade destino){
        List<Cidade> menorCaminho = new ArrayList<>();
        Map<Cidade, Double> menoresCaminhos = new HashMap<>();
        Map<Cidade, Cidade> menoresAntecessores = new HashMap<>();
        PriorityQueue<Cidade> naoAnalisados = new PriorityQueue<>(Comparator.comparing(Cidade::nome));
        Set<Cidade> analisados = new HashSet<>();

        Cidade cidadeAtual = origem;
        menoresCaminhos.put(origem, 0.0);

        while(cidadeAtual != null){
            // marquei como analisado
            analisados.add(cidadeAtual);
            naoAnalisados.poll();
            // iterando sobre os adjacentes
            for(Map.Entry<Cidade, Double> adjacente : cidadeAtual.verticesAdjacentes().entrySet()) {
                // verifica se já foi analisado
                if(!analisados.contains(adjacente.getKey())){
                    naoAnalisados.offer(adjacente.getKey());
                }
                // verificar menores valores do adjacente
                Double valorAtual = menoresCaminhos.get(cidadeAtual) + adjacente.getValue();
                Double valorAdjacente = menoresCaminhos.get(adjacente.getKey());
                if(valorAdjacente == null || valorAtual < valorAdjacente) {
                    // menor caminho até o adjacente
                    menoresCaminhos.put(adjacente.getKey(), valorAtual);
                    /* Cidade anterior ao menor caminho
                       Embora a ordem seja [Cidade Atual -> Cidade Adjacente],
                       a chave do mapa precisa ser a cidade adjacente, pois é o valor
                       que muda quando um caminho mais barato aparece.
                       Do contrário, teríamos que realizar buscar olhando pelo valor,
                       o que geraria buscas O(N). */
                    menoresAntecessores.put(adjacente.getKey(), cidadeAtual);
                }
            }
            // fazer com que a cidade atual seja a cidade não analisada com o menor custo
            cidadeAtual = naoAnalisados.peek();
        } // todos os caminhos estão calculados até aqui

        /* Começar buscando pelo destino nos fará utilizar todas as chaves
           do HashMap, ou seja, as buscas serão O(1), mas o resultado final
           estará invertido. Daí a necessidade de interter a ordem depois. */
        Cidade cidadeAnterior = destino;
        do {
            menorCaminho.add(cidadeAnterior);
            cidadeAnterior = menoresAntecessores.get(cidadeAnterior);
        } while(cidadeAnterior != origem);
        menorCaminho.add(origem);

        Collections.reverse(menorCaminho);
        return menorCaminho;
    }
}

record Cidade(String nome, Map<Cidade, Double> verticesAdjacentes) {
    @Override
    public String toString(){
        return this.nome();
    }
    @Override
    public int hashCode(){
        return this.nome().hashCode();
    }
    @Override
    public boolean equals(Object o){
        return this.nome().equals(((Cidade)o).nome());
    }
}
