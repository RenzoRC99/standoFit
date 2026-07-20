package com.standofit.back.modules.training.execution.infrastructure.catalog;

import com.standofit.back.modules.exercises.repository.ExerciseRepository;
import com.standofit.back.modules.training.execution.domain.catalog.ExerciseResolver;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ExerciseResolverImpl implements ExerciseResolver {

  private final ExerciseRepository exerciseRepository;

  public ExerciseResolverImpl(ExerciseRepository exerciseRepository) {
    this.exerciseRepository = exerciseRepository;
  }

  @Override
  public Map<UUID, ExerciseInfo> resolve(Set<UUID> ids) {
    if (ids.isEmpty()) {
      return Map.of();
    }

    Set<String> stringIds = ids.stream().map(UUID::toString).collect(Collectors.toSet());

    return exerciseRepository.findAllById(stringIds).stream()
        .collect(
            Collectors.toMap(
                ex -> UUID.fromString(ex.getId()),
                ex -> new ExerciseInfo(ex.getName(), ex.getMuscleGroup().name())));
  }
}
