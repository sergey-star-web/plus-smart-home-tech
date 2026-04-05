package model.sensor;

import enums.SensorEventType;
import lombok.*;

@Getter @Setter @ToString(callSuper = true)
public class SwitchSensorEvent extends SensorEvent{
    private boolean state;
    private String type;

    @Override
    public SensorEventType getType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }
}