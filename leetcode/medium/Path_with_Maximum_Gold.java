class Solution {

    public int getMaximumGold(int[][] grid) {

        int maiorValor = 0;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j] != 0){
                    int valor = calculaMaiorCaminho(i, j, grid);
                    maiorValor = valor > maiorValor ? valor : maiorValor;
                }
            }
        }
        return maiorValor;
    }

    public int calculaMaiorCaminho(int i, int j, int[][] grid){
        int quantidade = 0;
        // marca o item como visitado
        grid[i][j] *= -1;
        // pode testar pra baixo
        if(!(i == grid.length - 1) && grid[i+1][j] > 0) {
            int valor = calculaMaiorCaminho(i+1, j, grid);
            quantidade = valor > quantidade ? valor : quantidade;
        }
        // pode testar pra cima
        if(!(i == 0) && grid[i-1][j] > 0) {
            int valor = calculaMaiorCaminho(i-1, j, grid);
            quantidade = valor > quantidade ? valor : quantidade;
        }
        // pode testar pra direita
        if(!(j == grid[i].length - 1) && grid[i][j+1] > 0) {
            int valor = calculaMaiorCaminho(i, j+1, grid);
            quantidade = valor > quantidade ? valor : quantidade;
        }
        // pode testar pra esquerda
        if(!(j == 0) && grid[i][j-1] > 0) {
            int valor = calculaMaiorCaminho(i, j-1, grid);
            quantidade = valor > quantidade ? valor : quantidade;
        }
        grid[i][j] *= -1;
        return quantidade + grid[i][j];
    }
    
}
