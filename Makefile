.PHONY: all build up down seed clean help

all: up

build:
	docker compose build

up:
	docker compose up -d

down:
	docker compose down

seed:
	docker compose exec db psql -U standofit -d standofit -c "TRUNCATE exercise_logs, workout_sessions, workout_exercises, workout_days, workouts, exercises RESTART IDENTITY CASCADE;"
	docker compose cp src/main/resources/data.sql db:/tmp/data.sql
	docker compose exec db psql -U standofit -d standofit -f /tmp/data.sql

clean:
	docker compose down -v

help:
	@echo "Usage:"
	@echo "  make          Start containers (builds if needed)"
	@echo "  make build    Force rebuild images"
	@echo "  make up       Start containers in background"
	@echo "  make down     Stop containers"
	@echo "  make seed     Load seed data into database"
	@echo "  make clean    Stop and remove volumes"
	@echo "  make help     Show this message"
