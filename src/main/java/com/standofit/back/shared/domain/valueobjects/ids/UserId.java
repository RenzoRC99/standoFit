package com.standofit.back.shared.domain.valueobjects.ids;

import com.standofit.back.shared.domain.valueobjects.Id;
import java.util.UUID;

public class UserId extends Id {
  public UserId(UUID value) {
    super(value);
  }
}