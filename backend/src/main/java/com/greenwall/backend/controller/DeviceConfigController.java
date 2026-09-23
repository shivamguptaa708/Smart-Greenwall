package com.greenwall.backend.controller;

import com.greenwall.backend.dto.ThresholdDTO;
import com.greenwall.backend.entity.DeviceConfig;
import com.greenwall.backend.service.DeviceConfigService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@CrossOrigin(origins = "*")
public class DeviceConfigController {

    private final DeviceConfigService deviceConfigService;

    public DeviceConfigController(
            DeviceConfigService deviceConfigService) {

        this.deviceConfigService =
                deviceConfigService;
    }


    // ============================
    // UPDATE THRESHOLD
    // ============================

    @PutMapping("/{deviceId}/threshold")
    public ResponseEntity<?> updateThreshold(

            @PathVariable String deviceId,

            @RequestBody ThresholdDTO request) {

        try {

            DeviceConfig config =
                    deviceConfigService.updateThreshold(
                            deviceId,
                            request.getPm25Threshold()
                    );

            return ResponseEntity.ok(config);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body(
                        "Failed to update threshold: "
                        + e.getMessage()
                    );
        }
    }


    // ============================
    // GET THRESHOLD
    // ============================

    @GetMapping("/{deviceId}/config")
    public ResponseEntity<?> getConfig(
            @PathVariable String deviceId) {

        try {

            return ResponseEntity.ok(
                    deviceConfigService
                            .getConfig(deviceId)
            );

        } catch (Exception e) {

            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}