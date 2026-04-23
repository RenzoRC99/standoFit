# standoFit

Backend API para gestión de planes de entrenamiento físico (fitness/gimnasio).

---

## RESUMEN EXHAUSTIVO DEL PROYECTO

### 1. TIPO DE PROYECTO

**Framework**: Spring Boot 3.3.0  
**Lenguaje**: Java 21  
**Sistema de Build**: Gradle 9.4.0  
**Arquitectura**: Domain-Driven Design (DDD) con Arquitectura Hexagonal  
**Testing**: JUnit 5 + AssertJ + MockMvc  

---

### 2. ESTRUCTURA GENERAL DE DIRECTORIOS

```
standoFit/
├── src/main/java/com/standofit/back/
│   ├── modules/                          # Módulos DDD
│   │   └── training/
│   │       └── planning/
│   │           ├── domain/             # Domain Layer
│   │           │   ├── entity/         # Workout, WorkoutDay, WorkoutExercise
│   │           │   ├── vo/           # WorkoutName, WorkoutDayName, etc.
│   │           │   └── WorkoutDomainErrors.java
│   │           ├── application/        # Application Layer (CQRS)
│   │           │   ├── command/       # 9 Commands
│   │           │   └── query/        # 2 Queries
│   │           ├── infrastructure/    # Infrastructure Layer
│   │           │   ├── bus/         # InMemoryCommandBus, InMemoryQueryBus
│   │           │   ├── entity/       # JPA Entities
│   │           │   ├── mapper/       # WorkoutMapper
│   │           │   ├── repository/   # WorkoutRepositoryJpaImpl
│   │           │   └── WorkoutInfrastructureErrors.java
│   │           └── presentation/    # Presentation Layer
│   │               ├── controller/ # REST API
│   │               └── dto/       # Request/Response DTOs
│   ├── configuration/                # Configuración global
│   │   └── bus/                    # ApplicationBus
│   └── shared/                      # Código compartido
│       └── domain/
│           ├── bus/                 # Command/Query interfaces
│           ├── criteria/            # Criteria pattern (Filter, Order, Page)
│           ├── utils/               # CollectionUtils
│           └── valueobjects/         # BaseVO, IDs, Errors
├── src/test/
│   └── java/com/standofit/back/
│       └── training/
│           └── planning/
│               ├── domain/entity/  # Domain tests (94% coverage)
│               ├── infrastructure/ # Repository tests
│               └── presentation/  # Integration tests
├── build.gradle
└── README.md
```

---

### 3. ARQUITECTURA HEXAGONAL IMPLEMENTADA

#### Domain Layer (`domain/`)
- **Entidades**: Workout (Aggregate Root), WorkoutDay, WorkoutExercise
- **Value Objects**: WorkoutName, WorkoutDescription, WorkoutDayName, etc.
- **Errores**: WorkoutDomainErrors

**Características:**
- Inmutabilidad total
- Validación en constructores
- Métodos de dominio que retornan nuevas instancias (patrón Copy)
- Errors centralizados en enums

#### Application Layer (`application/`)
Implementa **CQRS** con 9 Commands + 2 Queries:

**Commands:**
| Command | Propósito |
|---------|----------|
| `PlanWorkoutCommand` | Crear nuevo workout |
| `RenameWorkoutCommand` | Renombrar workout |
| `ChangeWorkoutDescriptionCommand` | Cambiar descripción |
| `DeleteWorkoutCommand` | Eliminar workout |
| `AddDayToWorkoutCommand` | Añadir día |
| `RemoveDayFromWorkoutCommand` | Quitar día |
| `DuplicateWorkoutCommand` | Duplicar workout |
| `ArchiveWorkoutCommand` | Archivar workout |
| `ReorderDaysCommand` | Reordenar días |

**Queries:**
| Query | Propósito |
|-------|----------|
| `GetWorkoutByIdQuery` | Obtener workout por ID |
| `SearchWorkoutsQuery` | Búsqueda con criterios |

#### Infrastructure Layer (`infrastructure/`)
- **Buses**: InMemoryCommandBus, InMemoryQueryBus (registro automático via Spring)
- **Entities**: WorkoutJpaEntity, WorkoutDayJpaEntity, WorkoutExerciseJpaEntity
- **Repository**: WorkoutRepositoryJpaImpl con JPA
- **Mappers**: WorkoutMapper (domain ↔ JPA)
- **Errores**: WorkoutInfrastructureErrors

**Características:**
- `orderIndex` en WorkoutDayJpaEntity para mantener orden de días
- `@OrderBy("orderIndex ASC")` para recuperar en orden
- Errores centralizados con WorkoutInfrastructureException

#### Presentation Layer (`presentation/`)
- **Controller**: WorkoutController con REST endpoints
- **DTOs**: Request/Response objects

---

### 4. DECISIONES ARQUITECTÓNICAS

#### 4.1 CQRS Pattern
Separación estricta entre Commands (escritura) y Queries (lectura):
- Cada command representa un caso de uso específico de dominio
- No Commands genéricos (Create/Update/Delete)
- Queries usan Criteria pattern para búsquedas flexibles

#### 4.2 Criteria Pattern
Implementado en `com.standofit.back.shared.domain.criteria`:
- `Filter`: campo, operador, valor
- `Order`: campo, dirección
- `PageInfo`: página, tamaño
- `PagedResult`: resultados paginados
- Implementación JPA Specification para filtrado en BD

#### 4.3 Errores Centralizados
- **Domain**: WorkoutDomainErrors (enum)
- **Infrastructure**: WorkoutInfrastructureErrors (enum)

Ejemplo de uso:
```java
throw new WorkoutInfrastructureException(
    WorkoutInfrastructureErrors.COMMAND_HANDLER_NOT_FOUND.getMessage(commandClass)
);
```

#### 4.4 Value Objects
-Herencia: BaseVO → StringVO/IntegerVO/DateTimeVO
- Validación en construcción
- Inmutabilidad

#### 4.5 CollectionUtils
```java
isNullOrEmpty(Collection)      // Funciona para List, Set, etc.
isNullOrEmptyForMap(Map)  // Para Map
```

---

### 5. COVERAGE DE TESTS

| Capa | Coverage |
|------|----------|
| **Domain** | 94% |
| **Domain VO** | 100% |
| **Application** | 84% |
| **Infrastructure** | 59% (probado por integración) |
| **Presentation** | 87% |
| **Total** | 77% |

**Tipos de tests:**
- **Unit tests**: Domain entities, handlers
- **Integration tests**: REST API con MockMvc
- **Repository tests**: WorkoutRepositoryJpaImpl

---

### 6. API REST

| Método | Endpoint | Command/Query |
|--------|----------|-------------|
| POST | `/api/workouts` | PlanWorkout |
| GET | `/api/workouts` | SearchWorkouts |
| GET | `/api/workouts/{id}` | GetWorkoutById |
| PUT | `/api/workouts/{id}/name` | RenameWorkout |
| PUT | `/api/workouts/{id}/description` | ChangeWorkoutDescription |
| DELETE | `/api/workouts/{id}` | DeleteWorkout |
| POST | `/api/workouts/{id}/days` | AddDayToWorkout |
| DELETE | `/api/workouts/{id}/days/{dayId}` | RemoveDayFromWorkout |
| PUT | `/api/workouts/{id}/days/reorder` | ReorderDays |
| POST | `/api/workouts/{id}/duplicate` | DuplicateWorkout |
| POST | `/api/workouts/{id}/archive` | ArchiveWorkout |

---

### 7. PATRONES UTILIZADOS

1. **Aggregate Root**: Workout (mantiene consistencia)
2. **Value Objects**: Tipos inmutables con validación
3. **Factory Methods**: `Workout.create()`, `WorkoutDay.create()`
4. **Copy Pattern**: Métodos que retornan nuevas instancias
5. **CQRS**: Separación Commands/Queries
6. **Criteria Pattern**: Búsquedas dinámicas
7. **Errors Enum**: Errores centralizados

---

### 8. ESTADO ACTUAL

✅ Proyecto completo con:
- Dominio funcional
- CQRS implementado
- Repository con JPA
- REST API completa
- Tests con coverage 77%

⏳ Pendiente:
- Domain Events (publicación de eventos)

---

## Getting Started

```bash
./gradlew bootRun
```

### Run tests
```bash
./gradlew test
```

### Generate coverage report
```bash
./gradlew jacocoTestReport
```

---

## Reference Documentation

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Gradle](https://gradle.org)
- [JaCoCo](https://www.jacoco.org)