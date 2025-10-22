# Backend Adapter (GoF) 

## O que inclui
- `com.ailinguo.llm.LLMProvider` — **Target** do domínio
- `com.ailinguo.llm.OpenAIAdapter` — **Adapter** concreto reutilizando seu `OpenAIService`
- `com.ailinguo.llm.MockAdapter` — mock para dev/CI
- `com.ailinguo.llm.GeminiAdapter` — esqueleto
- `com.ailinguo.llm.LLMConfiguration` — Factory/Config (escolha por `app.llm.provider`)
- `PATCHES/tutor_service_adapter.patch` — troca `OpenAIService` por `LLMProvider` em `TutorService`

## Como aplicar
1) Copie a pasta `backend-java/src/main/java/com/ailinguo/llm` para o seu projeto.
2) Aplique o patch da service:
```bash
git apply PATCHES/tutor_service_adapter.patch
```

3) Configure o provider:
```yam
# application.yml
app:
  llm:
    provider: openai   # openai | mock | gemini
  openai:
    api-key: ${OPENAI_API_KEY:}
    model: gpt-4o-mini
```
4) Build/Run. O controller continua chamando `tutorService.processTutorRequest(...)` — agora abastecido pelo Adapter.

## Por que isso é útil
- **Desacoplamento do SDK** (OpenAI/Anthropic/Gemini) do domínio.
- **Aberto/Fechado**: adicionar um provider novo só requer mais um Adapter.
- **Testes**: `MockAdapter` sem bater em API paga.
