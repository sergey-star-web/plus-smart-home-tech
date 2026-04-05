package service;

import model.hub.*;
import model.sensor.*;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.generic.GenericDatumWriter;
import org.springframework.stereotype.Service;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class CollectorService {
    private final KafkaProducer<String, byte[]> kafkaProducer;
    private final Schema avroSchema; // Ваша схема Avro

    public CollectorService(KafkaProducer<String, byte[]> kafkaProducer, Schema avroSchema) {
        this.kafkaProducer = kafkaProducer;
        this.avroSchema = avroSchema;
    }

    public void processSensorEvent(SensorEvent event) {
        Map<String, Object> fields = prepareSensorEventFields(event);
        byte[] avroBytes = serializeToAvro(avroSchema, fields);
        kafkaProducer.send(new ProducerRecord<>("telemetry.sensors.v1", avroBytes));
    }

    public void processHubEvent(HubEvent event) {
        Map<String, Object> fields = prepareHubEventFields(event);
        byte[] avroBytes = serializeToAvro(avroSchema, fields);
        kafkaProducer.send(new ProducerRecord<>("telemetry.hubs.v1", avroBytes));
    }

    // Универсальный метод сериализации
    private static byte[] serializeToAvro(Schema schema, Map<String, Object> fields) {
        try {
            GenericRecord record = new GenericData.Record(schema);
            for (Map.Entry<String, Object> entry : fields.entrySet()) {
                record.put(entry.getKey(), entry.getValue());
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(schema);
            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            writer.write(record, encoder);
            encoder.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сериализации в Avro", e);
        }
    }

    // Подготовка полей для SensorEvent
    private Map<String, Object> prepareSensorEventFields(SensorEvent event) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("id", event.getId());
        fields.put("hubId", event.getHubId());
        fields.put("timestamp", event.getTimestamp().toString());
        fields.put("type", event.getType().name());

        switch (event) {
            case LightSensorEvent e -> {
                fields.put("linkQuality", e.getLinkQuality());
                fields.put("luminosity", e.getLuminosity());
            }
            case ClimateSensorEvent e -> {
                fields.put("temperatureC", e.getTemperatureC());
                fields.put("humidity", e.getHumidity());
                fields.put("co2Level", e.getCo2Level());
            }
            case MotionSensorEvent e -> {
                fields.put("linkQuality", e.getLinkQuality());
                fields.put("motion", e.isMotion());
                fields.put("voltage", e.getVoltage());
            }
            case SwitchSensorEvent e -> {
                fields.put("state", e.isState());
                fields.put("switchType", e.getType()); // Название поля — по смыслу
            }
            case TemperatureSensorEvent e -> {
                fields.put("temperatureC", e.getTemperatureC());
                fields.put("temperatureF", e.getTemperatureF());
            }
            default -> throw new RuntimeException("Неизвестный тип SensorEvent");
        }

        return fields;
    }

    // Подготовка полей для HubEvent
    private Map<String, Object> prepareHubEventFields(HubEvent event) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("id", event.getId());
        fields.put("hubId", event.getHubId());
        fields.put("timestamp", event.getTimestamp().toString());
        fields.put("type", event.getType().name());
        fields.put("eventType", event.getType().name()); // Можно добавить явно

        switch (event) {
            case ScenarioAddedEvent sae -> {
                fields.put("name", sae.getName());

                List<Map<String, Object>> conditions = new ArrayList<>();
                for (ScenarioCondition cond : sae.getConditions()) {
                    Map<String, Object> condMap = new HashMap<>();
                    condMap.put("conditionType", cond.getConditionType());
                    condMap.put("value", cond.getValue());
                    conditions.add(condMap);
                }
                fields.put("conditions", conditions);
            }
            case DeviceAddedEvent dae -> fields.put("deviceType", dae.getDeviceType());
            case ScenarioRemovedEvent sre -> fields.put("name", sre.getName());
            default -> {
            }
        }

        return fields;
    }
}