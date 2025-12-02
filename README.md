# 🧾 README – Sistema de Quiosque (Java)

## 📌 Descrição do Projeto
Este projeto implementa um **sistema de atendimento para um quiosque**, desenvolvido em Java.  
O sistema permite gerenciar pedidos, adicionar itens (pastéis e sucos), atualizar status e visualizar relatórios.

Ele foi projetado de forma modular, com classes separadas para facilitar manutenção, leitura e futuras expansões (como interface gráfica com JavaFX ou integração web).

---

## 📁 Estrutura do Projeto

```
📦 ProjetoQuiosque
 ├── Main.java
 ├── QuiosqueSistema.java
 ├── Atendimento.java
 ├── Item.java
 ├── Pastel.java
 ├── Suco.java
 └── run.bat
```

### 📄 Função de cada arquivo

| Arquivo | Descrição |
|--------|-----------|
| **Main.java** | Ponto de entrada da aplicação. Inicia o sistema. |
| **QuiosqueSistema.java** | Gerencia pedidos, itens e toda a lógica principal do sistema. |
| **Atendimento.java** | Representa um pedido contendo os itens e o status. |
| **Item.java** | Classe base para todos os produtos. |
| **Pastel.java** | Subclasse de Item representando um pastel. |
| **Suco.java** | Subclasse de Item representando um suco. |
| **run.bat** | Script para compilar e executar automaticamente (Windows). |

---

## ▶️ Como Executar

### ✔️ Opção 1 — Usando o `run.bat` (Windows)
Abra o arquivo:

```
run.bat
```

Ele irá:

1. Compilar todos os arquivos `.java`
2. Executar o programa automaticamente

---

### ✔️ Opção 2 — Compilando manualmente (Windows / Linux / Mac)

No terminal, dentro da pasta do projeto:

#### **1. Compilar**
```bash
javac *.java
```

#### **2. Executar**
```bash
java Main
```

---

## 🧪 Como Testar o Sistema (Testes Manuais)

### 🔹 Criar um pedido
- Adicione itens (pastel ou suco)
- Eles são listados no pedido atual

### 🔹 Remover item
- A versão atual remove sempre o **último item**
- A remoção por índice pode ser adicionada futuramente

### 🔹 Finalizar pedido
- Envia o pedido para a área do gestor
- A aba do cliente mostra somente pedidos ativos

### 🔹 Atualizar status
Aba do gestor permite alterar para:

- **Em preparo**
- **Pronto**
- **Entregue** (remove automaticamente da área do cliente)

### 🔹 Relatórios
- Exibe pedidos já finalizados / entregues

---

## 🛠️ Melhorias possíveis (Próximas versões)

- Função para remover **qualquer item**, não apenas o último  
- Cálculo automático de **faturamento diário** com filtro por data  
- Gestor pode **criar novos produtos** sem alterar código  
- Interface JavaFX com atualização automática  
- Persistência em arquivo ou banco de dados

---

## 💻 Requisitos

- Java 8 ou superior  
- Terminal / CMD  
- (Opcional) VS Code com extensão “Extension Pack for Java”

---