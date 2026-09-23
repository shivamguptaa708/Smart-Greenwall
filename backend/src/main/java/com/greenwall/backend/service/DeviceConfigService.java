package com.greenwall.backend.service;

import com.greenwall.backend.entity.DeviceConfig;
import com.greenwall.backend.repository.DeviceConfigRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeviceConfigService {

    private final DeviceConfigRepository repository;
    private final MqttPublisherService mqttPublisherService;

    public DeviceConfigService(
            DeviceConfigRepository repository,
            MqttPublisherService mqttPublisherService) {

        this.repository = repository;
        this.mqttPublisherService = mqttPublisherService;
    }

    // ============================
    // UPDATE THRESHOLD
    // ============================

    public DeviceConfig updateThreshold(
            String deviceId,
            Double threshold) throws Exception {

        // Validate threshold
        if (threshold == null || threshold < 0) {

            throw new IllegalArgumentException(
                    "Threshold must be greater than or equal to 0"
            );
        }

        // Find existing configuration
        // If not found, create a new one
        DeviceConfig config =
                repository.findByDeviceId(deviceId)
                        .orElseGet(() -> {

                            DeviceConfig newConfig =
                                    new DeviceConfig();

                            newConfig.setDeviceId(deviceId);

                            return newConfig;
                        });

        // Update threshold
        config.setPm25Threshold(threshold);

        // Update time
        config.setUpdatedAt(
                LocalDateTime.now()
        );

        // Save configuration in NeonDB
        DeviceConfig saved =
                repository.save(config);

        // Send new threshold to ESP32
        mqttPublisherService.sendThreshold(
                deviceId,
                threshold
        );

        return saved;
    }


    // ============================
    // GET CURRENT CONFIGURATION
    // ============================

    public DeviceConfig getConfig(
            String deviceId) {

        return repository
                .findByDeviceId(deviceId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Device configuration not found"
                        )
                );
    }
}