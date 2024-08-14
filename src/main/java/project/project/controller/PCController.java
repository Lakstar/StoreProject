package project.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import project.project.service.*;
import project.project.service.impl.*;

import java.security.Principal;

@Controller
public class PCController {

    private final GpuService gpuServiceImpl;
    private final CpuService cpuServiceImpl;
    private final RamService ramServiceImpl;
    private final MemoryService memoryServiceImpl;
    private final PcService pcServiceImpl;
    private final UserService userServiceImpl;

    @Autowired
    public PCController(GpuService gpuServiceImpl, CpuService cpuServiceImpl, RamService ramServiceImpl, MemoryService memoryServiceImpl, PcService pcServiceImpl, UserService userServiceImpl) {
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
        return "add-pc";
    }

    @PostMapping("/add/add-pc")
    public String addPC(@Valid @ModelAttribute("pc") PcDTO pcDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pc", pcDTO);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "add-pc";
        }

        PC pc = new PC();
        pc.setName(pcDTO.getName());
        pc.setCpuEntity(cpuServiceImpl.getPartById(pcDTO.getCpuId()));
        pc.setGpuEntity(gpuServiceImpl.getPartById(pcDTO.getGpuId()));
        pc.setMemoryEntity(memoryServiceImpl.getPartById(pcDTO.getMemoryId()));
        pc.setRamEntity(ramServiceImpl.getPartById(pcDTO.getRamId()));
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
