package com.fssilva.pucminasposback.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceOrderServiceDto {

    String providerId;
    String orderId;
    Devices device;

}
