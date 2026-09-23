package com.greenwall.backend.repository;

import com.greenwall.backend.entity.DeviceConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceConfigRepository
        extends JpaRepository<DeviceConfig, Long> {

    Optional<DeviceConfig> findByDeviceId(String deviceId);
}