# Migração do MongoDB para PostgreSQL

Este documento descreve a migração da aplicação AI Linguo de MongoDB para PostgreSQL.

## Motivação

A migração para PostgreSQL foi realizada pelos seguintes motivos:

1. **Relacionamentos Complexos**: PostgreSQL oferece melhor suporte para relacionamentos entre entidades
2. **ACID Compliance**: Garantia de consistência transacional
3. **Consultas SQL**: Facilita relatórios e análises de dados
4. **Ecosystem**: Melhor integração com ferramentas de BI e analytics
5. **Performance**: Otimizações específicas para consultas relacionais

## Mudanças Realizadas

### 1. Dependências (pom.xml)
- ❌ Removido: `spring-boot-starter-data-mongodb`
- ✅ Adicionado: `spring-boot-starter-data-jpa`
- ✅ Adicionado: `postgresql` driver
- ✅ Adicionado: `flyway-core` para migrações
- ✅ Adicionado: `flyway-database-postgresql`

### 2. Configuração (application.yml)
- ❌ Removido: Configurações MongoDB
- ✅ Adicionado: Configurações PostgreSQL
- ✅ Adicionado: Configurações JPA/Hibernate
- ✅ Adicionado: Configurações Flyway

### 3. Modelos de Dados
Todos os modelos foram convertidos de documentos MongoDB para entidades JPA:

#### User
- `@Document` → `@Entity`
- `String id` → `Long id` (auto-generated)
- Adicionados relacionamentos: `@OneToMany` com ChatSession, SrsReview, ChatTurn
- Adicionado auditing: `@CreatedDate`, `@LastModifiedDate`

#### VocabularyCard
- Convertido para entidade JPA
- Adicionado relacionamento: `@OneToMany` com SrsReview
- Campos de texto usando `@Column(columnDefinition = "TEXT")`

#### ChatSession
- Adicionado relacionamento: `@ManyToOne` com User
- Adicionado relacionamento: `@OneToMany` com ChatTurn

#### ChatTurn
- Adicionados relacionamentos: `@ManyToOne` com ChatSession e User
- Classes internas (Correction, MiniExercise) convertidas para entidades separadas
- Adicionados relacionamentos apropriados

#### SrsReview
- Adicionados relacionamentos: `@ManyToOne` com User e VocabularyCard
- Chave composta única: `(user_id, card_id)`

#### Group
- Adicionado relacionamento: `@ManyToMany` com User
- Tabela de junção: `group_members`

### 4. Repositórios
Todos os repositórios foram convertidos:
- `MongoRepository<User, String>` → `JpaRepository<User, Long>`
- Métodos de busca mantidos e adaptados para JPA
- Adicionados métodos que trabalham com entidades relacionadas

### 5. Migração de Dados (Flyway)
Criado script `V1__Create_initial_tables.sql` com:
- Criação de todas as tabelas
- Índices para performance
- Constraints de integridade referencial
- Triggers para `updated_at`
- Chaves estrangeiras apropriadas

### 6. Docker Compose
- ❌ Removido: Serviço MongoDB
- ✅ Adicionado: Serviço PostgreSQL
- ✅ Atualizadas variáveis de ambiente
- ✅ Atualizadas dependências entre serviços

## Estrutura do Banco de Dados

### Tabelas Principais
1. **users** - Usuários do sistema
2. **vocabulary_cards** - Cartões de vocabulário
3. **chat_sessions** - Sessões de conversa
4. **chat_turns** - Turnos de conversa
5. **corrections** - Correções de texto
6. **mini_exercises** - Mini exercícios
7. **exercise_options** - Opções de exercícios
8. **srs_reviews** - Revisões do sistema SRS
9. **groups** - Grupos de usuários
10. **group_members** - Membros dos grupos

### Relacionamentos
- User → ChatSession (1:N)
- User → ChatTurn (1:N)
- User → SrsReview (1:N)
- User ↔ Group (N:N)
- ChatSession → ChatTurn (1:N)
- ChatTurn → Correction (1:N)
- ChatTurn → MiniExercise (1:1)
- VocabularyCard → SrsReview (1:N)

## Vantagens da Migração

### 1. **Integridade Referencial**
- Constraints de chave estrangeira
- Prevenção de dados órfãos
- Consistência automática

### 2. **Performance**
- Índices otimizados para consultas relacionais
- Query planner avançado
- Estatísticas de uso para otimização

### 3. **Consultas Complexas**
- JOINs eficientes
- Subqueries
- Window functions
- Agregações avançadas

### 4. **Ferramentas**
- pgAdmin para administração
- Ferramentas de BI (Tableau, Power BI)
- Backup e recovery robustos
- Replicação nativa

## Como Executar

### 1. Desenvolvimento Local
```bash
# Subir apenas o PostgreSQL
docker-compose up postgres

# Executar a aplicação
mvn spring-boot:run
```

### 2. Docker Compose Completo
```bash
# Subir toda a stack
docker-compose up -d
```

### 3. Migração de Dados (se necessário)
Se você tem dados existentes no MongoDB, será necessário criar um script de migração personalizado para transferir os dados para PostgreSQL.

## Configurações de Ambiente

### Variáveis Necessárias
```bash
DATABASE_URL=jdbc:postgresql://localhost:5432/ailinguo
DB_USERNAME=ailinguo
DB_PASSWORD=ailinguo123
```

### Docker Compose
As variáveis já estão configuradas no `docker-compose-new.yml`.

## Próximos Passos

1. **Testes**: Executar testes para validar a migração
2. **Performance**: Monitorar performance das consultas
3. **Backup**: Configurar estratégia de backup do PostgreSQL
4. **Monitoramento**: Implementar monitoramento de saúde do banco

## Rollback

Se necessário, é possível reverter para MongoDB:
1. Restaurar configurações originais
2. Reverter commits da migração
3. Restaurar docker-compose original

## Suporte

Para dúvidas sobre a migração, consulte:
- [Documentação PostgreSQL](https://www.postgresql.org/docs/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Flyway](https://flywaydb.org/documentation/)
