package project.project.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.awt.*;

@Configuration
public class RestConfig {

    @Bean("genericRestClient")
    public RestClient restClient(){
        return RestClient.create();
    }

    @Bean("monitorsRestClient")
    public RestClient monitorRestClient(MonitorApiConfig monitorApiConfig){
        return RestClient
                .builder()
                .baseUrl(monitorApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
