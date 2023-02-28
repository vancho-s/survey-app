DROP TABLE IF EXISTS subjects;
CREATE TABLE subjects (
  id    BIGINT       NOT NULL AUTO_INCREMENT,
  label VARCHAR(100) NOT NULL,
  CONSTRAINT pk_subjects PRIMARY KEY (id)
);

DROP TABLE IF EXISTS questions;
CREATE TABLE questions (
  id         BIGINT       NOT NULL AUTO_INCREMENT,
  label      VARCHAR(100) NOT NULL,
  subject_id BIGINT       NOT NULL,
  enabled    BIT          DEFAULT true,
  CONSTRAINT pk_questions PRIMARY KEY (id),
  CONSTRAINT fk_subjects_questions FOREIGN KEY (subject_id) REFERENCES subjects (id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id   BIGINT       NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  CONSTRAINT pk_users PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_responses;
CREATE TABLE user_responses (
  id   BIGINT              NOT NULL AUTO_INCREMENT,
  content     VARCHAR(100) NOT NULL,
  question_id BIGINT       NOT NULL,
  user_id     BIGINT       NOT NULL,
  CONSTRAINT pk_user_responses_id PRIMARY KEY (id),
  CONSTRAINT pk_user_responses_question_id_user_id UNIQUE KEY (question_id, user_id),
  CONSTRAINT fk_questions_user_responses FOREIGN KEY (question_id) REFERENCES questions (id),
  CONSTRAINT fk_users_user_responses FOREIGN KEY (user_id) REFERENCES users (id)
);

