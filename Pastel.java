import java.util.HashMap;

// =============================
// Classe Pastel
// -----------------------------
// Especialização de Item para representar um pastel.
// Possui uma tabela de sabores x preços.
// =============================
public class Pastel extends Item {

    // Tabela com o preço de cada sabor de pastel
    private static final HashMap<String, Double> TABELA_PRECOS = new HashMap<>();

    // Bloco estático: executa uma vez, preenchendo a tabela de preços
    static {
        TABELA_PRECOS.put("Carne", 8.0);
        TABELA_PRECOS.put("Queijo", 7.0);
        TABELA_PRECOS.put("Pizza", 9.0);
        TABELA_PRECOS.put("Frango", 8.5);
        TABELA_PRECOS.put("Calabresa", 9.0);
        TABELA_PRECOS.put("4 Queijos", 10.0);
        TABELA_PRECOS.put("Chocolate", 8.0);
        TABELA_PRECOS.put("Romeu e Julieta", 9.0);
        TABELA_PRECOS.put("Bacon", 9.5);
        TABELA_PRECOS.put("Strogonoff", 10.0);
    }

    // Construtor: recebe o sabor e define o preço procurando na tabela
    public Pastel(String sabor) {
        this.sabor = sabor;
        // Se o sabor não estiver na tabela, retorna 0.0 (para não travar o sistema)
        this.preco = TABELA_PRECOS.getOrDefault(sabor, 0.0);
    }
}
