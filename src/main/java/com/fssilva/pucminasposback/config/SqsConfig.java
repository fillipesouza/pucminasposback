package com.fssilva.pucminasposback.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.regions.Region;

public class SqsConfig {
    private final int BATCH_SIZE = 10;
    private final String queueEndpoint;

    public SqsConfig(@Value("${queue.endpoint}") String queueEndpoint) {
        this.queueEndpoint = queueEndpoint;
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync(@Value("${spring.profiles.active}") String profile) {
        if (profile.equals("dev")) {
            return AmazonSQSAsyncClientBuilder
                    .standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(queueEndpoint,
                            Region.US_EAST_1.id()))
                    .build();
        } else {
            return AmazonSQSAsyncClientBuilder
                    .standard()
                    .withRegion(Region.US_EAST_1.id())
                    .build();
        }
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);

    }
}