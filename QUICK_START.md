# Início Rápido - AI Linguo

## Executar o Projeto

### Com Docker (Recomendado)

```bash
docker-compose -f docker-compose-new.yml up --build
```

### Acessar

- **Frontend**: http://localhost:3000
- **Backend**: http://localhost:8080
- **MongoDB**: localhost:27017

---

## Comandos Úteis

```bash
# Ver logs
docker-compose -f docker-compose-new.yml logs -f

# Parar
docker-compose -f docker-compose-new.yml down

# Reiniciar
docker-compose -f docker-compose-new.yml restart

# Limpar e recriar
docker-compose -f docker-compose-new.yml down -v
docker-compose -f docker-compose-new.yml up --build
```

---

## Rodar Localmente (Sem Docker)

### Backend
```bash
cd backend-java
mvn spring-boot:run
```

### Frontend
```bash
cd frontend-react
npm install
npm run dev
```

---

## Testar o Sistema

1. Acesse http://localhost:3000
2. Clique em "Criar Conta"
3. Preencha os dados
4. Explore as funcionalidades:
   - Chat com Tutor IA
   - Revisão de Vocabulário
   - Ranking e Gamificação
   - Exercícios de Gramática

---

## Solução de Problemas

**Porta ocupada:**
```bash
lsof -ti:8080 | xargs kill -9
```

**MongoDB não conecta:**
```bash
docker-compose -f docker-compose-new.yml restart mongo
```

**Erro no build:**
```bash
docker-compose -f docker-compose-new.yml down -v
docker-compose -f docker-compose-new.yml up --build
```

---

Para documentação completa, veja [README.md](./README.md)

