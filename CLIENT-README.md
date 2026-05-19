# StandoFit API

API REST para gestión de entrenamientos físicos. Planifica workouts, ejecuta sesiones y registra tu progreso.

---

## Cómo empezar

```bash
# Requisitos: Java 21

./gradlew bootRun     # Inicia el servidor en localhost:8080
```

---

## Endpoints principales

### Workouts (planes de entrenamiento)

| Método | Endpoint | Ejemplo cuerpo |
|--------|----------|----------------|
| `POST` | `/api/workouts` | `{ "name": "Push Pull Legs", "days": [...] }` |
| `GET` | `/api/workouts` | |
| `GET` | `/api/workouts/{id}` | |
| `PUT` | `/api/workouts/{id}` | `{ "name": "Nuevo nombre", "description": "Nueva desc" }` |
| `DELETE` | `/api/workouts/{id}` | |
| `POST` | `/api/workouts/{id}/duplicate` | `{ "newName": "Copia de..." }` |

**Días:**

| Método | Endpoint | Ejemplo cuerpo |
|--------|----------|----------------|
| `POST` | `/api/workouts/{id}/days` | `{ "dayName": "Push Day", "exercises": [...] }` |
| `PUT` | `/api/workouts/{id}/days/{dayId}` | `{ "name": "Nuevo nombre", "exercises": [...] }` |
| `DELETE` | `/api/workouts/{id}/days/{dayId}` | |
| `PUT` | `/api/workouts/{id}/days/reorder` | `{ "dayIds": ["uuid1", "uuid2"] }` |

### Sesiones (entrenamiento en vivo)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/sessions` | Iniciar sesión desde un día planificado |
| `GET` | `/api/sessions` | Listar sesiones |
| `GET` | `/api/sessions/{id}` | Ver detalle |
| `DELETE` | `/api/sessions/{id}` | Eliminar |
| `POST` | `/api/sessions/{id}/finish` | Marcar como completada |
| `POST` | `/api/sessions/{id}/cancel` | Cancelar |
| `PUT` | `/api/sessions/{id}/notes` | Actualizar notas |

**Ejercicios en sesión:**

| Método | Endpoint |
|--------|----------|
| `POST` | `/api/sessions/{id}/logs` |
| `DELETE` | `/api/sessions/{id}/logs/{logId}` |
| `PATCH` | `/api/sessions/{id}/logs/{logId}/sets` |
| `PATCH` | `/api/sessions/{id}/logs/{logId}/reps` |
| `PATCH` | `/api/sessions/{id}/logs/{logId}/weight` |

### Ejercicios (catálogo)

| Método | Endpoint |
|--------|----------|
| `GET` | `/api/exercises` |
| `GET` | `/api/exercises/{id}` |
| `GET` | `/api/exercises/muscle-group/{group}` |
| `GET` | `/api/exercises/search?name=` |
| `POST` | `/api/exercises` |
| `PUT` | `/api/exercises/{id}` |
| `DELETE` | `/api/exercises/{id}` |

---

## Notas

- Los campos opcionales se envían como `null` para omitirlos
- Todos los IDs son UUID v4
- Las respuestas de error incluyen `code`, `message` y `timestamp`
- La API está documentada en OpenAPI (`docs/openapi-planning.yaml`, `docs/openapi-execution.yaml`)
