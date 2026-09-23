package com.greenwall.backend.repository;

import com.greenwall.backend.entity.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorRepository
        extends JpaRepository<SensorData, Long> {

    List<SensorData> findByDeviceIdOrderByTimestampDesc(
            String deviceId
    );

    List<SensorData> findByDeviceIdAndTimestampBetweenOrderByTimestampAsc(
            String deviceId,
            LocalDateTime start,
            LocalDateTime end
    );
}