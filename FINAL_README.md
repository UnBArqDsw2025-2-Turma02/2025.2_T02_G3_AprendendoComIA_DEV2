# AI Linguo - Sistema de Tutoria de Inglês com IA

Aplicação completa para ensino de inglês com Inteligência Artificial.

---

## Arquitetura

**Backend:** Java 21 + Spring Boot 3.2.5 + MongoDB  
**Frontend:** React 18 + Vite + Tailwind CSS  
**IA:** OpenAI API

---

## Como Executar

```bash
# Iniciar todos os serviços
docker-compose -f docker-compose-new.yml up --build

# Acessar
# Frontend: http://localhost:3000
# Backend:  http://localhost:8080
```

---

## Estrutura

```
backend-java/       # Spring Boot REST API
frontend-react/     # React SPA
```

---

## Funcionalidades

- Autenticação JWT
- Chat com Tutor IA (OpenAI)
- Sistema de Vocabulário com SRS
- Gamificação (Ranking, Metas, Grupos)
- Exercícios de Gramática
- Dashboard de Progresso

---

## API Endpoints

```
POST /api/auth/register
POST /api/auth/login
GET  /api/auth/me
POST /api/tutor
GET  /api/vocabulary/due
POST /api/vocabulary/review
GET  /api/gamification/leaderboard
GET  /api/tasks/quiz
```

---

## Desenvolvimento Local

### Backend
```bash
cd backend-java
mvn spring-boot:run
```

### Frontend
```bash
cd frontend-react
npm install && npm run dev
```

---

## Tecnologias

**Backend:**
- Spring Boot, Spring Security
- MongoDB, JWT (jjwt)
- OpenAI Java SDK
- Lombok, Bean Validation

**Frontend:**
- React, React Router
- Axios, Tailwind CSS
- Vite, Lucide Icons

---

## Licença

MIT - Ver [LICENSE](./LICENSE)

---

## Projeto Acadêmico

Desenvolvido para o curso de Engenharia de Software.

