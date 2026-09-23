package com.greenwall.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenwall.backend.dto.SensorDTO;

import jakarta.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.MqttClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqttSubscriberService {

    private final MqttClient mqttClient;
    private final SensorService sensorService;

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @Value("${mqtt.sensor.topic}")
    private String sensorTopic;

    public MqttSubscriberService(
            MqttClient mqttClient,
            SensorService sensorService) {

        this.mqttClient = mqttClient;
        this.sensorService = sensorService;
    }

    @PostConstruct
    public void subscribe() throws Exception {

        mqttClient.subscribe(
                sensorTopic,
                (topic, message) -> {

                    try {

                        String payload =
                                new String(
                                        message.getPayload()
                                );

                        System.out.println(
                                "MQTT SENSOR DATA: "
                                + payload
                        );

                        SensorDTO dto =
                                objectMapper.readValue(
                                        payload,
                                        SensorDTO.class
                                );

                        sensorService.saveSensorData(dto);

                    } catch (Exception e) {

                        System.err.println(
                                "Error processing MQTT data: "
                                + e.getMessage()
                        );
                    }
                }
        );

        System.out.println(
                "Subscribed to: "
                + sensorTopic
        );
    }
}