package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.kafka.KafkaProducerService;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mapper.HubEventMapper;
import ru.practicum.mapper.SensorEventMapper;
import ru.practicum.model.hub.HubEvent;
import ru.practicum.model.sensor.SensorEvent;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class CollectorController {
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/sensors")
    public void collectSensorEvent(@RequestBody SensorEvent event) {
        System.out.println("Received sensor event: " + event);
        try {
            log.debug("Starting sensor event mapping to Avro...");
            var avroEvent = SensorEventMapper.toAvro(event);
            log.debug("Sensor event mapped successfully to Avro");
            log.info("Sending sensor event to Kafka...");
            kafkaProducerService.sendSensorEvent(avroEvent);
        } catch (Exception e) {
            log.error("Error processing sensor event: {}", event.toString(), e);
            throw e;
        }
    }

    @PostMapping("/hubs")
    public void collectHubEvent(@RequestBody HubEvent hubEvent) {
        System.out.println("Received hub event: " + hubEvent);
        try {
            log.debug("Starting hub event mapping to Avro...");
            var avroEvent = HubEventMapper.toAvro(hubEvent);
            log.debug("Hub event mapped successfully to Avro");
            log.info("Sending hub event to Kafka...");
            kafkaProducerService.sendHubEvent(avroEvent);
        } catch (Exception e) {
            log.error("Error processing hub event: {}", hubEvent.toString(), e);
            throw e;
        }
    }
}