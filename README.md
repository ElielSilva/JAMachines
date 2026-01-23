# JAMachines - Sistema de Gestão de Máquinas 🚀

Um sistema fullstack moderno desenvolvido para o monitoramento e controle de máquinas virtuais. A plataforma oferece gestão de inventário, controle de status operacional em tempo real e auditoria completa via logs de sistema.

## 🎨 Interface (Dark Theme)
O sistema conta com uma interface moderna em modo escuro, utilizando **Angular 19** com rotas dinâmicas e componentes standalone para máxima performance.

## 📋 Visão Geral

JAMachines é uma solução empresarial para:
- ✅ Gerenciamento centralizado de máquinas virtuais
- ✅ Monitoramento em tempo real de status operacional
- ✅ Auditoria completa e rastreabilidade de operações
- ✅ Gestão de inventário integrada
- ✅ Dashboard intuitivo e responsivo

## 🏗️ Arquitetura

### Stack Tecnológico

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

## 📡 API Endpoints

A API segue padrões RESTful com autenticação via JWT Bearer Token. Todos os endpoints de máquinas requerem autenticação.

### 🔐 Autenticação

| Método | Endpoint | Descrição |
|:---:|:---:|---|
| `POST` | `/auth/register` | Registra novo usuário na plataforma |
| `POST` | `/auth/login` | Autentica usuário e retorna JWT Token |

#### Exemplo: Login
**Request:**
```json
{
  "email": "user@example.com",
  "password": "Password123@"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**⚙️ Gerenciamento de Máquinas**
⚠️ Todos os endpoints abaixo requerem o header: Authorization: Bearer {token}

Método	Endpoint	Descrição
GET	/machine	Lista todas as máquinas do usuário autenticado
GET	/machine/id/{machineId}	Obtém detalhes de uma máquina específica
POST	/machine	Cria nova máquina vinculada ao usuário
PUT	/machine/{id}	Atualiza dados da máquina
PATCH	/machine/{id}/status	Altera status e gera log de auditoria
DELETE	/machine/{id}	Remove máquina do sistema

#### Exemplo: Criar Máquina

**Request:**

```json
{
  "name": "VM Production Server",
  "cpu": 8,
  "memory": 16384,
  "disk": 500
}
```

**Response (201 CREATED):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "VM Production Server",
  "cpu": 8,
  "memory": 16384,
  "disk": 500,
  "machineStatus": "STOP",
  "createdAt": "2026-01-23T10:30:00"
}
```

### Exemplo: Alterar Status
**Request:**
```json
{
  "status": "START"
}
```

**Response (200 OK):**

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "VM Production Server",
  "cpu": 8,
  "memory": 16384,
  "disk": 500,
  "machineStatus": "START",
  "createdAt": "2026-01-23T10:30:00"
}
```

### 📋 Logs de Auditoria
| Método | Endpoint | Descrição |
|GET	| /machine/log |	Retorna histórico completo de alterações de status|

**Response Exemplo (200 OK):**

```json
[
  {
    "id": "660e8400-e29b-41d4-a716-446655440001",
    "userName": "admin",
    "machineName": "VM Production Server",
    "dateTime": "2026-01-23T10:35:00",
    "status": "STOP"
  },
  {
    "id": "660e8400-e29b-41d4-a716-446655440002",
    "userName": "admin",
    "machineName": "VM Production Server",
    "dateTime": "2026-01-23T10:30:00",
    "status": "SUSPEND"
  }
]
```


### 📚 Documentação Swagger

Quando rodando em desenvolvimento, acesse a documentação interativa em:

`http://localhost:8080/swagger-ui.html`
