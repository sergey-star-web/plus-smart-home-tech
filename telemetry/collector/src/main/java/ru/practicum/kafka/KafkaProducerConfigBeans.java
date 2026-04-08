package ru.practicum.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import serialization.CustomHubEventSerializer;
import serialization.CustomSensorEventSerializer;
import java.util.Properties;

@Configuration
public class KafkaProducerConfigBeans {
    @Bean
    public KafkaProducer<String, SensorEventAvro> sensorKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomSensorEventSerializer.class);
        return new KafkaProducer<>(props);
    }

    @Bean
    public KafkaProducerWrapper<String, SensorEventAvro> sensorProducerWrapper(KafkaProducer<String,
            SensorEventAvro> producer) {
        return new KafkaProducerWrapper<>(producer);
    }

    @Bean
    public KafkaProducer<String, HubEventAvro> hubKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomHubEventSerializer.class);
        return new KafkaProducer<>(props);
    }

    @Bean
    public KafkaProducerWrapper<String, HubEventAvro> hubProducerWrapper(KafkaProducer<String, HubEventAvro> producer) {
        return new KafkaProducerWrapper<>(producer);
    }
}