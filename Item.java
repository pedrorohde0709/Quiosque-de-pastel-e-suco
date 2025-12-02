// =============================
// Classe abstrata Item
// -----------------------------
// Representa qualquer produto vendido no quiosque.
// Pastel e Suco vão herdar desta classe.
// =============================
public abstract class Item {

    // Sabor escolhido pelo cliente (ex.: "Calabresa", "Laranja" etc.)
    protected String sabor;

    // Preço do item, em reais
    protected double preco;

    // Retorna o preço do item
    public double getPreco() {
        return preco;
    }

    // Retorna o sabor do item
    public String getSabor() {
        return sabor;
    }

    // Representação básica do item (usada em resumos)
    @Override
    public String toString() {
        return getClass().getSimpleName() + " - " + sabor + " | R$ " + preco;
    }
}
