package com.standofit.back.modules.training.execution.domain;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.event.*;
import com.standofit.back.shared.domain.bus.event.DomainEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

import java.util.List;

public final class SessionReconstructor {

    private SessionReconstructor() {
    }

    public static Session replay(List<DomainEvent> events) {
        if (events.isEmpty()) {
            throw new SessionDomainException(SessionDomainErrors.REPLAY_EMPTY_EVENTS.getMessage());
        }

        DomainEvent first = events.get(0);
        if (!(first instanceof SessionStarted s)) {
            throw new SessionDomainException(
                    SessionDomainErrors.REPLAY_REQUIRES_SESSION_STARTED.getMessage());
        }
    
        Session session = Session.create(new SessionId(s.aggregateId().value()), s.dayId());

        for (int i = 1; i < events.size(); i++) {
            DomainEvent event = events.get(i);
            session =
                    switch (event) {
                        case SessionFinished ignored -> session.finish();
                        case SessionCancelled ignored -> session.cancel();
                        case SessionNotesChanged n -> session.changeNotes(n.notes());
                        case ExerciseLogAdded a -> session.addLog(a.log());
                        case ExerciseLogRemoved r -> session.removeLog(r.logId());
                        case ExerciseLogSetsUpdated u -> session.updateLogSets(u.logId(), u.sets());
                        case ExerciseLogRepsUpdated u -> session.updateLogReps(u.logId(), u.reps());
                        case ExerciseLogWeightUpdated u -> session.updateLogWeight(u.logId(), u.weight());
                        default -> throw new SessionDomainException(
                                String.format(
                                        SessionDomainErrors.REPLAY_UNHANDLED_EVENT.getMessage(),
                                        event.getClass().getSimpleName()));
                    };
        }

        session.pullDomainEvents();
        return session;
    }
}
