package com.greenwall.backend.dto;

import lombok.Data;

@Data
public class SensorDTO {

    private String deviceId;

	private Double pm1;

    private Double pm25;

    private Double pm10;

    private Double temperature;

    private Double humidity;

    private String timestamp;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
    public Double getPm1() {
		return pm1;
	}

	public void setPm1(Double pm1) {
		this.pm1 = pm1;
	}

	public Double getPm25() {
		return pm25;
	}

	public void setPm25(Double pm25) {
		this.pm25 = pm25;
	}

	public Double getPm10() {
		return pm10;
	}

	public void setPm10(Double pm10) {
		this.pm10 = pm10;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}