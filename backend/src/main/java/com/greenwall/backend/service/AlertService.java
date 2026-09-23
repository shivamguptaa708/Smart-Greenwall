package com.greenwall.backend.service;

import com.greenwall.backend.entity.Alert;
import com.greenwall.backend.entity.SensorData;
import com.greenwall.backend.repository.AlertRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    private static final double HIGH_PM25 = 60.0;

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public void checkPollutionAlert(SensorData data) {

        if (data.getPm25() == null) {
            return;
        }

        if (data.getPm25() >= HIGH_PM25) {

            createHighPmAlert(data);

        } else {

            closeHighPmAlert(data.getDeviceId());
        }
    }

    private void createHighPmAlert(SensorData data) {

        var existingAlert =
                alertRepository
                        .findFirstByDeviceIdAndAlertTypeAndActiveTrue(
                                data.getDeviceId(),
                                "HIGH_PM25"
                        );

        // Prevent repeated alerts
        if (existingAlert.isPresent()) {
            return;
        }

        // Create Alert without builder()
        Alert alert = new Alert();

        alert.setDeviceId(data.getDeviceId());

        alert.setAlertType("HIGH_PM25");

        alert.setMessage(
                "High PM2.5 level detected: "
                + data.getPm25()
                + " µg/m³"
        );

        alert.setPm25(data.getPm25());

        alert.setTimestamp(LocalDateTime.now());

        alert.setActive(true);

        alertRepository.save(alert);

        System.out.println(
                "ALERT CREATED for device "
                + data.getDeviceId()
        );
    }

    private void closeHighPmAlert(String deviceId) {

        var existingAlert =
                alertRepository
                        .findFirstByDeviceIdAndAlertTypeAndActiveTrue(
                                deviceId,
                                "HIGH_PM25"
                        );

        if (existingAlert.isPresent()) {

            Alert alert = existingAlert.get();

            alert.setActive(false);

            alertRepository.save(alert);
        }
    }

    public List<Alert> getAlerts(String deviceId) {

        return alertRepository
                .findByDeviceIdOrderByTimestampDesc(deviceId);
    }
}