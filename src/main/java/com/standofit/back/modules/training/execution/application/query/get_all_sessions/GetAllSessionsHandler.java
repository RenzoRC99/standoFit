package com.standofit.back.modules.training.execution.application.query.get_all_sessions;

import com.standofit.back.api.execution.dto.SessionListDTO;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.mapper.SessionDTOMapper;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllSessionsHandler implements QueryHandler<GetAllSessionsQuery, SessionListDTO> {

    private final SessionRepository repository;
    private final SessionDTOMapper mapper;

    public GetAllSessionsHandler(SessionRepository repository, SessionDTOMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Class<GetAllSessionsQuery> queryType() {
        return GetAllSessionsQuery.class;
    }

    @Override
    public SessionListDTO handle(GetAllSessionsQuery query) {
        List<Session> sessions = repository.getAll();
        return mapper.toListDTO(sessions);
    }
}