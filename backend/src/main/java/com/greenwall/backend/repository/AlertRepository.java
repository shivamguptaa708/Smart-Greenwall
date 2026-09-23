package com.greenwall.backend.repository;

import com.greenwall.backend.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlertRepository
        extends JpaRepository<Alert, Long> {

    List<Alert> findByDeviceIdOrderByTimestampDesc(
            String deviceId
    );

    Optional<Alert> findFirstByDeviceIdAndAlertTypeAndActiveTrue(
            String deviceId,
            String alertType
    );
}