# StandoFit

Backend API para gestión de planes de entrenamiento físico y sesiones de ejecución (fitness/gimnasio).

---

## 1. TIPO DE PROYECTO

**Framework**: Spring Boot 3.4.0  
**Lenguaje**: Java 21  
**Sistema de Build**: Gradle  
**Arquitectura**: Domain-Driven Design (DDD) con Arquitectura Hexagonal  
**Testing**: JUnit 5 + AssertJ + MockMvc + WebFlux  
**Base de datos**: H2 (in-memory)  
**OpenAPI**: OpenAPI Generator (Spring) con specs en `docs/`  
**Formato**: Spotless (Google Java Format)  
**Cobertura**: JaCoCo  

---

## 2. MÓDULOS

### 2.1 Planning (`modules/training/planning/`)

Gestión de planes de entrenamiento (workouts), días y ejercicios planificados.  
Arquitectura hexagonal completa con CQRS.

**Entidades de dominio**: `Workout` (Aggregate Root), `WorkoutDay`, `WorkoutExercise`

**Commands:**

| Command | Propósito |
|---------|-----------|
| `PlanWorkoutCommand` | Crear nuevo workout |
| `RenameWorkoutCommand` | Renombrar workout |
| `ChangeWorkoutDescriptionCommand` | Cambiar descripción |
| `DeleteWorkoutCommand` | Eliminar workout |
| `AddDayToWorkoutCommand` | Añadir día |
| `RemoveDayFromWorkoutCommand` | Quitar día |
| `ReorderDaysCommand` | Reordenar días |
| `DuplicateWorkoutCommand` | Duplicar workout |
| `ArchiveWorkoutCommand` | Archivar workout |

**Queries:**

| Query | Propósito |
|-------|-----------|
| `GetWorkoutByIdQuery` | Obtener workout por ID |
| `SearchWorkoutsQuery` | Búsqueda con criterios (filtros, orden, paginación) |

**Application Events:**

| Event | Propósito |
|-------|-----------|
| `PlanningActivityEvent` | Registro de actividad (éxito/fallo) en operaciones de planning |

### 2.2 Execution (`modules/training/execution/`)

Gestión de sesiones de entrenamiento y logs de ejercicios ejecutados.  
Arquitectura hexagonal completa con CQRS.

**Entidades de dominio**: `Session` (Aggregate Root), `ExerciseLog`

**Commands:**

| Command | Propósito |
|---------|-----------|
| `StartSessionCommand` | Iniciar nueva sesión |
| `FinishSessionCommand` | Finalizar sesión |
| `CancelSessionCommand` | Cancelar sesión |
| `DeleteSessionCommand` | Eliminar sesión |
| `AddExerciseLogCommand` | Añadir ejercicio a la sesión |
| `RemoveExerciseLogCommand` | Quitar ejercicio de la sesión |
| `UpdateExerciseLogSetsCommand` | Actualizar series |
| `UpdateExerciseLogRepsCommand` | Actualizar repeticiones |
| `UpdateExerciseLogWeightCommand` | Actualizar peso |
| `UpdateSessionNotesCommand` | Actualizar notas de sesión |

**Queries:**

| Query | Propósito |
|-------|-----------|
| `GetSessionByIdQuery` | Obtener sesión por ID |
| `GetAllSessionsQuery` | Listar todas las sesiones |

**Application Events:**

| Event | Propósito |
|-------|-----------|
| `SessionActivityEvent` | Registro de actividad en operaciones de execution |

### 2.3 Exercises (`modules/exercises/`)

Catálogo de ejercicios. Módulo simple CRUD (sin DDD).

**Entidad**: `Exercise` (JPA directa)  
**Endpoints**: `GET/POST/PUT/DELETE /api/exercises`

| Método | Endpoint | Propósito |
|--------|----------|-----------|
| GET | `/api/exercises` | Listar todos |
| GET | `/api/exercises/{id}` | Buscar por ID |
| GET | `/api/exercises/muscle-group/{group}` | Filtrar por grupo muscular |
| GET | `/api/exercises/search?name=` | Buscar por nombre |
| POST | `/api/exercises` | Crear ejercicio |
| PUT | `/api/exercises/{id}` | Actualizar ejercicio |
| DELETE | `/api/exercises/{id}` | Eliminar ejercicio |

**Grupos musculares**: `CHEST`, `BACK`, `SHOULDERS`, `BICEPS`, `TRICEPS`, `LEGS`, `CORE`, `FULL_BODY`

---

## 3. ESTRUCTURA DE DIRECTORIOS

```
standoFit/
├── docs/
│   ├── openapi-planning.yaml          # OpenAPI spec del módulo Planning
│   └── openapi-execution.yaml         # OpenAPI spec del módulo Execution
├── src/main/java/com/standofit/back/
│   ├── modules/
│   │   └── training/
│   │       ├── planning/                  # Módulo Planning (DDD hexagonal)
│   │       │   ├── domain/
│   │       │   │   ├── entity/           # Workout, WorkoutDay, WorkoutExercise, WorkoutRepository
│   │       │   │   ├── vo/               # WorkoutName, WorkoutDayName, WorkoutExerciseReps, etc.
│   │       │   │   ├── WorkoutDomainErrors.java
│   │       │   │   └── WorkoutDomainException.java
│   │       │   ├── application/
│   │       │   │   ├── command/          # 9 Commands + Handlers + Services
│   │       │   │   ├── query/           # 2 Queries + Handlers + Searchers
│   │       │   │   ├── event/            # PlanningActivityEvent, PlanningActivityType
│   │       │   │   ├── dto/             # WorkoutDto, WorkoutDayDto, WorkoutExerciseDto
│   │       │   │   ├── mapper/          # WorkoutDtoMapper
│   │       │   │   ├── PlanningUseCase.java
│   │       │   │   ├── PlanningApplicationError.java
│   │       │   │   └── ApplicationPlanningException.java
│   │       │   ├── infrastructure/
│   │       │   │   ├── repository/      # WorkoutRepositoryJpaImpl, WorkoutJpaRepository, WorkoutSpecification
│   │       │   │   ├── mapper/          # WorkoutMapper, DatabaseExceptionMapper
│   │       │   │   ├── entity/          # WorkoutJpaEntity, WorkoutDayJpaEntity, WorkoutExerciseJpaEntity
│   │       │   │   ├── WorkoutInfrastructureErrors.java
│   │       │   │   ├── WorkoutInfrastructureException.java
│   │       │   │   └── DataInitializer.java
│   │       │   └── presentation/
│   │       │       ├── PlanningApiController.java   # REST controller (implementa PlanningApi OpenAPI)
│   │       │       ├── PlanningCommandMapper.java
│   │       │       ├── PlanningQueryMapper.java
│   │       │       └── WorkoutApiMapper.java
│   │       └── execution/                 # Módulo Execution (DDD hexagonal)
│   │           ├── domain/
│   │           │   ├── entity/           # Session, ExerciseLog, SessionRepository
│   │           │   ├── vo/               # WorkoutSessionStatus, ExerciseLogSets/Reps/Weight, SessionId, etc.
│   │           │   ├── SessionDomainErrors.java
│   │           │   └── SessionDomainException.java
│   │           ├── application/
│   │           │   ├── command/          # 10 Commands + Handlers + Services
│   │           │   ├── query/           # 2 Queries + Handlers
│   │           │   ├── event/            # SessionActivityEvent, ExecutionActivityType
│   │           │   ├── dto/             # SessionDto, SessionListDto, ExerciseLogDto
│   │           │   ├── mapper/          # SessionDtoMapper
│   │           │   ├── ExecutionUseCase.java
│   │           │   ├── ExecutionApplicationError.java
│   │           │   └── ApplicationExecutionException.java
│   │           ├── infrastructure/
│   │           │   ├── repository/       # SessionRepositoryJpaImpl, SessionJpaRepository
│   │           │   ├── mapper/          # SessionMapper, SessionDatabaseExceptionMapper, SessionDTOMapper
│   │           │   ├── entity/          # SessionJpaEntity, ExerciseLogJpaEntity
│   │           │   ├── SessionInfrastructureErrors.java
│   │           │   └── SessionInfrastructureException.java
│   │           └── presentation/
│   │               └── ExecutionApiController.java  # REST controller (implementa ExecutionApi OpenAPI)
│   └── exercises/                         # Módulo Exercises (CRUD simple)
│       ├── Exercise.java
│       ├── ExerciseMuscleGroup.java
│       ├── controller/ExerciseController.java
│       ├── service/ExerciseService.java
│       ├── repository/ExerciseRepository.java
│       └── dto/ExerciseRequest.java
├── configuration/
│   └── bus/ApplicationBusConfiguration.java   # Configuración de buses (Command, Query, Event)
├── shared/
│   ├── domain/
│   │   ├── aggregate/                     # AggregateRoot
│   │   ├── bus/
│   │   │   ├── command/                  # Command, CommandBus, CommandHandler
│   │   │   ├── query/                    # Query, QueryBus, QueryHandler
│   │   │   ├── event/                    # EventBus, DomainEvent
│   │   │   └── application_event/        # ApplicationEvent, ApplicationEventBus, ApplicationEventHandler
│   │   ├── criteria/                     # Filter, Filters, Order, OrderBy, Criteria, PageInfo, PagedResult
│   │   ├── valueobjects/                 # BaseVO, StringVO, IntegerVO, DateTimeVO, Id
│   │   │   └── ids/                      # WorkoutId, WorkoutDayId, WorkoutExerciseId, ExerciseId, SessionId, SessionDayId, UserId
│   │   │   └── errors/                   # ValueobjectErrors, ValueObjectException
│   │   ├── DomainException.java
│   │   ├── ApplicationException.java
│   │   └── InfrastructureException.java
│   ├── infrastructure/bus/
│   │   ├── command/                      # InMemoryCommandBus, CommandBusErrors, CommandBusException
│   │   ├── query/                        # InMemoryQueryBus, QueryBusErrors, QueryBusException
│   │   └── event/                        # InMemoryApplicationEventBus
│   ├── api/
│   │   ├── GlobalExceptionHandler.java
│   │   └── dto/                          # ResponseDTO, ErrorDTO
│   └── utils/
│       ├── CollectionUtils.java
│       ├── EnumContract.java
│       └── BaseException.java
├── src/test/
│   └── java/com/standofit/back/
│       ├── training/planning/
│       │   ├── domain/entity/            # WorkoutTest, WorkoutExerciseTest, Mothers
│       │   ├── infrastructure/repository/ # WorkoutRepositoryJpaImplTest
│       │   └── presentation/controller/  # WorkoutRestApiIntegrationTest, WorkoutSearchCriteriaIntegrationTest
│       ├── training/execution/
│       │   ├── domain/entity/            # SessionTest, ExerciseLogDTOTest
│       │   └── application/             # Handler tests (todos los commands y queries)
│       └── exercises/                    # ExerciseTest, ExerciseRestApiIntegrationTest
├── build.gradle
├── settings.gradle
├── StandoFit-API.postman_collection.json
└── README.md
```

---

## 4. ARQUITECTURA HEXAGONAL (DETAILLE)

### Domain Layer (`domain/`)
- **Entidades**: Aggregate Roots y entidades con inmutabilidad total
- **Value Objects**: Validación en constructores, inmutabilidad
- **Errores**: Enums centralizados (`*DomainErrors`, `*InfrastructureErrors`)
- **Métodos de dominio**: Retornan nuevas instancias (patrón Copy)
- **Validaciones**: Reglas de negocio en la entidad (e.g., `ensureInProgress()`, `ensureLogExists()`)

### Application Layer (`application/`)
- **CQRS**: Commands (escritura) + Queries (lectura) separados
- **Use Case base**: `PlanningUseCase` / `ExecutionUseCase` con repository, mapper y event bus
- **Events**: `PlanningActivityEvent` / `SessionActivityEvent` con status SUCCESS/FAILURE
- **Handlers**: Cada command/query tiene su Handler (inyectado en buses via Spring)
- **DTOs**: Objetos de transferencia entre capas

### Infrastructure Layer (`infrastructure/`)
- **Buses**: InMemoryCommandBus, InMemoryQueryBus, InMemoryApplicationEventBus (registro automático via Spring)
- **Entities**: JPA Entities con `@OrderBy` para mantener orden
- **Repository**: Implementaciones JPA con Specification para Criteria
- **Mappers**: Domain ↔ JPA conversion
- **Errores centralizados**: Enums con mensajes parametrizables

### Presentation Layer (`presentation/`)
- **Controllers**: Implementan interfaces generadas por OpenAPI Generator
- **Mappers**: Conversión entre DTOs de aplicación y DTOs de API

---

## 5. API REST

### Planning API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/workouts` | Crear plan de entrenamiento |
| GET | `/api/workouts` | Listar todos los planes |
| POST | `/api/workouts/search` | Buscar planes con filtros/paginación |
| GET | `/api/workouts/{workoutId}` | Obtener plan por ID |
| PUT | `/api/workouts/{workoutId}/name` | Renombrar plan |
| PUT | `/api/workouts/{workoutId}/description` | Cambiar descripción |
| DELETE | `/api/workouts/{workoutId}` | Eliminar plan |
| POST | `/api/workouts/{workoutId}/days` | Añadir día al plan |
| DELETE | `/api/workouts/{workoutId}/days/{dayId}` | Quitar día del plan |
| PUT | `/api/workouts/{workoutId}/days/reorder` | Reordenar días |
| POST | `/api/workouts/{workoutId}/duplicate` | Duplicar plan |
| POST | `/api/workouts/{workoutId}/archive` | Archivar plan |

### Execution API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/sessions` | Iniciar nueva sesión |
| GET | `/api/sessions` | Listar todas las sesiones |
| GET | `/api/sessions/{sessionId}` | Obtener sesión por ID |
| DELETE | `/api/sessions/{sessionId}` | Eliminar sesión |
| POST | `/api/sessions/{sessionId}/finish` | Finalizar sesión |
| POST | `/api/sessions/{sessionId}/cancel` | Cancelar sesión |
| POST | `/api/sessions/{sessionId}/logs` | Añadir ejercicio a la sesión |
| DELETE | `/api/sessions/{sessionId}/logs/{logId}` | Quitar ejercicio de la sesión |
| PATCH | `/api/sessions/{sessionId}/logs/{logId}/sets` | Actualizar series |
| PATCH | `/api/sessions/{sessionId}/logs/{logId}/reps` | Actualizar repeticiones |
| PATCH | `/api/sessions/{sessionId}/logs/{logId}/weight` | Actualizar peso |
| PUT | `/api/sessions/{sessionId}/notes` | Actualizar notas |

### Exercises API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/exercises` | Listar todos |
| GET | `/api/exercises/{id}` | Buscar por ID |
| GET | `/api/exercises/muscle-group/{group}` | Filtrar por grupo muscular |
| GET | `/api/exercises/search?name=` | Buscar por nombre |
| POST | `/api/exercises` | Crear ejercicio |
| PUT | `/api/exercises/{id}` | Actualizar ejercicio |
| DELETE | `/api/exercises/{id}` | Eliminar ejercicio |

---

## 6. DECISIONES ARQUITECTÓNICAS

### 6.1 CQRS Pattern
- Separación estricta entre Commands (escritura) y Queries (lectura)
- Commands representan casos de uso específicos de dominio
- Queries usan Criteria pattern para búsquedas flexibles

### 6.2 Application Event Bus
- `ApplicationEventBus` publica eventos de actividad (SUCCESS/FAILURE)
- `InMemoryApplicationEventBus` con registro automático de handlers via Spring
- Registro de auditoría de cada operación ejecutada en Planning y Execution

### 6.3 Criteria Pattern
- `Filter`: campo, operador, valor
- `Order`: campo, dirección (ASC/DESC)
- `PageInfo`: página, tamaño
- `PagedResult`: resultados paginados
- Implementación JPA Specification para filtrado en BD

### 6.4 Errores Centralizados
- **Domain**: Enums con mensajes (`WorkoutDomainErrors`, `SessionDomainErrors`)
- **Application**: Excepciones específicas (`ApplicationPlanningException`, `ApplicationExecutionException`)
- **Infrastructure**: Enums con mensajes (`WorkoutInfrastructureErrors`, `SessionInfrastructureErrors`)
- **Global**: `GlobalExceptionHandler` para manejo centralizado de errores REST

### 6.5 Value Objects
- Herencia: `BaseVO` → `StringVO` / `IntegerVO` / `DateTimeVO`
- IDs tipados: `WorkoutId`, `WorkoutDayId`, `WorkoutExerciseId`, `ExerciseId`, `SessionId`, `SessionDayId`, `UserId`
- Validación en construcción, inmutabilidad

### 6.6 OpenAPI Generator
- Specs en `docs/openapi-planning.yaml` y `docs/openapi-execution.yaml`
- Generación automática de interfaces API (`PlanningApi`, `ExecutionApi`) y DTOs
- Controllers implementan las interfaces generadas

---

## 7. PATRONES UTILIZADOS

1. **Aggregate Root**: `Workout` y `Session` (mantiene consistencia)
2. **Value Objects**: Tipos inmutables con validación
3. **Factory Methods**: `Workout.create()`, `Session.create()`, `ExerciseLog.create()`
4. **Copy Pattern**: Métodos que retornan nuevas instancias para inmutabilidad
5. **CQRS**: Separación Commands/Queries con buses dedicados
6. **Criteria Pattern**: Búsquedas dinámicas con filtros y paginación
7. **Application Events**: Registro de actividad (éxito/fallo) por módulo
8. **Errors Enum**: Errores centralizados en enums por capa
9. **Mother Pattern**: Object Mothers para tests (`WorkoutMother`, `WorkoutDayMother`, etc.)
10. **Specification Pattern**: `WorkoutSpecification` para Criteria JPA

---

## 8. ESTADO ACTUAL

**Completado:**
- Dominio de Planning funcional (Workout, WorkoutDay, WorkoutExercise)
- Dominio de Execution funcional (Session, ExerciseLog)
- Módulo de Exercises (CRUD)
- CQRS implementado en Planning y Execution con buses en memoria
- Application Event Bus implementado
- Repository con JPA + H2
- REST API completa (Planning, Execution, Exercises)
- OpenAPI specs y generación de interfaces
- GlobalExceptionHandler
- Postman collection

**Pendiente (ver `cosas-hacer.txt`):**
- Middleware en buses: logging consistente, métricas, transacciones automáticas, tracing (OpenTelemetry), auditoría

---

## Getting Started

### Requisitos
- Java 21+
- Gradle (wrapper incluido)

### Ejecutar
```bash
./gradlew bootRun
```

### Tests
```bash
./gradlew test
```

### Cobertura
```bash
./gradlew jacocoTestReport
```

### Formato (Spotless)
```bash
./gradlew spotlessApply
```

---

## Reference Documentation

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Gradle](https://gradle.org)
- [JaCoCo](https://www.jacoco.org)
- [OpenAPI Generator](https://openapi-generator.tech/)
- [Spotless](https://github.com/diffplug/spotless)