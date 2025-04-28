-- Create tables
CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR,
    login VARCHAR,
    password VARCHAR,
    sessions_won BIGINT DEFAULT 0
);

CREATE TABLE session (
    id BIGSERIAL PRIMARY KEY,
    status VARCHAR,
    available BOOLEAN,
    curr_player_index INTEGER DEFAULT 0,
    card_order INTEGER DEFAULT 63
);

CREATE TABLE card (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR,
    value INTEGER,
    card_type VARCHAR NOT NULL,
    type VARCHAR
);

CREATE TABLE user_auth_secret (
    user_id BIGINT PRIMARY KEY REFERENCES "user"(id),
    secret VARCHAR NOT NULL,
    secret_creation_time TIMESTAMP NOT NULL
);

CREATE TABLE user_session_instance (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES "user"(id),
    session_id BIGINT REFERENCES session(id),
    name VARCHAR,
    points INTEGER DEFAULT 0,
    status VARCHAR,
    status_duration INTEGER DEFAULT 0
);

CREATE TABLE turn (
    id BIGSERIAL PRIMARY KEY