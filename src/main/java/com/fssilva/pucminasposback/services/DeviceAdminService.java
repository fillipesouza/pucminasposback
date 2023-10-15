package com.fssilva.pucminasposback.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fssilva.pucminasposback.models.DeviceAdmin;
import com.fssilva.pucminasposback.models.DeviceOrderServiceDto;
import com.fssilva.pucminasposback.models.Devices;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DeviceAdminService {

    public DeviceAdmin getDeviceAdmin(String deviceId) throws Exception {
        AmazonDynamoDB dynamoDBClient = new AmazonDynamoDBClient();
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);
        return mapper.load(DeviceAdmin.class, deviceId);

    }

    public DeviceAdmin edit(String deviceId, DeviceAdmin deviceAdmin) throws Exception {
        AmazonDynamoDB dynamoDBClient = new AmazonDynamoDBClient();
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);
        DeviceAdmin savedDeviceAdmin = mapper.load(DeviceAdmin.class, deviceId);
        savedDeviceAdmin.setDeviceAlias(deviceAdmin.getDeviceAlias());
        savedDeviceAdmin.setDeviceBrand(deviceAdmin.getDeviceBrand());
        savedDeviceAdmin.setDeviceType(deviceAdmin.getDeviceType());
        savedDeviceAdmin.setCustomerName(deviceAdmin.getCustomerName());
        savedDeviceAdmin.setCustomerId(deviceAdmin.getCustomerId());
        savedDeviceAdmin.setProviderBusinessNumber(deviceAdmin.getProviderBusinessNumber());
        savedDeviceAdmin.setProviderId(deviceAdmin.getProviderId());
        savedDeviceAdmin.setProviderName(deviceAdmin.getProviderName());
        savedDeviceAdmin.setInstallationTime(deviceAdmin.getInstallationTime());
        mapper.save(savedDeviceAdmin);
        return mapper.load(DeviceAdmin.class, deviceId);
    }

    public void saveDeviceAdmin(DeviceAdmin deviceAdmin) throws Exception {
        AmazonDynamoDB dynamoDBClient = new AmazonDynamoDBClient();
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);
        deviceAdmin.setDeviceId(UUID.randomUUID().toString());
        deviceAdmin.setInstallationTime(new Date());
        mapper.save(deviceAdmin);

    }
}
