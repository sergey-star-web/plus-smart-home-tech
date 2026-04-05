package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> collectSensorEvent(@RequestBody SensorEvent event) {
        System.out.println("Received sensor event: " + event);
        try {
            var avroEvent = SensorEventMapper.toAvro(event);
            kafkaProducerService.sendSensorEvent(avroEvent);
        } catch (Exception e) {
            log.error("Error processing sensor event: {}", event.toString(), e);
            throw e;
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/hubs")
    public ResponseEntity<?> collectHubEvent(@RequestBody HubEvent hubEvent) {
        System.out.println("Received hub event: " + hubEvent);
        try {
            var avroEvent = HubEventMapper.toAvro(hubEvent);
            kafkaProducerService.sendHubEvent(avroEvent);
        } catch (Exception e) {
            log.error("Error processing hub event: {}", hubEvent.toString(), e);
            throw e;
        }
        return ResponseEntity.ok().build();
    }
}