-- Inserir dados iniciais
-- Usuário admin
INSERT INTO users (email, name, password, cefr_level, daily_goal_minutes, streak_days, total_minutes, created_at) 
VALUES (
    'admin@lingu.com', 
    'Administrador', 
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', -- senha: admin123
    'C1', 
    30, 
    0, 
    0, 
    CURRENT_TIMESTAMP
);

-- Usuário de teste
INSERT INTO users (email, name, password, cefr_level, daily_goal_minutes, streak_days, total_minutes, created_at) 
VALUES (
    'test@lingu.com', 
    'Usuário Teste', 
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', -- senha: admin123
    'A2', 
    15, 
    5, 
    120, 
    CURRENT_TIMESTAMP
);

-- Cartões de vocabulário iniciais
INSERT INTO vocabulary_cards (term, meaning, example, cefr_level, created_at) VALUES
('hello', 'olá', 'Hello, how are you?', 'A1', CURRENT_TIMESTAMP),
('goodbye', 'tchau', 'Goodbye, see you later!', 'A1', CURRENT_TIMESTAMP),
('thank you', 'obrigado', 'Thank you for your help.', 'A1', CURRENT_TIMESTAMP),
('please', 'por favor', 'Please, can you help me?', 'A1', CURRENT_TIMESTAMP),
('sorry', 'desculpa', 'Sorry, I am late.', 'A1', CURRENT_TIMESTAMP),
('beautiful', 'bonito', 'The sunset is beautiful.', 'A2', CURRENT_TIMESTAMP),
('important', 'importante', 'This is an important meeting.', 'A2', CURRENT_TIMESTAMP),
('difficult', 'difícil', 'This exercise is difficult.', 'A2', CURRENT_TIMESTAMP),
('necessary', 'necessário', 'It is necessary to study English.', 'B1', CURRENT_TIMESTAMP),
('opportunity', 'oportunidade', 'This is a great opportunity.', 'B1', CURRENT_TIMESTAMP),
('challenge', 'desafio', 'Learning English is a challenge.', 'B1', CURRENT_TIMESTAMP),
('achievement', 'conquista', 'Graduating was a great achievement.', 'B2', CURRENT_TIMESTAMP),
('sophisticated', 'sofisticado', 'This is a sophisticated solution.', 'B2', CURRENT_TIMESTAMP),
('comprehensive', 'abrangente', 'We need a comprehensive approach.', 'B2', CURRENT_TIMESTAMP),
('extraordinary', 'extraordinário', 'She has extraordinary talent.', 'C1', CURRENT_TIMESTAMP);

-- Grupos iniciais
INSERT INTO groups (name, members, open, created_at) VALUES
('Iniciantes A1', 0, true, CURRENT_TIMESTAMP),
('Intermediários B1', 0, true, CURRENT_TIMESTAMP),
('Avançados C1', 0, false, CURRENT_TIMESTAMP),
('Conversação Livre', 0, true, CURRENT_TIMESTAMP);

-- Adicionar usuários aos grupos
INSERT INTO group_members (group_id, user_id, joined_at) VALUES
(1, 1, CURRENT_TIMESTAMP), -- admin no grupo iniciantes
(2, 2, CURRENT_TIMESTAMP); -- usuário teste no grupo intermediários
