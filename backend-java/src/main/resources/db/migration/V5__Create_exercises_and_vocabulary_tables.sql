-- Create exercises table
CREATE TABLE exercises (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    type VARCHAR(50) NOT NULL, -- 'multiple_choice', 'fill_blank', 'translation', 'listening'
    difficulty VARCHAR(20) NOT NULL, -- 'beginner', 'intermediate', 'advanced'
    cefr_level VARCHAR(10) NOT NULL, -- 'A1', 'A2', 'B1', 'B2', 'C1', 'C2'
    xp_reward INTEGER NOT NULL DEFAULT 10,
    time_limit INTEGER, -- in seconds
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create exercise_questions table
CREATE TABLE exercise_questions (
    id BIGSERIAL PRIMARY KEY,
    exercise_id BIGINT REFERENCES exercises(id) ON DELETE CASCADE,
    question_text TEXT NOT NULL,
    question_type VARCHAR(50) NOT NULL, -- 'multiple_choice', 'fill_blank', 'translation'
    correct_answer TEXT NOT NULL,
    options JSONB, -- for multiple choice questions
    explanation TEXT,
    order_index INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create vocabulary_categories table
CREATE TABLE vocabulary_categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    color VARCHAR(7), -- hex color
    icon VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create vocabulary_words table
CREATE TABLE vocabulary_words (
    id BIGSERIAL PRIMARY KEY,
    category_id BIGINT REFERENCES vocabulary_categories(id) ON DELETE SET NULL,
    english_word VARCHAR(255) NOT NULL,
    portuguese_translation VARCHAR(255) NOT NULL,
    phonetic VARCHAR(255),
    definition TEXT,
    example_sentence TEXT,
    difficulty VARCHAR(20) NOT NULL, -- 'beginner', 'intermediate', 'advanced'
    cefr_level VARCHAR(10) NOT NULL,
    xp_reward INTEGER NOT NULL DEFAULT 5,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create user_exercise_attempts table
CREATE TABLE user_exercise_attempts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    exercise_id BIGINT REFERENCES exercises(id) ON DELETE CASCADE,
    score DECIMAL(5,2) NOT NULL, -- percentage score
    xp_earned INTEGER NOT NULL DEFAULT 0,
    time_spent INTEGER, -- in seconds
    completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create user_vocabulary_progress table
CREATE TABLE user_vocabulary_progress (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    vocabulary_word_id BIGINT REFERENCES vocabulary_words(id) ON DELETE CASCADE,
    mastery_level INTEGER NOT NULL DEFAULT 0, -- 0-5 scale
    times_studied INTEGER NOT NULL DEFAULT 0,
    last_studied TIMESTAMP,
    xp_earned INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, vocabulary_word_id)
);

-- Create user_xp_log table for tracking XP changes
CREATE TABLE user_xp_log (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    xp_change INTEGER NOT NULL,
    source_type VARCHAR(50) NOT NULL, -- 'exercise', 'vocabulary', 'achievement', 'bonus'
    source_id BIGINT, -- reference to exercise, vocabulary word, etc.
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Add XP column to users table if not exists
ALTER TABLE users ADD COLUMN IF NOT EXISTS total_xp INTEGER DEFAULT 0;
ALTER TABLE users ADD COLUMN IF NOT EXISTS level INTEGER DEFAULT 1;

-- Create indexes for better performance
CREATE INDEX idx_exercises_cefr_level ON exercises(cefr_level);
CREATE INDEX idx_exercises_difficulty ON exercises(difficulty);
CREATE INDEX idx_exercises_type ON exercises(type);
CREATE INDEX idx_vocabulary_words_category ON vocabulary_words(category_id);
CREATE INDEX idx_vocabulary_words_cefr_level ON vocabulary_words(cefr_level);
CREATE INDEX idx_user_exercise_attempts_user ON user_exercise_attempts(user_id);
CREATE INDEX idx_user_vocabulary_progress_user ON user_vocabulary_progress(user_id);
CREATE INDEX idx_user_xp_log_user ON user_xp_log(user_id);
