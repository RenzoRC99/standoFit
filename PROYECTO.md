# PROYECTO.md — standoFit

> **Versión:** 1.0 · 2026-08-04

## Visión

Plataforma de entrenamiento que permite a los usuarios planificar rutinas de gimnasio, ejecutar sesiones registrando su rendimiento en tiempo real, y consultar estadísticas de progreso.

## Responsable

Renzo Romero

## Stack tecnológico

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 21 |
| Framework | Spring Boot 3.4 |
| Build | Gradle (wrapper) |
| Base de datos | PostgreSQL 16 (prod), H2 (dev/test) |
| Infraestructura | Docker Compose, Traefik |
| API | REST con OpenAPI 3 (generación de código) |
| Testing | JUnit 5, Testcontainers, JaCoCo |
| Formato | Spotless (Google Java Format) |

## Arquitectura

DDD + Hexagonal + CQRS con buses de comando, query y eventos de dominio:

```
presentation → application (commands/queries) → domain → infrastructure
```

- **Domain**: entidades, value objects, eventos de dominio, repositorios (interfaces)
- **Application**: handlers de comando/query, casos de uso
- **Infrastructure**: JPA, repositorios concretos, controladores REST
- **Presentation**: controladores que implementan interfaces generadas por OpenAPI

## Módulos

### Planning (implementado)
Gestión de workouts: crear, editar, duplicar rutinas con días y ejercicios. Reordenar días.

### Execution (implementado)
Ejecución de sesiones desde un día planificado. Registro de ejercicios realizados (series, reps, peso). Iniciar, pausar, finalizar, cancelar sesiones.

### Exercises (implementado)
Catálogo de ejercicios con nombre, descripción, grupo muscular. CRUD completo.

## Features planificadas (roadmap)

| Prioridad | Feature | Estado |
|---|---|---|
| 🔴 P0 | Migrar sesiones a event-driven (EventBus + DomainEventHandlers) | ✅ Hecho |
| 🔴 P0 | Event Sourcing — persistir eventos en event store + reconstruir aggregate | Pendiente |
| 🔴 P0 | Autenticación JWT + gestión de usuarios | Pendiente (diseño no decidido) |
| 🟡 P1 | Estadísticas y progreso (gráficas, histórico) | Pendiente |
| 🟡 P1 | Métricas avanzadas (1RM, volumen, rachas) | Pendiente |
| 🟢 P2 | Frontend / App móvil (o PWA) | Pendiente |

## Restricciones

- Sin deadline fijo.
- Proyecto personal.
- Multi-tenant: cada usuario ve solo sus datos (una vez implementada auth).

## Fuera de alcance (por ahora)

- Funcionalidades sociales (compartir rutinas, seguir usuarios).
- Generación automática de rutinas / IA.
