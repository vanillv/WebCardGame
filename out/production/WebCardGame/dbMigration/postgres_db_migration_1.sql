CREATE TABLE session (
    id BIGINT PRIMARY KEY,
    card_order INT,
    status VARCHAR(255),
    available BOOLEAN
);

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    login VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    sessions_won BIGINT
);

CREATE TABLE user_auth_secret (
    user_id BIGINT PRIMARY KEY,
    secret VARCHAR(255) NOT NULL,
    secret_creation_time DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE user_session_instance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    session_id BIGINT,
    name VARCHAR(255),
    points INT,
    status VARCHAR(255),
    status_duration INT,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (session_id) REFERENCES session(id)
);

CREATE TABLE turn (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    session_id BIGINT,
    player_id BIGINT,
    target_player_id BIGINT,
    status VARCHAR(255),
    card_id BIGINT,
    timestamp DATETIME,
    FOREIGN KEY (session_id) REFERENCES session(id),
    FOREIGN KEY (player_id) REFERENCES user_session_instance(id),
    FOREIGN KEY (target_player_id) REFERENCES user_session_instance(id)
);

CREATE TABLE card (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    card_type VARCHAR(20) NOT NULL, -- дискриминатор
    type VARCHAR(255), -- тип карты (значение enum)
    name VARCHAR(255),
    value INT,
    CONSTRAINT chk_card_type CHECK (card_type IN ('ACTION', 'POINTS')),
    CONSTRAINT chk_action_type CHECK (card_type != 'ACTION' OR type IN ('ATTACK', 'DEFENSE')),
    CONSTRAINT chk_points_type CHECK (card_type != 'POINTS' OR type IN ('GOLD', 'SILVER'))
);