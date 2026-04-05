package model.hub;

import enums.DeviceEventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString(callSuper = true)
public class DeviceAddedEvent extends HubEvent {
    private String id;
    private String deviceType;

    @Override
    public DeviceEventType getType() {
        return DeviceEventType.DEVICE_ADDED;
    }
}