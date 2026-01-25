CREATE TABLE IF NOT EXISTS gym_user (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    phone VARCHAR(255),
    birth_date DATE,
    password VARCHAR(255) NOT NULL,
    verified BOOLEAN DEFAULT FALSE,
    token VARCHAR(500),
    expiration_token TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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

CREATE TABLE IF NOT EXISTS muscle_group (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS exercise (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS exercise_muscle_group (
     exercise_id BIGINT NOT NULL,
     muscle_group_id BIGINT NOT NULL,
     PRIMARY KEY (exercise_id, muscle_group_id),
     FOREIGN KEY (exercise_id) REFERENCES exercise(id)
         ON DELETE CASCADE,
     FOREIGN KEY (muscle_group_id) REFERENCES muscle_group(id)
         ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS workout (
   id BIGSERIAL PRIMARY KEY,
   user_id BIGINT NOT NULL,
   name VARCHAR(255) NOT NULL,
   description TEXT,
   max_sessions INTEGER NOT NULL,
   start_date DATE NOT NULL,
   end_date DATE,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   FOREIGN KEY (user_id) REFERENCES gym_user(id)
       ON UPDATE CASCADE ON DELETE CASCADE,
   CONSTRAINT check_dates CHECK (end_date IS NULL OR end_date >= start_date)
);

CREATE TABLE IF NOT EXISTS workout_section (
   id BIGSERIAL PRIMARY KEY,
   workout_id BIGINT NOT NULL,
   name VARCHAR(255) NOT NULL,
   description TEXT,
   order_index INTEGER,
   FOREIGN KEY (workout_id) REFERENCES workout(id)
       ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS workout_exercise (
    id BIGSERIAL PRIMARY KEY,
    workout_section_id BIGINT NOT NULL,
    exercise_id BIGINT NOT NULL,
    sets INTEGER NOT NULL CHECK (sets > 0),
    repetitions INTEGER NOT NULL CHECK (repetitions > 0),
    weight DOUBLE PRECISION CHECK (weight >= 0),
    rest_time INTEGER CHECK (rest_time >= 0),
    FOREIGN KEY (workout_section_id) REFERENCES workout_section(id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (exercise_id) REFERENCES exercise(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);
