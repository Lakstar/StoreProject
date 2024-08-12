package project.project.service;

import project.project.model.dto.CreateMonitorDTO;
import project.project.model.dto.MonitorDTO;

import java.util.List;

public interface MonitorService {
    public void createMonitor(CreateMonitorDTO createMonitorDTO);
    public MonitorDTO getMonitorById(Long id);

    public List<MonitorDTO> getAllMonitors();
    public void deleteMonitor(Long id);

    MonitorDTO updateMonitor(Long id, CreateMonitorDTO updateMonitorDTO);
}
