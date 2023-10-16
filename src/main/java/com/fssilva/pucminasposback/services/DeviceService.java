package com.fssilva.pucminasposback.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
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
public class DeviceService {

    public List<Devices> findRepliesPostedWithinOneDay(String customerId) throws Exception {
        //AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        AmazonDynamoDB dynamoDBClient = new AmazonDynamoDBClient();
        //DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);

        String partitionKey = customerId;

        System.out.println(
                "FindRepliesPostedWithinTimePeriod: Find replies for thread Message = 'DynamoDB Thread 2' posted within a period.");
        long startDateMilli = (new Date()).getTime() - ( 24L * 60L * 60L * 1000L); // Two
        // weeks
        // ago.
        long endDateMilli = (new Date()).getTime(); // One
        // week
        // ago.
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String startDate = dateFormatter.format(startDateMilli);
        String endDate = dateFormatter.format(endDateMilli);

        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(partitionKey));
        //eav.put(":val2", new AttributeValue().withS(String.valueOf(startDateMilli)));
        //eav.put(":val3", new AttributeValue().withS(String.valueOf(endDateMilli)));

        DynamoDBQueryExpression<Devices> queryExpression = new DynamoDBQueryExpression<Devices>()
                .withKeyConditionExpression("customerId = :val1")
                //.withKeyConditionExpression("customerId = :val1")
                .withExpressionAttributeValues(eav);

        List<Devices> betweenReplies = mapper.query(Devices.class, queryExpression);

        for (Devices device : betweenReplies) {
            System.out.format("Id=%s, Device=%s, PostedBy=%s %n, PostedDateTime=%s %n", device.getDeviceId(),
                    device.getDeviceAlias(), device.getCustomerId(), device.getSampleTime());
        }

        return betweenReplies;

    }

    public List<Devices> findRepliesByDeviceOnlyPostedWithinOneWeek(String deviceId) throws Exception {
        //AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        AmazonDynamoDB dynamoDBClient = new AmazonDynamoDBClient();
        //DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);


        System.out.println(
                "FindRepliesPostedWithinTimePeriod: Find replies for thread Message = 'DynamoDB Thread 2' posted within a period.");
        long startDateMilli = (new Date()).getTime() - (7* 24L * 60L * 60L * 1000L); // 1 week ago

        long endDateMilli = (new Date()).getTime(); // One

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String startDate = dateFormatter.format(startDateMilli);
        String endDate = dateFormatter.format(endDateMilli);

        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(deviceId));
        eav.put(":val2", new AttributeValue().withS(startDate));
        eav.put(":val3", new AttributeValue().withS(endDate));

        DynamoDBQueryExpression<Devices> queryExpression = new DynamoDBQueryExpression<Devices>()
                .withKeyConditionExpression("deviceId = :val1 and sampleTime between :val2 and :val3")
                //.withKeyConditionExpression("customerId = :val1")
                .withExpressionAttributeValues(eav);

        List<Devices> betweenReplies = mapper.query(Devices.class, queryExpression);

        for (Devices device : betweenReplies) {
            System.out.format("Id=%s, Device=%s, PostedBy=%s %n, PostedDateTime=%s %n", device.getDeviceId(),
                    device.getDeviceAlias(), device.getCustomerId(), device.getSampleTime());
        }

        return betweenReplies;

    }


    public List<DeviceAdmin> findDeviceAdminDataByCustomer(String customerId) throws Exception {
        //AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        AmazonDynamoDB dynamoDBClient = new AmazonDynamoDBClient();
        //DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(customerId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        scanExpression.setFilterExpression("customerId = :val1");
        scanExpression.setExpressionAttributeValues(eav);


        List<DeviceAdmin> administratedDevices = mapper.scan(DeviceAdmin.class, scanExpression);


        return administratedDevices;

    }


    public void writeDevicesIntoQueue(DeviceOrderServiceDto deviceOrderServiceDto) throws Exception {
        SqsClient sqs = SqsClient.create();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(deviceOrderServiceDto);
        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl("https://sqs.us-east-1.amazonaws.com/303470822462/mgsense")
                .messageBody(json)
                .messageAttributes(Map.of(
                        "Title", MessageAttributeValue.builder().dataType("String").stringValue("DeviceCreate")
                        .build()))
                .delaySeconds(5)
                .build();
        sqs.sendMessage(sendMessageRequest);



    }
}
