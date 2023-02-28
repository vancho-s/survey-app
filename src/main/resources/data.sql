INSERT INTO subjects (id, label) VALUES (1, 'Where does Santa Claus Live?');
INSERT INTO subjects (id, label) VALUES (2, 'Where yo satisfied with your Christmas present?');

INSERT INTO questions (id, label, subject_id, enabled) VALUES (1, 'Hawaii?', 1, true);
INSERT INTO questions (id, label, subject_id, enabled) VALUES (2, 'Finland?', 1, true);
INSERT INTO questions (id, label, subject_id, enabled) VALUES (3, 'Sweden?', 1, true);
INSERT INTO questions (id, label, subject_id, enabled) VALUES (4, 'China?', 1, true);
INSERT INTO questions (id, label, subject_id, enabled) VALUES (5, 'Very satisfied', 2, true);
INSERT INTO questions (id, label, subject_id, enabled) VALUES (6, 'Somewhat satisfied', 2, true);
INSERT INTO questions (id, label, subject_id, enabled) VALUES (7, 'Never satisfied or dissatisfied', 2, true);
INSERT INTO questions (id, label, subject_id, enabled) VALUES (8, 'Dissatisfied', 2, true);
INSERT INTO questions (id, label, subject_id, enabled) VALUES (9, 'Very dissatisfied', 2, false);

INSERT INTO users (id, name) VALUES (1, 'user1');
INSERT INTO users (id, name) VALUES (2, 'user2');

INSERT INTO user_responses (id, content, question_id, user_id) VALUES (21, 'No',  1, 1);
INSERT INTO user_responses (id, content, question_id, user_id) VALUES (22, 'Yes', 2, 1);
INSERT INTO user_responses (id, content, question_id, user_id) VALUES (23, 'No',  3, 1);
INSERT INTO user_responses (id, content, question_id, user_id) VALUES (24, 'No',  4, 1);
INSERT INTO user_responses (id, content, question_id, user_id) VALUES (25, 'No',  5, 1);
INSERT INTO user_responses (id, content, question_id, user_id) VALUES (26, 'Yes', 6, 1);
INSERT INTO user_responses (id, content, question_id, user_id) VALUES (27, 'No',  7, 1);
INSERT INTO user_responses (id, content, question_id, user_id) VALUES (28, 'No',  8, 1);
INSERT INTO user_responses (id, content, question_id, user_id) VALUES (29, 'No',  9, 1);

