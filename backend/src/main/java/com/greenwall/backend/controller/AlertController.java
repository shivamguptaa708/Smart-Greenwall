package com.greenwall.backend.controller;

import com.greenwall.backend.entity.Alert;
import com.greenwall.backend.service.AlertService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = "*")
public class AlertController {

    private final AlertService alertService;

    public AlertController(
            AlertService alertService) {

        this.alertService = alertService;
    }

    @GetMapping("/{deviceId}")
    public List<Alert> getAlerts(
            @PathVariable String deviceId) {

        return alertService.getAlerts(deviceId);
    }
}