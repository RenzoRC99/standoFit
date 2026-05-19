# StandoFit API

API REST para gestión de entrenamientos físicos. Planifica tus semanas, ejecuta los ejercicios en el gimnasio y lleva un registro de tu progreso.

---

## El proyecto

StandoFit permite:

1. **Planificar** — Creas workouts con días y ejercicios (sets, reps, descanso)
2. **Ejecutar** — Inicias una sesión desde un día planificado y registras series, repes y peso en tiempo real
3. **Seguir** — Consultas el histórico de sesiones completadas

Está diseñado para un frontend que cargue el plan de entrenamiento y permita ir marcando ejercicios mientras entrena, con actualizaciones rápidas y sin páginas recargando.

---

## Decisiones técnicas

### DDD + Hexagonal + CQRS

Separamos la lógica en capas para que el código sea mantenible a largo plazo. El frontend no nota nada de esto — solo recibe JSON limpio — pero internamente cada operación tiene un flujo claro:

```
Request → Controller → Command/Query → Domain → Response
```

### Endpoints por intención, no por recurso

No hay un endpoint `PUT /workouts/{id}` monolítico que acepte todo. En su lugar, tienes endpoints que reflejan **lo que el usuario quiere hacer**:

- Renombrar un workout → `PUT /workouts/{id}/name`
- Cambiar descripción → `PUT /workouts/{id}/description`
- Editar un día completo → `PUT /workouts/{id}/days/{dayId}`

Esto permite al frontend enviar solo lo que cambió, no el objeto entero.

### Java + Spring Boot

Porque es el stack del equipo: maduro, con soporte empresarial y excelente para APIs REST.

### H2 en memoria

Base de datos embebida para desarrollo. En producción se conecta a PostgreSQL.

### OpenAPI

La especificación de los endpoints está en `docs/openapi-planning.yaml` y `docs/openapi-execution.yaml`. El frontend puede generar su cliente HTTP directamente desde ahí.

---

## Cómo empezar

```bash
# Requisitos: Java 21

./gradlew bootRun     # Inicia el servidor en localhost:8080
curl localhost:8080/api/workouts  # Probarlo
```

---

## Endpoints

### Workouts (planes de entrenamiento)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/workouts` | Listar todos |
| `GET` | `/api/workouts/{id}` | Ver detalle |
| `POST` | `/api/workouts/search` | Búsqueda con filtros y paginación |
| `POST` | `/api/workouts` | Crear workout con días y ejercicios |
| `PUT` | `/api/workouts/{id}` | Actualizar nombre y/o descripción |
| `DELETE` | `/api/workouts/{id}` | Eliminar |
| `POST` | `/api/workouts/{id}/duplicate` | Duplicar con otro nombre |

**Días:**

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/workouts/{id}/days` | Añadir día con ejercicios |
| `PUT` | `/api/workouts/{id}/days/{dayId}` | Editar nombre y/o ejercicios del día |
| `DELETE` | `/api/workouts/{id}/days/{dayId}` | Eliminar día |
| `PUT` | `/api/workouts/{id}/days/reorder` | Reordenar días |

### Sesiones (entrenamiento en vivo)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/sessions` | Iniciar sesión |
| `GET` | `/api/sessions` | Historial de sesiones |
| `GET` | `/api/sessions/{id}` | Detalle de sesión |
| `DELETE` | `/api/sessions/{id}` | Eliminar sesión |
| `POST` | `/api/sessions/{id}/finish` | Marcar completada |
| `POST` | `/api/sessions/{id}/cancel` | Cancelar |
| `PUT` | `/api/sessions/{id}/notes` | Actualizar notas |

**Ejercicios en sesión:**

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/sessions/{id}/logs` | Añadir ejercicio ejecutado |
| `DELETE` | `/api/sessions/{id}/logs/{logId}` | Quitar ejercicio |
| `PATCH` | `/api/sessions/{id}/logs/{logId}/sets` | Actualizar series |
| `PATCH` | `/api/sessions/{id}/logs/{logId}/reps` | Actualizar repeticiones |
| `PATCH` | `/api/sessions/{id}/logs/{logId}/weight` | Actualizar peso |

### Ejercicios (catálogo)

Catálogo global de ejercicios con grupo muscular.

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/exercises` | Listar todos |
| `GET` | `/api/exercises/{id}` | Por ID |
| `GET` | `/api/exercises/muscle-group/{group}` | Filtrar por grupo muscular |
| `GET` | `/api/exercises/search?name=` | Buscar por nombre |
| `POST` | `/api/exercises` | Crear |
| `PUT` | `/api/exercises/{id}` | Actualizar |
| `DELETE` | `/api/exercises/{id}` | Eliminar |

---

## Notas

- Los campos opcionales se envían como `null` para omitirlos (no se actualizan)
- Los campos que no se envían no se tocan
- Todos los IDs son UUID v4
- Errores: `{ "code": "ERROR_CODE", "message": "Descripción", "timestamp": "..." }`
