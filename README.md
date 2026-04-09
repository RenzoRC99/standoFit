# standoFit

Backend API para gestión de planes de entrenamiento físico (fitness/gimnasio).

---

## RESUMEN EXHAUSTIVO DEL PROYECTO

### 1. TIPO DE PROYECTO

**Framework**: Spring Boot 4.0.4 (versión con soporte de largo plazo)  
**Lenguaje**: Java 21  
**Sistema de Build**: Gradle 9.4.0  
**Arquitectura**: Domain-Driven Design (DDD)  

---

### 2. ESTRUCTURA GENERAL DE DIRECTORIOS

```
standoFit/
├── src/
│   ├── main/
│   │   ├── java/com/standofit/back/
│   │   │   ├── training/planning/
│   │   │   │   └── domain/
│   │   │   │       ├── entity/          # Entidades del dominio
│   │   │   │       └── vo/              # Value Objects del dominio
│   │   │   └── shared/
│   │   │       └── domain/
│   │   │           ├── aggregate/       # Base AggregateRoot
│   │   │           ├── bus/             # Command Bus y Event Bus
│   │   │           │   ├── command/      # Command/CommandHandler/CommandBus
│   │   │           │   └── event/        # DomainEvent
│   │   │           └── valueobjects/
│   │   │               ├── ids/          # Identificadores (UUID)
│   │   │               ├── errors/       # Errores y excepciones
│   │   │               └── BaseVO, StringVO, IntegerVO, DateTimeVO, Id
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/standofit/back/
├── build.gradle                          # Configuración de dependencias
├── settings.gradle                       # Nombre del proyecto
├── gradlew / gradlew.bat                 # Wrapper de Gradle
├── .idea/                                # Configuración IntelliJ IDEA
├── .gradle/                              # Caché de Gradle
├── build/                               # Archivos compilados
└── bin/                                 # Archivos compilados (Eclipse)
```

---

### 3. ARCHIVOS PRINCIPALES DE CONFIGURACIÓN

| Archivo | Propósito |
|---------|-----------|
| `build.gradle` | Definición de plugins (Spring Boot, Java), dependencias y configuración del toolchain Java 21 |
| `settings.gradle` | Nombre del proyecto raíz (`standoFit`) |
| `application.properties` | Configuración mínima de Spring (solo nombre de aplicación) |
| `gradle/wrapper/gradle-wrapper.properties` | Define Gradle 9.4.0 |
| `.project`, `.classpath` | Configuración de proyecto Eclipse/Buildship |
| `.idea/workspace.xml` | Configuración específica de IntelliJ IDEA |

---

### 4. MÓDULOS/COMPONENTES PRINCIPALES

#### A. **Dominio de Training Planning** (`com.standofit.back.training.planning`)

##### Entidades:

1. **Workout** (Agregado Raíz)
   - Representa un plan de entrenamiento completo
   - Campos: id, name, description, days (lista de WorkoutDay), version, createdAt, updatedAt
   - Patrón Builder para construcción inmutable
   - Métodos: `create()`, `renameWorkout()`

2. **WorkoutDay** (Entidad)
   - Representa un día de entrenamiento dentro de un Workout
   - Campos: id, name, exercises (lista de WorkoutExercise)

3. **WorkoutExercise** (Entidad)
   - Representa un ejercicio individual con sus parámetros
   - Campos: sets, reps, restSeconds

4. **WorkoutBuilder** (Clase interna/Builder)
   - Implementa el patrón Builder para crear/inmutar Workout
   - Permite modificar campos individuales manteniendo inmutabilidad

##### Value Objects (VO):

| Value Object | Tipo Base | Validación |
|--------------|-----------|------------|
| `WorkoutName` | StringVO | No vacío/blank |
| `WorkoutDescription` | StringVO | Ninguna específica |
| `WorkoutDayName` | StringVO | No vacío/blank |
| `WorkoutExerciseSets` | IntegerVO | <= 100 |
| `WorkoutExerciseReps` | IntegerVO | <= 1000 |
| `WorkoutExerciseRest` | IntegerVO | <= 3600 (segundos) |
| `WorkoutVersion` | IntegerVO | Ninguna |
| `WorkoutCreatedAt` | DateTimeVO | Instante de creación |
| `WorkoutUpdatedAt` | DateTimeVO | Instante de última actualización |

#### B. **Capa Compartida** (`com.standofit.back.shared`)

##### Value Objects Base:

1. **BaseVO<T>** - Clase base abstracta para todos los VO
   - Almacena el valor inmutable
   - Proporciona validación `validateNotNull()`

2. **StringVO** - Extiende BaseVO<String>
   - Validaciones: longitud (min-max), no vacío

3. **IntegerVO** - Extiende BaseVO<Integer>
   - Validaciones: no mayor que un valor máximo

4. **DateTimeVO** - Extiende BaseVO<Instant>
   - Manejo de timestamps

5. **Id** - Extiende BaseVO<UUID>
   - Para identificadores únicos

##### IDs Específicos:
- `WorkoutId`
- `WorkoutDayId`
- `ExerciseId`

##### Sistema de Errores:
- **EnumContract** - Interface para enumeraciones de errores
- **ValueobjectErrors** - Enum con mensajes predefinidos:
  - NULL_VALUE
  - INVALID_LENGTH_RANGE
  - NOT_EMPTY
  - INT_BIGGER_THAN

##### Excepciones:
- **BaseException** - Clase base abstracta para excepciones con contexto
- **ValueObjectException** - Excepción para errores en Value Objects

##### Bus de Comandos (CQRS Pattern):
- **Command** - Interfaz marcadora vacía
- **CommandHandler** - Interfaz marcadora vacía
- **CommandBus** - Interfaz con método `dispatch(Command)`

##### Bus de Eventos:
- **DomainEvent** - Clase base abstracta para eventos de dominio
  - Genera UUID y timestamp automáticamente
  - Método abstracto `StringName()`

##### Aggregate Root:
- **AggregateRoot** - Clase base vacía para entidades raíz

---

### 5. DEPENDENCIAS EXTERNAS

| Dependencia | Versión | Propósito |
|-------------|---------|-----------|
| `spring-boot-starter` | 4.0.4 | Núcleo de Spring Boot |
| `spring-boot-devtools` | 4.0.4 | Herramientas de desarrollo (hot reload) |
| `spring-boot-starter-test` | 4.0.4 | Testing con JUnit, Mockito |
| `junit-platform-launcher` | (managed) | Lanzador de tests JUnit |

---

### 6. CONVENCIONES Y PATRONES IDENTIFICADOS

1. **Inmutabilidad**: Los Value Objects y Entidades son inmutables
2. **Patrón Builder**: Utilizado para crear entidades (WorkoutBuilder)
3. **Herencia jerárquica**: BaseVO -> StringVO/IntegerVO/DateTimeVO/Id
4. **Interfaces marcadoras**: Command, CommandHandler, CommandBus, DomainEvent, AggregateRoot
5. **Validación en constructores**: Los VO validan en su construcción
6. **Naming conventions**: 
   - Paquetes en minúsculas: `domain`, `entity`, `vo`, `valueobjects`
   - Clases con PascalCase
   - Value Objects con sufijo descriptivo (Name, Description, Sets, etc.)

---

### 7. PROPÓSITO DEL PROYECTO

**StandoFit** es una **API backend para gestión de planes de entrenamiento físico (fitness/gimnasio)**.

El dominio modela:
- **Workout** (Plan de entrenamiento): Nombre, descripción, días de entrenamiento
- **WorkoutDay** (Día de entrenamiento): Nombre del día, lista de ejercicios
- **WorkoutExercise** (Ejercicio): Series, repeticiones, tiempo de descanso

La arquitectura DDD está preparada para escalar hacia una aplicación más compleja con:
- Persistencia (repositorios no implementados aún)
- Casos de uso (Commands/CommandHandlers no implementados)
- APIs REST (no hay controllers)
- Eventos de dominio para comunicar cambios

**Estado actual**: Proyecto en fase inicial de modelado de dominio (estilo "clean domain" o "hexagonal"), sin lógica de aplicación, persistencia ni endpoints HTTP implementados. Es un esqueleto DDD funcional que demuestra las convenciones y patrones elegidos para el desarrollo.

---

### 8. RUTAS DE ARCHIVOS PRINCIPALES

```
/home/rromero/Documents/openCodePruebas/standoFit/build.gradle
/home/rromero/Documents/openCodePruebas/standoFit/src/main/java/com/standofit/back/StandoFitApplication.java
/home/rromero/Documents/openCodePruebas/standoFit/src/main/java/com/standofit/back/training/planning/domain/entity/Workout.java
/home/rromero/Documents/openCodePruebas/standoFit/src/main/java/com/standofit/back/training/planning/domain/entity/WorkoutDay.java
/home/rromero/Documents/openCodePruebas/standoFit/src/main/java/com/standofit/back/training/planning/domain/entity/WorkoutExercise.java
/home/rromero/Documents/openCodePruebas/standoFit/src/main/java/com/standofit/back/shared/domain/aggregate/AggregateRoot.java
/home/rromero/Documents/openCodePruebas/standoFit/src/main/java/com/standofit/back/shared/domain/valueobjects/BaseVO.java
```

---

## Getting Started

### Reference Documentation

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/4.0.4/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.0.4/gradle-plugin/packaging-oci-image.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/4.0.4/reference/using/devtools.html)

### Additional Links

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
