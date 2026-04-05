package controller;

import jakarta.validation.Valid;
import model.hub.HubEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import model.sensor.SensorEvent;
import org.springframework.http.ResponseEntity;
import service.CollectorService;

@RestController
@RequestMapping("/events")
public class CollectorController {
    private final CollectorService collectorService;

    @Autowired
    public CollectorController(CollectorService collectorService) {
        this.collectorService = collectorService;
    }

    @PostMapping("/sensors")
    public ResponseEntity<?> collectSensorEvent(@Valid @RequestBody SensorEvent event) {
        System.out.println("Received sensor event: " + event);
        collectorService.processSensorEvent(event);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/hubs")
    public ResponseEntity<?> collectHubEvent(@Valid @RequestBody HubEvent hubEvent) {
        System.out.println("Received hub event: " + hubEvent);
        collectorService.processHubEvent(hubEvent);
        return ResponseEntity.ok().build();
    }
}