# Alura Food - Microserviços

Este projeto é uma arquitetura de microserviços para o ecossistema **Alura Food**, demonstrando a integração de serviços utilizando **Spring Cloud Gateway**, **Eureka Discovery Service** e bancos de dados **PostgreSQL**.

## 🏗️ Arquitetura

A aplicação é composta por três componentes principais:

1.  **Pagamentos (Eureka Server)**: 
    *   Atua como o centro de descoberta do ecossistema.
    *   **Porta**: 8081
    *   **Tecnologias**: Spring Boot 3.3.4, Netflix Eureka Server.

2.  **Gateway**:
    *   Ponto único de entrada (API Gateway) para todas as requisições.
    *   **Porta**: 8082
    *   **Tecnologias**: Spring Boot 3.5.14, Spring Cloud Gateway.

3.  **Pedidos**:
    *   Serviço responsável pela gestão de pedidos dos clientes.
    *   **Porta**: 8080
    *   **Tecnologias**: Spring Boot 2.6.7, Spring Data JPA, PostgreSQL, Flyway.

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Cloud Netflix Eureka**
- **Spring Cloud Gateway**
- **PostgreSQL**
- **Flyway** (Migração de banco de dados)
- **Maven**

## 🚀 Como Rodar

Certifique-se de ter o **Java 17** e o **PostgreSQL** instalados e rodando em sua máquina.

1.  **Iniciar o Eureka Server (Pagamentos)**:
    ```bash
    cd pagamento
    ./mvnw spring-boot:run
    ```

2.  **Iniciar o MS de Pedidos**:
    ```bash
    cd pedidos
    ./mvnw spring-boot:run
    ```

3.  **Iniciar o API Gateway**:
    ```bash
    cd Gateway
    ./mvnw spring-boot:run
    ```

## 🔗 Endpoints e Acessos

### Dashboards
- **Eureka Server**: [http://localhost:8081](http://localhost:8081) (Visualize todos os serviços registrados)

### Acessos via Gateway (Porta 8082)
Todas as requisições devem passar pelo Gateway para garantir o balanceamento e descoberta automática:

- **Pedidos**: `http://localhost:8082/pedidos-ms/pedidos`
- **Pagamentos**: `http://localhost:8082/pagamentos-ms/pagamentos`

### Documentação (Swagger)
Você pode acessar a documentação interativa das APIs através do Gateway:
- **Swagger Pedidos**: `http://localhost:8082/pedidos-ms/swagger-ui/index.html`
- **Swagger Pagamentos**: `http://localhost:8082/pagamentos-ms/swagger-ui/index.html`


## 📂 Estrutura do Projeto

```text
.
├── Gateway/       # API Gateway Service
├── pagamento/     # Eureka Server & Pagamentos Service
└── pedidos/       # Pedidos Service
```

## 📝 Notas de Migração
O serviço de **Pedidos** foi configurado para converter automaticamente as migrações SQL de MySQL para PostgreSQL, garantindo compatibilidade com o banco de dados atual.
