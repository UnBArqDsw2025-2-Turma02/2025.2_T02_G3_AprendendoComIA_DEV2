# AI Linguo Frontend (React + Vite)

Frontend do sistema de tutoria de inglês com IA.

## Tecnologias

- React 18
- Vite
- React Router
- Axios
- Tailwind CSS
- Lucide React (ícones)

## Pré-requisitos

- Node.js 18+
- npm ou yarn

## Instalação

```bash
npm install
# ou
yarn install
```

## Rodando localmente

```bash
npm run dev
# ou
yarn dev
```

O aplicativo estará disponível em `http://localhost:3000`

## Build para produção

```bash
npm run build
# ou
yarn build
```

## Docker

```bash
# Build
docker build -t ailinguo-frontend .

# Run
docker run -p 3000:80 ailinguo-frontend
```

## Páginas

- `/` - Landing page
- `/auth` - Login/Registro
- `/dashboard` - Dashboard principal
- `/chat` - Chat com tutor IA
- `/vocabulary` - Sistema de vocabulário (SRS)
- `/gamification` - Ranking e gamificação
- `/tasks` - Exercícios e quizzes

## Componentes

- `Navbar` - Barra de navegação
- `AuthContext` - Contexto de autenticação

## Configuração

O projeto usa proxy do Vite para direcionar chamadas `/api` para o backend:

```js
// vite.config.js
server: {
  proxy: {
    '/api': 'http://localhost:8080'
  }
}
```

## Documentação

Ver `BUSINESS_RULES.md` na raiz do projeto para regras de negócio detalhadas.
