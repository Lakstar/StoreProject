package project.project.service;

import org.springframework.ui.Model;
import project.project.model.entity.PC;

import java.util.List;

public interface PcService {
    List<PC> getAllPCs();
    void savePC(PC pc);
    Model getData(Model model);
}
