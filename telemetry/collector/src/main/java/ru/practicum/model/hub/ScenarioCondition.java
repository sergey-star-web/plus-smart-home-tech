package ru.practicum.model.hub;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.ConditionOperation;
import ru.practicum.enums.ConditionType;

@Getter @Setter
public class ScenarioCondition {
    private String sensorId;
    private ConditionType type;
    private ConditionOperation operation;

    // может быть int или boolean
    private Object value;
}
