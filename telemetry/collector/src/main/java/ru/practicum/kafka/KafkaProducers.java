package ru.practicum.kafka;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Component
public class KafkaProducers {
    private final KafkaProducerWrapper<String, SensorEventAvro> sensorProducer;
    private final KafkaProducerWrapper<String, HubEventAvro> hubProducer;

    public KafkaProducers(
            KafkaProducerWrapper<String, SensorEventAvro> sensorProducer,
            KafkaProducerWrapper<String, HubEventAvro> hubProducer) {
        this.sensorProducer = sensorProducer;
        this.hubProducer = hubProducer;
    }

    public KafkaProducerWrapper<String, SensorEventAvro> sensor() {
        return sensorProducer;
    }

    public KafkaProducerWrapper<String, HubEventAvro> hub() {
        return hubProducer;
    }
}