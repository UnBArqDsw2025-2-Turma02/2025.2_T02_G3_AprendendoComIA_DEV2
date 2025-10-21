# AI Linguo Backend (Java/Spring Boot)

Backend do sistema de tutoria de inglês com IA.

## Tecnologias

- Java 21
- Spring Boot 3.2.5
- MongoDB
- JWT (autenticação)
- OpenAI API
- Maven

## Pré-requisitos

- Java 21+
- Maven 3.9+
- MongoDB 7+

## Configuração

1. Copiar arquivo de ambiente:
```bash
cp .env.example .env
```

2. Ajustar variáveis no `.env` conforme necessário

## Rodando localmente

```bash
# Compilar
mvn clean install

# Executar
mvn spring-boot:run
```

O servidor estará disponível em `http://localhost:8080`

## Docker

```bash
# Build
docker build -t ailinguo-backend .

# Run
docker run -p 8080:8080 --env-file .env ailinguo-backend
```

## Endpoints

### Autenticação
- `POST /api/auth/register` - Registrar usuário
- `POST /api/auth/login` - Login
- `GET /api/auth/me` - Usuário atual
- `POST /api/auth/logout` - Logout

### Tutor IA
- `POST /api/tutor` - Conversar com tutor IA

### Vocabulário
- `GET /api/vocabulary/due` - Cartões devidos
- `POST /api/vocabulary/review` - Revisar cartão

### Chat
- `POST /api/chat/sessions` - Criar sessão de chat

### Gamificação
- `GET /api/gamification/leaderboard` - Ranking
- `GET /api/gamification/groups` - Grupos
- `POST /api/gamification/groups` - Criar/entrar em grupo
- `GET /api/gamification/goals` - Metas

### Tarefas
- `GET /api/tasks/quiz` - Questões de quiz

## Segurança

- JWT em cookie HTTP-only
- BCrypt para senhas
- CORS configurável
- Spring Security

## Documentação

Ver `BUSINESS_RULES.md` na raiz do projeto para regras de negócio detalhadas.
