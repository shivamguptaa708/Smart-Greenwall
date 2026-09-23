package com.greenwall.backend.controller;

import com.greenwall.backend.dto.ManualCommandDTO;
import com.greenwall.backend.service.MqttPublisherService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@CrossOrigin(origins = "*")
public class DeviceController {

    private final MqttPublisherService mqttPublisherService;

    public DeviceController(
            MqttPublisherService mqttPublisherService) {

        this.mqttPublisherService =
                mqttPublisherService;
    }

    @PostMapping("/command")
    public ResponseEntity<String> sendCommand(
            @RequestBody ManualCommandDTO command) {

        try {

            String cmd =
                    command.getCommand()
                           .toUpperCase();

            if (!cmd.equals("ON")
                    && !cmd.equals("OFF")) {

                return ResponseEntity
                        .badRequest()
                        .body(
                            "Command must be ON or OFF"
                        );
            }

            mqttPublisherService.sendCommand(
                    command.getDeviceId(),
                    cmd
            );

            return ResponseEntity.ok(
                    "Command sent successfully"
            );

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body(
                        "Failed to send command: "
                        + e.getMessage()
                    );
        }
    }
}