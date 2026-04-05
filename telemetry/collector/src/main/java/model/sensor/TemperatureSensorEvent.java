package model.sensor;

import enums.SensorEventType;
import lombok.*;

@Getter @Setter @ToString(callSuper = true)
public class TemperatureSensorEvent extends SensorEvent {
    private int temperatureC;
    private int temperatureF;

    @Override
    public SensorEventType getType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }
}
