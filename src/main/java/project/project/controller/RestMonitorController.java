package project.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.project.model.dto.CreateMonitorDTO;
import project.project.model.dto.MonitorDTO;
import project.project.service.MonitorService;
import project.project.service.impl.MonitorServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/monitors")
public class RestMonitorController {

    private final MonitorService monitorService;

    @Autowired
    public RestMonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @PostMapping
    public ResponseEntity<Void> addMonitor(@RequestBody CreateMonitorDTO createMonitorDTO) {
        monitorService.createMonitor(createMonitorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonitorDTO> getMonitor(@PathVariable("id") Long id) {
        MonitorDTO monitor = monitorService.getMonitorById(id);
        return ResponseEntity.ok(monitor);
    }

    @GetMapping
    public ResponseEntity<List<MonitorDTO>> getAllMonitors() {
        List<MonitorDTO> monitors = monitorService.getAllMonitors();
        return ResponseEntity.ok(monitors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonitor(@PathVariable("id") Long id) {
        monitorService.deleteMonitor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonitorDTO> updateMonitor(@PathVariable("id") Long id, @RequestBody CreateMonitorDTO updateMonitorDTO) {
        MonitorDTO updatedMonitor = monitorService.updateMonitor(id, updateMonitorDTO);
        return ResponseEntity.ok(updatedMonitor);
    }
}
