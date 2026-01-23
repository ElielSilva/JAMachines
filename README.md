# 🚀 JAMachines - Sistema de Gestão de Máquinas

Sistema fullstack desenvolvido para o monitoramento e controle de máquinas virtuais. A plataforma permite a gestão de inventário, controle de status operacional em tempo real e auditoria completa via logs de sistema.
---

## 🎨 Interface (Dark Theme)
O sistema conta com uma interface moderna em modo escuro, utilizando **Angular 19** com rotas dinâmicas e componentes standalone para máxima performance.

---

## 🛠️ Tecnologias e Arquitetura

### Backend (Java 17 + Spring Boot 4.0)
- **Segurança**: Autenticação e autorização via Spring Security.
- **Performance**: Filtragem de dados realizada diretamente em nível de query (Database level) para garantir escalabilidade.
- **Persistência**: Spring Data JPA com mapeamento de relacionamentos complexos (User -> Machine -> StatusLogs).
- **Auditoria**: Sistema automático de logs para cada mudança de status das máquinas.

### Frontend (Angular 19)
- **Rotas Filhas**: Navegação otimizada no Dashboard utilizando `router-outlet` para troca de abas (Máquinas/Logs) sem recarregar gráficos.
- **Componentes Standalone**: Arquitetura modular sem a necessidade de NgModules.
- **Estilização**: SCSS avançado com aninhamento e temas baseados em atributos dinâmicos (`data-status`).
- **Gerenciamento de Versão**: NVM (Node Version Manager) para consistência de ambiente.

---
## 📋 Estrutura do Projeto

```text
JAMachines/
├── ☕ backend/ (Spring Boot)
│   └── src/main/java/com/example/JAMachines/
│       ├── 📂 domain/
│       │   └── entity/             # Entidades JPA (User, Machine, MachineStatusLog)
│       ├── 📂 infrastructure/
│       │   ├── config/             # Configurações globais e Beans do sistema
│       │   ├── persistence/        # Repositories (Queries JPA e filtros de Principal)
│       │   └── security/           # Spring Security, JWT e filtros de acesso
│       ├── 📂 features/
│       │   ├── auth/               # Recursos de Autenticação e Registro
│       │   └── machine/            # Recursos de Gestão e Controle de Máquinas
│       └── 📂 application/
│           └── commonexceptions/   # Global Exception Handler e Exceções de Negócio
│
└── 🅰️ frontend/ (Angular 19)
    └── src/app/
        ├── 📂 pages/               # Componentes de página (Auth, Dashboard)
        ├── 📂 layout/
        │   └── header/             # Barra de navegação e controles de usuário
        ├── 📂 core/                # Infraestrutura do Frontend (Singletons)
        │   ├── guard/              # Proteção de rotas (AuthGuard)
        │   ├── interceptor/        # Interceptadores de Request (Auth Token)
        │   └── services/           # Serviços de comunicação com a API
        └── 📂 components/          # Blocos funcionais e dashboards
            ├── machines-list/      # Listagem e CRUD de máquinas
            ├── machines-logs/      # Tabela de histórico de auditoria
            ├── status/             # Gráficos e indicadores de status
            └── quantity/           # Contador e métricas de quantidade
```
---

# 🚀 Como Executar o Projeto

## 🐳 Rodando com Docker Compose
Na raiz do projeto (onde estão as pastas backend e frontend), execute os comandos abaixo:

### 1. Construir e subir os containers
Este comando irá compilar o Java, gerar o build do Angular e configurar o banco de dados automaticamente.
```bash
   docker-compose up --build
```

### 2. Acessar a aplicação
Após a finalização do processo (o terminal mostrará os logs do Spring), acesse:

* **Frontend:** http://localhost:4000
* **Backend API:** http://localhost:8080
* **Banco de Dados (Postgres):** Localhost na porta 5432

### 📋 Pré-requisitos
*   **Java JDK 17**
*   **Node.js 20** ou superior (Gerenciado via `nvm`)
*   **Angular CLI 19**
*   **Maven 3.9+**
*   **PostgreSQL** (ou H2 configurado para desenvolvimento)

###  Rodando com localmente
### 1️⃣ Configuração do Backend
Antes de iniciar o backend, você precisa configurar o arquivo de conexão com o banco de dados

### Localize o arquivo de configuração**

Vá até o caminho: backend/src/main/resources/application.properties

### Configure os parâmetros de conexão

**Edite o arquivo com as seguintes informações:**

* **URL de Conexão:** spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_seu_banco 
* **Usuário:** spring.datasource.username=seu_usuario_postgres *
* **Senha:** spring.datasource.password=sua_senha_postgres *
* **Driver:** spring.datasource.driver-class-name=org.postgresql.Driver *
* **Hibernate:** spring.jpa.hibernate.ddl-auto=update *



**Navegue até o diretório do servidor e execute:**
```bash
    cd backend
```
### Instalar dependências e compilar
```bash
    ./mvnw clean install
```
### Iniciar a aplicação
```bash
    ./mvnw spring-boot:run
```
### 2️⃣ Configuração do Frontend

Navegue até o diretório da aplicação cliente:
```bash
    cd frontend
```
Certifique-se de estar utilizando o Node.js 20 (LTS) via nvm:
#### Instalar as dependências
```bash
    nvm use 20
    npm install
```
## Iniciar o servidor de desenvolvimento
```bash
    ng serve
```

Acesse o sistema através do navegador em: http://localhost:4200

## 🔒 Endpoints Principais (API)


| Método | Endpoint | Descrição |

| :--- | :--- | :--- |

| `POST` | `/api/auth/login` | Autenticação e geração de token. |
| `GET` | `/api/machines` | Lista máquinas filtradas pelo usuário autenticado. |
| `POST` | `/api/machines` | Cadastra nova máquina vinculada ao Principal. |
| `PATCH` | `/api/machines/{id}/status` | Altera status e gera log de auditoria. |
| `GET` | `/api/logs` | Retorna o histórico completo de auditoria. |