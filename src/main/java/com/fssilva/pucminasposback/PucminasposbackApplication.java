package com.fssilva.pucminasposback;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fssilva.pucminasposback.models.Devices;
import com.fssilva.pucminasposback.services.DeviceService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class PucminasposbackApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PucminasposbackApplication.class, args);

/*
		DeviceService deviceService = new DeviceService();
		List<Devices> result = deviceService.findRepliesPostedWithinTimePeriod("test");
		System.out.println(result);
		for(Devices d: result){
			System.out.println(d);
		}
		*/


		//AmazonDynamoDB dynamoDBClient = new AmazonDynamoDBClient();
		//DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);
		//Devices device = mapper.load(Devices.class, "test", 0);
		//System.out.println(device);


	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:3000", "https://g1j5wrxrz8.execute-api.us-east-1.amazonaws.com")
						.allowedHeaders("*")
						.allowedMethods("OPTIONS","GET", "POST", "PUT", "DELETE", "HEAD")
						.allowCredentials(true)
						.maxAge(-1);
			}
		};
	}

}
