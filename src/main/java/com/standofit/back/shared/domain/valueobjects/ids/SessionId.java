package com.standofit.back.shared.domain.valueobjects.ids;

import com.standofit.back.shared.domain.valueobjects.Id;
import java.util.UUID;

public class SessionId extends Id {
  public SessionId(UUID value) {
    super(value);
  }
}
