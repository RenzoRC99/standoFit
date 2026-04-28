-- Insert Exercises first
INSERT INTO exercises (id, name, description, muscle_group) 
VALUES 
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Press de banca', 'Press banca con barra', 'PECHO'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab', 'Press militar', 'Press militar con mancuernas', 'HOMBROS'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac', 'Dominadas', 'Dominadas en barra', 'ESPALDA'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaad', 'Elevaciones laterales', 'Elevaciones laterales', 'HOMBROS'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaae', 'Peso muerto', 'Peso muerto convencional', 'PIERNA'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaf', 'Sentadilla', 'Sentadilla con barra', 'PIERNA'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaag', 'Prensa', 'Prensa de piernas', 'PIERNA'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaah', 'Curl femoral', 'Curl de pernas', 'PIERNA');

-- Workout 1: Upper Body
INSERT INTO workouts (id, name, description, created_at, updated_at) 
VALUES ('11111111-1111-1111-1111-111111111111', 'Upper Body', 'Rutina para pecho, espalda y hombros', NOW(), NOW());

-- Workout Day 1: Push Day
INSERT INTO workout_days (id, name, order_index, workout_id) 
VALUES ('22222222-2222-2222-2222-222222222222', 'Push Day', 0, '11111111-1111-1111-1111-111111111111');

-- Workout Day 2: Pull Day
INSERT INTO workout_days (id, name, order_index, workout_id) 
VALUES ('33333333-3333-3333-3333-333333333333', 'Pull Day', 1, '11111111-1111-1111-1111-111111111111');

-- Workout Day 3: Legs Day
INSERT INTO workout_days (id, name, order_index, workout_id) 
VALUES ('44444444-4444-4444-4444-444444444444', 'Legs Day', 2, '11111111-1111-1111-1111-111111111111');

-- Exercises for Push Day (Day 1)
INSERT INTO workout_exercises (id, exercise_id, sets, reps, rest_seconds, workout_day_id) 
VALUES 
('55555555-5555-5555-5555-555555555551', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 4, 10, 90, '22222222-2222-2222-2222-222222222222'),
('55555555-5555-5555-5555-555555555552', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab', 4, 10, 90, '22222222-2222-2222-2222-222222222222'),
('55555555-5555-5555-5555-555555555553', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac', 3, 12, 60, '22222222-2222-2222-2222-222222222222'),
('55555555-5555-5555-5555-555555555554', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaad', 3, 15, 60, '22222222-2222-2222-2222-222222222222');

-- Exercises for Pull Day (Day 2)
INSERT INTO workout_exercises (id, exercise_id, sets, reps, rest_seconds, workout_day_id) 
VALUES 
('66666666-6666-6666-6666-666666666661', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 4, 10, 90, '33333333-3333-3333-3333-333333333333'),
('66666666-6666-6666-6666-666666666662', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab', 4, 10, 90, '33333333-3333-3333-3333-333333333333'),
('66666666-6666-6666-6666-666666666663', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac', 3, 12, 60, '33333333-3333-3333-3333-333333333333');

-- Exercises for Legs Day (Day 3)
INSERT INTO workout_exercises (id, exercise_id, sets, reps, rest_seconds, workout_day_id) 
VALUES 
('77777777-7777-7777-7777-777777777771', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaae', 4, 10, 120, '44444444-4444-4444-4444-444444444444'),
('77777777-7777-7777-7777-777777777772', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaf', 4, 10, 120, '44444444-4444-4444-4444-444444444444'),
('77777777-7777-7777-7777-777777777773', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaag', 3, 12, 90, '44444444-4444-4444-4444-444444444444'),
('77777777-7777-7777-7777-777777777774', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaah', 3, 15, 90, '44444444-4444-4444-4444-444444444444');

-- Workout 2: Lower Body
INSERT INTO workouts (id, name, description, created_at, updated_at) 
VALUES ('88888888-8888-8888-8888-888888888888', 'Lower Body', 'Rutina Focus legs', NOW(), NOW());

-- Workout Day for Workout 2
INSERT INTO workout_days (id, name, order_index, workout_id) 
VALUES ('99999999-9999-9999-9999-999999999999', 'Full Legs', 0, '88888888-8888-8888-8888-888888888888');

-- Exercises for Full Legs
INSERT INTO workout_exercises (id, exercise_id, sets, reps, rest_seconds, workout_day_id) 
VALUES 
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa01', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaae', 4, 10, 120, '99999999-9999-9999-9999-999999999999'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa02', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaf', 4, 12, 90, '99999999-9999-9999-9999-999999999999'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa03', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaag', 4, 10, 90, '99999999-9999-9999-9999-999999999999');

-- Sessions para Workout 1 (Upper Body)
INSERT INTO workout_sessions (id, day_id, status, notes, created_at, updated_at) 
VALUES 
('cccccccc-cccc-cccc-cccc-cccccccccccc', '22222222-2222-2222-2222-222222222222', 'COMPLETED', 'Buena sesión', NOW(), NOW()),
('dddddddd-dddd-dddd-dddd-dddddddddddd', '33333333-3333-3333-3333-333333333333', 'IN_PROGRESS', '', NOW(), NOW());

-- Exercise logs para session 1 (completada con logs)
INSERT INTO exercise_logs (id, session_id, exercise_id, sets, reps, weight) 
VALUES 
('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 4, 10, 50),
('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeef', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab', 4, 10, 40);

-- Exercise logs para session 2 (en progreso)
INSERT INTO exercise_logs (id, session_id, exercise_id, sets, reps, weight) 
VALUES 
('ffffffff-ffff-ffff-ffff-ffffffffffff', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 3, 8, 45);