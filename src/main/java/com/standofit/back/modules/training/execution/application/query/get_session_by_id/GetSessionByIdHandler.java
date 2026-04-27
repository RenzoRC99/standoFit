package com.standofit.back.modules.training.execution.application.query.get_session_by_id;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.presentation.dto.SessionDto;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Service;

@Service
public class GetSessionByIdHandler implements QueryHandler<GetSessionByIdQuery, SessionDto> {

    private final SessionRepository repository;

    public GetSessionByIdHandler(SessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<GetSessionByIdQuery> queryType() {
        return GetSessionByIdQuery.class;
    }

    @Override
    public SessionDto handle(GetSessionByIdQuery query) {
        Session session = repository.findById(query.id());
        return session != null ? SessionDto.from(session) : null;
    }
}