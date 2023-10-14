package com.fssilva.pucminasposback.controllers;

import com.fssilva.pucminasposback.models.DeviceAdmin;
import com.fssilva.pucminasposback.models.DeviceResponseDto;
import com.fssilva.pucminasposback.models.DeviceOrderServiceDto;
import com.fssilva.pucminasposback.models.Devices;
import com.fssilva.pucminasposback.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/devices")
public class DeviceController {


    @Autowired
    DeviceService deviceService;

    @GetMapping("/{deviceId}")
    public List<DeviceResponseDto> getDeviceReadData(@PathVariable("deviceId") String deviceId) throws Exception {


        List<Devices> deviceList = deviceService.findRepliesByDeviceOnlyPostedWithinOneWeek(deviceId);
        return deviceList.stream().map(DeviceResponseDto::convertFromDevice).collect(Collectors.toList());
    }


    @GetMapping("/summary/{customerId}")
    public List<DeviceResponseDto> getDeviceSummaryStatusByCustomer(@PathVariable("customerId") String customerId) throws Exception {


        List<Devices> deviceList = deviceService.findRepliesPostedWithinOneDay(customerId);
        return deviceList.stream().map(DeviceResponseDto::convertFromDevice).collect(Collectors.toList());
    }


    @GetMapping("/customer/{customerId}")
    public List<DeviceAdmin> getDeviceAdminStatusByCustomer(@PathVariable("customerId") String customerId) throws Exception {


        return deviceService.findDeviceAdminDataByCustomer(customerId);
    }

    @PostMapping
    public void saveDevice(@RequestBody DeviceOrderServiceDto deviceOrderServiceDto) throws Exception {


        deviceService.writeDevicesIntoQueue(deviceOrderServiceDto);
    }


}