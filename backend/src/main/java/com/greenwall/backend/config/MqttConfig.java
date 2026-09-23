package com.greenwall.backend.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class MqttConfig {

    @Value("${mqtt.broker}")
    private String broker;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Bean
    public MqttClient mqttClient() throws Exception {

        String clientId =
                "greenwall-backend-" +
                UUID.randomUUID();

        MqttClient client =
                new MqttClient(broker, clientId);

        MqttConnectOptions options =
                new MqttConnectOptions();

        options.setUserName(username);
        options.setPassword(password.toCharArray());

        options.setAutomaticReconnect(true);
        options.setCleanSession(true);

        client.connect(options);

        System.out.println(
                "Connected to HiveMQ Cloud"
        );

        return client;
    }
}