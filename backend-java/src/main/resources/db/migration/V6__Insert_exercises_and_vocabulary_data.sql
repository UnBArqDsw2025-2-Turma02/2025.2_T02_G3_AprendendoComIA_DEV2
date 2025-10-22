-- Insert vocabulary categories
INSERT INTO vocabulary_categories (name, description, color, icon) VALUES
('Daily Life', 'Common words for everyday activities', '#3B82F6', 'home'),
('Food & Drinks', 'Vocabulary related to food and beverages', '#10B981', 'utensils'),
('Travel', 'Words for traveling and transportation', '#8B5CF6', 'plane'),
('Work & Business', 'Professional and business vocabulary', '#F59E0B', 'briefcase'),
('Health & Body', 'Health, body parts, and medical terms', '#EF4444', 'heart'),
('Technology', 'Modern technology and digital terms', '#06B6D4', 'smartphone'),
('Nature & Environment', 'Environmental and nature vocabulary', '#84CC16', 'tree'),
('Entertainment', 'Leisure, sports, and entertainment', '#F97316', 'music');

-- Insert vocabulary words
INSERT INTO vocabulary_words (category_id, english_word, portuguese_translation, phonetic, definition, example_sentence, difficulty, cefr_level, xp_reward) VALUES
-- Daily Life (A1-A2)
(1, 'hello', 'olá', '/həˈloʊ/', 'A greeting used when meeting someone', 'Hello, how are you today?', 'beginner', 'A1', 5),
(1, 'goodbye', 'tchau', '/ɡʊdˈbaɪ/', 'A farewell when leaving', 'Goodbye, see you tomorrow!', 'beginner', 'A1', 5),
(1, 'please', 'por favor', '/pliːz/', 'A polite way to make a request', 'Can you help me, please?', 'beginner', 'A1', 5),
(1, 'thank you', 'obrigado', '/θæŋk juː/', 'An expression of gratitude', 'Thank you for your help.', 'beginner', 'A1', 5),
(1, 'house', 'casa', '/haʊs/', 'A building where people live', 'I live in a big house.', 'beginner', 'A1', 5),
(1, 'family', 'família', '/ˈfæməli/', 'A group of related people', 'My family is very important to me.', 'beginner', 'A1', 5),
(1, 'friend', 'amigo', '/frend/', 'A person you like and know well', 'She is my best friend.', 'beginner', 'A1', 5),
(1, 'work', 'trabalho', '/wɜːrk/', 'Activity involving mental or physical effort', 'I work in an office.', 'beginner', 'A1', 5),
(1, 'school', 'escola', '/skuːl/', 'An institution for education', 'Children go to school.', 'beginner', 'A1', 5),
(1, 'time', 'tempo', '/taɪm/', 'The indefinite continued progress of existence', 'What time is it?', 'beginner', 'A1', 5),

-- Food & Drinks (A1-A2)
(2, 'water', 'água', '/ˈwɔːtər/', 'A clear liquid essential for life', 'I drink water every day.', 'beginner', 'A1', 5),
(2, 'food', 'comida', '/fuːd/', 'Any nutritious substance that people eat', 'The food at this restaurant is delicious.', 'beginner', 'A1', 5),
(2, 'bread', 'pão', '/bred/', 'A staple food made from flour', 'I eat bread for breakfast.', 'beginner', 'A1', 5),
(2, 'coffee', 'café', '/ˈkɔːfi/', 'A hot drink made from coffee beans', 'I drink coffee every morning.', 'beginner', 'A1', 5),
(2, 'milk', 'leite', '/mɪlk/', 'A white liquid produced by mammals', 'Children need milk for strong bones.', 'beginner', 'A1', 5),
(2, 'apple', 'maçã', '/ˈæpəl/', 'A round fruit with red or green skin', 'An apple a day keeps the doctor away.', 'beginner', 'A1', 5),
(2, 'banana', 'banana', '/bəˈnænə/', 'A long curved fruit with yellow skin', 'Bananas are rich in potassium.', 'beginner', 'A1', 5),
(2, 'chicken', 'frango', '/ˈtʃɪkɪn/', 'A bird kept for its meat and eggs', 'I prefer chicken to beef.', 'beginner', 'A1', 5),
(2, 'rice', 'arroz', '/raɪs/', 'A cereal grain that is a staple food', 'Rice is common in Asian cuisine.', 'beginner', 'A1', 5),
(2, 'pizza', 'pizza', '/ˈpiːtsə/', 'A dish of Italian origin with toppings', 'Pizza is my favorite food.', 'beginner', 'A1', 5),

-- Travel (A2-B1)
(3, 'airport', 'aeroporto', '/ˈeərpɔːrt/', 'A place where aircraft take off and land', 'We arrived at the airport early.', 'intermediate', 'A2', 8),
(3, 'hotel', 'hotel', '/hoʊˈtel/', 'A place providing lodging for travelers', 'The hotel has a beautiful view.', 'intermediate', 'A2', 8),
(3, 'passport', 'passaporte', '/ˈpæspɔːrt/', 'An official document for travel', 'Don''t forget your passport.', 'intermediate', 'A2', 8),
(3, 'ticket', 'bilhete', '/ˈtɪkɪt/', 'A piece of paper allowing entry or travel', 'I need to buy a train ticket.', 'intermediate', 'A2', 8),
(3, 'luggage', 'bagagem', '/ˈlʌɡɪdʒ/', 'Bags and suitcases for travel', 'The luggage is too heavy.', 'intermediate', 'A2', 8),
(3, 'journey', 'viagem', '/ˈdʒɜːrni/', 'An act of traveling from one place to another', 'The journey was long but enjoyable.', 'intermediate', 'A2', 8),
(3, 'destination', 'destino', '/ˌdestɪˈneɪʃən/', 'The place to which someone is going', 'What is your final destination?', 'intermediate', 'A2', 8),
(3, 'adventure', 'aventura', '/ədˈventʃər/', 'An exciting or unusual experience', 'Traveling is always an adventure.', 'intermediate', 'A2', 8),

-- Work & Business (B1-B2)
(4, 'meeting', 'reunião', '/ˈmiːtɪŋ/', 'An assembly of people for discussion', 'We have a meeting at 3 PM.', 'intermediate', 'B1', 10),
(4, 'project', 'projeto', '/ˈprɑːdʒekt/', 'A planned piece of work with a specific purpose', 'This project will take six months.', 'intermediate', 'B1', 10),
(4, 'deadline', 'prazo', '/ˈdedlaɪn/', 'The latest time or date for completion', 'The deadline is next Friday.', 'intermediate', 'B1', 10),
(4, 'colleague', 'colega', '/ˈkɑːliːɡ/', 'A person with whom one works', 'My colleague is very helpful.', 'intermediate', 'B1', 10),
(4, 'presentation', 'apresentação', '/ˌprezənˈteɪʃən/', 'A formal display of information', 'I need to prepare a presentation.', 'intermediate', 'B1', 10),
(4, 'budget', 'orçamento', '/ˈbʌdʒɪt/', 'An estimate of income and expenditure', 'We need to stay within budget.', 'intermediate', 'B1', 10),
(4, 'strategy', 'estratégia', '/ˈstrætədʒi/', 'A plan of action designed to achieve a goal', 'Our marketing strategy is working.', 'intermediate', 'B1', 10),
(4, 'negotiation', 'negociação', '/nɪˌɡoʊʃiˈeɪʃən/', 'Discussion aimed at reaching an agreement', 'The negotiation was successful.', 'intermediate', 'B1', 10),

-- Health & Body (A2-B1)
(5, 'doctor', 'médico', '/ˈdɑːktər/', 'A qualified medical practitioner', 'I need to see a doctor.', 'intermediate', 'A2', 8),
(5, 'hospital', 'hospital', '/ˈhɑːspɪtəl/', 'An institution providing medical treatment', 'The hospital is very modern.', 'intermediate', 'A2', 8),
(5, 'medicine', 'medicamento', '/ˈmedəsən/', 'A substance used to treat illness', 'Take this medicine twice a day.', 'intermediate', 'A2', 8),
(5, 'exercise', 'exercício', '/ˈeksərsaɪz/', 'Physical activity to maintain health', 'Regular exercise is important.', 'intermediate', 'A2', 8),
(5, 'healthy', 'saudável', '/ˈhelθi/', 'In good physical or mental condition', 'Eating vegetables keeps you healthy.', 'intermediate', 'A2', 8),
(5, 'pain', 'dor', '/peɪn/', 'Physical suffering or discomfort', 'I have a pain in my back.', 'intermediate', 'A2', 8),
(5, 'treatment', 'tratamento', '/ˈtriːtmənt/', 'Medical care given to a patient', 'The treatment was successful.', 'intermediate', 'A2', 8),
(5, 'surgery', 'cirurgia', '/ˈsɜːrdʒəri/', 'Medical treatment involving cutting', 'The surgery went well.', 'intermediate', 'A2', 8),

-- Technology (B1-B2)
(6, 'computer', 'computador', '/kəmˈpjuːtər/', 'An electronic device for processing data', 'I work on my computer all day.', 'intermediate', 'B1', 10),
(6, 'software', 'software', '/ˈsɔːftwer/', 'Programs and other operating information', 'This software is very useful.', 'intermediate', 'B1', 10),
(6, 'internet', 'internet', '/ˈɪntərnet/', 'A global computer network', 'The internet has changed everything.', 'intermediate', 'B1', 10),
(6, 'website', 'site', '/ˈwebsaɪt/', 'A location on the World Wide Web', 'Visit our website for more information.', 'intermediate', 'B1', 10),
(6, 'password', 'senha', '/ˈpæswɜːrd/', 'A secret word or phrase for access', 'Don''t share your password.', 'intermediate', 'B1', 10),
(6, 'database', 'banco de dados', '/ˈdeɪtəbeɪs/', 'A structured set of data', 'The database contains all our records.', 'intermediate', 'B1', 10),
(6, 'algorithm', 'algoritmo', '/ˈælɡərɪðəm/', 'A process or set of rules for calculation', 'This algorithm is very efficient.', 'intermediate', 'B1', 10),
(6, 'innovation', 'inovação', '/ˌɪnəˈveɪʃən/', 'A new method, idea, or product', 'Innovation drives progress.', 'intermediate', 'B1', 10);

-- Insert exercises
INSERT INTO exercises (title, description, type, difficulty, cefr_level, xp_reward, time_limit) VALUES
-- A1 Level Exercises
('Basic Greetings', 'Practice common greetings and polite expressions', 'multiple_choice', 'beginner', 'A1', 15, 300),
('Numbers 1-20', 'Learn and practice numbers from 1 to 20', 'multiple_choice', 'beginner', 'A1', 15, 300),
('Family Members', 'Identify family relationships in English', 'multiple_choice', 'beginner', 'A1', 15, 300),
('Colors and Shapes', 'Learn basic colors and geometric shapes', 'multiple_choice', 'beginner', 'A1', 15, 300),
('Daily Activities', 'Common daily routines and activities', 'multiple_choice', 'beginner', 'A1', 15, 300),

-- A2 Level Exercises
('Present Simple', 'Practice present simple tense with common verbs', 'multiple_choice', 'beginner', 'A2', 20, 400),
('Food and Drinks', 'Vocabulary related to meals and beverages', 'multiple_choice', 'beginner', 'A2', 20, 400),
('Shopping', 'Essential vocabulary for shopping situations', 'multiple_choice', 'beginner', 'A2', 20, 400),
('Directions', 'Asking for and giving directions', 'multiple_choice', 'beginner', 'A2', 20, 400),
('Weather', 'Describing weather conditions and seasons', 'multiple_choice', 'beginner', 'A2', 20, 400),

-- B1 Level Exercises
('Past Simple', 'Practice past simple tense and irregular verbs', 'multiple_choice', 'intermediate', 'B1', 25, 500),
('Job Interviews', 'Professional vocabulary for job interviews', 'multiple_choice', 'intermediate', 'B1', 25, 500),
('Travel Planning', 'Vocabulary for planning and booking trips', 'multiple_choice', 'intermediate', 'B1', 25, 500),
('Health and Medicine', 'Medical vocabulary and health discussions', 'multiple_choice', 'intermediate', 'B1', 25, 500),
('Technology', 'Modern technology and digital communication', 'multiple_choice', 'intermediate', 'B1', 25, 500),

-- B2 Level Exercises
('Conditional Sentences', 'Practice first, second, and third conditionals', 'multiple_choice', 'advanced', 'B2', 30, 600),
('Business Communication', 'Professional email and meeting vocabulary', 'multiple_choice', 'advanced', 'B2', 30, 600),
('Academic Writing', 'Formal language for academic purposes', 'multiple_choice', 'advanced', 'B2', 30, 600),
('Environmental Issues', 'Vocabulary related to climate and environment', 'multiple_choice', 'advanced', 'B2', 30, 600),
('Cultural Differences', 'Understanding cultural nuances in language', 'multiple_choice', 'advanced', 'B2', 30, 600);

-- Insert exercise questions for Basic Greetings (A1)
INSERT INTO exercise_questions (exercise_id, question_text, question_type, correct_answer, options, explanation, order_index) VALUES
(1, 'How do you greet someone in the morning?', 'multiple_choice', 'Good morning', '["Good morning", "Good night", "Good afternoon", "Good evening"]', 'We say "Good morning" when greeting someone in the morning.', 1),
(1, 'What is a polite way to ask for something?', 'multiple_choice', 'Please', '["Please", "Now", "Quickly", "Always"]', '"Please" is the polite way to make a request.', 2),
(1, 'How do you respond to "Thank you"?', 'multiple_choice', 'You''re welcome', '["You''re welcome", "Thank you", "Please", "Sorry"]', '"You''re welcome" is the polite response to "Thank you".', 3),
(1, 'What do you say when you meet someone for the first time?', 'multiple_choice', 'Nice to meet you', '["Nice to meet you", "Goodbye", "See you later", "How are you?"]', '"Nice to meet you" is used when meeting someone for the first time.', 4),
(1, 'How do you say goodbye politely?', 'multiple_choice', 'Have a good day', '["Have a good day", "Go away", "Leave now", "Stop talking"]', '"Have a good day" is a polite way to say goodbye.', 5);

-- Insert exercise questions for Numbers 1-20 (A1)
INSERT INTO exercise_questions (exercise_id, question_text, question_type, correct_answer, options, explanation, order_index) VALUES
(2, 'What number comes after 5?', 'multiple_choice', '6', '["4", "6", "7", "8"]', 'The number after 5 is 6.', 1),
(2, 'How do you write the number 12?', 'multiple_choice', 'twelve', '["twelve", "twenty", "two", "ten"]', 'The number 12 is written as "twelve".', 2),
(2, 'What is 10 + 3?', 'multiple_choice', '13', '["12", "13", "14", "15"]', '10 + 3 equals 13.', 3),
(2, 'How do you say the number 15?', 'multiple_choice', 'fifteen', '["fifty", "fifteen", "five", "five-teen"]', 'The number 15 is pronounced "fifteen".', 4),
(2, 'What number is between 8 and 10?', 'multiple_choice', '9', '["7", "9", "11", "12"]', 'The number between 8 and 10 is 9.', 5);

-- Insert exercise questions for Present Simple (A2)
INSERT INTO exercise_questions (exercise_id, question_text, question_type, correct_answer, options, explanation, order_index) VALUES
(6, 'I _____ to school every day.', 'multiple_choice', 'go', '["go", "goes", "going", "went"]', 'With "I", we use "go" in present simple.', 1),
(6, 'She _____ English very well.', 'multiple_choice', 'speaks', '["speak", "speaks", "speaking", "spoke"]', 'With "she", we use "speaks" (third person singular).', 2),
(6, 'They _____ in a big city.', 'multiple_choice', 'live', '["live", "lives", "living", "lived"]', 'With "they", we use "live" (no -s for plural subjects).', 3),
(6, 'We _____ coffee every morning.', 'multiple_choice', 'drink', '["drink", "drinks", "drinking", "drank"]', 'With "we", we use "drink" (no -s for plural subjects).', 4),
(6, 'He _____ television in the evening.', 'multiple_choice', 'watches', '["watch", "watches", "watching", "watched"]', 'With "he", we use "watches" (third person singular).', 5);
