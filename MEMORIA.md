# MEMORIA.md — standoFit

> **Sello:** v6 · 2026-09-15 · Pendientes actualizados (chat de revisión)

## 1 · Cronología

| Fecha | Versión | Hito |
|---|---|---|
| 2026-08-04 | v1 | Bootstrap inicial. Preflight de entorno. Análisis del código existente. |
| 2026-08-04 | v3 | Migración event-driven completada: EventDrivenSessionRepository + 9 DomainEventHandlers. 112 tests pasan. Abierta P0 de Event Sourcing. |
| 2026-08-06 | v4 | Revisión integral de reglas de negocio. Documentadas en PROYECTO.md. Identificados gaps: borrado de ejercicios sin validación, borrado de workouts con sesiones huérfanas, límites transaccionales en controller en lugar de handlers, manejo de errores en InMemoryBus. |
| 2026-08-10 | v5 | Event Sourcing completado: tabla event_store + DomainEventSerializer con factories + SessionReconstructor + EventSourcedSessionRepository. Eliminado repositorio JPA (workout_sessions). 8 commits. |
| 2026-09-15 | v6 | Revisión de estado del Event Sourcing en Execution. Confirmado en vivo: agregado Session es 100% event-sourced (sin `SessionJpaEntity`, `JpaSessionRepository` ni `JpaSessionMapper`). Solo persisten entidades JPA: `EventStoreJpaEntity` (event store) + 3 read-views (`SessionReadViewJpaEntity`, `ExerciseLogReadViewJpaEntity`, `PlannedExerciseReadViewJpaEntity`) como cachés derivados para queries. Sin acción de código, solo actualización de pendientes. |

---

## 2 · Decisiones (ADR-lite)

| ID | Fecha | Decisión | Justificación | Estado |
|---|---|---|---|---|
| D01 | 2026-08-04 | Stack Java 21 + Spring Boot 3.4 + PostgreSQL | Según build.gradle | Registrado |
| D02 | 2026-08-04 | Arquitectura DDD + Hexagonal + CQRS | Según estructura del código (buses cmd/query/event) | Registrado |
| D03 | 2026-08-04 | Multi-user con JWT | Confirmado por Renzo. Diseño específico pendiente. | Pendiente |
| D04 | 2026-08-04 | Migrar execution de relacional a event-driven (usando eventos de dominio ya existentes) | Se creó EventSourcedSessionRepository + 9 DomainEventHandler. Eventos se persisten en event_store, se publican al EventBus y la read-view se actualiza reactivamente. | Hecho |
| D05 | 2026-08-04 → 2026-08-10 | Migrar de event-driven a Event Sourcing (tabla event_store + reconstrucción de aggregate) | Creada tabla event_store con EventStoreJpaEntity. EventStore (puerto) + JpaEventStore (adaptador). DomainEventSerializer con 9 factories por evento. SessionReconstructor (domain service) reconstruye el aggregate desde eventos. EventSourcedSessionRepository (@Primary) reemplaza al JPA. Eliminadas SessionJpaEntity, JpaSessionMapper, JpaSessionRepository. | Hecho |
| D06 | 2026-08-06 | Soft-delete en Exercises en lugar de borrado físico | Evita datos huérfanos en Planning y Execution sin introducir FK entre bounded contexts. Patrón estándar para catálogos compartidos. | Pendiente |
| D07 | 2026-08-06 | Mover @Transactional de controllers a command handlers | La atomicidad debe vivir en el caso de uso (aplicación), no en el adaptador HTTP (presentación). Permite reutilizar comandos desde jobs, mensajes o tests sin depender del controller. | Pendiente |
| D08 | 2026-08-06 | Documentar reglas de negocio de cada bounded context en PROYECTO.md | Facilita entrevistas técnicas, onboarding y sirve como referencia viva del dominio. | Hecho |

---

## 3 · Aprendizajes

- El proyecto ya tiene código sustancial (~130 ficheros Java). No es un proyecto vacío.
- Arquitectura DDD + Hexagonal + CQRS con buses de command/query/event.
- Tres bounded contexts: Exercises (catálogo), Planning (workouts), Execution (sesiones).
- La comunicación entre contextos es por ID tipado, sin FK en BD. Correcto para DDD pero requiere protección en capa de aplicación (soft-delete, validación pre-delete).
- 9 eventos de dominio específicos en Execution (no un SessionUpdated genérico) — cada uno con intención de negocio. Facilitan read-view, auditoría y futuro Event Sourcing.
- Los @Transactional están en los controllers, no en los handlers. Esto debería invertirse para que el caso de uso controle la atomicidad.
- Planning no usa @Transactional en handlers ni MANDATORY en infraestructura. Execution tiene MANDATORY en SessionReadViewUpdater.
- InMemoryBus se traga excepciones de los handlers pero Spring ya marca la TX como rollback-only. El error resultante es confuso para el cliente.
- Generación de código desde OpenAPI (openapi-execution.yaml, openapi-planning.yaml).
- Se usa Gradle wrapper (`./gradlew`), no Gradle global.
- Docker Compose con PostgreSQL 16 para desarrollo local.
- Docker Compose **no permite interpolación fiable de variables del `.env` en la clave `ports:`** (la sintaxis `"${VAR}:5432"` falla o se ignora según el contexto). El mapeo de puertos queda **literal en el yml**. `.env` se usa solo para credenciales, URLs JDBC y configuración de Spring. Si se quiere parametrizar el puerto, hay que usar un `docker-compose.override.yml`.

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

## 6 · Pendientes (siguiente sesión)

### Event Sourcing — Execution
- [ ] Implementar evento `SessionDeleted` y soporte en `SessionReconstructor` — actualmente `deleteById()` es no-op en el event store (append-only). El `SessionReconstructor` debe lanzar `SessionNotFoundException` si el último evento es `SessionDeleted`, para que `getById()` no reconstruya sesiones borradas.
- [ ] Definir política de snapshots si la cadena de eventos crece (rebobinar N eventos en cada `getById()` puede degradarse). Criterio de decisión: nº de eventos por agregado o tiempo desde el último snapshot.

### Transacciones y buses
- [ ] Mover `@Transactional` de controllers a command handlers en Execution (StartSessionHandler, FinishSessionHandler, etc.)
- [ ] Añadir `@Transactional(propagation = MANDATORY)` a repos de Planning (mismo patrón que SessionReadViewUpdater)
- [ ] Auditar manejo de errores en `InMemoryBus` — captura la excepción pero la TX sigue marcada rollback-only
- [ ] Añadir `rollbackFor = Exception.class` explícito en todos los `@Transactional`

### Integridad referencial entre bounded contexts
- [ ] Implementar soft-delete (`active=false`) en Exercise + validación pre-delete — actualmente borrar un ejercicio referenciado provoca: null silencioso en Planning (DTO) y error 500 en Execution (`SessionReadViewUpdater` no resuelve el nombre).
- [ ] Añadir verificación de sesiones activas antes de borrar workout/día — evitar workouts huérfanos con sesiones en `IN_PROGRESS` apuntando a días inexistentes.

### Exercises
- [ ] Normalizar Exercises a value objects para consistencia con el resto de módulos (actualmente validación solo a nivel JPA/BD, sin invariantes de dominio).

### Operación / futuras
- [ ] Diseñar e implementar autenticación JWT + gestión de usuarios (D03). Bloquea multi-tenancy real.
- [ ] Decidir persistencia de read-views ante fallo del bus: hoy un fallo en `SessionReadViewUpdater` puede dejar el agregado y la read-view divergentes si la TX no rollbackea el append.
- [ ] Consumidores de Application Event Bus: `PlanningActivityEvent` y `SessionActivityEvent` se publican desde los handlers de Planning y Execution pero no tienen suscriptores → WARN `No subscriber found` en `InMemoryBus`. Decidir destino: tabla `activity_log` para auditoría interna, stream externo (Kafka/SSE), o silenciar el WARN a DEBUG. No es bug funcional; el sistema trabaja correctamente.
- [ ] ActivityEvent deben publicarse **solo en failure** (no en éxito). Diseño orientado a futuro consumer Kafka: `PlanningActivityEvent.failure(...)` y `SessionActivityEvent.failure(...)` quedan dentro del `catch (Exception)` de cada handler; las llamadas `.success(...)` en el path de éxito se eliminan. Hoy `event_store` ya actúa como log de auditoría de operaciones exitosas, por lo que perder el `success` no implica perder trazabilidad. Pendiente: localizar todos los publishers, eliminar solo los `success`, mantener las clases `ActivityEvent`/`ActivityType` por si Kafka las consume, actualizar tests. No se implementa hoy el adapter Kafka.
