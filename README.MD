# 💰 Sistema Bancário em Java

Aplicação desktop desenvolvida em **Java** com interface gráfica (Swing) que simula operações bancárias reais, incluindo cadastro de clientes, autenticação segura, movimentações financeiras e controle de transações.

---

## 🚀 Tecnologias Utilizadas

* ☕ Java 17
* 🖥️ Java Swing (Interface Gráfica)
* 🐘 PostgreSQL (Banco de Dados)
* 🔐 BCrypt (Criptografia de Senha)
* 🧠 JDBC (Conexão com banco)
* 🛠️ IntelliJ IDEA

---

## 📌 Funcionalidades

### 👤 Cadastro de Cliente

* Cadastro com:

    * Nome
    * Email
    * CPF (com máscara e validação)
    * Telefone (com máscara)
    * Senha (6 dígitos)
* Geração automática de:

    * Agência
    * Número da conta
* Senha armazenada de forma **criptografada (hash)**

---

### 🔐 Login Seguro

* Autenticação com:

    * CPF
    * Senha
* Validação utilizando **BCrypt**
* Comparação segura entre senha digitada e hash armazenado

---

### 💵 Depósito

* Permite adicionar saldo à conta
* Aceita valores decimais (ex: 10,50)
* Atualiza saldo no banco
* Registra transação automaticamente

---

### 💸 Saque

* Permite retirada de saldo
* Valida:

    * Saldo suficiente
* Atualiza saldo no banco
* Registra transação

---

### 🔁 Transferência entre contas

* Transferência utilizando:

    * Agência
    * Número da conta
* Exibe nome do destinatário antes de confirmar
* Atualiza saldo das duas contas
* Registra:

    * Saída (TRANSFERENCIA_ENVIO)
    * Entrada (TRANSFERENCIA_RECEBIDA)

---

### 📄 Extrato Bancário

* Lista todas as movimentações do cliente
* Exibe:

    * Tipo da transação
    * Valor
    * Data e hora
* Ordenado da mais recente para a mais antiga
* Interface organizada e de fácil leitura

---

### 👁️ Visualização de Saldo

* Saldo pode ser:

    * Ocultado (••••••)
    * Exibido (R$ valor)
* Botão estilo “olho” para alternar

---

## 🔒 Segurança

* Senhas não são armazenadas em texto puro
* Uso de **hash com BCrypt**
* Comparação segura com `checkpw`
* Uso de PreparedStatement (proteção contra SQL Injection)

---

## 🗄️ Estrutura do Banco de Dados

### Tabela `clientes`

| Campo        | Tipo      |
| ------------ | --------- |
| nome         | TEXT      |
| cpf          | TEXT (PK) |
| telefone     | TEXT      |
| senha        | TEXT      |
| agencia      | TEXT      |
| numero_conta | TEXT      |
| saldo        | DOUBLE    |

---

### Tabela `transacoes`

| Campo       | Tipo   |
| ----------- | ------ |
| id          | SERIAL |
| cpf_cliente | TEXT   |
| tipo        | TEXT   |
| valor       | DOUBLE |
| data        | TEXT   |

---

## ⚙️ Configuração do Projeto

### 1. Banco de Dados

Crie o banco no PostgreSQL:

```sql
CREATE DATABASE banco_java;
```

---

### 2. Variáveis de Ambiente

Configure no sistema:

```bash
DB_URL=""
DB_USER=""
DB_PASSWORD=""
```

---

### 3. Executar Projeto

* Abrir no IntelliJ
* Rodar a classe principal (Main)

---

## 📷 Interface

O sistema possui interface inspirada em aplicativos bancários reais, com:

* Layout moderno
* Cores baseadas em banco digital
* Componentes estilizados

---

## 🧠 Conceitos Aplicados

* Programação Orientada a Objetos (POO)
* DAO (Data Access Object)
* MVC (Model-View-Controller)
* Segurança de aplicações
* Integração com banco de dados
* Tratamento de exceções
* Boas práticas de código

---

## 📈 Melhorias Futuras

* 📱 Versão mobile (React Native)
* 🌐 API REST (Spring Boot)
* 🔐 Autenticação com JWT
* 📊 Dashboard com gráficos
* ☁️ Deploy em nuvem

---

## 👨‍💻 Autor

Lucas Alves da Cruz
Projeto com foco em aprendizado prático e evolução para nível profissional.

---

## ⭐ Considerações Finais

Este projeto simula um sistema bancário real, aplicando conceitos essenciais de desenvolvimento backend, segurança e manipulação de dados.

---
