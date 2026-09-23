package com.greenwall.backend.controller;

import com.greenwall.backend.entity.SensorData;
import com.greenwall.backend.service.SensorService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sensors")
@CrossOrigin(origins = "*")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(
            SensorService sensorService) {

        this.sensorService = sensorService;
    }

    @GetMapping("/{deviceId}")
    public List<SensorData> getSensorData(
            @PathVariable String deviceId) {

        return sensorService
                .getLatestData(deviceId);
    }

    @GetMapping("/{deviceId}/history")
    public List<SensorData> getHistoricalData(

            @PathVariable String deviceId,

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime end) {

        return sensorService.getHistoricalData(
                deviceId,
                start,
                end
        );
    }
}