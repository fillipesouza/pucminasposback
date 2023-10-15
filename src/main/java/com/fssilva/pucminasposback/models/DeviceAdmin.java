package com.fssilva.pucminasposback.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName="deviceadmin")
public class DeviceAdmin {

    private String customerId;

    @DynamoDBHashKey(attributeName="deviceId")
    private String deviceId;

    @DynamoDBAttribute
    private String providerId;

    @DynamoDBAttribute
    private String providerName;

    @DynamoDBAttribute
    private String providerBusinessNumber;

    @DynamoDBAttribute
    private Date installationTime;

    @DynamoDBAttribute
    private String customerName;

    @DynamoDBAttribute
    private String deviceAlias;
    @DynamoDBAttribute
    private String deviceBrand;
    @DynamoDBAttribute
    private String deviceType;
    @DynamoDBAttribute
    private String status;




}
