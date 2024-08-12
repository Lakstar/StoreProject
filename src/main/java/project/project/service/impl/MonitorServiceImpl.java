package project.project.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import project.project.config.MonitorApiConfig;
import project.project.model.dto.CreateMonitorDTO;
import project.project.model.dto.MonitorDTO;
import project.project.service.MonitorService;

import java.util.Collections;
import java.util.List;

@Service
public class MonitorServiceImpl implements MonitorService {

    private final RestClient monitorRestClient;
    private final String baseUrl;

    public MonitorServiceImpl(@Qualifier("monitorsRestClient") RestClient monitorRestClient, MonitorApiConfig monitorApiConfig) {
        this.monitorRestClient = monitorRestClient;
        this.baseUrl = monitorApiConfig.getBaseUrl();
    }

    @Override
    public void createMonitor(CreateMonitorDTO createMonitorDTO) {
        monitorRestClient
                .post()
                .uri("/monitors")
                .body(createMonitorDTO)
                .retrieve();
    }

    @Override
    public MonitorDTO getMonitorById(Long id) {
        return monitorRestClient
                .get()
                .uri("/monitors/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(MonitorDTO.class);
    }

    @Override
    public List<MonitorDTO> getAllMonitors() {
        return monitorRestClient
                .get()
                .uri("/monitors")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<MonitorDTO>>() {});
    }

    @Override
    public void deleteMonitor(Long id) {
        monitorRestClient
                .delete()
                .uri("/monitors/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public MonitorDTO updateMonitor(Long id, CreateMonitorDTO updateMonitorDTO) {
        return monitorRestClient
                .put()
                .uri("/monitors/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .body(updateMonitorDTO)
                .retrieve()
                .body(MonitorDTO.class);
    }
}
