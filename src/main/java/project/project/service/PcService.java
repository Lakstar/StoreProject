package project.project.service;

import project.project.model.entity.PC;

import java.util.List;

public interface PcService {
    public List<PC> getAllPCs();
    public void savePC(PC pc);
}
