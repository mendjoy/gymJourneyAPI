INSERT INTO role (name) VALUES
('ADMIN'),
('TRAINER'),
('USER')
ON CONFLICT (name) DO NOTHING;

INSERT INTO muscle_group (name) VALUES
('Peito'),
('Costas'),
('Ombros'),
('Bíceps'),
('Tríceps'),
('Antebraço'),
('Abdômen'),
('Quadríceps'),
('Posterior de coxa'),
('Glúteos'),
('Panturrilhas')
ON CONFLICT (name) DO NOTHING;