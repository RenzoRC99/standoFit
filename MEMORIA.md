# MEMORIA.md — standoFit

> **Sello:** v3 · 2026-08-04 · Event-driven completado

## 1 · Cronología

| Fecha | Versión | Hito |
|---|---|---|
| 2026-08-04 | v1 | Bootstrap inicial. Preflight de entorno. Análisis del código existente. |
| 2026-08-04 | v3 | Migración event-driven completada: EventDrivenSessionRepository + 9 DomainEventHandlers. 112 tests pasan. Abierta P0 de Event Sourcing. |

---

## 2 · Decisiones (ADR-lite)

| ID | Fecha | Decisión | Justificación | Estado |
|---|---|---|---|---|
| D01 | 2026-08-04 | Stack Java 21 + Spring Boot 3.4 + PostgreSQL | Según build.gradle | Registrado |
| D02 | 2026-08-04 | Arquitectura DDD + Hexagonal + CQRS | Según estructura del código (buses cmd/query/event) | Registrado |
| D03 | 2026-08-04 | Multi-user con JWT | Confirmado por Renzo. Diseño específico pendiente. | Pendiente |
| D04 | 2026-08-04 | Migrar execution de relacional a event-driven (usando eventos de dominio ya existentes) | Se creó EventDrivenSessionRepository + 9 DomainEventHandler. Eventos se publican al EventBus y el read-view se actualiza reactivamente. | Hecho |
| D05 | 2026-08-04 | Migrar de event-driven a Event Sourcing (tabla event_store + reconstrucción de aggregate) | Se añade como nueva P0. El paso actual (event-driven) es la base. Falta: persistir eventos en BD, reconstruir Session desde historial, snapshotting. | Pendiente |

---

## 3 · Aprendizajes

- El proyecto ya tiene código sustancial (~130 ficheros Java). No es un proyecto vacío.
- Arquitectura DDD + Hexagonal + CQRS con buses de command/query/event.
- Dos módulos principales: `planning` (workouts) y `execution` (sesiones).
- Generación de código desde OpenAPI (openapi-execution.yaml, openapi-planning.yaml).
- Se usa Gradle wrapper (`./gradlew`), no Gradle global.
- Docker Compose con PostgreSQL 16 para desarrollo local.

---

## 4 · Entorno

- **SO:** Ubuntu 24.04.4 LTS (Noble Numbat) · kernel 7.0.0-28-generic · x86_64
- **Shell:** /bin/bash
- **Java:** OpenJDK 21.0.11
- **Python:** 3.12.3
- **Node:** v24.19.0 · npm 11.17.0
- **Gradle:** vía wrapper (`./gradlew`) — no instalado globalmente
- **Docker:** 29.4.3 · Docker Compose v2.35.1
- **Git:** repositorio inicializado

---

## 5 · Anexos condicionales

| Anexo | Aplica | Notas |
|---|---|---|
| Base de datos | ✅ | PostgreSQL 16 en prod, H2 en dev/test. Esquema gestionado por Hibernate (`ddl-auto: update`). |
| OpenAPI | ✅ | Especificaciones YAML en `docs/`. Generación de interfaces Spring desde ellas. |
| Docker | ✅ | `docker-compose.yml` (cloud/Traefik) y `docker-compose.local.yml` (desarrollo local). |
| CI/CD | ❓ | No verificado aún. `.github/` presente. |
| Tests | ✅ | JUnit 5 + Testcontainers (PostgreSQL) + JaCoCo. 29 ficheros de test. |

---

## 6 · Pendientes

- [x] Crear `INSTRUCCIONES.md` en raíz del proyecto.
- [x] Crear `PROYECTO.md` (entrevista de definición completada).
- [ ] Verificar si hay CI/CD configurado en `.github/`.
- [ ] Verificar que `./gradlew build` compila correctamente.
- [ ] Diseñar e implementar autenticación JWT + entidad Usuario.
- [x] Migrar módulo de execution a event-driven (usando eventos de dominio ya existentes).
- [ ] Migrar módulo de execution a Event Sourcing (persistir eventos en event_store).
- [ ] Implementar estadísticas y métricas avanzadas.
