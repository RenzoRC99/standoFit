-- Exercises
INSERT INTO exercises (id, name, description, muscle_group) 
VALUES 
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Bench Press', 'Barbell bench press', 'CHEST'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab', 'Incline Dumbbell Press', 'Incline dumbbell press', 'CHEST'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac', 'Pull Ups', 'Wide grip pull ups', 'BACK'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaad', 'Barbell Row', 'Barbell bent over row', 'BACK'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaae', 'Military Press', 'Barbell overhead press', 'SHOULDERS'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaf', 'Lateral Raises', 'Dumbbell lateral raises', 'SHOULDERS'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaag', 'Squat', 'Barbell back squat', 'LEGS'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaah', 'Deadlift', 'Conventional deadlift', 'LEGS'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaai', 'Leg Press', 'Leg press machine', 'LEGS'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaj', 'Barbell Curl', 'Standing barbell curl', 'BICEPS'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaak', 'Triceps Pushdown', 'Cable triceps pushdown', 'TRICEPS'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaal', 'Plank', 'Core plank hold', 'CORE');

-- Workout 1: Push Pull Legs
INSERT INTO workouts (id, name, description, created_at, updated_at) 
VALUES ('11111111-1111-1111-1111-111111111111', 'Push Pull Legs', 'PPL split for muscle growth', NOW(), NOW());

-- Workout Days for PPL
INSERT INTO workout_days (id, name, order_index, workout_id) 
VALUES 
('22222222-2222-2222-2222-222222222201', 'Push Day', 0, '11111111-1111-1111-1111-111111111111'),
('22222222-2222-2222-2222-222222222202', 'Pull Day', 1, '11111111-1111-1111-1111-111111111111'),
('22222222-2222-2222-2222-222222222203', 'Legs Day', 2, '11111111-1111-1111-1111-111111111111');

-- Exercises for Push Day (chest, shoulders, triceps)
INSERT INTO workout_exercises (id, exercise_id, sets, reps, rest_seconds, workout_day_id) 
VALUES 
('33333333-3333-3333-3333-333333333301', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 4, 10, 90, '22222222-2222-2222-2222-222222222201'),
('33333333-3333-3333-3333-333333333302', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab', 3, 12, 60, '22222222-2222-2222-2222-222222222201'),
('33333333-3333-3333-3333-333333333303', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaae', 4, 8, 90, '22222222-2222-2222-2222-222222222201'),
('33333333-3333-3333-3333-333333333304', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaf', 3, 15, 45, '22222222-2222-2222-2222-222222222201'),
('33333333-3333-3333-3333-333333333305', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaak', 3, 12, 60, '22222222-2222-2222-2222-222222222201');

-- Exercises for Pull Day (back, biceps)
INSERT INTO workout_exercises (id, exercise_id, sets, reps, rest_seconds, workout_day_id) 
VALUES 
('33333333-3333-3333-3333-333333333306', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac', 4, 8, 90, '22222222-2222-2222-2222-222222222202'),
('33333333-3333-3333-3333-333333333307', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaad', 4, 10, 90, '22222222-2222-2222-2222-222222222202'),
('33333333-3333-3333-3333-333333333308', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaj', 3, 12, 60, '22222222-2222-2222-2222-222222222202');

-- Exercises for Legs Day
INSERT INTO workout_exercises (id, exercise_id, sets, reps, rest_seconds, workout_day_id) 
VALUES 
('33333333-3333-3333-3333-333333333309', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaag', 4, 10, 120, '22222222-2222-2222-2222-222222222203'),
('33333333-3333-3333-3333-333333333310', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaah', 3, 8, 120, '22222222-2222-2222-2222-222222222203'),
('33333333-3333-3333-3333-333333333311', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaai', 4, 12, 90, '22222222-2222-2222-2222-222222222203'),
('33333333-3333-3333-3333-333333333312', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaal', 3, 30, 45, '22222222-2222-2222-2222-222222222203');

-- Workout 2: Full Body
INSERT INTO workouts (id, name, description, created_at, updated_at) 
VALUES ('44444444-4444-4444-4444-444444444444', 'Full Body', 'Full body workout for beginners', NOW(), NOW());

INSERT INTO workout_days (id, name, order_index, workout_id) 
VALUES ('55555555-5555-5555-5555-555555555501', 'Full Body A', 0, '44444444-4444-4444-4444-444444444444');

INSERT INTO workout_exercises (id, exercise_id, sets, reps, rest_seconds, workout_day_id) 
VALUES 
('66666666-6666-6666-6666-666666666601', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaag', 3, 10, 90, '55555555-5555-5555-5555-555555555501'),
('66666666-6666-6666-6666-666666666602', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 3, 10, 90, '55555555-5555-5555-5555-555555555501'),
('66666666-6666-6666-6666-666666666603', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaad', 3, 10, 90, '55555555-5555-5555-5555-555555555501'),
('66666666-6666-6666-6666-666666666604', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaae', 3, 10, 90, '55555555-5555-5555-5555-555555555501');

-- Sessions
INSERT INTO workout_sessions (id, day_id, status, notes, created_at, updated_at) 
VALUES 
('77777777-7777-7777-7777-777777777701', '22222222-2222-2222-2222-222222222201', 'COMPLETED', 'Great session!', NOW(), NOW()),
('77777777-7777-7777-7777-777777777702', '22222222-2222-2222-2222-222222222202', 'IN_PROGRESS', '', NOW(), NOW());

-- Exercise logs for completed session
INSERT INTO exercise_logs (id, session_id, exercise_id, sets, reps, weight) 
VALUES 
('88888888-8888-8888-8888-888888888801', '77777777-7777-7777-7777-777777777701', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 4, 10, 60),
('88888888-8888-8888-8888-888888888802', '77777777-7777-7777-7777-777777777701', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab', 3, 12, 20),
('88888888-8888-8888-8888-888888888803', '77777777-7777-7777-7777-777777777701', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaae', 4, 8, 40);

-- Exercise logs for in-progress session
INSERT INTO exercise_logs (id, session_id, exercise_id, sets, reps, weight) 
VALUES 
('88888888-8888-8888-8888-888888888804', '77777777-7777-7777-7777-777777777702', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac', 2, 8, 0);
