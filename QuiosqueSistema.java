import java.util.ArrayList;

// =============================
// Classe QuiosqueSistema
// -----------------------------
// Responsável pelas regras de negócio do quiosque.
// Guarda o pedido atual (itens temporários) e a lista de atendimentos já criados.
// =============================
public class QuiosqueSistema {

    // Lista de itens que o cliente está montando AGORA (antes de finalizar)
    private ArrayList<Item> pedidoAtual = new ArrayList<>();

    // Lista de todos os atendimentos já finalizados
    private ArrayList<Atendimento> atendimentos = new ArrayList<>();

    // Referência para o último atendimento criado (pedido mais recente)
    private Atendimento ultimoAtendimento;

    // Cria um Item (Pastel ou Suco) conforme o tipo e sabor escolhidos
    public Item criarItem(String tipo, String sabor) {
        if ("Pastel".equals(tipo)) {
            return new Pastel(sabor);
        }
        return new Suco(sabor);
    }

    // Adiciona um item ao pedido atual do cliente
    public void adicionarItem(Item i) {
        pedidoAtual.add(i);
    }

    // Remove o último item que foi adicionado ao pedido atual
    public void removerUltimo() {
        if (!pedidoAtual.isEmpty()) {
            pedidoAtual.remove(pedidoAtual.size() - 1);
        }
    }

    // -----------------------------
    // monta um texto com todos os itens do pedido atual
    // (usado para atualizar o TextArea do cliente sempre que
    //  algo é adicionado ou removido)
    // -----------------------------
    public String montarResumoPedidoAtual() {
        StringBuilder sb = new StringBuilder();
        for (Item i : pedidoAtual) {
            sb.append("Adicionado: ").append(i).append("\n");
        }
        return sb.toString();
    }

    // Finaliza o pedido atual, criando um novo Atendimento
    public Atendimento finalizarAtendimento() {
        Atendimento at = new Atendimento();
        for (Item i : pedidoAtual) {
            at.adicionar(i);
        }
        atendimentos.add(at);
        ultimoAtendimento = at;
        pedidoAtual.clear(); // esvazia o carrinho para o próximo pedido
        return at;
    }

    // Retorna a lista de todos os atendimentos
    public ArrayList<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    // Retorna o último atendimento (pedido atual para o gestor)
    public Atendimento getUltimoAtendimento() {
        return ultimoAtendimento;
    }
}
