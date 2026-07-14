package com.standofit.back.modules.training.planning.infrastructure.query;

import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.application.query.WorkoutReadRepository;
import com.standofit.back.modules.training.planning.application.service.ExerciseEnrichmentService;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.PagedResult;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class JpaWorkoutReadRepository implements WorkoutReadRepository {

  private final WorkoutRepository workoutRepository;
  private final ExerciseEnrichmentService enrichmentService;
  private final WorkoutDtoMapper mapper;

  public JpaWorkoutReadRepository(
      WorkoutRepository workoutRepository,
      ExerciseEnrichmentService enrichmentService,
      WorkoutDtoMapper mapper) {
    this.workoutRepository = workoutRepository;
    this.enrichmentService = enrichmentService;
    this.mapper = mapper;
  }

  @Override
  public Optional<WorkoutDto> findById(WorkoutId id) {
    Optional<Workout> workout = workoutRepository.findById(id);
    if (workout.isEmpty()) {
      return Optional.empty();
    }
    var exerciseMaps = enrichmentService.loadExerciseData(workout.get());
    return Optional.of(
        mapper.toDto(workout.get(), exerciseMaps.names(), exerciseMaps.muscleGroups()));
  }

  @Override
  public PagedResult<WorkoutDto> search(Criteria criteria) {
    var workouts = workoutRepository.searchByCriteria(criteria);
    var exerciseMaps = enrichmentService.loadExerciseData(workouts);
    var enriched =
        workouts.stream()
            .map(w -> mapper.toDto(w, exerciseMaps.names(), exerciseMaps.muscleGroups()))
            .toList();
    long total = workoutRepository.countByCriteria(criteria);
    return PagedResult.of(
        enriched, total, criteria.pageInfo().page(), criteria.pageInfo().pageSize());
  }
}
