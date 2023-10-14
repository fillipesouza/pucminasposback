package com.fssilva.pucminasposback.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceResponseDto {

    private String deviceId;
    private String customerId;
    private String operatorId;

    private long sampleTime;


    private String deviceAlias;
    private String deviceType;
    private double measurementValue;

    public static DeviceResponseDto convertFromDevice(Devices device){
        return DeviceResponseDto.builder()
                .deviceId(device.getDeviceId())
                .deviceAlias(device.getDeviceAlias())
                .deviceType(device.getDeviceType())
                .customerId(device.getCustomerId())
                .operatorId("")
                .measurementValue(device.getMeasurementValue())
                .sampleTime(device.getSampleTime())
                .build();
    }

}
