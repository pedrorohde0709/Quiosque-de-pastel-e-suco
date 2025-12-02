import java.util.HashMap;

// =============================
// Classe Suco
// -----------------------------
// Especialização de Item para representar um suco.
// Também possui tabela de sabores x preços.
// =============================
public class Suco extends Item {

    // Tabela com o preço de cada sabor de suco
    private static final HashMap<String, Double> TABELA_PRECOS = new HashMap<>();

    // Bloco estático: executa uma vez preenchendo a tabela
    static {
        TABELA_PRECOS.put("Laranja", 6.0);
        TABELA_PRECOS.put("Abacaxi", 5.5);
        TABELA_PRECOS.put("Morango", 7.0);
        TABELA_PRECOS.put("Manga", 6.5);
        TABELA_PRECOS.put("Limão", 5.0);
        TABELA_PRECOS.put("Uva", 7.0);
        TABELA_PRECOS.put("Maracujá", 6.0);
        TABELA_PRECOS.put("Coco", 7.5);
        TABELA_PRECOS.put("Goiaba", 6.0);
        TABELA_PRECOS.put("Acerola", 5.5);
    }

    // Construtor: recebe o sabor e define o preço procurando na tabela
    public Suco(String sabor) {
        this.sabor = sabor;
        this.preco = TABELA_PRECOS.getOrDefault(sabor, 0.0);
    }
}
