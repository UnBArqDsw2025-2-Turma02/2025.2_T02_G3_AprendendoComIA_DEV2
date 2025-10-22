-- Criação das tabelas principais
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    cefr_level VARCHAR(10),
    daily_goal_minutes INTEGER,
    streak_days INTEGER,
    total_minutes INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    last_study_date TIMESTAMP
);

-- Índices para performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_cefr_level ON users(cefr_level);

-- Tabela de cartões de vocabulário
CREATE TABLE vocabulary_cards (
    id BIGSERIAL PRIMARY KEY,
    term VARCHAR(255) NOT NULL,
    meaning TEXT NOT NULL,
    example TEXT,
    cefr_level VARCHAR(10),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Índices para vocabulary_cards
CREATE INDEX idx_vocabulary_cards_cefr_level ON vocabulary_cards(cefr_level);
CREATE INDEX idx_vocabulary_cards_term ON vocabulary_cards(term);

-- Tabela de sessões de chat
CREATE TABLE chat_sessions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    level VARCHAR(10),
    topic TEXT,
    summary TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Índices para chat_sessions
CREATE INDEX idx_chat_sessions_user_id ON chat_sessions(user_id);
CREATE INDEX idx_chat_sessions_created_at ON chat_sessions(created_at);

-- Tabela de turnos de chat
CREATE TABLE chat_turns (
    id BIGSERIAL PRIMARY KEY,
    session_id BIGINT NOT NULL REFERENCES chat_sessions(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role VARCHAR(10) NOT NULL,
    text TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Índices para chat_turns
CREATE INDEX idx_chat_turns_session_id ON chat_turns(session_id);
CREATE INDEX idx_chat_turns_user_id ON chat_turns(user_id);
CREATE INDEX idx_chat_turns_created_at ON chat_turns(created_at);

-- Tabela de correções
CREATE TABLE corrections (
    id BIGSERIAL PRIMARY KEY,
    chat_turn_id BIGINT NOT NULL REFERENCES chat_turns(id) ON DELETE CASCADE,
    original TEXT,
    corrected TEXT,
    explanation TEXT,
    rule TEXT
);

-- Índices para corrections
CREATE INDEX idx_corrections_chat_turn_id ON corrections(chat_turn_id);

-- Tabela de mini exercícios
CREATE TABLE mini_exercises (
    id BIGSERIAL PRIMARY KEY,
    chat_turn_id BIGINT NOT NULL REFERENCES chat_turns(id) ON DELETE CASCADE,
    type VARCHAR(50),
    question TEXT,
    correct INTEGER,
    explanation TEXT
);

-- Índices para mini_exercises
CREATE INDEX idx_mini_exercises_chat_turn_id ON mini_exercises(chat_turn_id);

-- Tabela de opções de exercícios
CREATE TABLE exercise_options (
    exercise_id BIGINT NOT NULL REFERENCES mini_exercises(id) ON DELETE CASCADE,
    option_text TEXT NOT NULL
);

-- Índices para exercise_options
CREATE INDEX idx_exercise_options_exercise_id ON exercise_options(exercise_id);

-- Tabela de revisões SRS
CREATE TABLE srs_reviews (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    card_id BIGINT NOT NULL REFERENCES vocabulary_cards(id) ON DELETE CASCADE,
    due_at TIMESTAMP,
    interval INTEGER,
    ease DOUBLE PRECISION,
    last_result VARCHAR(10),
    reviewed_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Índices para srs_reviews
CREATE INDEX idx_srs_reviews_user_id ON srs_reviews(user_id);
CREATE INDEX idx_srs_reviews_card_id ON srs_reviews(card_id);
CREATE INDEX idx_srs_reviews_due_at ON srs_reviews(due_at);
CREATE UNIQUE INDEX idx_srs_reviews_user_card ON srs_reviews(user_id, card_id);

-- Tabela de grupos
CREATE TABLE groups (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    members INTEGER DEFAULT 0,
    open BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Índices para groups
CREATE INDEX idx_groups_open ON groups(open);

-- Tabela de membros de grupos (relacionamento many-to-many)
CREATE TABLE group_members (
    group_id BIGINT NOT NULL REFERENCES groups(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    joined_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (group_id, user_id)
);

-- Índices para group_members
CREATE INDEX idx_group_members_user_id ON group_members(user_id);
CREATE INDEX idx_group_members_group_id ON group_members(group_id);

-- Trigger para atualizar updated_at automaticamente
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Aplicar trigger em todas as tabelas com updated_at
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_vocabulary_cards_updated_at BEFORE UPDATE ON vocabulary_cards FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_chat_sessions_updated_at BEFORE UPDATE ON chat_sessions FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_chat_turns_updated_at BEFORE UPDATE ON chat_turns FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_srs_reviews_updated_at BEFORE UPDATE ON srs_reviews FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_groups_updated_at BEFORE UPDATE ON groups FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
