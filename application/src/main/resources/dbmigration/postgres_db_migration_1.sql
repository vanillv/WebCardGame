-- Создание таблицы пользователей
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    login VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    sessions_won BIGINT DEFAULT 0
);

-- Секреты авторизации
CREATE TABLE user_auth_secrets (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    secret VARCHAR(255) NOT NULL,
    secret_creation_time TIMESTAMP NOT NULL
);

-- Игровые сессии
CREATE TABLE sessions (
    id BIGINT PRIMARY KEY,
    status VARCHAR(20) NOT NULL,
    available BOOLEAN NOT NULL,
    card_order INT NOT NULL
);

-- Участники сессий
CREATE TABLE user_session_instances (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    session_id BIGINT NOT NULL REFERENCES sessions(id) ON DELETE CASCADE,
    points INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    status_duration INT DEFAULT 0
);

-- Карты
CREATE TABLE cards (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    value INT NOT NULL,
    session_id BIGINT REFERENCES sessions(id) ON DELETE CASCADE
);

-- Ходы
CREATE TABLE turns (
    id BIGSERIAL PRIMARY KEY,
    session_id BIGINT NOT NULL REFERENCES sessions(id) ON DELETE CASCADE,
    player_id BIGINT NOT NULL REFERENCES user_session_instances(id) ON DELETE CASCADE,
    target_player_id BIGINT REFERENCES user_session_instances(id) ON DELETE SET NULL,
    status VARCHAR(20) NOT NULL,
    card_id BIGINT REFERENCES cards(id) ON DELETE SET NULL,
    timestamp TIMESTAMP NOT NULL
);