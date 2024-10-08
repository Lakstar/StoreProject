package project.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.project.model.dto.CreateMonitorDTO;
import project.project.model.dto.MonitorDTO;
import project.project.model.dto.RamDTO;
import project.project.model.enums.RamSizes;
import project.project.model.enums.RamType;
import project.project.service.MonitorService;
import project.project.service.impl.MonitorServiceImpl;

import java.util.List;

@Controller
public class MonitorController {

    private final MonitorService monitorService;

    @Autowired
    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @GetMapping("/add/add-monitor")
    public String showAddMonitorForm(Model model) {
        model.addAttribute("monitorData", new CreateMonitorDTO());
        return "add-monitor";
    }

    @PostMapping("/delete/monitor/{id}")
    public String deleteMonitor(@PathVariable("id") Long id) {
        monitorService.deleteMonitor(id);
        return "redirect:/view/monitors";
    }

    @GetMapping("/view/monitors")
    public String showMonitors(Model model) {
        List<MonitorDTO> monitors = monitorService.getAllMonitors();
        model.addAttribute("monitors", monitors);
        return "monitor-control";
    }

    @GetMapping("/edit/monitor/{id}")
    public String showEditMonitorForm(@PathVariable("id") Long id, Model model) {
        MonitorDTO monitor = monitorService.getMonitorById(id);
        model.addAttribute("monitorData", monitor);
        return "edit-monitor";
    }

    @PostMapping("/edit/monitor/{id}")
    public String updateMonitor(@PathVariable("id") Long id,
                                @ModelAttribute("monitorData") CreateMonitorDTO updateMonitorDTO) {
        monitorService.updateMonitor(id, updateMonitorDTO);
        return "redirect:/view/monitors";
    }
}
