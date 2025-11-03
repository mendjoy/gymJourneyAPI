CREATE TABLE IF NOT EXISTS gym_user (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    phone VARCHAR(255),
    birth_date DATE,
    password VARCHAR(255) NOT NULL,
    verified BOOLEAN DEFAULT FALSE,
    token VARCHAR(255),
    expiration_token TIMESTAMP,
    active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS role (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES gym_user(id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS exercise (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(255) NOT NULL,
    muscle_group_id BIGINT NOT NULL,
    FOREIGN KEY (muscle_group_id) REFERENCES muscle_group(id)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS muscle_group (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS exercise_muscle_group (
    exercise_id BIGINT NOT NULL,
    muscle_group_id BIGINT NOT NULL,
    PRIMARY KEY (exercise_id, muscle_group_id),
    CONSTRAINT fk_exercise
    FOREIGN KEY (exercise_id) REFERENCES exercise(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_muscle_group
    FOREIGN KEY (muscle_group_id) REFERENCES muscle_group(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS workout (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(255),
    description VARCHAR(255),
    max_sessions INTEGER NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    FOREIGN KEY (user_id) REFERENCES gym_user(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS workout_section (
    id BIGSERIAL PRIMARY KEY,
    workout_id BIGINT NOT NULL,
    name VARCHAR(255),
    description VARCHAR(255),
    FOREIGN KEY (workout_id) REFERENCES workout(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS workout_exercise (
    id BIGSERIAL PRIMARY KEY,
    workout_section_id BIGINT NOT NULL,
    exercise_id BIGINT NOT NULL,
    sets INTEGER NOT NULL,
    repetitions INTEGER NOT NULL,
    weight DOUBLE PRECISION,
    rest_time INTEGER,
    FOREIGN KEY (workout_section_id) REFERENCES workout_section(id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (exercise_id) REFERENCES exercise(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);