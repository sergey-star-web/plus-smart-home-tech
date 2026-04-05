package model.hub;

import enums.DeviceEventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString(callSuper = true)
public class ScenarioRemovedEvent extends HubEvent {
    private String name;

    @Override
    public DeviceEventType getType() {
        return DeviceEventType.SCENARIO_REMOVED;
    }
}