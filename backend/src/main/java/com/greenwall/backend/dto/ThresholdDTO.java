package com.greenwall.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThresholdDTO {

    private Double pm25Threshold;

	public Double getPm25Threshold() {
		return pm25Threshold;
	}

	public void setPm25Threshold(Double pm25Threshold) {
		this.pm25Threshold = pm25Threshold;
	}
}