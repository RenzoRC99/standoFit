# StandoFit API

API REST para gestión de entrenamientos físicos. Planifica tus semanas, ejecuta los ejercicios en el gimnasio y lleva un registro de tu progreso.

---

## ¿Qué hace?

StandoFit te permite tres cosas:

1. **Planificar** — Creas rutinas de entrenamiento con días y ejercicios (series, repeticiones, descanso)
2. **Ejecutar** — Inicias una sesión desde un día planificado y registras series, repeticiones y peso en tiempo real
3. **Seguir** — Consultas el histórico de sesiones completadas y tu progreso

Está pensado para que una aplicación frontend (web o móvil) cargue tu plan de entrenamiento y te permita ir marcando ejercicios mientras entrenas, con actualizaciones rápidas y sin recargar páginas.

---

## Tecnologías utilizadas

| Tecnología | Para qué se usa |
|---|---|
| **Java 21** | Lenguaje principal del servidor. Elegido por su madurez, rendimiento y ecosistema empresarial. |
| **Spring Boot 3.4** | Framework que facilita crear APIs REST. Es el estándar de la industria para Java en backend. |
| **Gradle** | Herramienta que compila el código, gestiona librerías externas y ejecuta tests automáticamente. |
| **PostgreSQL** | Base de datos principal en producción. Almacena workouts, sesiones, ejercicios, etc. |
| **H2** | Base de datos en memoria para desarrollo local y tests. No requiere instalación. |
| **OpenAPI** | Especificación técnica de los endpoints. Permite que el frontend genere automáticamente su código para conectarse a la API. |
| **JaCoCo** | Mide qué porcentaje del código está cubierto por tests automáticos. |
| **Spotless** | Mantiene el formato del código uniforme (espacios, imports, etc.) sin esfuerzo manual. |

---

## Partes del proyecto

### Planning (planificación de rutinas)
Crea workouts con tantos días como quieras. Cada día contiene una lista de ejercicios con series (sets), repeticiones (reps), peso y tiempo de descanso. Puedes renombrar, reordenar días, duplicar rutinas completas, etc.

### Execution (ejecución de sesiones)
Cuando vas al gimnasio, inicias una sesión desde un día planificado. Durante el entrenamiento vas registrando cada ejercicio que haces: series completadas, repeticiones y peso utilizado. Puedes pausar, añadir notas, finalizar o cancelar la sesión.

### Exercises (catálogo de ejercicios)
Lista global de ejercicios con nombre, descripción y grupo muscular (pecho, espalda, hombros, brazos, piernas, abdominales). Sirve como fuente de datos para los otros módulos.

---

## Cómo ejecutarlo localmente

Necesitas **Java 21** instalado.

```bash
# Iniciar el servidor
./gradlew bootRun

# El servidor arranca en http://localhost:8080
# Probarlo:
curl http://localhost:8080/api/workouts
```

```bash
# Ejecutar tests
./gradlew test

# Generar reporte de cobertura
./gradlew jacocoTestReport
# El reporte HTML queda en build/reports/jacoco/test/html/
```

Por defecto usa H2 (base de datos en memoria). Para usar PostgreSQL, configura las variables de entorno correspondientes.

---

## Endpoints principales

### Workouts (rutinas)

| Método | Endpoint | Qué hace |
|---|---|---|
| GET | `/api/workouts` | Listar todas las rutinas |
| GET | `/api/workouts/{id}` | Ver detalle de una rutina |
| POST | `/api/workouts` | Crear rutina con días y ejercicios |
| PUT | `/api/workouts/{id}` | Actualizar nombre y/o descripción |
| DELETE | `/api/workouts/{id}` | Eliminar rutina |
| POST | `/api/workouts/{id}/duplicate` | Duplicar rutina con otro nombre |
| POST | `/api/workouts/{id}/days` | Añadir un día con ejercicios |
| PUT | `/api/workouts/{id}/days/{dayId}` | Editar nombre y/o ejercicios de un día |
| DELETE | `/api/workouts/{id}/days/{dayId}` | Eliminar un día |
| PUT | `/api/workouts/{id}/days/reorder` | Reordenar los días |

### Sesiones (entrenamiento)

| Método | Endpoint | Qué hace |
|---|---|---|
| POST | `/api/sessions` | Iniciar una sesión de entrenamiento |
| GET | `/api/sessions` | Historial de sesiones |
| GET | `/api/sessions/{id}` | Detalle de una sesión |
| POST | `/api/sessions/{id}/finish` | Marcar sesión como completada |
| POST | `/api/sessions/{id}/cancel` | Cancelar sesión |
| DELETE | `/api/sessions/{id}` | Eliminar sesión |
| PUT | `/api/sessions/{id}/notes` | Actualizar notas de la sesión |
| POST | `/api/sessions/{id}/logs` | Añadir un ejercicio realizado |
| DELETE | `/api/sessions/{id}/logs/{logId}` | Quitar un ejercicio de la sesión |
| PATCH | `/api/sessions/{id}/logs/{logId}/sets` | Actualizar series de un ejercicio |
| PATCH | `/api/sessions/{id}/logs/{logId}/reps` | Actualizar repeticiones |
| PATCH | `/api/sessions/{id}/logs/{logId}/weight` | Actualizar peso |

### Ejercicios (catálogo)

| Método | Endpoint | Qué hace |
|---|---|---|
| GET | `/api/exercises` | Listar todos los ejercicios |
| GET | `/api/exercises/{id}` | Ver detalle de un ejercicio |
| GET | `/api/exercises/muscle-group/{group}` | Filtrar por grupo muscular |
| GET | `/api/exercises/search?name=` | Buscar por nombre |
| POST | `/api/exercises` | Crear un nuevo ejercicio |
| PUT | `/api/exercises/{id}` | Actualizar un ejercicio |
| DELETE | `/api/exercises/{id}` | Eliminar un ejercicio |

---

## Arquitectura (para curiosos)

El código está organizado en capas para que sea mantenible a largo plazo:

- **Domain** — Contiene las reglas de negocio. No depende de frameworks ni bases de datos.
- **Application** — Orquesta las operaciones (commands y queries). Cada acción tiene un flujo claro y predecible.
- **Infrastructure** — Implementa la comunicación con la base de datos y otras tecnologías externas.
- **Presentation** — Expone los endpoints REST y traduce los datos entre JSON y el formato interno.

Esto permite cambiar la base de datos, el framework o añadir funcionalidades sin reescribir todo el código.
