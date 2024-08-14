package project.project.service;

import org.springframework.ui.Model;
import project.project.model.entity.PC;

import java.util.List;

public interface PcService {
    public List<PC> getAllPCs();
    public void savePC(PC pc);
    public Model getData(Model model);
}
