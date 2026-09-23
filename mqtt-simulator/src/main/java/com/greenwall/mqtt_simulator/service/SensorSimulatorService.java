package com.greenwall.mqtt_simulator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenwall.mqtt_simulator.model.SensorData;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class SensorSimulatorService {

    private final MqttClient client;

    private final ObjectMapper mapper =
            new ObjectMapper();

    private final Random random =
            new Random();

    public SensorSimulatorService(MqttClient client) {
        this.client = client;
    }

    @PostConstruct
    public void startSimulation() {

        new Thread(() -> {

            while (true) {

                try {

                    SensorData data =
                            new SensorData(
                                    "GW001",
                                    10 + random.nextInt(100),
                                    20 + random.nextInt(150),
                                    30 + random.nextInt(200),
                                    24 + random.nextInt(10),
                                    45 + random.nextInt(40),
                                    LocalDateTime.now().toString()
                            );

                    String json =
                            mapper.writeValueAsString(data);

                    client.publish(
                            "greenwall/sensor/data",
                            new MqttMessage(json.getBytes())
                    );

                    System.out.println("Published:");
                    System.out.println(json);

                    Thread.sleep(1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }).start();

    }

}