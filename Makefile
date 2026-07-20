.PHONY: all build up down seed clean help

all: up

ENV ?= local

ifeq ($(ENV),cloud)
	COMPOSE_FILE := docker-compose.yml
else
	COMPOSE_FILE := docker-compose.local.yml
endif

build:
	docker compose -f $(COMPOSE_FILE) build

up:
	docker compose -f $(COMPOSE_FILE) up -d

down:
	docker compose -f $(COMPOSE_FILE) down

seed:
	docker compose -f $(COMPOSE_FILE) exec db psql -U standofit -d standofit -c "TRUNCATE exercise_logs, workout_sessions, workout_exercises, workout_days, workouts, exercises RESTART IDENTITY CASCADE;"
	docker compose -f $(COMPOSE_FILE) cp src/main/resources/data.sql db:/tmp/data.sql
	docker compose -f $(COMPOSE_FILE) exec db psql -U standofit -d standofit -f /tmp/data.sql

clean:
	docker compose -f $(COMPOSE_FILE) down -v

help:
	@echo "Usage: make [ENV=local|cloud] [target]"
	@echo ""
	@echo "Targets:"
	@echo "  make                Start containers (default: local)"
	@echo "  make build          Force rebuild images"
	@echo "  make up             Start containers in background"
	@echo "  make down           Stop containers"
	@echo "  make seed           Load seed data into database"
	@echo "  make clean          Stop and remove volumes"
	@echo "  make help           Show this message"
	@echo ""
	@echo "Examples:"
	@echo "  make ENV=local up   Start local dev environment"
	@echo "  make ENV=cloud up    Start with cloud/production config"