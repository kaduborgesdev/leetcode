class ArrayTrie {
  
    Letra dicionario;
    static final int FIM_DE_PALAVRA = 26;
    static final char BASE = 'a';
  
    public ArrayTrie() {
        this.dicionario = new Letra('#');
    }

  public void insert(String palavra) {
        if(palavra != null && palavra.length() > 0) {
            Letra letraAtual = this.dicionario;
            for(char caractere : palavra.toCharArray()) {
                int indice = caractere - BASE;
                if(letraAtual.proximos[indice] == null) {
                    letraAtual.proximos[indice] = new Letra(caractere);
                }
                letraAtual = letraAtual.proximos[indice];
                letraAtual.quantidade++;
            }
            if(letraAtual.proximos[FIM_DE_PALAVRA] == null) {
                letraAtual.proximos[FIM_DE_PALAVRA] = new Letra('*');
            }
            letraAtual.proximos[FIM_DE_PALAVRA].quantidade++;
        }
    }

    public int countWordsEqualTo(String palavra) {
        int resultado = 0;
        if(palavra != null && palavra.length() > 0) {
            Letra letraAtual = this.dicionario;
            boolean encontrou = true;
            for(char caractere : palavra.toCharArray()) {
                int indice = caractere - BASE;
                letraAtual = letraAtual.proximos[indice];
                if(letraAtual == null) {
                    encontrou = false;
                    break;
                }
            }
            if(encontrou && letraAtual.proximos[FIM_DE_PALAVRA] != null) {
                resultado = letraAtual.proximos[FIM_DE_PALAVRA].quantidade;
            }
        }
        return resultado;
    }

    public int countWordsStartingWith(String prefixo) {
        int resultado = 0;
        if(prefixo != null && prefixo.length() > 0) {
            Letra letraAtual = this.dicionario;
            boolean encontrou = true;
            for(char caractere : prefixo.toCharArray()) {
                int indice = caractere - BASE;
                letraAtual = letraAtual.proximos[indice];
                if(letraAtual == null) {
                    encontrou = false;
                    break;
                }
            }
            if(encontrou) {
                resultado = letraAtual.quantidade;
            }
        }
        return resultado;
    }

    public void erase(String palavra) {
        if(palavra != null && palavra.length() > 0) {
            Letra letraAtual = this.dicionario;
            excluir(letraAtual, palavra, 0);
        }
    }

    private boolean excluir(Letra letraAtual, String palavra, int nivel) {
        boolean resultado = true;
        if(letraAtual.letra == '*') {
            letraAtual.quantidade--;
        } else {
            int indice;
            if (nivel == palavra.length()) {
                indice = FIM_DE_PALAVRA;
            } else {
                indice = palavra.charAt(nivel) - BASE;
            }
            Letra proximaLetra = letraAtual.proximos[indice];
            if (proximaLetra == null) {
                resultado = false;
            } else if (excluir(proximaLetra, palavra, nivel + 1)) {
                letraAtual.quantidade--;
            }
        }
        return resultado;
    }

    private class Letra {
        final char letra;
        final Letra[] proximos;
        int quantidade;
        Letra(char letra) {
            this.proximos = new Letra[27];
            this.letra = letra;
            this.quantidade = 0;
        }
    }
}

// ################################################################################################

class HashTrie {

    Letra dicionario;
    static final char FIM_DE_PALAVRA = '*';

    public HashTrie() {
        this.dicionario = new Letra('#');
    }

    public void insert(String palavra) {
        if(palavra != null && palavra.length() > 0) {
            Letra letraAtual = this.dicionario;
            for(char caractere : palavra.toCharArray()) {
                if(!letraAtual.proximos.containsKey(caractere)) {
                    letraAtual.proximos.put(caractere, new Letra(caractere));
                }
                letraAtual = letraAtual.proximos.get(caractere);
                letraAtual.quantidade++;
            }
            if(!letraAtual.proximos.containsKey(FIM_DE_PALAVRA)) {
                letraAtual.proximos.put(FIM_DE_PALAVRA, new Letra(FIM_DE_PALAVRA));
            }
            letraAtual.proximos.get(FIM_DE_PALAVRA).quantidade++;
        }
    }

    public int countWordsEqualTo(String palavra) {
        int resultado = 0;
        if(palavra != null && palavra.length() > 0) {
            Letra letraAtual = this.dicionario;
            boolean encontrou = true;
            for(char caractere : palavra.toCharArray()) {
                letraAtual = letraAtual.proximos.get(caractere);
                if(letraAtual == null) {
                    encontrou = false;
                    break;
                }
            }
            if(encontrou && letraAtual.proximos.containsKey(FIM_DE_PALAVRA)) {
                resultado = letraAtual.proximos.get(FIM_DE_PALAVRA).quantidade;
            }
        }
        return resultado;
    }

    public int countWordsStartingWith(String prefixo) {
        int resultado = 0;
        if(prefixo != null && prefixo.length() > 0) {
            Letra letraAtual = this.dicionario;
            boolean encontrou = true;
            for(char caractere : prefixo.toCharArray()) {
                letraAtual = letraAtual.proximos.get(caractere);
                if(letraAtual == null) {
                    encontrou = false;
                    break;
                }
            }
            if(encontrou) {
                resultado = letraAtual.quantidade;
            }
        }
        return resultado;
    }

    public void erase(String palavra) {
        if(palavra != null && palavra.length() > 0) {
            Letra letraAtual = this.dicionario;
            excluir(letraAtual, palavra, 0);
        }
    }

    private boolean excluir(Letra letraAtual, String palavra, int indice) {
        boolean resultado = true;
        
        if(letraAtual.letra == FIM_DE_PALAVRA) {
            letraAtual.quantidade--;
        } else {
            char letra;
            if (indice == palavra.length()) {
                letra = FIM_DE_PALAVRA;
            } else {
                letra = palavra.charAt(indice);
            }
            Letra proximaLetra = letraAtual.proximos.get(letra);
            if (proximaLetra == null) {
                resultado = false;
            } else if (excluir(proximaLetra, palavra, indice + 1)) {
                letraAtual.quantidade--;
            }
        }        
        return resultado;
    }

    private class Letra {
        final char letra;
        final Map<Character, Letra> proximos;
        int quantidade;
        Letra(char letra) {
            this.proximos = new HashMap<>();
            this.letra = letra;
            this.quantidade = 0;
        }
    }
}
