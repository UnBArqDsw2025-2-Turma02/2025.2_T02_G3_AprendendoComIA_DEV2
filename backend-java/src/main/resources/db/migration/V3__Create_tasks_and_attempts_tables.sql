-- Criar tabela de tarefas/exercícios
CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    type VARCHAR(50) NOT NULL, -- grammar, vocabulary, listening, reading
    difficulty VARCHAR(10) NOT NULL, -- A1, A2, B1, B2, C1, C2
    question TEXT NOT NULL,
    correct_answer_index INTEGER NOT NULL,
    explanation TEXT,
    xp_reward INTEGER DEFAULT 10,
    time_limit INTEGER, -- em segundos
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de opções das tarefas
CREATE TABLE task_options (
    task_id BIGINT REFERENCES tasks(id) ON DELETE CASCADE,
    option_text TEXT NOT NULL,
    option_index INTEGER NOT NULL,
    PRIMARY KEY (task_id, option_index)
);

-- Criar tabela de tentativas de tarefas
CREATE TABLE task_attempts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    task_id BIGINT REFERENCES tasks(id) ON DELETE CASCADE,
    selected_answer_index INTEGER NOT NULL,
    is_correct BOOLEAN NOT NULL,
    time_spent INTEGER, -- em segundos
    xp_earned INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar índices para performance
CREATE INDEX idx_tasks_difficulty ON tasks(difficulty);
CREATE INDEX idx_tasks_type ON tasks(type);
CREATE INDEX idx_tasks_active ON tasks(is_active);
CREATE INDEX idx_task_attempts_user_id ON task_attempts(user_id);
CREATE INDEX idx_task_attempts_task_id ON task_attempts(task_id);
CREATE INDEX idx_task_attempts_created_at ON task_attempts(created_at);
