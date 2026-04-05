package ru.practicum.model.hub;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.ActionType;

@Getter
@Setter
public class DeviceAction {
    private String sensorId;
    private ActionType type;
    private Integer value;
}
