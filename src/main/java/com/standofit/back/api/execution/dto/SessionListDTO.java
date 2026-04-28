package com.standofit.back.api.execution.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class SessionListDTO {

    @JsonProperty("sessions")
    @Valid
    @NotNull
    private List<SessionDTO> sessions;

    public SessionListDTO sessions(List<SessionDTO> sessions) {
        this.sessions = sessions;
        return this;
    }

    public List<SessionDTO> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionDTO> sessions) {
        this.sessions = sessions;
    }
}