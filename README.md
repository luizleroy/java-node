# Microsserviço 1: Checkout em Java
# Tecnologia Relevante: Utilização do MICRONAUT para performance

## Requisitos Funcionais

### Criação de Pedidos
- Criar pedidos com informações detalhadas (itens, preços, quantidade, impostos, etc.).
- Gerar um ID único para cada pedido.

### Estado do Pedido
- Gerenciar os estados do pedido (Ex.: `Criado`, `Pagamento Pendente`, `Pago`, `Cancelado`).

### Integração com o Payment Gateway
- Enviar o pedido para o microsserviço de pagamento com informações relevantes (ID do pedido, valor total, métodos aceitos, etc.).
- Receber notificações de sucesso ou falha no pagamento.

### Cancelamento de Pedidos
- Atualizar o estado para `Cancelado` em caso de falha de pagamento.
- Realizar ações compensatórias, como liberar estoque reservado.

### Consulta de Pedidos
- Permitir que o cliente ou sistema acesse o estado atual de um pedido (ex.: via API REST).

### Reserva de Estoque
- Reservar os itens no estoque enquanto o pagamento está pendente.
- Liberar a reserva em caso de cancelamento.

## Requisitos Não Funcionais
- APIs RESTful com autenticação (por exemplo, OAuth2).
- Alta disponibilidade para evitar indisponibilidade durante picos de transação.
- Log detalhado das transações para auditoria.
- Tolerância a falhas ao lidar com comunicação com o Payment Gateway.

# Microsserviço 2: Payment
# Tecnologia Relevante: Utilização de GraphQL para flexibilidade

## Requisitos Funcionais

### Processamento de Pagamentos
- Suporte para múltiplos métodos de pagamento (cartão de crédito, débito, carteira digital, etc.).
- Validar detalhes de pagamento (ex.: número do cartão, CVV, saldo).

### Notificação de Status
- Retornar o status do pagamento (ex.: `Sucesso`, `Falha`, `Pendente`) ao serviço de Checkout.
- Reenviar notificações em caso de falhas temporárias.

### Gerenciamento de Falhas
- Detectar e registrar falhas no pagamento.
- Notificar o Checkout sobre o motivo da falha (ex.: saldo insuficiente, erro técnico).

### Reembolso (se aplicável)
- Gerar reembolsos automáticos para transações que falhem após o pedido ser confirmado.

### Histórico de Transações
- Manter registros de todas as transações processadas para consultas e auditorias.


# Critérios para Escolher Java em checkout

## Complexidade e Escalabilidade
- Java é mais adequado para microsserviços com lógica de negócio complexa ou que precisam de alta escalabilidade e robustez.
- **Exemplo**: Checkout, que pode ter regras complexas como cálculo de impostos, descontos e lógica de cancelamento.

## Ambientes Corporativos
- Se você precisa de integrações com sistemas corporativos existentes (como ERPs), Java geralmente se integra melhor nesses cenários.

## Desempenho e Threads Pesadas
- Para serviços que processam tarefas computacionais pesadas ou dependem de multi-threading, Java é uma escolha mais eficiente.

## Confiabilidade e Padrões
- Java é frequentemente usado em sistemas onde confiabilidade, manutenibilidade e segurança são críticas (ex.: serviços financeiros).

## Comunidade e Frameworks Sólidos
- Frameworks como **Micronaut** oferecem um ecossistema rico para criar microsserviços robustos, com suporte a monitoramento, segurança e escalabilidade.

# Critérios para Escolher Node.js

## Desempenho em I/O Intensivo
- Node.js, com seu modelo assíncrono baseado em eventos, é ideal para microsserviços que realizam muitas operações de entrada/saída, como chamadas de APIs externas ou integração com bancos de dados de leitura/escrita frequentes.
- **Exemplo**: Payment Gateway, que pode lidar com várias requisições externas simultâneas para provedores de pagamento.

## Velocidade de Desenvolvimento
- A comunidade de Node.js tem muitas bibliotecas e ferramentas para desenvolvimento rápido, o que pode ser útil em serviços que precisam de iteração rápida.
- **Exemplo**: Um Checkout simples, onde o foco é na velocidade de entrega de um MVP.

## Comunicação em Tempo Real
- Se o serviço precisar de suporte para **WebSocket** ou outras formas de comunicação em tempo real (ex.: atualizações instantâneas de status de pagamento), Node.js é uma boa escolha.

## APIs Flexíveis
- Node.js é eficiente para construir APIs GraphQL devido à sua flexibilidade e vasta gama de bibliotecas, como Apollo Server.


# Sugestão para o Cenário Checkout e Payment Gateway:

## Node.js para o Payment Gateway
- O serviço **Payment Gateway** geralmente envolve alta interação com provedores externos (ex.: gateways de pagamento) e é altamente I/O intensivo.
- A abordagem assíncrona de Node.js lida bem com essas requisições simultâneas.
- A flexibilidade do Node.js pode ajudar a implementar integrações rápidas com APIs de terceiros.

## Java para o Checkout
- O serviço **Checkout** pode envolver regras de negócio mais complexas, como cálculo de impostos, controle de estoque e gerenciamento de pedidos.
- Java é mais adequado para gerenciar lógica de negócios robusta e manusear a escalabilidade de serviços críticos.
