package com.greenwall.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManualCommandDTO {

    private String deviceId;

    private String command;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
}