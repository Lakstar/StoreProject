package project.project.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.project.model.dto.PcDTO;
import project.project.model.entity.PC;
import project.project.model.entity.UserEntity;
import project.project.repository.CPURepository;
import project.project.repository.GPURepository;
import project.project.repository.MemoryRepository;
import project.project.repository.RAMRepository;
import project.project.service.impl.*;

import java.security.Principal;

@Controller
public class PCController {

    private final GpuServiceImpl gpuServiceImpl;
    private final CpuServiceImpl cpuServiceImpl;
    private final RamServiceImpl ramServiceImpl;
    private final MemoryServiceImpl memoryServiceImpl;
    private final PcServiceImpl pcServiceImpl;
    private final UserServiceImpl userServiceImpl;


    public PCController(CPURepository cpuRepository, GPURepository gpuRepository, MemoryRepository memoryRepository, RAMRepository ramRepository, GpuServiceImpl gpuServiceImpl, CpuServiceImpl cpuServiceImpl, RamServiceImpl ramServiceImpl, MemoryServiceImpl memoryServiceImpl, PcServiceImpl pcServiceImpl, UserServiceImpl userServiceImpl) {
        this.gpuServiceImpl = gpuServiceImpl;
        this.cpuServiceImpl = cpuServiceImpl;
        this.ramServiceImpl = ramServiceImpl;
        this.memoryServiceImpl = memoryServiceImpl;
        this.pcServiceImpl = pcServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/add/add-pc")
    public String showAddPcForm(Model model) {
        model= pcServiceImpl.getData(model);
        model.addAttribute("pc", new PcDTO());
        return "addLaptop";
    }

    @PostMapping("/add/add-pc")
    public String addPC(@Valid @ModelAttribute("pc") PcDTO pcDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pc", pcDTO);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "addLaptop";
        }

        PC pc = new PC();
        pc.setName(pcDTO.getName());
        pc.setCpu(cpuServiceImpl.getPartById(pcDTO.getCpuId()));
        pc.setGpu(gpuServiceImpl.getPartById(pcDTO.getGpuId()));
        pc.setMemory(memoryServiceImpl.getPartById(pcDTO.getMemoryId()));
        pc.setRam(ramServiceImpl.getPartById(pcDTO.getRamId()));
        pc.setPcType(pcDTO.getPcType());
        pc.setPrice(pcDTO.getPrice());

        pcServiceImpl.savePC(pc);

        return "redirect:/add";
    }

    @PostMapping("/shop/buy")
    public String buyPC(@RequestParam("pcId") Long pcId, Principal principal) {
        String username = principal.getName();
        UserEntity user = userServiceImpl.getUserByUsername(username);
        if (user != null) {
            userServiceImpl.addPCToUserOrders(user.getId(), pcId);
        }
        return "redirect:/";
    }
}
