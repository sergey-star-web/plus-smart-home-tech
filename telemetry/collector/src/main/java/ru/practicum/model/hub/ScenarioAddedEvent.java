package ru.practicum.model.hub;

import ru.practicum.enums.DeviceEventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString(callSuper = true)
public class ScenarioAddedEvent extends HubEvent {
    private String id;
    private String name;
    private List<ScenarioCondition> conditions;

    @Override
    public DeviceEventType getType() {
        return DeviceEventType.SCENARIO_ADDED;
    }
}