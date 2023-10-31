class Solution {
    public int findCircleNum(int[][] isConnected) {
        UnionFind uf = new UnionFind(isConnected);
        return uf.provinces;
    }
}

// O(Vertices + Edges)
public class UnionFind {

    int[] parent;
    int[] rank;
    int provinces = 0;

    UnionFind(int[][] cities) {
        parent = new int[cities.length];
        rank = new int[cities.length];
        provinces = cities.length;

        // O(N)
        for(int i = 0; i < cities.length; i++) {
            parent[i] = i;
        }

        for(int i = 0; i < cities.length; i++) {
            for(int j = 0; j < cities[i].length; j++) {
                if(cities[i][j] == 1) {
                    if(union(i, j)) {
                        provinces--;
                    }
                }
            }
        }
    }

    // encontrar o parent do vértice
    // path compression (técnica)
    // performance amortizada O(1)
    int find(int node) {
        if(parent[node] != node) {
            return parent[node] = find(parent[node]);
        }
        return node;
    }

    // junta os dois nodes em um mesmo grafo
    boolean union(int nodeA, int nodeB) {

        int parentA = find(nodeA);
        int parentB = find(nodeB);

        if(parentA == parentB) {
            return false;
        }
        
        if(rank[parentA] > rank[parentB]) {
            parent[parentB] = parentA;
        } else if(rank[parentA] < rank[parentB]) {
            parent[parentA] = parentB;
        } else {
            parent[parentB] = parentA;
            rank[parentA]++;
        }
        return true;
    }
}
