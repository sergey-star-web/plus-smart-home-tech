package ru.practicum.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaProducers producers;
    private final KafkaProducerConfig kafkaProducerConfig;

    public void sendSensorEvent(SensorEventAvro event) {
        String key = event.getHubId();
        String topic = kafkaProducerConfig.getSensorTopic();
        ProducerRecord<String, SensorEventAvro> record =
                new ProducerRecord<>(topic, key, event);
        producers.sensor().send(record);
    }

    public void sendHubEvent(HubEventAvro event) {
        String key = event.getHubId();
        String topic = kafkaProducerConfig.getHubTopic();
        ProducerRecord<String, HubEventAvro> record =
                new ProducerRecord<>(topic, key, event);
        producers.hub().send(record);
    }
}