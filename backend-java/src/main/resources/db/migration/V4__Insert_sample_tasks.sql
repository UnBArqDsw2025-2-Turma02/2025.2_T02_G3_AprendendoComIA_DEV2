-- Inserir tarefas de exemplo
INSERT INTO tasks (title, description, type, difficulty, question, correct_answer_index, explanation, xp_reward, time_limit) VALUES
('Past Simple - Regular Verbs', 'Choose the correct past form of the verb', 'grammar', 'A2', 'Choose the correct form: "I _____ to the store yesterday."', 1, 'The past tense of "go" is "went". We use "went" for actions that happened in the past.', 15, 60),
('Plural Forms', 'Identify the correct plural form', 'vocabulary', 'A1', 'What is the plural of "child"?', 1, 'The plural of "child" is "children". This is an irregular plural form.', 10, 45),
('Comparative Adjectives', 'Complete with the correct comparative form', 'grammar', 'A2', 'Complete: "She is _____ than her sister."', 1, 'We use "taller" for comparative adjectives with short adjectives. "More tall" is incorrect.', 15, 60),
('Present Perfect Continuous', 'Choose the correct sentence', 'grammar', 'B1', 'Which sentence is correct?', 0, 'We use present perfect continuous (have been living) for actions that started in the past and continue to the present.', 20, 90),
('Advanced Vocabulary', 'Choose the correct meaning', 'vocabulary', 'B2', 'What does "procrastinate" mean?', 1, 'To procrastinate means to delay or postpone doing something, usually something important.', 25, 75),
('Future Tenses', 'Choose the correct future form', 'grammar', 'B1', 'Complete: "By next year, I _____ English for 5 years."', 2, 'We use "will have been studying" for future perfect continuous to show duration up to a point in the future.', 20, 90),
('Idioms', 'Choose the correct idiom meaning', 'vocabulary', 'B2', 'What does "break the ice" mean?', 0, '"Break the ice" means to initiate conversation in a social setting, especially when people are meeting for the first time.', 20, 60),
('Conditional Sentences', 'Choose the correct conditional', 'grammar', 'B2', 'Complete: "If I _____ you, I would study harder."', 1, 'We use "were" (subjunctive) in second conditional sentences, even with "I".', 25, 90),
('Phrasal Verbs', 'Choose the correct phrasal verb', 'vocabulary', 'B1', 'Complete: "I need to _____ my homework before dinner."', 2, '"Finish up" means to complete something. "Give up" means to quit, and "put off" means to postpone.', 20, 75),
('Reported Speech', 'Choose the correct reported speech', 'grammar', 'B2', 'Complete: "She said she _____ the book the previous week."', 1, 'In reported speech, we change "read" to "had read" to show the action happened before the reporting.', 25, 90);

-- Inserir opções para as tarefas
INSERT INTO task_options (task_id, option_text, option_index) VALUES
-- Tarefa 1: Past Simple
(1, 'go', 0),
(1, 'went', 1),
(1, 'gone', 2),
(1, 'going', 3),

-- Tarefa 2: Plural Forms
(2, 'childs', 0),
(2, 'children', 1),
(2, 'childes', 2),
(2, 'child', 3),

-- Tarefa 3: Comparative Adjectives
(3, 'tall', 0),
(3, 'taller', 1),
(3, 'tallest', 2),
(3, 'more tall', 3),

-- Tarefa 4: Present Perfect Continuous
(4, 'I have been living here for 5 years.', 0),
(4, 'I am living here for 5 years.', 1),
(4, 'I live here for 5 years.', 2),
(4, 'I lived here for 5 years.', 3),

-- Tarefa 5: Advanced Vocabulary
(5, 'To work hard', 0),
(5, 'To delay or postpone', 1),
(5, 'To celebrate', 2),
(5, 'To communicate', 3),

-- Tarefa 6: Future Tenses
(6, 'will study', 0),
(6, 'will be studying', 1),
(6, 'will have been studying', 2),
(6, 'am going to study', 3),

-- Tarefa 7: Idioms
(7, 'To initiate conversation', 0),
(7, 'To end a relationship', 1),
(7, 'To solve a problem', 2),
(7, 'To make a mistake', 3),

-- Tarefa 8: Conditional Sentences
(8, 'am', 0),
(8, 'were', 1),
(8, 'was', 2),
(8, 'be', 3),

-- Tarefa 9: Phrasal Verbs
(9, 'give up', 0),
(9, 'put off', 1),
(9, 'finish up', 2),
(9, 'look up', 3),

-- Tarefa 10: Reported Speech
(10, 'reads', 0),
(10, 'had read', 1),
(10, 'read', 2),
(10, 'was reading', 3);
