import java.util.ArrayList;

// =============================
// Classe Atendimento
// -----------------------------
// Representa um pedido completo (vários itens) feito pelo cliente.
// Guarda número, itens e status.
// =============================
public class Atendimento {

    // Status possíveis do atendimento
    public static final String STATUS_EM_PREPARO = "Em preparo";
    public static final String STATUS_PRONTO = "Pronto";
    public static final String STATUS_ENTREGUE = "Entregue";

    // Contador estático para gerar números sequenciais de atendimento
    private static int contador = 1;

    // Número do atendimento (1, 2, 3, ...)
    private int numero;

    // Lista de itens deste atendimento
    private ArrayList<Item> itens = new ArrayList<>();

    // Status atual do atendimento
    private String status;

    // Construtor: define número automático e status inicial
    public Atendimento() {
        numero = contador++;
        status = STATUS_EM_PREPARO; // todo atendimento começa em preparo
    }

    // Retorna o número do atendimento
    public int getNumero() {
        return numero;
    }

    // Retorna o status atual
    public String getStatus() {
        return status;
    }

    // Atualiza o status (usado pelo gestor)
    public void setStatus(String status) {
        this.status = status;
    }

    // Adiciona um item a este atendimento
    public void adicionar(Item item) {
        itens.add(item);
    }

    // Soma os preços dos itens para obter o total
    public double calcularTotal() {
        return itens.stream().mapToDouble(Item::getPreco).sum();
    }

    // Representação DETALHADA do atendimento (usada em relatórios)
    public String detalhes() {
        StringBuilder sb = new StringBuilder();

        sb.append("Atendimento Nº ").append(numero).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Itens:\n");
        for (Item i : itens) {
            sb.append(" - ").append(i).append("\n");
        }
        sb.append("Total: R$ ").append(calcularTotal()).append("\n");

        return sb.toString();
    }

    // Representação CURTA do atendimento (usada no ComboBox do gestor)
    @Override
    public String toString() {
        return "Atendimento Nº " + numero;
    }
}
