package com.standofit.back.modules.training.execution.application.query;

import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
import java.util.Optional;
import java.util.UUID;

public interface SessionReadRepository {

  SessionListDto findAllAsDtos();

  Optional<SessionDto> findDtoById(UUID id);
}
