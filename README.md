# AI Linguo - Tutor de Inglês com IA

> Aplicação de tutoria de inglês com Inteligência Artificial

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-blue)](https://reactjs.org/)
[![MongoDB](https://img.shields.io/badge/MongoDB-7-green)](https://www.mongodb.com/)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

---

## Sobre o Projeto

Sistema completo de tutoria de inglês com IA que oferece:

- **Tutor IA**: Conversação com correções em tempo real
- **Vocabulário SRS**: Sistema de repetição espaçada
- **Gamificação**: Ranking, metas e grupos
- **Exercícios**: Quizzes de gramática
- **Progresso**: Acompanhamento de evolução

---

## Arquitetura

### Stack Tecnológico

**Backend:**
- Java 21
- Spring Boot 3.2.5
- Spring Security + JWT
- MongoDB
- OpenAI API

**Frontend:**
- React 18
- Vite
- React Router
- Tailwind CSS
- Axios

**Infraestrutura:**
- Docker & Docker Compose
- MongoDB 7
- Nginx (produção)

---

## Início Rápido

### Pré-requisitos

- Docker e Docker Compose
- OU Java 21+ e Node.js 18+

### Executar com Docker (Recomendado)

```bash
# Clonar repositório
git clone <repo-url>
cd 2025.2_T02_G3_AprendendoComIA_DEV

# Iniciar todos os serviços
docker-compose -f docker-compose-new.yml up --build

# Acessar:
# - Frontend: http://localhost:3000
# - Backend:  http://localhost:8080
```

### Executar Localmente

#### Backend

```bash
cd backend-java
mvn clean install
mvn spring-boot:run
```

#### Frontend

```bash
cd frontend-react
npm install
npm run dev
```

---

## Estrutura do Projeto

```
.
├── backend-java/          # Backend Spring Boot
│   ├── src/main/java/
│   │   └── com/ailinguo/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── model/
│   │       ├── repository/
│   │       └── service/
│   └── pom.xml
│
├── frontend-react/        # Frontend React + Vite
│   ├── src/
│   │   ├── components/
│   │   ├── context/
│   │   ├── pages/
│   │   └── App.jsx
│   └── package.json
│
├── docker-compose-new.yml    # Docker Compose
├── BUSINESS_RULES.md         # Regras de negócio
├── MIGRATION_GUIDE.md        # Guia de migração
└── NEW_PROJECT_README.md     # Docs detalhadas
```

---

## Documentação

- **[NEW_PROJECT_README.md](./NEW_PROJECT_README.md)** - Documentação completa
- **[BUSINESS_RULES.md](./BUSINESS_RULES.md)** - Regras de negócio detalhadas
- **[MIGRATION_GUIDE.md](./MIGRATION_GUIDE.md)** - Guia de migração Next.js → Java/React
- **[backend-java/README.md](./backend-java/README.md)** - Docs do backend
- **[frontend-react/README.md](./frontend-react/README.md)** - Docs do frontend

---

## Funcionalidades

### Autenticação
- Registro e login de usuários
- JWT em cookie HTTP-only
- Níveis CEFR (A1-C1)

### Tutor IA
- Chat interativo com IA
- Correções gramaticais em tempo real
- Exercícios personalizados
- Adaptação ao nível do aluno

### Vocabulário (SRS)
- Sistema de repetição espaçada
- Algoritmo de revisão inteligente
- Cartões por nível CEFR

### Gamificação
- Ranking de usuários
- Sistema de XP e níveis
- Metas semanais
- Grupos/Times
- Streaks diários

### Exercícios
- Quizzes de gramática
- Feedback imediato
- Sistema de pontuação

---

## Segurança

- JWT com tokens HTTP-only
- Senhas com BCrypt
- Spring Security
- CORS configurável
- Validação de entrada

---

## API Endpoints

### Autenticação
```
POST   /api/auth/register
POST   /api/auth/login
GET    /api/auth/me
POST   /api/auth/logout
```

### Tutor
```
POST   /api/tutor
```

### Vocabulário
```
GET    /api/vocabulary/due
POST   /api/vocabulary/review
```

### Gamificação
```
GET    /api/gamification/leaderboard
GET    /api/gamification/goals
GET    /api/gamification/groups
POST   /api/gamification/groups
```

### Tarefas
```
GET    /api/tasks/quiz
```

---

## Testes

### Backend
```bash
cd backend-java
mvn test
```

### Frontend
```bash
cd frontend-react
npm run test
```

---

## Variáveis de Ambiente

Crie um arquivo `.env` baseado em `.env.new`:

```bash
# MongoDB
MONGODB_URI=mongodb://mongo:27017/ailinguo

# JWT
JWT_SECRET=your-secret-key-here

# OpenAI (opcional)
OPENAI_API_KEY=your-openai-key
AI_TUTOR_MOCK=true

# CORS
CORS_ORIGINS=http://localhost:3000
```

---

## Solução de Problemas

### Backend não inicia
```bash
# Verificar porta 8080
lsof -ti:8080 | xargs kill -9

# Ver logs
docker-compose -f docker-compose-new.yml logs backend
```

### Frontend não conecta
- Verificar proxy no `vite.config.js`
- Backend deve estar em `localhost:8080`
- Verificar CORS

### MongoDB não conecta
```bash
# Reiniciar MongoDB
docker-compose -f docker-compose-new.yml restart mongo

# Ver logs
docker-compose -f docker-compose-new.yml logs mongo
```

---

## Roadmap

- [x] Backend Java completo
- [x] Frontend React completo
- [x] Autenticação JWT
- [x] Integração OpenAI
- [x] Sistema SRS
- [x] Gamificação
- [ ] Testes E2E
- [ ] CI/CD
- [ ] PWA
- [ ] WebSockets
- [ ] Audio/Pronúncia

---

## Contribuindo

1. Fork o projeto
2. Crie uma branch: `git checkout -b feature/nova-feature`
3. Commit: `git commit -m 'Add nova feature'`
4. Push: `git push origin feature/nova-feature`
5. Abra um Pull Request

---

## Licença

Este projeto está sob a licença MIT. Veja [LICENSE](LICENSE) para detalhes.

---

## Time

Projeto desenvolvido como parte do curso de Engenharia de Software.

---

## Agradecimentos

- OpenAI pela API
- Spring Team
- React Team
- Comunidade Open Source

---

## Suporte

Para dúvidas ou problemas:
- Abra uma [Issue](../../issues)
- Consulte a [Documentação](./NEW_PROJECT_README.md)
- Veja o [Guia de Migração](./MIGRATION_GUIDE.md)

---

**Desenvolvido com amor usando Java, React e muita dedicação**

---

## Nota sobre Projeto Antigo

O projeto antigo morreu graças ao Arthur Leite.
