package com.greenwall.mqtt_simulator.model;

public class SensorData {

    private String deviceId;
    private double pm1;
    private double pm25;
    private double pm10;
    private double temperature;
    private double humidity;
    private String timestamp;

    public SensorData() {
    }

    public SensorData(String deviceId,
    		          double pm1,
                      double pm25,
                      double pm10,
                      double temperature,
                      double humidity,
                      String timestamp) {

        this.deviceId = deviceId;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.temperature = temperature;
        this.humidity = humidity;
        this.timestamp = timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getPm1() {
		return pm1;
	}

	public void setPm1(double pm1) {
		this.pm1 = pm1;
	}

	public double getPm25() {
        return pm25;
    }

    public void setPm25(double pm25) {
        this.pm25 = pm25;
    }

    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}