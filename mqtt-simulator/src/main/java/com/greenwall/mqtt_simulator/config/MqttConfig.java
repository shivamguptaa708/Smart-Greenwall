package com.greenwall.mqtt_simulator.config;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

        MqttClient client =
                new MqttClient(
                        broker,
                        MqttClient.generateClientId());

        MqttConnectOptions options =
                new MqttConnectOptions();

        options.setUserName(username);
        options.setPassword(password.toCharArray());

        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(30);
        options.setKeepAliveInterval(60);

        client.connect(options);

        return client;
    }

}