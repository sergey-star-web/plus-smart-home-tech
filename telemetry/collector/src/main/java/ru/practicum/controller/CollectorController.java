package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.kafka.KafkaProducerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.kafka.telemetry.enums.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.enums.SensorEventAvro;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class CollectorController {
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/sensors")
    public ResponseEntity<?> collectSensorEvent(@RequestBody SensorEventAvro event) {
        System.out.println("Received sensor event: " + event);
        kafkaProducerService.sendSensorEvent(event);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/hubs")
    public ResponseEntity<?> collectHubEvent(@RequestBody HubEventAvro hubEvent) {
        System.out.println("Received hub event: " + hubEvent);
        kafkaProducerService.sendHubEvent(hubEvent);
        return ResponseEntity.ok().build();
    }
}