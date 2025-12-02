import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// =============================
// Classe Main (JavaFX Application)
// -----------------------------
// Responsável por montar a interface gráfica do cliente e do gestor.
// =============================
public class Main extends Application {

    // Objeto com as regras de negócio do quiosque
    private QuiosqueSistema sistema = new QuiosqueSistema();

    // Flag usada para saber se o cliente já finalizou o pedido e vai iniciar outro
    private boolean aguardandoNovoPedido = false;

    // Componentes que o gestor precisa atualizar de vários lugares
    private TextArea txtPedidoAtual;
    private TextArea txtRelatorio;
    private ComboBox<Atendimento> comboAtendimentos;

    // TextArea de resumo do lado do cliente (virou atributo para ser limpo quando entregar)
    private TextArea resumoCliente;

    @Override
    public void start(Stage stage) {

        // --------- FUNDO LARANJA FORTE (Janela inteira) ---------
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ff8c00;"); // laranja forte de fundo

        // -------------------------------------------------------------------
        // LADO DO CLIENTE
        // -------------------------------------------------------------------
        VBox clienteBox = new VBox(12);
        clienteBox.setPadding(new Insets(20));
        // Retângulo laranja claro com cantos arredondados
        clienteBox.setStyle("-fx-background-color: #ffcc99; -fx-background-radius: 18;");
        clienteBox.setPrefWidth(520);

        Label tituloCliente = new Label("Área do Cliente");
        tituloCliente.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // ComboBox para escolher tipo (Pastel / Suco)
        ComboBox<String> tipo = new ComboBox<>();
        tipo.getItems().addAll("Pastel", "Suco");
        tipo.setPromptText("Tipo do produto");

        // ComboBox de sabor (preenchido conforme o tipo)
        ComboBox<String> sabor = new ComboBox<>();
        sabor.setPromptText("Escolha o tipo primeiro");

        // Quando o usuário escolhe o tipo, preenche a lista de sabores
        tipo.setOnAction(e -> {
            sabor.getItems().clear();
            if ("Pastel".equals(tipo.getValue())) {
                sabor.getItems().addAll(
                        "Carne","Queijo","Pizza","Frango","Calabresa",
                        "4 Queijos","Chocolate","Romeu e Julieta","Bacon","Strogonoff"
                );
            } else if ("Suco".equals(tipo.getValue())) {
                sabor.getItems().addAll(
                        "Laranja","Abacaxi","Morango","Manga","Limão",
                        "Uva","Maracujá","Coco","Goiaba","Acerola"
                );
            }
        });

        // Área de texto para mostrar SOMENTE o pedido atual do cliente
        resumoCliente = new TextArea();
        resumoCliente.setPromptText("Resumo do pedido (apenas pedido atual)");
        resumoCliente.setPrefHeight(260);

        // Label que mostra o status do pedido para o cliente (com cor)
        Label statusLabel = new Label("Status do pedido: nenhum pedido em andamento");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-padding: 8; "
                + "-fx-background-color: #eeeeee; -fx-text-fill: #333333; "
                + "-fx-background-radius: 10;");

        // Botões de ações do cliente
        Button add = new Button("Adicionar");
        Button rem = new Button("Remover Último");
        Button fim = new Button("Finalizar Pedido");

        // --------------------- Ação: Adicionar item ---------------------
        add.setOnAction(e -> {
            // Se o pedido anterior foi finalizado, limpar a área para um novo pedido
            if (aguardandoNovoPedido) {
                resumoCliente.clear();
                aguardandoNovoPedido = false;
            }

            if (tipo.getValue() == null || sabor.getValue() == null) {
                resumoCliente.appendText("Escolha tipo e sabor.\n");
                return;
            }
            Item item = sistema.criarItem(tipo.getValue(), sabor.getValue());
            sistema.adicionarItem(item);

            // Em vez de ficar adicionando linhas, recriamos o resumo inteiro
            resumoCliente.setText(sistema.montarResumoPedidoAtual());
        });

        // --------------------- Ação: Remover último item ---------------------
        rem.setOnAction(e -> {
            sistema.removerUltimo();
            // Apenas atualiza o resumo com a lista atual de itens (sem mensagem extra)
            resumoCliente.setText(sistema.montarResumoPedidoAtual());
        });

        // --------------------- Ação: Finalizar pedido do CLIENTE ---------------------
        fim.setOnAction(e -> {
            Atendimento at = sistema.finalizarAtendimento();

            // Depois de finalizado, mostramos resumo com número e total
            resumoCliente.appendText("\nPedido FINALIZADO Nº " + at.getNumero() +
                    "\nTotal: R$ " + at.calcularTotal() + "\n");

            // Marca que o próximo "Adicionar" limpará a área para um novo pedido
            aguardandoNovoPedido = true;

            // Atualiza a label de status (todo pedido começa Em preparo)
            atualizarStatusLabel(statusLabel, at.getStatus());

            // ATUALIZA a visão do gestor automaticamente (sem apertar botão lá)
            atualizarDadosGestor(statusLabel);
        });

        // Monta o layout do lado do cliente
        clienteBox.getChildren().addAll(
                tituloCliente, tipo, sabor, add, rem, fim, resumoCliente, statusLabel
        );

        // -------------------------------------------------------------------
        // LADO DO GESTOR
        // -------------------------------------------------------------------
        VBox gestorBox = new VBox(12);
        gestorBox.setPadding(new Insets(20));
        gestorBox.setStyle("-fx-background-color: #ffcc99; -fx-background-radius: 18;");
        gestorBox.setPrefWidth(520);

        Label tituloGestor = new Label("Área do Gestor");
        tituloGestor.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Campo que mostra somente o pedido atual (último atendimento criado)
        Label lblAtual = new Label("Pedido atual:");
        txtPedidoAtual = new TextArea();
        txtPedidoAtual.setPrefHeight(150);

        // Campo que mostra o RELATÓRIO de todos os pedidos (já na lista assim que são criados)
        Label lblRel = new Label("Relatório de pedidos:");
        txtRelatorio = new TextArea();
        txtRelatorio.setPrefHeight(160);

        // ComboBox com os atendimentos, mas exibindo só "Atendimento Nº X"
        comboAtendimentos = new ComboBox<>();
        comboAtendimentos.setPromptText("Selecione um atendimento");

        // ComboBox para o gestor escolher o NOVO status desejado
        ComboBox<String> comboStatus = new ComboBox<>();
        comboStatus.getItems().addAll(
                Atendimento.STATUS_EM_PREPARO,
                Atendimento.STATUS_PRONTO,
                Atendimento.STATUS_ENTREGUE
        );
        comboStatus.setPromptText("Novo status");

        // Botão para aplicar a mudança de status
        Button btnStatus = new Button("Atualizar Status");

        // --------------------- Ação: Atualizar status pelo GESTOR ---------------------
        btnStatus.setOnAction(e -> {
            Atendimento selecionado = comboAtendimentos.getValue();
            String novoStatus = comboStatus.getValue();

            if (selecionado == null || novoStatus == null) {
                txtRelatorio.appendText("Selecione um atendimento e um status.\n");
                return;
            }

            // Atualiza o status do atendimento selecionado
            selecionado.setStatus(novoStatus);

            // Atualiza telas do gestor (pedido atual + relatório)
            atualizarDadosGestor(statusLabel);

            // Se o atendimento alterado for o último (pedido atual do cliente),
            // atualiza também a label de status do lado do cliente
            if (selecionado == sistema.getUltimoAtendimento()) {
                atualizarStatusLabel(statusLabel, novoStatus);

                // Se o gestor marcou como ENTREGUE:
                //  - some da área de pedido atual (gestor)
                //  - some também do resumo do cliente
                if (Atendimento.STATUS_ENTREGUE.equals(novoStatus)) {
                    txtPedidoAtual.clear();
                    resumoCliente.clear();
                }
            }
        });

        // Monta o layout do lado do gestor
        gestorBox.getChildren().addAll(
                tituloGestor,
                lblAtual,
                txtPedidoAtual,
                lblRel,
                txtRelatorio,
                new Label("Alterar status do atendimento:"),
                comboAtendimentos,
                comboStatus,
                btnStatus
        );

        // -------------------------------------------------------------------
        // ORGANIZAÇÃO GERAL NA TELA (Cliente à esquerda, Gestor à direita)
        // -------------------------------------------------------------------
        HBox center = new HBox(40, clienteBox, gestorBox);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(40));

        root.setCenter(center);

        // Cria a cena e exibe a janela
        Scene scene = new Scene(root, 1300, 750);
        stage.setScene(scene);
        stage.setTitle("Quiosque de Pastéis e Sucos");
        stage.show();

        // Garante que a visão do gestor começa sincronizada (sem nada)
        atualizarDadosGestor(statusLabel);
    }

    // ============================================================
    // Método auxiliar: atualiza o texto e a cor da label de status
    // ============================================================
    private void atualizarStatusLabel(Label label, String status) {
        String base = "-fx-font-size: 14px; -fx-padding: 8; -fx-background-radius: 10;";

        String estilo;
        if (Atendimento.STATUS_EM_PREPARO.equals(status)) {
            // Vermelho
            estilo = base + " -fx-background-color: #ef4444; -fx-text-fill: white;";
        } else if (Atendimento.STATUS_PRONTO.equals(status)) {
            // Azul
            estilo = base + " -fx-background-color: #3b82f6; -fx-text-fill: white;";
        } else if (Atendimento.STATUS_ENTREGUE.equals(status)) {
            // Verde
            estilo = base + " -fx-background-color: #22c55e; -fx-text-fill: white;";
        } else {
            // Estilo neutro
            estilo = base + " -fx-background-color: #eeeeee; -fx-text-fill: #333333;";
        }

        label.setText("Status do pedido: " + status);
        label.setStyle(estilo);
    }

    // ============================================================
    // Método auxiliar: atualiza a parte do GESTOR
    //  - txtPedidoAtual: mostra o último atendimento (pedido atual)
    //  - txtRelatorio: mostra TODOS os atendimentos já existentes
    //  - comboAtendimentos: lista de atendimentos para mudar status
    //  - statusLabel: pode ser ajustado junto, se necessário
    // ============================================================
    private void atualizarDadosGestor(Label statusLabel) {

        // Limpa as áreas de texto
        txtPedidoAtual.clear();
        txtRelatorio.clear();

        // Mostra o último atendimento (se existir) na área "Pedido atual"
        Atendimento ultimo = sistema.getUltimoAtendimento();
        if (ultimo != null) {
            txtPedidoAtual.appendText(ultimo.detalhes());
            // Também garante que o status mostrado no cliente está alinhado
            atualizarStatusLabel(statusLabel, ultimo.getStatus());
        }

        // Relatório: escreve TODOS os atendimentos da lista,
        // inclusive os recém-criados (não precisa clicar em botão).
        for (Atendimento at : sistema.getAtendimentos()) {
            txtRelatorio.appendText(at.detalhes());
            txtRelatorio.appendText("------------------\n");
        }

        // Atualiza o ComboBox de atendimentos
        comboAtendimentos.getItems().setAll(sistema.getAtendimentos());
    }

    // Método main tradicional para iniciar a aplicação JavaFX
    public static void main(String[] args) {
        launch();
    }
}
