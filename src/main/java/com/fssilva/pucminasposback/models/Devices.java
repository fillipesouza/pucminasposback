package com.fssilva.pucminasposback.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName="devices")
public class Devices {


    @DynamoDBHashKey(attributeName="customerId")
    private String customerId;

    @DynamoDBRangeKey(attributeName="sampleTime")
    private long sampleTime;


    @DynamoDBAttribute
    private String deviceId;

    @DynamoDBAttribute
    private String deviceAlias;
    @DynamoDBAttribute
    private String deviceType;
    @DynamoDBAttribute
    private double measurementValue;




}
