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

## Reglas de negocio

### Planning (Workouts)

**Invariantes del agregado Workout**

- Un workout debe tener al menos 1 día. Si se intenta crear o añadir días con lista vacía o nula, lanza `DAYS_CANNOT_BE_NULL_OR_EMPTY`.
- Los nombres de día son únicos dentro del workout (case-insensitive, trimmed). Duplicados lanzan `DAY_NAME_ALREADY_EXISTS`.
- Para eliminar, renombrar o modificar un día, su ID debe existir en el workout. Si no, lanza `DAY_ID_NOT_FOUND`.
- Para eliminar, añadir o actualizar ejercicios de un día, la lista de ejercicios/IDs no puede ser vacía o nula.
- Al reordenar días: la lista no puede ser vacía/nula, todos los IDs deben existir y la cantidad debe coincidir exactamente con los días actuales (`DAYS_COUNT_MISMATCH`).
- Todas las mutaciones son inmutables: `renameWorkout()`, `addDays()`, `removeDays()`, `reorderDays()`, etc. devuelven una nueva instancia de `Workout` vía el factory `copy()`. El original nunca se modifica.
- Al duplicar un workout se generan nuevos UUIDs para el workout, sus días y ejercicios. Si no se especifica nombre, se usa "[nombre original] (Copy)".

**Value Objects y restricciones**

| VO | Rango | Regla |
|---|---|---|
| WorkoutName | 1-100 chars | No vacío, no solo whitespace |
| WorkoutDescription | 0-500 chars | Vacío permitido (default "") |
| WorkoutDayName | 1-50 chars | No vacío |
| WorkoutExerciseSets | 1-100 | Mínimo 1 serie |
| WorkoutExerciseReps | 1-1000 | Mínimo 1 repetición |
| WorkoutExerciseRest | 0-3600s | 0 = sin descanso, máx 1 hora |
| IDs (WorkoutId, WorkoutDayId, etc.) | no null | Validado en el constructor de Id |

**Validación cross-aggregate**

- `WorkoutDomainValidator.ensureExercisesExist()`: al crear workout, añadir día o reemplazar ejercicios, todos los ExerciseId deben existir en el catálogo de ejercicios. Si alguno falta, lanza `EXERCISE_NOT_FOUND_IN_CATALOG`.

**Errores específicos de Planning**

- Todos lanzan `WorkoutDomainException` con el mensaje correspondiente de `WorkoutDomainErrors`.

---

### Execution (Sesiones)

**Máquina de estados**

El agregado `Session` tiene 3 estados: `IN_PROGRESS`, `COMPLETED` y `CANCELLED`. `COMPLETED` y `CANCELLED` son estados terminales e inmutables.

Transiciones permitidas:
- `create()` → `IN_PROGRESS`
- `IN_PROGRESS` → `finish()` → `COMPLETED`
- `IN_PROGRESS` → `cancel()` → `CANCELLED`

Transiciones prohibidas:
- `COMPLETED` + `finish()` → error `SESSION_ALREADY_FINISHED`
- `CANCELLED` + `finish()` → error `SESSION_CANNOT_BE_FINISHED`
- `COMPLETED` + `cancel()` → error `SESSION_CANNOT_BE_CANCELLED`
- `CANCELLED` + `cancel()` → error `SESSION_ALREADY_CANCELLED`

**Guard de mutación**

Cualquier operación que modifique la sesión (`addLog`, `removeLog`, `updateLogSets`, `updateLogReps`, `updateLogWeight`, `changeNotes`) solo se permite en `IN_PROGRESS`. En cualquier otro estado lanza `SESSION_CANNOT_BE_MODIFIED`.

**Exercise logs**

- Para eliminar o actualizar un log, su `ExerciseLogId` debe existir en la sesión. Si no, lanza `LOG_NOT_FOUND`.

**Inmutabilidad**

Toda mutación (`finish()`, `cancel()`, `addLog()`, `removeLog()`, `updateLog*()`, `changeNotes()`) devuelve una nueva instancia de `Session`. El original nunca se modifica.

**Eventos de dominio**

Cada transición registra un evento en el aggregate. Al persistir, `EventDrivenSessionRepository` los publica al `EventBus` y los `DomainEventHandler` actualizan la read-view desnormalizada para consultas eficientes:

| Operación | Evento |
|---|---|
| `create()` | `SessionStarted` |
| `finish()` | `SessionFinished` |
| `cancel()` | `SessionCancelled` |
| `changeNotes()` | `SessionNotesChanged` |
| `addLog()` | `ExerciseLogAdded` |
| `removeLog()` | `ExerciseLogRemoved` |
| `updateLogSets()` | `ExerciseLogSetsUpdated` |
| `updateLogReps()` | `ExerciseLogRepsUpdated` |
| `updateLogWeight()` | `ExerciseLogWeightUpdated` |

**Value Objects**

| VO | Rango |
|---|---|
| WorkoutSessionNotes | 0-1000 chars |
| ExerciseLogSets | 0-100 |
| ExerciseLogReps | 0-1000 |
| ExerciseLogWeight | 0-1000 |

**Validación cross-aggregate**

- `SessionDomainValidator.ensureDayExists()`: al iniciar sesión, el día debe existir en Planning (`DAY_NOT_FOUND`).
- `SessionDomainValidator.ensureExerciseExists()`: al añadir un log, el ejercicio debe existir en el catálogo (`EXERCISE_NOT_FOUND`).

**Errores específicos**

Todos lanzan `SessionDomainException` con el mensaje de `SessionDomainErrors`.

---

### Exercises (Catálogo)

Módulo CRUD simple. Validación a nivel JPA/BD:

- **Nombre**: requerido, máx 100 chars
- **Descripción**: opcional, máx 500 chars
- **Grupo muscular**: requerido, enum `CHEST | BACK | SHOULDERS | BICEPS | TRICEPS | LEGS | CORE | FULL_BODY`

A futuro se planea migrar a value objects para consistencia con el resto de módulos.

**Problema conocido: borrado de ejercicios con referencias activas**

Al eliminar un ejercicio del catálogo, no existe validación pre-delete ni FK en base de datos que impida el borrado (los contextos se comunican por ID, sin `@ManyToOne` ni `@ForeignKey`). Esto provoca:

- **Planning**: los workouts que referencian el ejercicio borrado muestran `null` en nombre y grupo muscular del DTO (degradación silenciosa).
- **Execution**: `SessionReadViewUpdater` lanza `SessionInfrastructureException` al intentar resolver el nombre del ejercicio en la read-view, provocando un error 500.

Solución pendiente: implementar soft-delete (`active=false`) en Exercises y añadir validación pre-delete que rechace el borrado si existen referencias activas en otros contextos.

## Features planificadas (roadmap)

| Prioridad | Feature | Estado |
|---|---|---|
| 🔴 P0 | Migrar sesiones a event-driven (EventBus + DomainEventHandlers) | ✅ Hecho |
| 🔴 P0 | Event Sourcing — persistir eventos en event store + reconstruir aggregate | Pendiente |
| 🔴 P0 | Autenticación JWT + gestión de usuarios | Pendiente (diseño no decidido) |
| 🟡 P1 | Proteger borrado de ejercicios (soft-delete + validación pre-delete) | Pendiente |
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
