package com.greenwall.backend.service;

import com.greenwall.backend.dto.SensorDTO;
import com.greenwall.backend.entity.SensorData;
import com.greenwall.backend.repository.SensorRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    private final AlertService alertService;

    public SensorService(
            SensorRepository sensorRepository,
            AlertService alertService) {

        this.sensorRepository = sensorRepository;
        this.alertService = alertService;
    }

    public SensorData saveSensorData(SensorDTO dto) {

        LocalDateTime timestamp;

        if (dto.getTimestamp() != null) {
            timestamp = LocalDateTime.parse(dto.getTimestamp());
        } else {
            timestamp = LocalDateTime.now();
        }

        // Create object without builder()
        SensorData data = new SensorData();

        data.setDeviceId(dto.getDeviceId());
        data.setPm1(dto.getPm1());
        data.setPm25(dto.getPm25());
        data.setPm10(dto.getPm10());
        data.setTemperature(dto.getTemperature());
        data.setHumidity(dto.getHumidity());
        data.setTimestamp(timestamp);

        // Save sensor data
        SensorData saved = sensorRepository.save(data);

        // Generate alert
        alertService.checkPollutionAlert(saved);

        return saved;
    }

    public List<SensorData> getLatestData(String deviceId) {

        return sensorRepository
                .findByDeviceIdOrderByTimestampDesc(deviceId);
    }

    public List<SensorData> getHistoricalData(
            String deviceId,
            LocalDateTime start,
            LocalDateTime end) {

        return sensorRepository
                .findByDeviceIdAndTimestampBetweenOrderByTimestampAsc(
                        deviceId,
                        start,
                        end
                );
    }
}