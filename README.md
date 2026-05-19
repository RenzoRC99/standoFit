# StandoFit

Backend API para gestión de planes de entrenamiento físico y sesiones de ejecución.

---

## 1. TECH STACK

**Framework**: Spring Boot 3.4.0  
**Lenguaje**: Java 21  
**Build**: Gradle  
**Arquitectura**: DDD + Hexagonal + CQRS  
**Testing**: JUnit 5, Mockito, MockMvc, WebFlux  
**BD**: H2 (in-memory)  
**OpenAPI**: OpenAPI Generator (specs en `docs/`)  
**Formato**: Spotless (Google Java Format)  
**Cobertura**: JaCoCo  

---

## 2. MÓDULOS

### 2.1 Planning (`modules/training/planning/`)

Workouts, días y ejercicios planificados. DDD hexagonal con CQRS.

**Aggregate Root**: `Workout`  
**Child entities**: `WorkoutDay`, `WorkoutExercise`  

**Commands (cada uno implementa `EventfulCommand` — conoce su evento):**

| Command | Propósito |
|---------|-----------|
| `CreateWorkoutCommand` | Crear nuevo workout |
| `RenameWorkoutCommand` | Renombrar workout |
| `ChangeWorkoutDescriptionCommand` | Cambiar descripción |
| `DeleteWorkoutCommand` | Eliminar workout |
| `DuplicateWorkoutCommand` | Duplicar workout |
| `AddDayToWorkoutCommand` | Añadir día |
| `RemoveDayFromWorkoutCommand` | Quitar día |
| `ReorderDaysCommand` | Reordenar días |
| `RenameDayCommand` | Renombrar un día |
| `ReplaceDayExercisesCommand` | Reemplazar ejercicios de un día |

**Queries:**

| Query | Propósito |
|-------|-----------|
| `GetWorkoutByIdQuery` | Obtener workout por ID |
| `SearchWorkoutsQuery` | Búsqueda con filtros, orden y paginación |

### 2.2 Execution (`modules/training/execution/`)

Sesiones de entrenamiento y logs de ejercicios ejecutados. DDD hexagonal con CQRS.

**Aggregate Root**: `Session`  
**Child entity**: `ExerciseLog`

**Commands:**

| Command | Propósito |
|---------|-----------|
| `StartSessionCommand` | Iniciar sesión |
| `FinishSessionCommand` | Finalizar sesión |
| `CancelSessionCommand` | Cancelar sesión |
| `DeleteSessionCommand` | Eliminar sesión |
| `AddExerciseLogCommand` | Añadir ejercicio a la sesión |
| `RemoveExerciseLogCommand` | Quitar ejercicio |
| `UpdateExerciseLogSetsCommand` | Actualizar series |
| `UpdateExerciseLogRepsCommand` | Actualizar repeticiones |
| `UpdateExerciseLogWeightCommand` | Actualizar peso |
| `UpdateSessionNotesCommand` | Actualizar notas |

**Queries:**

| Query | Propósito |
|-------|-----------|
| `GetSessionByIdQuery` | Obtener sesión por ID |
| `GetAllSessionsQuery` | Listar todas las sesiones |

### 2.3 Exercises (`modules/exercises/`)

Catálogo de ejercicios. CRUD simple (sin DDD).  
**Entidad**: `Exercise` (JPA)  
**Grupos musculares**: `CHEST`, `BACK`, `SHOULDERS`, `BICEPS`, `TRICEPS`, `LEGS`, `CORE`, `FULL_BODY`

---

## 3. ESTRUCTURA

```
standoFit/
├── docs/
│   ├── openapi-planning.yaml
│   └── openapi-execution.yaml
├── src/main/java/com/standofit/back/
│   ├── modules/training/
│   │   ├── planning/                    # DDD hexagonal
│   │   │   ├── domain/entity/           # Workout, WorkoutDay, WorkoutExercise, WorkoutRepository
│   │   │   ├── domain/vo/               # WorkoutName, WorkoutCreatedAt, WorkoutExerciseSets, etc.
│   │   │   ├── application/command/     # 10 commands + handlers + services
│   │   │   ├── application/query/       # 2 queries + handlers
│   │   │   ├── application/event/       # PlanningActivityEvent, PlanningActivityType
│   │   │   ├── application/dto/         # WorkoutDto, WorkoutDayDto, WorkoutExerciseDto
│   │   │   ├── application/mapper/      # WorkoutDtoMapper
│   │   │   ├── infrastructure/repository/
│   │   │   ├── infrastructure/mapper/   # WorkoutMapper (domain ↔ JPA)
│   │   │   ├── infrastructure/entity/   # WorkoutJpaEntity, WorkoutDayJpaEntity, WorkoutExerciseJpaEntity
│   │   │   └── presentation/            # Controller + mappers (API ↔ application)
│   │   └── execution/                   # DDD hexagonal (misma estructura)
│   │       ├── domain/entity/           # Session, ExerciseLog, SessionRepository
│   │       ├── application/command/     # 10 commands
│   │       └── ...
│   └── exercises/                       # CRUD simple
├── configuration/
│   └── bus/ApplicationBusConfiguration.java
├── shared/
│   ├── domain/bus/command/              # Command, CommandBus, CommandHandler, EventfulCommand
│   ├── domain/bus/query/                # Query, QueryBus, QueryHandler
│   ├── domain/bus/application_event/    # ApplicationEvent, ApplicationEventBus
│   ├── domain/criteria/                 # Filter, Criteria, PagedResult
│   ├── domain/valueobjects/             # StringVO, IntegerVO, DateTimeVO, ids (WorkoutId, SessionId, etc.)
│   ├── domain/aggregate/                # AggregateRoot
│   └── infrastructure/bus/              # InMemoryCommandBus, InMemoryQueryBus, InMemoryApplicationEventBus
└── src/test/                            # Tests unitarios + integración
```

---

## 4. ARQUITECTURA

### Domain Layer
- Entidades inmutables con `private` constructor y factories estáticas
- Value objects con validación en construcción
- Métodos de dominio retornan nuevas instancias (copy-on-write)
- `Workout.copy()` / `Session.copy()` públicas para reconstrucción desde BD

### Application Layer
- CQRS estricto: commands (write) + queries (read)
- `EventfulCommand`: cada comando define `toSuccessEvent()` y `toFailureEvent()`
- Servicios sin repetir try/catch — los comandos saben su evento
- `@Transactional` en controllers (no en handlers)

### Infrastructure Layer
- Buses en memoria con registro automático vía Spring
- JPA Entities con cascade y orphanRemoval
- Mappers: dominio ↔ JPA (preservan timestamps)

### Presentation Layer
- Controllers implementan interfaces generadas por OpenAPI
- Endpoints compuestos: el controller orquesta múltiples comandos con `if`

---

## 5. API REST

### Planning

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/workouts` | Crear workout |
| GET | `/api/workouts` | Listar todos |
| POST | `/api/workouts/search` | Buscar con filtros |
| GET | `/api/workouts/{id}` | Obtener por ID |
| PUT | `/api/workouts/{id}` | Actualizar nombre y/o descripción |
| DELETE | `/api/workouts/{id}` | Eliminar |
| POST | `/api/workouts/{id}/duplicate` | Duplicar |
| POST | `/api/workouts/{id}/days` | Añadir día |
| PUT | `/api/workouts/{id}/days/{dayId}` | Editar día (nombre y/o ejercicios) |
| DELETE | `/api/workouts/{id}/days/{dayId}` | Eliminar día |
| PUT | `/api/workouts/{id}/days/reorder` | Reordenar días |

### Execution

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/sessions` | Iniciar sesión |
| GET | `/api/sessions` | Listar todas |
| GET | `/api/sessions/{id}` | Obtener por ID |
| DELETE | `/api/sessions/{id}` | Eliminar |
| POST | `/api/sessions/{id}/finish` | Finalizar |
| POST | `/api/sessions/{id}/cancel` | Cancelar |
| POST | `/api/sessions/{id}/logs` | Añadir ejercicio |
| DELETE | `/api/sessions/{id}/logs/{logId}` | Quitar ejercicio |
| PATCH | `/api/sessions/{id}/logs/{logId}/sets` | Actualizar series |
| PATCH | `/api/sessions/{id}/logs/{logId}/reps` | Actualizar reps |
| PATCH | `/api/sessions/{id}/logs/{logId}/weight` | Actualizar peso |
| PUT | `/api/sessions/{id}/notes` | Actualizar notas |

### Exercises

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/exercises` | Listar |
| GET | `/api/exercises/{id}` | Por ID |
| GET | `/api/exercises/muscle-group/{group}` | Por grupo muscular |
| GET | `/api/exercises/search?name=` | Por nombre |
| POST | `/api/exercises` | Crear |
| PUT | `/api/exercises/{id}` | Actualizar |
| DELETE | `/api/exercises/{id}` | Eliminar |

---

## 6. DECISIONES ARQUITECTÓNICAS

- **CQRS**: Commands y Queries separados con buses dedicados
- **EventfulCommand**: cada comando conoce su evento — los servicios no crean eventos manualmente
- **Endpoints compuestos**: el controller orquesta N comandos en una request (evita que el frontend haga mil llamadas)
- **@Transactional en controllers**: los handlers no tienen tx propia — la tx la define quien orquesta
- **Inmutabilidad**: todas las entidades son inmutables; las mutaciones retornan nuevas instancias
- **Timestamps preservados**: el mapper pasa los timestamps reales del dominio a JPA y viceversa
- **Value objects con validación**: cada VO valida su invariante en el constructor (StringVO no vacío, IntegerVO rangos, etc.)
- **Ids tipados**: WorkoutId, SessionId, etc. — no UUIDs sueltos
- **Mother Pattern**: Object Mothers para tests (`WorkoutMother`, `SessionMother`, etc.)
- **Criteria + Specification**: búsquedas dinámicas con filtros y paginación

---

## 7. GETTING STARTED

```bash
# Requisitos: Java 21+

# Ejecutar
./gradlew bootRun

# Tests
./gradlew test

# Cobertura
./gradlew jacocoTestReport

# Formato
./gradlew spotlessApply
```

---

## 8. ESTADO

**Módulos completos:**
- Planning (workouts, días, ejercicios planificados)
- Execution (sesiones, logs de ejercicios)
- Exercises (catálogo CRUD)

**Infraestructura:**
- CQRS con buses en memoria
- OpenAPI specs + generación automática de interfaces
- Tests unitarios e integración
- GlobalExceptionHandler
- Postman collection
