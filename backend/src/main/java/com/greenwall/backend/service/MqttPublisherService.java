package com.greenwall.backend.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisherService {

    private final MqttClient mqttClient;

    @Value("${mqtt.control.topic}")
    private String controlTopic;

    @Value("${mqtt.config.topic}")
    private String configTopic;

    public MqttPublisherService(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    // ============================
    // MANUAL ON / OFF
    // ============================

    public void sendCommand(
            String deviceId,
            String command) throws Exception {

        String payload =
                "{"
                + "\"deviceId\":\"" + deviceId + "\","
                + "\"command\":\"" + command + "\""
                + "}";

        publish(controlTopic, payload);
    }


    // ============================
    // UPDATE PM2.5 THRESHOLD
    // ============================

    public void sendThreshold(
            String deviceId,
            Double threshold) throws Exception {

        String payload =
                "{"
                + "\"deviceId\":\"" + deviceId + "\","
                + "\"pm25Threshold\":" + threshold
                + "}";

        publish(configTopic, payload);
    }


    // ============================
    // COMMON MQTT PUBLISH METHOD
    // ============================

    private void publish(
            String topic,
            String payload) throws Exception {

        MqttMessage message =
                new MqttMessage(
                        payload.getBytes()
                );

        message.setQos(1);

        mqttClient.publish(
                topic,
                message
        );

        System.out.println(
                "MQTT SENT"
        );

        System.out.println(
                "Topic: " + topic
        );

        System.out.println(
                "Payload: " + payload
        );
    }
}