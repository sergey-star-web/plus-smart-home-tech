package ru.practicum.model.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.practicum.enums.SensorEventType;
import lombok.*;

@Getter @Setter @ToString
public class TemperatureSensorEvent extends SensorEvent {
    @JsonProperty("temperature_c")
    private int temperatureC;
    @JsonProperty("temperature_f")
    private int temperatureF;

    @Override
    public SensorEventType getType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }
}
