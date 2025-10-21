# Comece Aqui!

Bem-vindo ao projeto **AI Linguo** completamente reescrito!

---

## O Projeto Foi Migrado!

O projeto foi **completamente reescrito** de Next.js para **Java + React**:

- Backend: **Java 21 + Spring Boot** → `backend-java/`
- Frontend: **React 18 + Vite** → `frontend-react/`
- Todas as funcionalidades reimplementadas
- Código organizado e documentado

---

## Como Executar (3 passos)

### 1. Pré-requisito
- Tenha Docker instalado

### 2. Executar
```bash
docker-compose -f docker-compose-new.yml up --build
```

### 3. Acessar
- Frontend: **http://localhost:3000**
- Backend: **http://localhost:8080**

**Pronto!**

---

## Documentação

Leia nesta ordem:

1. **[README.md](./README.md)** - Visão geral
2. **[NEW_PROJECT_README.md](./NEW_PROJECT_README.md)** - Documentação completa
3. **[BUSINESS_RULES.md](./BUSINESS_RULES.md)** - Regras de negócio
4. **[MIGRATION_GUIDE.md](./MIGRATION_GUIDE.md)** - Como foi migrado

---

## Estrutura

```
.
├── backend-java/          [Novo backend Java]
├── frontend-react/        [Novo frontend React]
├── docker-compose-new.yml [Docker compose atualizado]
└── Documentação...
```

---

## Funcionalidades

- Tutor IA com OpenAI
- Sistema de vocabulário (SRS)
- Gamificação (ranking, metas)
- Exercícios de gramática
- Autenticação JWT
- Dashboard de progresso

---

## Comandos Rápidos

```bash
# Subir tudo
docker-compose -f docker-compose-new.yml up --build

# Parar tudo
docker-compose -f docker-compose-new.yml down

# Ver logs
docker-compose -f docker-compose-new.yml logs -f

# Apenas backend
cd backend-java && mvn spring-boot:run

# Apenas frontend
cd frontend-react && npm run dev
```

---

## Problemas?

1. Porta ocupada? `lsof -ti:8080 | xargs kill -9`
2. MongoDB não inicia? `docker-compose -f docker-compose-new.yml restart mongo`
3. Erro no build? Limpe: `docker-compose -f docker-compose-new.yml down -v`

---

## Ajuda

- Leia a documentação: [NEW_PROJECT_README.md](./NEW_PROJECT_README.md)
- Veja migração: [MIGRATION_GUIDE.md](./MIGRATION_GUIDE.md)
- Abra uma issue no GitHub

---

## Importante

O projeto antigo (Next.js) foi completamente removido.

Use apenas o novo projeto!

---

**Boa sorte!**
